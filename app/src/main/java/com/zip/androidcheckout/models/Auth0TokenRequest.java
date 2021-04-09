package com.zip.androidcheckout.models;


/**
 * POJO used when sending token requests to auth0
 */
public class Auth0TokenRequest {
    public String client_id;
    public String client_secret;
    public String audience;
    public String grant_type;
}
