package com.codechallenge.accountmanagement.clients;

import com.codechallenge.accountmanagement.models.Customer;
import io.reactivex.rxjava3.core.Maybe;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CustomerServiceClient {

    @GET("/clientes/{id}")
    Maybe<Response<Customer>> getCustomerById(@Path("id") Integer id);

}
