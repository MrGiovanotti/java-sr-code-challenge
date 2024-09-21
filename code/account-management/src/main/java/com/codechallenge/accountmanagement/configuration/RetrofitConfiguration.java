package com.codechallenge.accountmanagement.configuration;

import com.codechallenge.accountmanagement.clients.CustomerServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class RetrofitConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public CustomerServiceClient customerServiceClient() {
        return new Retrofit.Builder()
                .baseUrl(applicationProperties.getCustomerUrlBase())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(CustomerServiceClient.class);
    }

}
