package com.systango.springbutt.api.v1.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.systango.springbutt.domain.model.payment.WalletLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String username;

    private WalletLevel walletLevel;
}
