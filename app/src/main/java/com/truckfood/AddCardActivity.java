package com.truckfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCardActivity extends AppCompatActivity {

    @BindView(R.id.card_holder_name)
    EditText holderName;
    @BindView(R.id.card_number)
    EditText cardNumber;
    @BindView(R.id.card_expiry)
    EditText cardExpiry;
    @BindView(R.id.card_cvv_no)
    EditText cardCvv;

    @BindView(R.id.save_card)
    Button btnAdd;

    com.stripe.android.Stripe stripe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);

        Stripe.apiKey="sk_test_51KFvmjSIji825MBLbXH2K2hmTKnoqVT3ELNim3B5JDDFj8wsjqi4vn6frArTKNb30RbBvzZkYrGZyQjKP1HOLtlA00kR3wQdTx";
        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @OnClick(R.id.save_card)
    void addCard(){
        String name=holderName.getText().toString();
        String cardNo=cardNumber.getText().toString();
        String expiry=cardExpiry.getText().toString();
        String cvv=cardCvv.getText().toString();

        Map<String,Object> card=new HashMap<>();
        Map<String,Object> billingDetaild=new HashMap<>();

        String m=expiry.split("\\/")[0];
        String y=expiry.split("\\/")[1];

        int month=Integer.parseInt(m);
        int year=Integer.parseInt(y);

        card.put("number",cardNo);
        card.put("exp_month",month);
        card.put("exp_year",year);
        card.put("cvc",cvv);

        billingDetaild.put("name",name);
        billingDetaild.put("email","jake@gmail.com");

        Map<String,Object> allDetail=new HashMap<>();

        allDetail.put("type","card");
        allDetail.put("card",card);
        allDetail.put("billing_details",billingDetaild);

        PaymentMethod paymentMethod=null;
        try {
            paymentMethod = PaymentMethod.create(allDetail);
            PaymentMethod paymentMethodRetriveID=null;
            paymentMethodRetriveID=PaymentMethod.retrieve(paymentMethod.getId());

            Map<String,Object> cutomer=new HashMap<>();
            cutomer.put("customer","cus_LZ8oQcSkbgsaF9");

            PaymentMethod attchedCard=paymentMethodRetriveID.attach(cutomer);
            Toast.makeText(this,"Your card added Successfully",Toast.LENGTH_LONG).show();

        }catch (StripeException e){
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}