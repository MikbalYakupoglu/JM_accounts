package com.example.accounts.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "accounts")
public class AccountContactInfoDto {

    private final String message;
    private final Map<String, String> contactDetails;
    private final List<String> onCallSupport;

    public AccountContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport){

        this.message = message;
        this.contactDetails = contactDetails;
        this.onCallSupport = onCallSupport;
    }

}
