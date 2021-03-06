# ZipCheckout-Android

An example on how to handle the Zip NZ checkout flow from iOS

## Prerequisites

* Android Studio
* Android Emulator (Ideally emulatoing Pixel 3a + For usability)
* Android SDK 29

## Running the App

Most of the input values are defaulted at the start page (and inside the code). Feel free to experiment by changing the values to meet your needs

The only variables that you need to provide are your merchant client id and merchant client secret, provided by the [merchant onboarding process]((https://zip.co/nz/for-merchants/#getting-started))

![Checkout Initialization](./readme-resources/checkout-start.png)
## Running through checkout

### Creating a test account

If you haven't got an account already, you can create a test account with dummy information as documented here:

https://docs-nz.zip.co/merchant-api/testing#customer-account

### Existing accounts

If you have already got a test account, you can simply login with the same details. Ensure the account has sufficient spend by managing it on the online [Customer Portal](https://sandbox.zip.co/nz/portal)

## Catching Redirects

On order completion, the webview will redirect to a provided success / failure url

MainActivity.java 

```java
private final String redirectSuccessUrl = "https://sandbox.zip.co/nz/api?yay=true";
private final String redirectFailureUrl = "https://sandbox.zip.co/nz/api?yay=false";
```

By intercepting the navigation actions via

https://developer.android.com/reference/android/webkit/WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView,%20android.webkit.WebResourceRequest)

```java
@Override
public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {}
```
We can see when either the SuccessUrl or FailureUrl are hit and determine what state the order has reached. In this example, we display a popup in the parent controller to notify the user on their order status

## In regards to Auth0Client.java / ZipNzClient.java

These clients are purely to keep the example self-contained. In reality, your app must call your own ecommerce platform Api to create the orders and provide Zip NZ checkout urls for the web view.

We completely discourage the storage of any Zip NZ credentials in a client app. We also completely discourage direct access to the Zip NZ bearer token in a client app.

## Trouble shooting

Having trouble? Reach out to the Zip NZ Team or _checkout_ our [full documentation](https://docs-nz.zip.co/)

