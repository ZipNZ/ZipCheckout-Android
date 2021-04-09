
package com.zip.androidcheckout.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateOrderRequest {
    public Integer amount;
    public Consumer consumer;
    public Billing billing;
    public Shipping shipping;
    public String description;
    public List<Item> items = null;
    public Merchant merchant;
    public String merchantReference;
    public Integer taxAmount;
    public Integer shippingAmount;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}

class Billing {
    public String addressLine1;
    public String addressLine2;
    public String suburb;
    public String postcode;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}

class Consumer {
    public String phoneNumber;
    public String givenNames;
    public String surname;
    public String email;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}


class Item {
    public String name;
    public String sku;
    public Integer quantity;
    public Integer price;
    public String merchantChannel;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}


class Merchant {
    public String redirectConfirmUrl;
    public String redirectCancelUrl;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}

class Shipping {
    public String addressLine1;
    public String addressLine2;
    public String suburb;
    public String postcode;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();
}