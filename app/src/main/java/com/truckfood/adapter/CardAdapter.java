package com.truckfood.adapter;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.truckfood.R;
import com.truckfood.model.Dutam;
import java.util.List;
import com.stripe.exception.StripeException;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    Context context;
    List<Dutam> cardList;
    boolean isPay;
    int amount;

    public CardAdapter(Context context, List<Dutam> cardList,boolean isPay,int amount) {
        this.context = context;
        this.cardList = cardList;
        this.isPay=isPay;
        this.amount=amount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        holder.brand.setText(cardList.get(position).getCard().getBrand());
        //here we add some *** for locking better
        holder.lastDigit.setText("**** **** **** "+cardList.get(position).getCard().getLast4());
        // here we add on click
         if (isPay)
         {
             holder.deleteBtn.setVisibility(View.GONE);
              String cutomerId="cus_LZ8oQcSkbgsaF9";
             // first we make amount multi by 100 becouse stripe not take decial point
              double total=17.50*100; // first we directly send amount the we edit it
             // now pass total in amount
             // here pass point value for testing decimal point
             //here we add payment when click on card
             holder.payLayout.setOnClickListener(view -> {
                 // first we need  call stripe config
                 Stripe.apiKey = "sk_test_51KFvmjSIji825MBLbXH2K2hmTKnoqVT3ELNim3B5JDDFj8wsjqi4vn6frArTKNb30RbBvzZkYrGZyQjKP1HOLtlA00kR3wQdTx";
                 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                 StrictMode.setThreadPolicy(policy);
                 // i copy and paste that i use in delete card


                 // first i told you directly adding the address the you break it
                 // for direct you type in setShipping method see
                 // here address also a builder so create it
                 // ok address and all data set
                 PaymentIntentCreateParams.Shipping.Address  address=
                 PaymentIntentCreateParams.Shipping.Address.builder()
                         .setLine1("s10 towsend 31")
                         .setPostalCode("98140")
                         .setCity("san francisco")
                         .setState("CA")
                         .setCountry("US")
                         .build();

                 PaymentIntentCreateParams.Shipping shippinggDetail=
                 PaymentIntentCreateParams.Shipping.builder()
                         .setName("DevLance Stack RAJ")
                         .setAddress(address).build();
                      //like this you can break the code

                 PaymentIntentCreateParams payIntetPara=PaymentIntentCreateParams.builder()
                         .setCurrency("usd") // here set payment currency
                 .setShipping(shippinggDetail)// here need address we set it latter
                 .setAmount((long)total) // set total payment amount // set it also latter
                 .setCustomer(cutomerId) // customer id
                 .setPaymentMethod(cardList.get(position).getId()) // set card id
                 .addPaymentMethodType("card") // add payment type add card becouse we pay by card
                 .setDescription("This is payment to test by Dev lance stack")
                         .setConfirm(true)
                         .setOffSession(true)
                         .build(); // now we set amount first



                 // now here we make payment
                 try{
                     PaymentIntent payMent=PaymentIntent.create(payIntetPara);// pass here all parmeters
                     System.out.println("payment success ID = "+payMent.getId());
                 }catch(StripeException e){
                    System.out.println("error"+e.getMessage());
                 }

             });

         }else {
             holder.deleteBtn.setOnClickListener(view -> {
                 // now here we need card Id but in our card list
                 // last time we add brand and last 4 digit of card
                 // for card id we make some change in our card list
                 // lets see

                 // now here we can get the card id that want to delete
                 //  this is our card id print it
                 // System.out.println(cardList.get(holder.getAbsoluteAdapterPosition()).getId());
                 // here we get both id on click now add delete card
                 // first i will show you the card on stripe also
                 // jake have two card
                 // now we delete one card of jake using on clik icon
                 try {
                     // first add stripe key
                     // copey and paste from add activity some code of line
                     Stripe.apiKey = "sk_test_51KFvmjSIji825MBLbXH2K2hmTKnoqVT3ELNim3B5JDDFj8wsjqi4vn6frArTKNb30RbBvzZkYrGZyQjKP1HOLtlA00kR3wQdTx";
                     StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                     StrictMode.setThreadPolicy(policy);

                     // first line for api key of stripe
                     // second two line for setting the thread poslicy other wise we get main thread exception

                     PaymentMethod paymentMethod = null;
                     // create one paymentmethod object
                     // here we retrive the paymentmethod by id and the delete that paymentMethod
                     // here pass card id that you want to delete
                     paymentMethod = PaymentMethod.retrieve(cardList.get(holder.getAbsoluteAdapterPosition()).getId());
                     //now delete it
                     PaymentMethod updatePayment = paymentMethod.detach();
                     // call detach method for delete
                     // now remove from ui list and update ui
                     cardList.remove(holder.getAbsoluteAdapterPosition());
                     // update ui by using notify
                     notifyDataSetChanged();
                     // now test the app

                 } catch (Exception e) {

                 }
             });
         }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // first add delete button icon
        // add layout here
        LinearLayout payLayout;
        TextView brand,lastDigit,deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brand=(itemView).findViewById(R.id.cardBrand);
            lastDigit=(itemView).findViewById(R.id.cardNumber);
            deleteBtn=(itemView).findViewById(R.id.delete);
            payLayout=(itemView).findViewById(R.id.payLayout);
            //we atteched the icon in view now add on click
        }
    }
}
