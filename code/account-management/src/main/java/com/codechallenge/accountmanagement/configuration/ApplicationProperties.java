package com.codechallenge.accountmanagement.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationProperties {

    @Value("${customer-url-base}")
    private String customerUrlBase;

}
