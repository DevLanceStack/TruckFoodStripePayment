package com.truckfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.truckfood.stripeConnect.StripeClient;
import com.truckfood.stripeConnect.StripeServices;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,email;
    Button add,addCard,viewCard,payBtn;

    StripeServices stripeServices;
    CompositeDisposable compositeDisposable =new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.customrname);
        email=findViewById(R.id.customer_email);
        add=findViewById(R.id.addcustomer);
        addCard=findViewById(R.id.addCard);
        payBtn=findViewById(R.id.payBtn);
        viewCard=findViewById(R.id.viewCard);
        stripeServices= StripeClient.getInstance().create(StripeServices.class);
        add.setOnClickListener(this);
        addCard.setOnClickListener(this);
        viewCard.setOnClickListener(this);
        payBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.viewCard) {
            Intent i=  new Intent(MainActivity.this,ViewCardActivity.class);
            i.putExtra("isPay",false );//make false here
            startActivity(i);
        }else if(view.getId()==R.id.addCard){
            startActivity(new Intent(MainActivity.this,AddCardActivity.class));
        }else if(view.getId()==R.id.payBtn){
            startActivity(new Intent(MainActivity.this,PaymentActivity.class));
        }else{
        compositeDisposable.add(stripeServices.createCustomer(
                email.getText().toString(),
                name.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(customerModel ->{
                    if(customerModel.getId()==null){
                        System.out.println("Try again");
                    }else{
                        System.out.println("===> customer Id : "+customerModel.getId());
                    }
                },throwable -> {
                    System.out.println(throwable.getMessage());
                } )
        );}
    }

}