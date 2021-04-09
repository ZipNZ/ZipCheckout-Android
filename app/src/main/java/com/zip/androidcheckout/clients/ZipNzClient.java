package com.zip.androidcheckout.clients;

import com.zip.androidcheckout.models.CreateOrderRequest;
import com.zip.androidcheckout.models.CreateOrderResponse;

import java.io.IOException;

public class ZipNzClient extends RestClient {
    private final String _accessToken;

    public ZipNzClient(String accessToken){
        _accessToken = accessToken;
    }

    public CreateOrderResponse createOrder(String itemName, Double amount, String redirectConfirmUrl, String redirectCancelUrl) throws IOException {
        // Set consumer.email to autofill the checkout screens email address field
        String orderPayload = "{\n" +
                "          \"amount\": " + amount + ",\n" +
                "          \"consumer\": {\n" +
                "              \"phoneNumber\": \"0200000000\",\n" +
                "              \"givenNames\": \"John\",\n" +
                "              \"surname\": \"Smith\"\n" +
                "          },\n" +
                "          \"billing\": {\n" +
                "              \"addressLine1\": \"Address Line 1\",\n" +
                "              \"addressLine2\": \"Suite 1\",\n" +
                "              \"suburb\": \"Auckland\",\n" +
                "              \"postcode\": \"1000\"\n" +
                "          },\n" +
                "          \"shipping\": {\n" +
                "              \"addressLine1\": \"Address Line 1\",\n" +
                "              \"addressLine2\": \"Suite 1\",\n" +
                "              \"suburb\": \"Auckland\",\n" +
                "              \"postcode\": \"1000\"\n" +
                "          },\n" +
                "          \"description\": \"Purchase of example product\",\n" +
                "          \"items\": [\n" +
                "              {\n" +
                "                  \"name\": \"" + itemName + "\",\n" +
                "                  \"sku\": \"M/X1824C\",\n" +
                "                  \"quantity\": 1,\n" +
                "                  \"price\": " + amount + ",\n" +
                "                  \"merchantChannel\": \"Store\"\n" +
                "              }\n" +
                "          ],\n" +
                "          \"merchant\": {\n" +
                "              \"redirectConfirmUrl\": \"" + redirectConfirmUrl + "\",\n" +
                "              \"redirectCancelUrl\": \"" + redirectCancelUrl + "\"\n" +
                "          },\n" +
                "          \"merchantReference\": \"x-android-example-application\",\n" +
                "          \"taxAmount\": 40,\n" +
                "          \"shippingAmount\": 0\n" +
                "      }";

        CreateOrderRequest responseObject = jsonDeserializer.fromJson(orderPayload, CreateOrderRequest.class);
        String _apiInstance = "sandbox.zip.co/nz/api";

        return postRequest("https://" + _apiInstance + "/order", responseObject, CreateOrderResponse.class, _accessToken);
    }
}
