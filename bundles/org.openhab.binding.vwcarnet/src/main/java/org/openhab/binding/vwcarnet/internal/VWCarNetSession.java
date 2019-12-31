/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.vwcarnet.internal;

import static org.openhab.binding.vwcarnet.internal.VWCarNetBindingConstants.*;

import java.math.BigDecimal;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.Fields;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetAlarmsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetBroadbandConnectionsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetClimatesJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetDoorWindowsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetInstallationsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetInstallationsJSON.Owainstallation;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetSmartLockJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetSmartLocksJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetSmartPlugsJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetThingJSON;
import org.openhab.binding.vwcarnet.internal.model.VWCarNetUserPresencesJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * This class performs the communication with VWCarNet My Pages.
 *
 * @author Jarle Hjortland - Initial contribution
 * @author Jan Gustafsson - Re-design and support for several sites and update to new VWCarNet API
 *
 */
@NonNullByDefault
public class VWCarNetSession {

    @NonNullByDefault
    private final class VWCarNetInstallation {
        private @Nullable String installationName;
        private @Nullable BigDecimal installationId;
        private @Nullable String pinCode;

        public @Nullable String getPinCode() {
            return pinCode;
        }

        public void setPinCode(@Nullable String pinCode) {
            this.pinCode = pinCode;
        }

        public VWCarNetInstallation() {
        }

        public @Nullable BigDecimal getInstallationId() {
            return installationId;
        }

        public @Nullable String getInstallationName() {
            return installationName;
        }

        public void setInstallationId(@Nullable BigDecimal installationId) {
            this.installationId = installationId;
        }

        public void setInstallationName(@Nullable String installationName) {
            this.installationName = installationName;
        }
    }

    private final HashMap<String, VWCarNetThingJSON> verisureThings = new HashMap<String, VWCarNetThingJSON>();
    private final Logger logger = LoggerFactory.getLogger(VWCarNetSession.class);
    private final Gson gson = new GsonBuilder().create();
    private final List<DeviceStatusListener> deviceStatusListeners = new CopyOnWriteArrayList<>();
    private final Hashtable<@Nullable BigDecimal, @Nullable VWCarNetInstallation> verisureInstallations = new Hashtable<@Nullable BigDecimal, @Nullable VWCarNetInstallation>();
    private static final List<String> APISERVERLIST = new ArrayList<>(
            Arrays.asList("https://m-api01.verisure.com", "https://m-api02.verisure.com"));
    private int apiServerInUseIndex = 0;
    private String apiServerInUse = APISERVERLIST.get(apiServerInUseIndex);
    private boolean areWeLoggedOut = true;
    private String authstring = "";
    private @Nullable String csrf;
    private @Nullable String xCsrfToken;
    private @Nullable String referer;
    private @Nullable String authRefUrl;
    private @Nullable String guestLanguageId;
    private @Nullable String pinCode;
    private HttpClient httpClient;
    private @Nullable String userName = "";
    private String passwordName = "vid";
    private @Nullable String password = "";

    public VWCarNetSession(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void initialize(@Nullable String userName, @Nullable String password, @Nullable String pinCode) {
        logger.debug("VWCarNetSession:initialize");
        this.pinCode = pinCode;
        this.userName = userName;
        this.password = password;
        // Try to login to VWCarNet
        if (logIn()) {
            getInstallations();
        } else {
            logger.warn("Failed to login to VWCarNet!");
        }
    }

    public boolean refresh() {
        logger.debug("VWCarNetSession:refresh");
        int statusCode = areWeLoggedIn();
        if (statusCode == 503) {
            return false;
        } else if (!areWeLoggedOut && (statusCode == 1)) {
            updateStatus();
            return true;
        } else {
            if (logIn()) {
                updateStatus();
                areWeLoggedOut = false;
                return true;
            } else {
                areWeLoggedOut = true;
                return false;
            }
        }
    }

    public int sendCommand(String url, String data, @Nullable BigDecimal installationId) {
        logger.debug("Sending command with URL {} and data {}", url, data);
        configureInstallationInstance(installationId);
        int httpResultCode = setSessionCookieAuthLogin();
        if (httpResultCode == HttpStatus.OK_200) {
            return postVWCarNetAPI(url, data);
        } else {
            return httpResultCode;
        }
    }

    public boolean unregisterDeviceStatusListener(DeviceStatusListener deviceStatusListener) {
        logger.debug("unregisterDeviceStatusListener for listener {}", deviceStatusListener);
        return deviceStatusListeners.remove(deviceStatusListener);
    }

    public boolean registerDeviceStatusListener(DeviceStatusListener deviceStatusListener) {
        logger.debug("registerDeviceStatusListener for listener {}", deviceStatusListener);
        return deviceStatusListeners.add(deviceStatusListener);
    }

    public void dispose() {
    }

    public @Nullable VWCarNetThingJSON getVWCarNetThing(String deviceId) {
        return verisureThings.get(deviceId);
    }

    public HashMap<String, VWCarNetThingJSON> getVWCarNetThings() {
        return verisureThings;
    }

    public @Nullable String getCsrf() {
        return csrf;
    }

    public @Nullable String getPinCode() {
        return pinCode;
    }

    public String getApiServerInUse() {
        return apiServerInUse;
    }

    public void setApiServerInUse(String apiServerInUse) {
        this.apiServerInUse = apiServerInUse;
    }

    public String getNextApiServer() {
        apiServerInUseIndex++;
        if (apiServerInUseIndex > (APISERVERLIST.size() - 1)) {
            apiServerInUseIndex = 0;
        }
        return APISERVERLIST.get(apiServerInUseIndex);
    }

    public void configureInstallationInstance(@Nullable BigDecimal installationId) {
        logger.debug("Attempting to fetch CSRF and configure installation instance");
        try {
            csrf = getCsrfToken(installationId);
            logger.debug("Got CSRF: {}", csrf);
            // Set installation
            String url = SET_INSTALLATION + installationId.toString();
            logger.debug("Set installation URL: {}", url);
            httpClient.GET(url);
        } catch (ExecutionException e) {
            logger.warn("Caught ExecutionException {}", e);
        } catch (InterruptedException e) {
            logger.warn("Caught InterruptedException {}", e);
        } catch (TimeoutException e) {
            logger.warn("Caught TimeoutException {}", e);
        }
    }

    public @Nullable String getCsrfToken(@Nullable BigDecimal installationId) {
        String subString = null;
        String source = null;
        String url = SETTINGS + installationId.toString();
        logger.debug("Settings URL: {}", url);
        try {
            ContentResponse resp = httpClient.GET(url);
            source = resp.getContentAsString();
            logger.trace("{} html: {}", url, source);
        } catch (ExecutionException e) {
            logger.warn("Caught ExecutionException {}", e);
            return null;
        } catch (InterruptedException e) {
            logger.warn("Caught InterruptedException {}", e);
            return null;
        } catch (TimeoutException e) {
            logger.warn("Caught TimeoutException {}", e);
            return null;
        }

        try {
            int labelIndex = source.indexOf("_csrf\" value=");
            subString = source.substring(labelIndex + 14, labelIndex + 78);
            logger.debug("csrf-token: {}", subString);
        } catch (IndexOutOfBoundsException e) {
            logger.debug("Exception caught when parsing csrf {}", e);
        }
        return subString;
    }

    public @Nullable String getPinCode(@Nullable BigDecimal installationId) {
        return verisureInstallations.get(installationId).getPinCode();
    }

    private int areWeLoggedIn() {
        logger.debug("areWeLoggedIn() - Checking if we are logged in");
        String url = STATUS;
        try {
            logger.debug("Check for login status, url: {}", url);
            ContentResponse response = httpClient.newRequest(url).method(HttpMethod.GET).send();
            logger.trace("HTTP status response: {}", response.getContentAsString());
            switch (response.getStatus()) {
                case HttpStatus.OK_200:
                    if (response.getContentAsString().contains("<title>MyPages</title>")) {
                        logger.debug("Status code 200 and on MyPages!");
                        CookieStore c = httpClient.getCookieStore();
                        List<HttpCookie> cookies = c.get(URI.create("http://verisure.com"));
                        cookies.forEach(cookie -> {
                            logger.debug("Response Cookie: {}", cookie);
                            if (cookie.getName().equals(passwordName)) {
                                password = cookie.getValue();
                                logger.debug("Fetching vid {} from cookie", password);
                            }
                        });
                        return 1;
                    } else {
                        logger.debug("Not on MyPages, we need to login again!");
                        return 0;
                    }
                case HttpStatus.MOVED_TEMPORARILY_302:
                    // Redirection
                    logger.debug("Status code 302. Redirected. Probably not logged in");
                    return 0;
                case HttpStatus.INTERNAL_SERVER_ERROR_500:
                    // VWCarNet service temporarily down
                    logger.debug("Status code 500. VWCarNet service temporarily down");
                    return HttpStatus.INTERNAL_SERVER_ERROR_500;
                case HttpStatus.SERVICE_UNAVAILABLE_503:
                    // VWCarNet service temporarily down
                    logger.debug("Status code 503. VWCarNet service temporarily down");
                    return HttpStatus.SERVICE_UNAVAILABLE_503;
                default:
                    logger.info("Status code {} body {}", response.getStatus(), response.getContentAsString());
                    break;
            }
        } catch (ExecutionException e) {
            logger.warn("ExecutionException: {}", e);
        } catch (InterruptedException e) {
            logger.warn("InterruptedException: {}", e);
        } catch (TimeoutException e) {
            logger.warn("TimeoutException: {}", e);
        }
        return 0;
    }

    private @Nullable ContentResponse getVWCarNetAPI(String url) {
        logger.debug("HTTP GET: {}", url);
        ContentResponse httpResult = null;

        try {
            httpResult = httpClient.GET(url);
            logger.debug("HTTP Response ({}) Body:{}", httpResult.getStatus(),
                    httpResult.getContentAsString().replaceAll("\n+", "\n"));
            return httpResult;
        } catch (ExecutionException e) {
            logger.warn("Caught ExecutionException {} for URL string {}", e, url);
        } catch (InterruptedException e) {
            logger.warn("Caught InterruptedException {} for URL string {}", e, url);
        } catch (TimeoutException e) {
            logger.warn("Caught TimeoutException {} for URL string {}", e, url);
        }
        return httpResult;
    }

    private @Nullable ContentResponse getVWCarNetAPI(String url, Boolean headers) {
        try {
            logger.debug("getVWCarNetAPI URL: {} headers: {}", url, headers);
            URL urlEncoded = new URL(url);
            /*
             * String correctEncodedURL = urlEncoded.getProtocol() + urlEncoded.getUserInfo() + urlEncoded.getHost()
             * + urlEncoded.getPort() + urlEncoded.getPath() + URLEncoder.encode(urlEncoded.getQuery(), "UTF-8")
             * + urlEncoded.getRef();
             */
            String correctEncodedURL = url.replace(" ", "%20");
            logger.warn("Encoded URL: {}", correctEncodedURL);

            httpClient.setFollowRedirects(false);
            Request request = httpClient.newRequest(correctEncodedURL).method(HttpMethod.GET);
            if (headers) {
                request.header("accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
                request.header("content-type", "application/x-www-form-urlencoded");
                request.header("user-agent",
                        "Mozilla/5.0 (Linux; Android 6.0.1; D5803 Build/23.5.A.1.291; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.111 Mobile Safari/537.36");
            }
            logger.warn("HTTP GET Request {}.", request);
            return request.send();
        } catch (ExecutionException e) {
            logger.warn("Caught ExecutionException {}", e);
        } catch (InterruptedException e) {
            logger.warn("Caught InterruptedException {}", e);
        } catch (TimeoutException e) {
            logger.warn("Caught TimeoutException {}", e);
        } catch (RuntimeException e) {
            logger.warn("Caught RuntimeException {}", e);
        } catch (MalformedURLException e) {
            logger.warn("Caught MalformedURLException {}", e);
        }
        return null;
    }

    private @Nullable <T> T getJSONVWCarNetAPI(String url, Class<T> jsonClass) {
        T result = null;
        logger.debug("HTTP GET: {}", BASEURL + url);
        try {
            ContentResponse httpResult = httpClient.GET(BASEURL + url + "?_=" + System.currentTimeMillis());
            logger.debug("HTTP Response ({}) Body:{}", httpResult.getStatus(),
                    httpResult.getContentAsString().replaceAll("\n+", "\n"));
            if (httpResult.getStatus() == HttpStatus.OK_200) {
                result = gson.fromJson(httpResult.getContentAsString(), jsonClass);
            }
            return result;
        } catch (ExecutionException e) {
            logger.warn("Caught ExecutionException {} for URL string {}", e, url);
        } catch (InterruptedException e) {
            logger.warn("Caught InterruptedException {} for URL string {}", e, url);
        } catch (TimeoutException e) {
            logger.warn("Caught TimeoutException {} for URL string {}", e, url);
        }
        return null;
    }

    private @Nullable ContentResponse postVWCarNetAPI(String url, @Nullable Fields fields, @Nullable String referer,
            String csrf) {
        try {
            logger.debug("postVWCarNetAPI URL: {} Fields: {} Referer: {} CSRF:{}", url, fields, referer, csrf);
            Request request = httpClient.newRequest(url).method(HttpMethod.POST);

            request.header("accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            request.header("content-type", "application/x-www-form-urlencoded");
            request.header("user-agent",
                    "Mozilla/5.0 (Linux; Android 6.0.1; D5803 Build/23.5.A.1.291; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.111 Mobile Safari/537.36");
            request.header("referer", SESSION_BASE + referer);

            if (fields != null) {
                fields.forEach(f -> request.param(f.getName(), f.getValue()));
            } else {
                request.header("x-csrf-token", csrf);
            }
            logger.debug("HTTP POST Request {}.", request.toString());
            return request.send();
        } catch (ExecutionException e) {
            logger.warn("Caught ExecutionException {}", e);
        } catch (InterruptedException e) {
            logger.warn("Caught InterruptedException {}", e);
        } catch (TimeoutException e) {
            logger.warn("Caught TimeoutException {}", e);
        } catch (RuntimeException e) {
            logger.warn("Caught RuntimeException {}", e);
        }
        return null;
    }

    private @Nullable ContentResponse postJSONVWCarNetAPI(String url, @Nullable Fields fields, @Nullable String referer,
            @Nullable String xCsrf) {
        try {
            logger.debug("postJSONVWCarNetAPI URL: {} Fields: {} Referer: {} XCSRF:{}", url, fields, referer, xCsrf);
            Request request = httpClient.newRequest(url).method(HttpMethod.POST);

            request.header("accept", "application/json, text/plain, */*");
            request.header("content-type", "application/json;charset=UTF-8");
            request.header("user-agent",
                    "Mozilla/5.0 (Linux; Android 6.0.1; D5803 Build/23.5.A.1.291; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/63.0.3239.111 Mobile Safari/537.36");
            request.header("referer", referer);
            request.header("x-csrf-token", xCsrf);

            if (fields != null) {
                fields.forEach(f -> request.param(f.getName(), f.getValue()));
            }

            logger.debug("HTTP POST Request {}.", request.toString());
            return request.send();
        } catch (ExecutionException e) {
            logger.warn("Caught ExecutionException {}", e);
        } catch (InterruptedException e) {
            logger.warn("Caught InterruptedException {}", e);
        } catch (TimeoutException e) {
            logger.warn("Caught TimeoutException {}", e);
        } catch (RuntimeException e) {
            logger.warn("Caught RuntimeException {}", e);
        }
        return null;
    }

    private @Nullable <T> T postJSONVWCarNetAPI(String url, String data, Class<T> jsonClass) {
        for (int cnt = 0; cnt < APISERVERLIST.size(); cnt++) {
            ContentResponse response = null; // postVWCarNetAPI(apiServerInUse + url, data, Boolean.TRUE);
            if (response != null) {
                logger.debug("HTTP Response ({})", response.getStatus());
                if (response.getStatus() == HttpStatus.OK_200) {
                    String content = response.getContentAsString();
                    if (content.contains("\"message\":\"Request Failed") && content.contains("503")) {
                        // Maybe VWCarNet has switched PAI server in use
                        setApiServerInUse(getNextApiServer());
                    } else {
                        String contentChomped = StringUtils.chomp(content);
                        logger.trace("Response body: {}", content);
                        return gson.fromJson(contentChomped, jsonClass);
                    }
                } else {
                    logger.debug("Failed to send POST, Http status code: {}", response.getStatus());
                }
            }
        }
        return null;
    }

    private int postVWCarNetAPI(String urlString, String data) {
        String url;
        if (urlString.contains("https://mypages")) {
            url = urlString;
        } else {
            url = apiServerInUse + urlString;
        }

        for (int cnt = 0; cnt < APISERVERLIST.size(); cnt++) {
            ContentResponse response = null;// postVWCarNetAPI(url, data, Boolean.FALSE);
            if (response != null) {
                logger.debug("HTTP Response ({})", response.getStatus());
                if (response.getStatus() == HttpStatus.OK_200) {
                    String content = response.getContentAsString();
                    if (content.contains("\"message\":\"Request Failed. Code 503 from")) {
                        if (url.contains("https://mypages")) {
                            // Not an API URL
                            return HttpStatus.SERVICE_UNAVAILABLE_503;
                        } else {
                            // Maybe VWCarNet has switched API server in use
                            setApiServerInUse(getNextApiServer());
                            url = apiServerInUse + urlString;
                        }
                    } else {
                        logger.trace("Response body: {}", content);
                        return response.getStatus();
                    }
                } else {
                    logger.debug("Failed to send POST, Http status code: {}", response.getStatus());
                }
            }
        }
        return 999;
    }

    private int setSessionCookieAuthLogin() {
        // URL to set status which will give us 2 cookies with username and password used for the session
        String url = STATUS;

        try {
            ContentResponse response = httpClient.GET(url);
            logger.trace("HTTP Response ({}) Body:{}", response.getStatus(),
                    response.getContentAsString().replaceAll("\n+", "\n"));
            CookieStore c = httpClient.getCookieStore();
            List<HttpCookie> cookies = c.get(URI.create("http://verisure.com"));
            Iterator<HttpCookie> cookiesIterator = cookies.iterator();
            while (cookiesIterator.hasNext()) {
                HttpCookie theCookie = cookiesIterator.next();
                logger.debug("Response Cookie: name: {}, value: {} ", theCookie.getName(), theCookie.getValue());
                if (theCookie.getName().equals(passwordName)) {
                    password = theCookie.getValue();
                    logger.debug("Fetching vid {} from cookie", password);
                }
            }
        } catch (ExecutionException e) {
            logger.warn("ExecutionException: {}", e);
        } catch (InterruptedException e) {
            logger.warn("InterruptedException: {}", e);
        } catch (TimeoutException e) {
            logger.warn("TimeoutException: {}", e);
        }

        url = AUTH_LOGIN;
        return postVWCarNetAPI(url, "empty");
    }

    private void getInstallations() {
        logger.debug("Attempting to get all installations");

        int httpResultCode = setSessionCookieAuthLogin();
        if (httpResultCode == HttpStatus.OK_200) {
            String url = START_GRAPHQL;

            String queryQLAccountInstallations = "[{\"operationName\":\"AccountInstallations\",\"variables\":{\"email\":\""
                    + userName
                    + "\"},\"query\":\"query AccountInstallations($email: String!) {\\n  account(email: $email) {\\n    owainstallations {\\n      giid\\n      alias\\n      type\\n      subsidiary\\n      dealerId\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}]";
            Class<VWCarNetInstallationsJSON> jsonClass = VWCarNetInstallationsJSON.class;
            VWCarNetInstallationsJSON installations = postJSONVWCarNetAPI(url, queryQLAccountInstallations, jsonClass);

            if (installations == null) {
                logger.debug("Failed to get installations");
            } else {
                logger.debug("Installation: {}", installations.toString());
                if (installations.getData() != null) {
                    List<Owainstallation> owaInstList = installations.getData().getAccount().getOwainstallations();
                    Boolean pinCodesMatchInstallations = true;
                    List<String> pinCodes = null;
                    if (pinCode != null) {
                        pinCodes = Arrays.asList(pinCode.split(","));
                        if (owaInstList != null && owaInstList.size() != pinCodes.size()) {
                            logger.debug("Number of installations {} does not match number of pin codes configured {}",
                                    owaInstList.size(), pinCodes.size());
                            pinCodesMatchInstallations = false;
                        } else if (owaInstList == null) {
                            logger.warn("Failed to get VWCarNet installations");
                            return;
                        }
                    } else {
                        logger.debug("No pin-code defined for user {}", userName);
                    }
                    for (int i = 0; i < owaInstList.size(); i++) {
                        VWCarNetInstallation vInst = new VWCarNetInstallation();
                        if (owaInstList.get(i).getAlias() != null && owaInstList.get(i).getGiid() != null) {
                            vInst.setInstallationId(new BigDecimal(owaInstList.get(i).getGiid()));
                            vInst.setInstallationName(owaInstList.get(i).getAlias());
                            if (pinCode != null) {
                                if (pinCodesMatchInstallations) {
                                    vInst.setPinCode(pinCodes.get(i));
                                    logger.debug("Setting pincode {} to installation ID {}", pinCodes.get(i),
                                            owaInstList.get(i).getGiid());
                                } else {
                                    vInst.setPinCode(pinCodes.get(0));
                                    logger.debug("Setting pincode {} to installation ID {}", pinCodes.get(0),
                                            owaInstList.get(i).getGiid());
                                }
                            }
                            verisureInstallations.put(new BigDecimal(owaInstList.get(i).getGiid()), vInst);
                        } else {
                            logger.warn("Failed to get alias and/or giid");
                        }
                    }
                } else {
                    logger.warn("Failed to fetch installations: {}", installations.toString());
                }
            }
        } else {
            logger.warn("Failed to set session cookie and auth login, HTTP result code: {}", httpResultCode);
        }
    }

    private synchronized boolean logIn() {
        logger.info("Attempting to log in to https://www.portal.volkswagen-we.com");
        // Request landing page and get CSRF:
        String url = SESSION_BASE + "/portal/en_GB/web/guest/home";
        logger.debug("Login URL: {}", url);
        ContentResponse httpResponse = getVWCarNetAPI(url);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.OK_200) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }
        Document htmlDocument = Jsoup.parse(httpResponse.getContentAsString());
        Element nameInput = htmlDocument.select("meta[name=_csrf]").first();
        String csrf = nameInput.attr("content");

        // Request login page and get login URL
        url = SESSION_BASE + "portal/web/guest/home/-/csrftokenhandling/get-login-url";
        httpResponse = postVWCarNetAPI(url, null, "portal", csrf);
        String json = httpResponse.getContentAsString();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        url = jsonObject.get("loginURL").getAsJsonObject().get("path").getAsString();

        httpResponse = getVWCarNetAPI(url, Boolean.TRUE);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.MOVED_TEMPORARILY_302) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        url = httpResponse.getHeaders().get("Location");
        httpResponse = getVWCarNetAPI(url, Boolean.TRUE);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.OK_200) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        String previousUrl = url;

        // Get actual login page and get session id and ViewState
        htmlDocument = Jsoup.parse(httpResponse.getContentAsString());
        nameInput = htmlDocument.select("input[name=_csrf]").first();
        String loginCsrf = nameInput.attr("value");
        nameInput = htmlDocument.select("input[name=relayState]").first();
        String loginToken = nameInput.attr("value");
        nameInput = htmlDocument.select("input[name=hmac]").first();
        String loginHmac = nameInput.attr("value");
        Element loginForm = htmlDocument.getElementById("emailPasswordForm");
        url = AUTH_BASE + loginForm.attr("action");

        Fields fields = new Fields(true);
        fields.put("email", userName);
        fields.put("password", password);
        fields.put("relayState", loginToken);
        fields.put("hmac", loginHmac);
        fields.put("_csrf", loginCsrf);

        httpResponse = postVWCarNetAPI(url, fields, previousUrl, csrf);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.SEE_OTHER_303) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        url = httpResponse.getHeaders().get("Location");
        url = AUTH_BASE + url;
        httpResponse = getVWCarNetAPI(url, Boolean.TRUE);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.OK_200) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        previousUrl = url;
        htmlDocument = Jsoup.parse(httpResponse.getContentAsString());
        nameInput = htmlDocument.select("input[name=_csrf]").first();
        String authCsrf = nameInput.attr("value");
        nameInput = htmlDocument.select("input[name=relayState]").first();
        String authToken = nameInput.attr("value");
        nameInput = htmlDocument.select("input[name=hmac]").first();
        String authHmac = nameInput.attr("value");
        Element authForm = htmlDocument.getElementById("credentialsForm");
        url = AUTH_BASE + authForm.attr("action");

        fields = new Fields(true);
        fields.put("email", userName);
        fields.put("password", password);
        fields.put("relayState", authToken);
        fields.put("hmac", authHmac);
        fields.put("_csrf", authCsrf);

        httpResponse = postVWCarNetAPI(url, fields, previousUrl, csrf);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.MOVED_TEMPORARILY_302) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        url = httpResponse.getHeaders().get("Location");
        httpResponse = getVWCarNetAPI(url, Boolean.TRUE);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.MOVED_TEMPORARILY_302) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        url = httpResponse.getHeaders().get("Location");
        httpResponse = getVWCarNetAPI(url, Boolean.TRUE);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.MOVED_TEMPORARILY_302) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        url = httpResponse.getHeaders().get("Location");
        httpResponse = getVWCarNetAPI(url, Boolean.TRUE);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.MOVED_TEMPORARILY_302) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        url = httpResponse.getHeaders().get("Location");
        previousUrl = url;
        URI uri;
        String state = "", code = "", path = "";

        try {
            uri = new URI(url);
            String[] queryStrings = uri.getQuery().split("&");
            for (String query : queryStrings) {
                String[] nameValuePair = query.split("=");
                if (nameValuePair[0].equals("state")) {
                    state = nameValuePair[1];
                } else if (nameValuePair[0].equals("code")) {
                    code = nameValuePair[1];
                }
            }
            path = uri.getPath();
        } catch (URISyntaxException e) {
            logger.warn("Failed to login, caught URISyntaxException {}", e);
            return false;
        }

        fields = new Fields(true);
        fields.put("_33_WAR_cored5portlet_code", code);
        fields.put("_33_WAR_cored5portlet_landingPageUrl", "");

        url = SESSION_BASE + path + "?p_auth=" + state
                + "&p_p_id=33_WAR_cored5portlet&p_p_lifecycle=1&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_33_WAR_cored5portlet_javax.portlet.action=getLoginStatus";

        httpResponse = postVWCarNetAPI(url, fields, previousUrl, csrf);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.MOVED_TEMPORARILY_302) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        url = httpResponse.getHeaders().get("Location");
        httpResponse = getVWCarNetAPI(url, Boolean.TRUE);
        if (httpResponse == null) {
            logger.debug("Failed to login, Exception caught!");
            return false;
        } else if (httpResponse.getStatus() != HttpStatus.OK_200) {
            logger.debug("Failed to login, HTTP status code: {}", httpResponse.getStatus());
            return false;
        }

        htmlDocument = Jsoup.parse(httpResponse.getContentAsString());
        nameInput = htmlDocument.select("meta[name=_csrf]").first();
        xCsrfToken = nameInput.attr("content");
        referer = url;
        authRefUrl = url + "/";

        CookieStore cookieStore = httpClient.getCookieStore();
        List<HttpCookie> cookies = cookieStore.get(URI.create("www.portal.volkswagen-we.com"));
        cookies.forEach(cookie -> {
            logger.debug("Cookie: {}", cookie);
            if (cookie.getName().equals("GUEST_LANGUAGE_ID")) {
                guestLanguageId = cookie.getValue();
                logger.debug("Fetching guest language id {} from cookie", guestLanguageId);
            }
        });

        fields = null;
        String content = null;
        url = authRefUrl + "-/mainnavigation/get-fully-loaded-cars";
        httpResponse = postJSONVWCarNetAPI(url, fields, referer, xCsrfToken);
        if (httpResponse != null) {
            content = httpResponse.getContentAsString();
            logger.warn(content);
            DocumentContext context = JsonPath.parse(content);
            List<Object> vehicleList = context.read("$[fullyLoadedVehiclesResponse][vehiclesNotFullyLoaded]['vin']");
        }

        url = authRefUrl + "-/rah/get-status";
        httpResponse = postJSONVWCarNetAPI(url, fields, referer, xCsrfToken);
        content = httpResponse.getContentAsString();
        logger.warn(content);

        url = authRefUrl + "-/vehicle-info/get-vehicle-details";
        httpResponse = postJSONVWCarNetAPI(url, fields, referer, xCsrfToken);
        content = httpResponse.getContentAsString();
        logger.warn(content);

        url = authRefUrl + "-/mainnavigation/load-car-details/WVGZZZ5NZLW806713";
        httpResponse = postJSONVWCarNetAPI(url, fields, referer, xCsrfToken);
        content = httpResponse.getContentAsString();
        logger.warn(content);

        return true;
    }

    private void notifyListeners(VWCarNetThingJSON thing) {
        for (DeviceStatusListener listener : deviceStatusListeners) {
            listener.onDeviceStateChanged(thing);
        }
    }

    private void updateStatus() {
        logger.debug("VWCarNetSession:updateStatus");

        VWCarNetInstallation vInst = null;
        for (Enumeration<@Nullable VWCarNetInstallation> num = verisureInstallations.elements(); num
                .hasMoreElements();) {
            vInst = num.nextElement();
            if (vInst != null) {
                configureInstallationInstance(vInst.getInstallationId());
                int httpResultCode = setSessionCookieAuthLogin();
                if (httpResultCode == HttpStatus.OK_200) {
                    updateAlarmStatus(VWCarNetAlarmsJSON.class, vInst);
                    updateSmartLockStatus(VWCarNetSmartLocksJSON.class, vInst);
                    updateClimateStatus(VWCarNetClimatesJSON.class, vInst);
                    updateDoorWindowStatus(VWCarNetDoorWindowsJSON.class, vInst);
                    updateUserPresenceStatus(VWCarNetUserPresencesJSON.class, vInst);
                    updateSmartPlugStatus(VWCarNetSmartPlugsJSON.class, vInst);
                    updateBroadbandConnectionStatus(VWCarNetBroadbandConnectionsJSON.class, vInst);
                } else {
                    logger.warn("Failed to set session cookie and auth login, HTTP result code: {}", httpResultCode);
                }
            }
        }
    }

    private synchronized void updateAlarmStatus(Class<? extends VWCarNetThingJSON> jsonClass,
            @Nullable VWCarNetInstallation installation) {
        if (installation != null) {
            BigDecimal installationId = installation.getInstallationId();
            String url = START_GRAPHQL;

            String queryQLAlarmStatus = "[{\"operationName\":\"ArmState\",\"variables\":{\"giid\":\"" + installationId
                    + "\"},\"query\":\"query ArmState($giid: String!) {\\n  installation(giid: $giid) {\\n    armState {\\n      type\\n      statusType\\n      date\\n      name\\n      changedVia\\n      allowedForFirstLine\\n      allowed\\n      errorCodes {\\n        value\\n        message\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}]";
            logger.debug("Trying to get alarm status with URL {} and data {}", url, queryQLAlarmStatus);
            VWCarNetThingJSON thing = postJSONVWCarNetAPI(url, queryQLAlarmStatus, jsonClass);
            logger.debug("REST Response ({})", thing);

            if (thing != null) {
                // Set unique deviceID
                String deviceId = "alarm" + installationId.toString();
                thing.setDeviceId(deviceId);
                VWCarNetThingJSON oldObj = verisureThings.get(thing.getDeviceId());
                if (oldObj == null || !oldObj.equals(thing)) {
                    thing.setSiteId(installation.getInstallationId());
                    thing.setSiteName(installation.getInstallationName());
                    verisureThings.put(deviceId, thing);
                    notifyListeners(thing);
                }
            } else {
                logger.warn("Failed to update alarm status!");
            }
        }
    }

    private synchronized void updateSmartLockStatus(Class<? extends VWCarNetThingJSON> jsonClass,
            @Nullable VWCarNetInstallation installation) {
        if (installation != null) {
            BigDecimal installationId = installation.getInstallationId();
            String url = START_GRAPHQL;

            String queryQLSmartLock = "[{\"operationName\":\"DoorLock\",\"variables\":{\"giid\":\"" + installationId
                    + "\"},\"query\":\"query DoorLock($giid: String!) {\\n  installation(giid: $giid) {\\n    doorlocks {\\n      device {\\n        deviceLabel\\n        area\\n        __typename\\n      }\\n      currentLockState\\n      eventTime\\n      secureModeActive\\n      motorJam\\n      userString\\n      method\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}]\n"
                    + "";
            logger.debug("Trying to get smart lock status with URL {} and data {}", url, queryQLSmartLock);
            VWCarNetSmartLocksJSON thing = (VWCarNetSmartLocksJSON) postJSONVWCarNetAPI(url, queryQLSmartLock,
                    jsonClass);
            logger.debug("REST Response ({})", thing);

            if (thing != null && thing.getData() != null) {
                List<VWCarNetSmartLocksJSON.Doorlock> doorLockList = thing.getData().getInstallation().getDoorlocks();
                for (VWCarNetSmartLocksJSON.Doorlock doorLock : doorLockList) {
                    VWCarNetSmartLocksJSON slThing = new VWCarNetSmartLocksJSON();
                    VWCarNetSmartLocksJSON.Installation inst = new VWCarNetSmartLocksJSON.Installation();
                    List<VWCarNetSmartLocksJSON.Doorlock> list = new ArrayList<VWCarNetSmartLocksJSON.Doorlock>();
                    list.add(doorLock);
                    inst.setDoorlocks(list);
                    VWCarNetSmartLocksJSON.Data data = new VWCarNetSmartLocksJSON.Data();
                    data.setInstallation(inst);
                    slThing.setData(data);
                    // Set unique deviceID
                    String deviceId = doorLock.getDevice().getDeviceLabel();
                    if (deviceId != null) {
                        slThing.setDeviceId(deviceId);
                        // Set location
                        slThing.setLocation(doorLock.getDevice().getArea());
                        // Fetch more info from old endpoint
                        VWCarNetSmartLockJSON smartLockThing = getJSONVWCarNetAPI(
                                SMARTLOCK_PATH + slThing.getDeviceId(), VWCarNetSmartLockJSON.class);
                        logger.debug("REST Response ({})", smartLockThing);
                        slThing.setSmartLockJSON(smartLockThing);
                        VWCarNetThingJSON oldObj = verisureThings.get(slThing.getDeviceId());
                        if (oldObj == null || !oldObj.equals(slThing)) {
                            slThing.setSiteId(installation.getInstallationId());
                            slThing.setSiteName(installation.getInstallationName());
                            verisureThings.put(slThing.getDeviceId(), slThing);
                            notifyListeners(slThing);
                        }
                    }
                }
            } else {
                logger.warn("Failed to update SmartLockStatus thing: {}, thing.getData: {}", thing, thing.getData());
            }
        }
    }

    private synchronized void updateSmartPlugStatus(Class<? extends VWCarNetThingJSON> jsonClass,
            @Nullable VWCarNetInstallation installation) {
        if (installation != null) {
            BigDecimal installationId = installation.getInstallationId();
            String url = START_GRAPHQL;

            String queryQLSmartPlug = "{\"operationName\":\"SmartPlug\",\"variables\":{\"giid\":\"" + installationId
                    + "\"},\"query\":\"query SmartPlug($giid: String!) {\\n installation(giid: $giid) {\\n smartplugs {\\n device {\\n deviceLabel\\n area\\n __typename\\n }\\n currentState\\n icon\\n isHazardous\\n __typename\\n }\\n __typename\\n }\\n}\\n\"}";
            logger.debug("Trying to get smart plug status with URL {} and data {}", url, queryQLSmartPlug);
            VWCarNetSmartPlugsJSON thing = (VWCarNetSmartPlugsJSON) postJSONVWCarNetAPI(url, queryQLSmartPlug,
                    jsonClass);
            logger.debug("REST Response ({})", thing);

            if (thing != null && thing.getData() != null) {
                List<VWCarNetSmartPlugsJSON.Smartplug> smartPlugList = thing.getData().getInstallation()
                        .getSmartplugs();
                for (VWCarNetSmartPlugsJSON.Smartplug smartPlug : smartPlugList) {
                    VWCarNetSmartPlugsJSON slThing = new VWCarNetSmartPlugsJSON();
                    VWCarNetSmartPlugsJSON.Installation inst = new VWCarNetSmartPlugsJSON.Installation();
                    List<VWCarNetSmartPlugsJSON.Smartplug> list = new ArrayList<VWCarNetSmartPlugsJSON.Smartplug>();
                    list.add(smartPlug);
                    inst.setSmartplugs(list);
                    VWCarNetSmartPlugsJSON.Data data = new VWCarNetSmartPlugsJSON.Data();
                    data.setInstallation(inst);
                    slThing.setData(data);
                    // Set unique deviceID
                    String deviceId = smartPlug.getDevice().getDeviceLabel();
                    if (deviceId != null) {
                        slThing.setDeviceId(deviceId);
                        // Set location
                        slThing.setLocation(smartPlug.getDevice().getArea());
                        VWCarNetThingJSON oldObj = verisureThings.get(slThing.getDeviceId());
                        if (oldObj == null || !oldObj.equals(slThing)) {
                            slThing.setSiteId(installation.getInstallationId());
                            slThing.setSiteName(installation.getInstallationName());
                            verisureThings.put(slThing.getDeviceId(), slThing);
                            notifyListeners(slThing);
                        }
                    }
                }
            } else {
                logger.warn("Failed to update SmartPlugStatus thing: {}, thing.getData: {}", thing, thing.getData());
            }
        }
    }

    private synchronized void updateClimateStatus(Class<? extends VWCarNetThingJSON> jsonClass,
            @Nullable VWCarNetInstallation installation) {
        if (installation != null) {
            BigDecimal installationId = installation.getInstallationId();
            String url = START_GRAPHQL;

            String queryQLClimates = "{\"operationName\":\"Climate\",\"variables\":{\"giid\":\"" + installationId
                    + "\"},\"query\":\"query Climate($giid: String!) {\\n installation(giid: $giid) {\\n climates {\\n device {\\n deviceLabel\\n area\\n gui {\\n label\\n __typename\\n }\\n __typename\\n }\\n humidityEnabled\\n humidityTimestamp\\n humidityValue\\n temperatureTimestamp\\n temperatureValue\\n __typename\\n }\\n __typename\\n }\\n}\\n\"}";
            logger.debug("Trying to get climate status with URL {} and data {}", url, queryQLClimates);
            VWCarNetClimatesJSON thing = (VWCarNetClimatesJSON) postJSONVWCarNetAPI(url, queryQLClimates, jsonClass);
            logger.debug("REST Response ({})", thing);

            if (thing != null && thing.getData() != null) {
                List<VWCarNetClimatesJSON.Climate> climateList = thing.getData().getInstallation().getClimates();
                for (VWCarNetClimatesJSON.Climate climate : climateList) {
                    VWCarNetClimatesJSON cThing = new VWCarNetClimatesJSON();
                    VWCarNetClimatesJSON.Installation inst = new VWCarNetClimatesJSON.Installation();
                    List<VWCarNetClimatesJSON.Climate> list = new ArrayList<VWCarNetClimatesJSON.Climate>();
                    list.add(climate);
                    inst.setClimates(list);
                    VWCarNetClimatesJSON.Data data = new VWCarNetClimatesJSON.Data();
                    data.setInstallation(inst);
                    cThing.setData(data);
                    // Set unique deviceID
                    String deviceId = climate.getDevice().getDeviceLabel();
                    if (deviceId != null) {
                        cThing.setDeviceId(deviceId);
                        // Set location
                        cThing.setLocation(climate.getDevice().getArea());
                        VWCarNetThingJSON oldObj = verisureThings.get(cThing.getDeviceId());
                        if (oldObj == null || !oldObj.equals(cThing)) {
                            cThing.setSiteId(installation.getInstallationId());
                            cThing.setSiteName(installation.getInstallationName());
                            verisureThings.put(cThing.getDeviceId(), cThing);
                            notifyListeners(cThing);
                        }
                    }
                }
            } else {
                logger.warn("Failed to update ClimateStatus thing: {}, thing.getData: {}", thing, thing.getData());
            }
        }
    }

    private synchronized void updateDoorWindowStatus(Class<? extends VWCarNetThingJSON> jsonClass,
            @Nullable VWCarNetInstallation installation) {
        if (installation != null) {
            BigDecimal installationId = installation.getInstallationId();
            String url = START_GRAPHQL;

            String queryQLDoorWindow = "{\"operationName\":\"DoorWindow\",\"variables\":{\"giid\":\"" + installationId
                    + "\"},\"query\":\"query DoorWindow($giid: String!) {\\n installation(giid: $giid) {\\n doorWindows {\\n device {\\n deviceLabel\\n area\\n __typename\\n }\\n type\\n state\\n wired\\n reportTime\\n __typename\\n }\\n __typename\\n }\\n}\\n\"}";
            logger.debug("Trying to get climate status with URL {} and data {}", url, queryQLDoorWindow);
            VWCarNetDoorWindowsJSON thing = (VWCarNetDoorWindowsJSON) postJSONVWCarNetAPI(url, queryQLDoorWindow,
                    jsonClass);
            logger.debug("REST Response ({})", thing);

            if (thing != null && thing.getData() != null) {
                List<VWCarNetDoorWindowsJSON.DoorWindow> doorWindowList = thing.getData().getInstallation()
                        .getDoorWindows();
                for (VWCarNetDoorWindowsJSON.DoorWindow doorWindow : doorWindowList) {
                    VWCarNetDoorWindowsJSON dThing = new VWCarNetDoorWindowsJSON();
                    VWCarNetDoorWindowsJSON.Installation inst = new VWCarNetDoorWindowsJSON.Installation();
                    List<VWCarNetDoorWindowsJSON.DoorWindow> list = new ArrayList<VWCarNetDoorWindowsJSON.DoorWindow>();
                    list.add(doorWindow);
                    inst.setDoorWindows(list);
                    VWCarNetDoorWindowsJSON.Data data = new VWCarNetDoorWindowsJSON.Data();
                    data.setInstallation(inst);
                    dThing.setData(data);
                    // Set unique deviceID
                    String deviceId = doorWindow.getDevice().getDeviceLabel();
                    if (deviceId != null) {
                        dThing.setDeviceId(deviceId);
                        // Set location
                        dThing.setLocation(doorWindow.getDevice().getArea());
                        VWCarNetThingJSON oldObj = verisureThings.get(dThing.getDeviceId());
                        if (oldObj == null || !oldObj.equals(dThing)) {
                            dThing.setSiteId(installation.getInstallationId());
                            dThing.setSiteName(installation.getInstallationName());
                            verisureThings.put(dThing.getDeviceId(), dThing);
                            notifyListeners(dThing);
                        }
                    }
                }
            } else {
                logger.warn("Failed to update DoorWindowStatus thing: {}, thing.getData: {}", thing, thing.getData());
            }
        }
    }

    private synchronized void updateBroadbandConnectionStatus(Class<? extends VWCarNetThingJSON> jsonClass,
            @Nullable VWCarNetInstallation inst) {
        if (inst != null) {
            BigDecimal installationId = inst.getInstallationId();
            String url = START_GRAPHQL;

            String queryQLBroadbandConnection = "{\"operationName\":\"Broadband\",\"variables\":{\"giid\":\""
                    + installationId
                    + "\"},\"query\":\"query Broadband($giid: String!) {\\n installation(giid: $giid) {\\n broadband {\\n testDate\\n isBroadbandConnected\\n __typename\\n }\\n __typename\\n }\\n}\\n\"}";
            logger.debug("Trying to get alarm status with URL {} and data {}", url, queryQLBroadbandConnection);
            VWCarNetThingJSON thing = postJSONVWCarNetAPI(url, queryQLBroadbandConnection, jsonClass);
            logger.debug("REST Response ({})", thing);

            if (thing != null) {
                // Set unique deviceID
                String deviceId = "bc" + installationId.toString();
                thing.setDeviceId(deviceId);
                VWCarNetThingJSON oldObj = verisureThings.get(deviceId);
                if (oldObj == null || !oldObj.equals(thing)) {
                    thing.setSiteId(inst.getInstallationId());
                    thing.setSiteName(inst.getInstallationName());
                    verisureThings.put(deviceId, thing);
                    notifyListeners(thing);
                }
            }
        }
    }

    private synchronized void updateUserPresenceStatus(Class<? extends VWCarNetThingJSON> jsonClass,
            @Nullable VWCarNetInstallation installation) {
        if (installation != null) {
            BigDecimal installationId = installation.getInstallationId();
            String url = START_GRAPHQL;

            String queryQLUserPresence = "{\"operationName\":\"userTrackings\",\"variables\":{\"giid\":\""
                    + installationId
                    + "\"},\"query\":\"query userTrackings($giid: String!) {\\n  installation(giid: $giid) {\\n    userTrackings {\\n      isCallingUser\\n      webAccount\\n      status\\n      xbnContactId\\n      currentLocationName\\n      deviceId\\n      name\\n      currentLocationTimestamp\\n      deviceName\\n      currentLocationId\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"}";
            logger.debug("Trying to get user presence status with URL {} and data {}", url, queryQLUserPresence);
            VWCarNetUserPresencesJSON thing = (VWCarNetUserPresencesJSON) postJSONVWCarNetAPI(url, queryQLUserPresence,
                    jsonClass);
            logger.debug("REST Response ({})", thing);

            if (thing != null && thing.getData() != null) {
                List<VWCarNetUserPresencesJSON.UserTracking> userTrackingList = thing.getData().getInstallation()
                        .getUserTrackings();
                for (VWCarNetUserPresencesJSON.UserTracking userTracking : userTrackingList) {
                    if (userTracking.getStatus().equals("ACTIVE")) {
                        VWCarNetUserPresencesJSON upThing = new VWCarNetUserPresencesJSON();
                        VWCarNetUserPresencesJSON.Installation inst = new VWCarNetUserPresencesJSON.Installation();
                        List<VWCarNetUserPresencesJSON.UserTracking> list = new ArrayList<VWCarNetUserPresencesJSON.UserTracking>();
                        list.add(userTracking);
                        inst.setUserTrackings(list);
                        VWCarNetUserPresencesJSON.Data data = new VWCarNetUserPresencesJSON.Data();
                        data.setInstallation(inst);
                        upThing.setData(data);
                        // Set unique deviceID
                        String deviceId = "up" + userTracking.getWebAccount() + installationId.toString();
                        upThing.setDeviceId(deviceId);
                        VWCarNetThingJSON oldObj = verisureThings.get(upThing.getDeviceId());
                        if (oldObj == null || !oldObj.equals(upThing)) {
                            upThing.setSiteId(installation.getInstallationId());
                            upThing.setSiteName(installation.getInstallationName());
                            verisureThings.put(upThing.getDeviceId(), upThing);
                            notifyListeners(upThing);
                        }
                    }
                }
            } else {
                logger.warn("Failed to update UserPresenceStatus thing: {}, thing.getData: {}", thing, thing.getData());
            }
        }
    }
}
