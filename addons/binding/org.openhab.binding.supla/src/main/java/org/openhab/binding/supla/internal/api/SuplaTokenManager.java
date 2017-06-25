package org.openhab.binding.supla.internal.api;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.openhab.binding.supla.internal.server.http.CommonHeaders.CONTENT_TYPE_JSON;

import java.util.Optional;

import org.openhab.binding.supla.internal.server.SuplaCloudServer;
import org.openhab.binding.supla.internal.server.Token;
import org.openhab.binding.supla.internal.server.http.*;
import org.openhab.binding.supla.internal.server.mappers.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

public final class SuplaTokenManager implements TokenManager {
    private final Logger logger = LoggerFactory.getLogger(SuplaTokenManager.class);
    private final JsonMapper jsonMapper;
    private final HttpExecutor httpExecutor;
    private final SuplaCloudServer server;
    private final Body body;

    public SuplaTokenManager(JsonMapper jsonMapper, HttpExecutor httpExecutor, SuplaCloudServer server) {
        this.jsonMapper = checkNotNull(jsonMapper);
        this.httpExecutor = httpExecutor;
        this.server = checkNotNull(server);
        body = new JsonBody(ImmutableMap.<String, String> builder().put("client_id", server.getClientId())
                .put("client_secret", server.getSecretAsString()).put("grant_type", "password")
                .put("username", server.getUsername()).put("password", server.getPasswordAsString()).build(), jsonMapper);
    }

    @Override
    public Optional<Token> obtainToken() {
        final Response response = httpExecutor.post(new Request("/oauth/v2/token", CONTENT_TYPE_JSON), body);
        if (response.success()) {
            return Optional.of(jsonMapper.to(Token.class, response.getResponse()));
        } else {
            logger.warn("Got error {} while obtaining token for server {}!", response.getStatusCode(), server);
            return Optional.empty();
        }
    }
}