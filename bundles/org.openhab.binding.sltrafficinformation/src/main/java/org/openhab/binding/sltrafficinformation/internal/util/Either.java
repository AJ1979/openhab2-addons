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
package org.openhab.binding.sltrafficinformation.internal.util;

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link Either} is an implementation of an Either type
 *
 * @author Jan Gustafsson - Initial contribution
 */
@NonNullByDefault
public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> Left(L value) {
        return new Either(value, null);
    }

    public static <L, R> Either<L, R> Right(R value) {
        return new Either(null, value);
    }

    public Optional<L> getLeft() {
        return Optional.ofNullable(left);
    }

    public Optional<R> getRight() {
        return Optional.ofNullable(right);
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean isRight() {
        return right != null;
    }

    public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {
        if (isLeft()) {
            return Optional.of(mapper.apply(left));
        }
        return Optional.empty();
    }

    public <T> Optional<T> mapRight(Function<? super R, T> mapper) {
        if (isRight()) {
            return Optional.of(mapper.apply(right));
        }
        return Optional.empty();
    }

    public static <T, R> Function<T, Either> lift(CheckedFunction<T, R> function) {
        return t -> {
            try {
                return Either.Right(function.apply(t));
            } catch (Exception ex) {
                return Either.Left(ex);
            }
        };
    }

    public static <T, R> Function<T, Either> liftWithValue(CheckedFunction<T, R> function) {
        return t -> {
            try {
                return Either.Right(function.apply(t));
            } catch (Exception ex) {
                return Either.Left(Pair.of(ex, t));
            }
        };
    }

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @Override
    public String toString() {
        if (isLeft()) {
            return "Left(" + left + ")";
        }
        return "Right(" + right + ")";
    }

}
