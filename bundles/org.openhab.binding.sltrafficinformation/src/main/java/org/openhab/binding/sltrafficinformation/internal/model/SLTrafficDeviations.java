/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
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
package org.openhab.binding.sltrafficinformation.internal.model;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link SLTrafficDeviations} is responsible for JSON conversion.
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
public class SLTrafficDeviations {

    @SerializedName("StatusCode")
    private int statusCode;
    @SerializedName("Message")
    private @Nullable Object message;
    @SerializedName("ExecutionTime")
    private int executionTime;
    @SerializedName("ResponseData")
    private @Nullable List<ResponseData> responseData;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public @Nullable Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public @Nullable List<ResponseData> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseData> responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("statusCode", statusCode).append("message", message)
                .append("executionTime", executionTime).append("responseData", responseData).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(executionTime).append(responseData).append(message).append(statusCode)
                .toHashCode();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SLTrafficDeviations)) {
            return false;
        }
        SLTrafficDeviations rhs = ((SLTrafficDeviations) other);
        return new EqualsBuilder().append(executionTime, rhs.executionTime).append(responseData, rhs.responseData)
                .append(message, rhs.message).append(statusCode, rhs.statusCode).isEquals();
    }

    @NonNullByDefault
    public class ResponseData {

        @SerializedName("Created")
        private @Nullable String created;
        @SerializedName("MainNews")
        private boolean mainNews;
        @SerializedName("SortOrder")
        private int sortOrder;
        @SerializedName("Header")
        private @Nullable String header;
        @SerializedName("Details")
        private @Nullable String details;
        @SerializedName("Scope")
        private @Nullable String scope;
        @SerializedName("DevCaseGid")
        private @Nullable BigDecimal devCaseGid;
        @SerializedName("DevMessageVersionNumber")
        private int devMessageVersionNumber;
        @SerializedName("ScopeElements")
        private @Nullable String scopeElements;
        @SerializedName("FromDateTime")
        private @Nullable String fromDateTime;
        @SerializedName("UpToDateTime")
        private @Nullable String upToDateTime;
        @SerializedName("Updated")
        private @Nullable String updated;

        public @Nullable String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public boolean isMainNews() {
            return mainNews;
        }

        public void setMainNews(boolean mainNews) {
            this.mainNews = mainNews;
        }

        public int getSortOrder() {
            return sortOrder;
        }

        public void setSortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        public @Nullable String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public @Nullable String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public @Nullable String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public @Nullable BigDecimal getDevCaseGid() {
            return devCaseGid;
        }

        public void setDevCaseGid(BigDecimal devCaseGid) {
            this.devCaseGid = devCaseGid;
        }

        public int getDevMessageVersionNumber() {
            return devMessageVersionNumber;
        }

        public void setDevMessageVersionNumber(int devMessageVersionNumber) {
            this.devMessageVersionNumber = devMessageVersionNumber;
        }

        public @Nullable String getScopeElements() {
            return scopeElements;
        }

        public void setScopeElements(String scopeElements) {
            this.scopeElements = scopeElements;
        }

        public @Nullable String getFromDateTime() {
            return fromDateTime;
        }

        public void setFromDateTime(String fromDateTime) {
            this.fromDateTime = fromDateTime;
        }

        public @Nullable String getUpToDateTime() {
            return upToDateTime;
        }

        public void setUpToDateTime(String upToDateTime) {
            this.upToDateTime = upToDateTime;
        }

        public @Nullable String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("created", created).append("mainNews", mainNews)
                    .append("sortOrder", sortOrder).append("header", header).append("details", details)
                    .append("scope", scope).append("devCaseGid", devCaseGid)
                    .append("devMessageVersionNumber", devMessageVersionNumber).append("scopeElements", scopeElements)
                    .append("fromDateTime", fromDateTime).append("upToDateTime", upToDateTime)
                    .append("updated", updated).toString();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(created).append(upToDateTime).append(mainNews)
                    .append(devMessageVersionNumber).append(fromDateTime).append(sortOrder).append(scope)
                    .append(devCaseGid).append(header).append(details).append(updated).append(scopeElements)
                    .toHashCode();
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof ResponseData)) {
                return false;
            }
            ResponseData rhs = ((ResponseData) other);
            return new EqualsBuilder().append(created, rhs.created).append(upToDateTime, rhs.upToDateTime)
                    .append(mainNews, rhs.mainNews).append(devMessageVersionNumber, rhs.devMessageVersionNumber)
                    .append(fromDateTime, rhs.fromDateTime).append(sortOrder, rhs.sortOrder).append(scope, rhs.scope)
                    .append(devCaseGid, rhs.devCaseGid).append(header, rhs.header).append(details, rhs.details)
                    .append(updated, rhs.updated).append(scopeElements, rhs.scopeElements).isEquals();
        }

    }
}
