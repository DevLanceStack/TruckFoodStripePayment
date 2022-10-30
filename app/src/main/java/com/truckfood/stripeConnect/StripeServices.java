package com.truckfood.stripeConnect;

import com.truckfood.model.CustomerModel;
import com.truckfood.model.Result;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface StripeServices {
    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "Authorization:Bearer sk_test_51KFvmjSIji825MBLbXH2K2hmTKnoqVT3ELNim3B5JDDFj8wsjqi4vn6frArTKNb30RbBvzZkYrGZyQjKP1HOLtlA00kR3wQdTx"
    })
    @FormUrlEncoded
    @POST("customers")
    Observable<CustomerModel> createCustomer(
            @Field("email") String email,
            @Field("name") String name
    );




    @Headers({
            "Authorization:Bearer sk_test_51KFvmjSIji825MBLbXH2K2hmTKnoqVT3ELNim3B5JDDFj8wsjqi4vn6frArTKNb30RbBvzZkYrGZyQjKP1HOLtlA00kR3wQdTx"
    })
    @GET
    Observable<Result> getCard(
            @Url String url,
            @Query("type") String type  //define parmas name
    );

}
