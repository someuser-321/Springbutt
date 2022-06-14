package com.systango.springbutt.service.user;

import com.systango.springbutt.domain.model.payment.WalletDetails;
import com.systango.springbutt.domain.model.payment.WalletLevel;
import com.systango.springbutt.dto.model.user.UserDto;
import com.systango.springbutt.service.exception.PaymentException;
import com.systango.springbutt.service.exception.UserException;

/**
 * Created by Arpit Khandelwal.
 */
public interface UserService {
    /**
     * Service method to add a new user.
     *
     * @param userDto
     * @return
     * @throws UserException
     */
    String addUser(UserDto userDto) throws UserException;

    /**
     * Create a new wallet for the given user.
     *
     * @param userDto
     * @param walletLevel
     * @return
     */

    WalletDetails createUserWallet(UserDto userDto, WalletLevel walletLevel) throws PaymentException;
}
