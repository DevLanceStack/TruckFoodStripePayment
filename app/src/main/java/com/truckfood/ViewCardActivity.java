package com.truckfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.truckfood.adapter.CardAdapter;
import com.truckfood.model.Card;
import com.truckfood.model.Dutam;
import com.truckfood.stripeConnect.StripeClient;
import com.truckfood.stripeConnect.StripeServices;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ViewCardActivity extends AppCompatActivity {

    StripeServices stripeServices;

    //now we create composite object for api call and thred

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    RecyclerView recyclerView;
    LinearLayoutManager manager;

    List<Dutam> cardList;
//create a variable herere
    boolean isPay=false;
    int amount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
//  call stripe client and create instace of service to connect stripe
        stripeServices= StripeClient.getInstance().create(StripeServices.class);

        recyclerView=findViewById(R.id.cardViewrecyler);
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //here get ispay parameter and pass it to adapter
        isPay=getIntent().getBooleanExtra("isPay",false);
        if(isPay){
            amount=getIntent().getIntExtra("amount",0);
        }
        cardList=new ArrayList<>();
        // create a function loadCard
        loadCard();
    }

    private void loadCard() {
        // we need two params url and type
        // first add customer id to get their card
        // get id from stripe
        // make customers here now test aga in
        String url="customers/cus_LZ8oQcSkbgsaF9/payment_methods";
        String type="card";
        compositeDisposable.add(stripeServices.getCard(url,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                 //if success
                    // get data here we print 4 digit of card that given by stripe
                   for(int i=0;i<result.getData().size();i++){
                    //  System.out.println(result.getData().get(i).getId());
                       // here also change list as dutam
                       cardList.add(result.getData().get(i));
                   }
                   // now change in adapter
                    CardAdapter cardAdapter =new CardAdapter(this,cardList,isPay,amount);
                   recyclerView.setAdapter(cardAdapter);
                },throwable -> {
                    //if get thread exception then add thred message
                    System.out.println(throwable.getMessage());
                })
        );
    }
}