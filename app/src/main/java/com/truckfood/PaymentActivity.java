package com.truckfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {
   Button checkOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        checkOut=findViewById(R.id.check_out_btn);

        //here add on click listener on checkout button
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here we create intent and send on viewcard acivity
                Intent i=  new Intent(PaymentActivity.this,ViewCardActivity.class);
                //here we send a extra parameter for hide the delete button
                //same pamameter send from main activity also when click on
                //view card
                i.putExtra("isPay",true );
                i.putExtra("amount",17 );
                startActivity(i);
            }
        });

    }
}