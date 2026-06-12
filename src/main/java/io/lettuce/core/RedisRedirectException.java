package io.lettuce.core;

import java.net.InetSocketAddress;

import io.lettuce.core.internal.HostAndPort;

/**
 * Exception for CAPA {@code -REDIRECT host:port} responses.
 *
 * @since 7.0
 */
@SuppressWarnings("serial")
public class RedisRedirectException extends RedisCommandExecutionException {

    private static final String PREFIX = "REDIRECT ";

    private final String host;

    private final int port;

    public RedisRedirectException(String message) {
        super(message);

        HostAndPort hostAndPort = parseTarget(message);
        this.host = hostAndPort.getHostText();
        this.port = hostAndPort.getPort();
    }

    public RedisRedirectException(String message, Throwable cause) {
        super(message, cause);

        HostAndPort hostAndPort = parseTarget(message);
        this.host = hostAndPort.getHostText();
        this.port = hostAndPort.getPort();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public InetSocketAddress getSocketAddress() {
        return InetSocketAddress.createUnresolved(host, port);
    }

    public static boolean isRedirect(String message) {
        return message != null && message.startsWith(PREFIX);
    }

    private static HostAndPort parseTarget(String message) {
        if (!isRedirect(message)) {
            throw new IllegalArgumentException("Invalid REDIRECT error: " + message);
        }

        String target = message.substring(PREFIX.length()).trim();
        int space = target.indexOf(' ');
        if (space != -1) {
            target = target.substring(0, space);
        }

        HostAndPort hostAndPort = HostAndPort.parse(target);
        if (!hostAndPort.hasPort()) {
            throw new IllegalArgumentException("Invalid REDIRECT target: " + target);
        }

        return hostAndPort;
    }

}
