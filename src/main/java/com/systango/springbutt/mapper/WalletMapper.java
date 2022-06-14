package com.systango.springbutt.mapper;


import com.systango.springbutt.domain.model.payment.WalletDetails;
import com.systango.springbutt.dto.model.payment.WalletDto;

/**
 * @author Arpit Khandelwal
 */
public class WalletMapper {

    public static WalletDto mapWallet(WalletDetails walletDetails) {
        return new WalletDto()
                .setBalance(walletDetails.getBalance())
                .setUserName(walletDetails.getApplicationUser().getUsername())
                .setWalletLevel(walletDetails.getWalletLevel());
    }
}