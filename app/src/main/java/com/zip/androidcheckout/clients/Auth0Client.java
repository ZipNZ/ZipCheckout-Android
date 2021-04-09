package com.zip.androidcheckout.clients;

import com.zip.androidcheckout.models.Auth0TokenRequest;

import java.io.IOException;

import com.zip.androidcheckout.models.Auth0TokenResponse;

/**
 * Example implementation for obtaining bearer tokens
 * IMPORTANT NOTE -
 * In practice, we would offload this to a non-client side application.
 * This is simply to keep the example relatively self-contained
 */
public class Auth0Client extends RestClient {
    private final String _clientId;
    private final String _clientSecret;
    private final String _auth0Domain;
    private final String _auth0Audience;

    public Auth0Client(String clientId, String clientSecret, String auth0Domain, String auth0Audience) {
        this._clientId = clientId;
        this._clientSecret = clientSecret;
        this._auth0Domain = auth0Domain;
        this._auth0Audience = auth0Audience;
    }

    public Auth0TokenResponse acquireToken() throws IOException {

        Auth0TokenRequest auth0TokenRequest = new Auth0TokenRequest();
        auth0TokenRequest.client_id = this._clientId;
        auth0TokenRequest.client_secret = this._clientSecret;
        auth0TokenRequest.audience = this._auth0Audience;
        auth0TokenRequest.grant_type = "client_credentials";

        return postRequest("https://" + this._auth0Domain + "/oauth/token", auth0TokenRequest, Auth0TokenResponse.class);
    }
}
