package com.zip.androidcheckout.services;

import android.util.Log;

import com.zip.androidcheckout.clients.Auth0Client;
import com.zip.androidcheckout.clients.ZipNzClient;
import com.zip.androidcheckout.models.Auth0TokenResponse;
import com.zip.androidcheckout.models.CreateOrderResponse;

/**
 * Acquires an auth0 bearer token in an asynchronous context
 */
public class OrderUrlRequesterService implements Runnable {

    private volatile CreateOrderResponse response;
    private final String _loginDomain;
    private final String _loginAudience;
    private final String _clientId;
    private final String _clientSecret;
    private final String _itemName;
    private final Double _amount;

    public String redirectSuccessUrl;
    public String redirectFailureUrl;

    public OrderUrlRequesterService(String loginDomain, String loginAudience, String clientId, String clientSecret,
                                    String itemName, Double amount) {
        _loginAudience = loginAudience;
        _loginDomain = loginDomain;
        _clientId = clientId;
        _clientSecret = clientSecret;
        _itemName = itemName;
        _amount = amount;
    }

    @Override
    public void run() {
        response = acquireOrderUrl();
    }

    public CreateOrderResponse getResponse() {
        return response;
    }

    private CreateOrderResponse acquireOrderUrl() {
        Auth0Client auth0Client = new Auth0Client(_clientId, _clientSecret, _loginDomain, _loginAudience);

        Auth0TokenResponse auth0TokenResponse;

        try {
            auth0TokenResponse = auth0Client.acquireToken();
        } catch (Exception e) {
            Log.e("auth", "Could not retrieve token", e);
            return null;
        }

        ZipNzClient zipNzClient = new ZipNzClient(auth0TokenResponse.access_token);

        CreateOrderResponse createOrderResponse;
        try {
            createOrderResponse = zipNzClient.createOrder(_itemName, _amount, redirectSuccessUrl, redirectFailureUrl);
        } catch (Exception e) {
            Log.e("order", "Could not retrieve order url", e);
            return null;
        }

        return createOrderResponse;
    }
}