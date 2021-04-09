package com.zip.androidcheckout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.zip.androidcheckout.models.CreateOrderResponse;
import com.zip.androidcheckout.services.OrderUrlRequesterService;

public class MainActivity extends AppCompatActivity {


    private final String redirectSuccessUrl = "https://sandbox.zip.co/nz/api?yay=true";
    private final String redirectFailureUrl = "https://sandbox.zip.co/nz/api?yay=false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.startCheckoutButton);
        button.setOnClickListener(v -> {


            String loginDomain = ((EditText) findViewById(R.id.loginField)).getText().toString();
            String loginAudience = ((EditText) findViewById(R.id.audField)).getText().toString();
            String clientId = ((EditText) findViewById(R.id.clientIdField)).getText().toString();
            String clientSecret = ((EditText) findViewById(R.id.clientSecretField)).getText().toString();
            String itemName = ((EditText) findViewById(R.id.itemNameField)).getText().toString();
            String orderAmountStr = ((EditText) findViewById(R.id.amountField)).getText().toString();

            double orderAmount = Double.parseDouble(orderAmountStr);

            OrderUrlRequesterService orderUrlRequesterService = new OrderUrlRequesterService(loginDomain, loginAudience, clientId, clientSecret, itemName, orderAmount);
            orderUrlRequesterService.redirectSuccessUrl = redirectSuccessUrl;
            orderUrlRequesterService.redirectFailureUrl = redirectFailureUrl;
            Thread requester = new Thread(orderUrlRequesterService);

            requester.start();
            try {
                requester.join();
            } catch (Exception e) {
                Log.e("auth", "Trouble obtaining auth token", e);
            }

            CreateOrderResponse orderResponse = orderUrlRequesterService.getResponse();
            if (orderResponse == null) {
                return;
            }

            Bundle bundle = new Bundle();

            bundle.putString("orderUrl", orderResponse.redirectUrl);
            bundle.putString("redirectSuccessUrl", redirectSuccessUrl);
            bundle.putString("redirectFailureUrl", redirectFailureUrl);

            Intent i = new Intent(MainActivity.this, CheckoutActivity.class);
            i.putExtras(bundle);
            startActivityForResult(i, 1);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String orderStatus = "Incomplete";
        if (requestCode == 1 && resultCode == RESULT_OK)
            orderStatus = data.getStringExtra("orderStatus");


        showPopUp(orderStatus);
    }

    private void showPopUp(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Order Status");
        alertDialog.setMessage("Order Status \n" + message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", (dialog, which) -> {});
        alertDialog.show();
    }

}