/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.cosenonjaviste.alfresco.annotations.processors.exceptions;

/**
 * @author Andrea Como
 */
@SuppressWarnings("unused")
public class ConfigurationAnnotationException extends RuntimeException {
    /**
     * Constructs a new {@code ConfigurationAnnotationException} with no detail message or cause.
     */
    public ConfigurationAnnotationException() {
    }

    /**
     * Constructs a new {@code ConfigurationAnnotationException} with the specified detail message.
     *
     * @param message the detail message, which can be retrieved later using the {@code getMessage()} method
     */
    public ConfigurationAnnotationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ConfigurationAnnotationException} with the specified detail
     * message and cause.
     *
     * @param message the detail message, which can be retrieved later using the
     *        {@code getMessage()} method
     * @param cause the cause of the exception, which can be retrieved later using the
     *        {@code getCause()} method; a {@code null} value indicates that the cause is
     *        nonexistent or unknown
     */
    public ConfigurationAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code ConfigurationAnnotationException} with the specified cause.
     *
     * @param cause the cause of the exception, which can be retrieved later using the
     *        {@code getCause()} method; a {@code null} value indicates that the cause is
     *        nonexistent or unknown
     */
    public ConfigurationAnnotationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code ConfigurationAnnotationException} with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message the detail message, which can be retrieved later using the {@code getMessage()} method
     * @param cause the cause of the exception, which can be retrieved later using the {@code getCause()} method;
     *        a {@code null} value indicates that the cause is nonexistent or unknown
     * @param enableSuppression whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public ConfigurationAnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
