package com.example.kanika.parkzyapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class PayService extends AppCompatActivity {

    private TextView m_response;
    private PayPalConfiguration m_configuration;
    private String m_paypalClientId = "AZYsxr0BEIl7XneFJYPBKiiqG0t_O8DY77NV-HeNeODoBoNspkNew6go19jSG3B8-8c4oFia--RqMHDk";
    private Intent m_service;
    private int m_paypalRequestCode = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_service);
        TextView m_response = (TextView) findViewById(R.id.response);
        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(m_paypalClientId);

        m_service = new Intent(this, PayService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        startService(m_service);
    }
    void pay(View view)
    {
        PayPalPayment payment =new PayPalPayment(new BigDecimal(10), "USD", "Test payment with PayPal",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, m_paypalRequestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == m_paypalRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirmatiton = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmatiton != null) {
                    String state = confirmatiton.getProofOfPayment().getState();

                    if (state.equals("approved"))
                        m_response.setText("error in payment");

                } else
                    m_response.setText("confirmation is null");


            }
        }

    }

}

