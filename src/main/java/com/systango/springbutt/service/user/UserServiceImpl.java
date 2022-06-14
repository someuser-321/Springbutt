package com.systango.springbutt.service.user;

import com.systango.springbutt.domain.model.payment.WalletDetails;
import com.systango.springbutt.domain.model.payment.WalletLevel;
import com.systango.springbutt.domain.model.user.ApplicationUser;
import com.systango.springbutt.domain.repository.ApplicationUserRepository;
import com.systango.springbutt.dto.model.payment.WalletDto;
import com.systango.springbutt.dto.model.user.UserDto;
import com.systango.springbutt.service.exception.PaymentException;
import com.systango.springbutt.service.exception.UserException;
import com.systango.springbutt.service.payment.PaymentService;
import com.systango.springbutt.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Arpit Khandelwal.
 */
@Component
public class UserServiceImpl implements UserService {

    private final ApplicationUserRepository userRepository;

    private PaymentService paymentService;

    /**
     * @param userRepository
     */
    @Autowired
    public UserServiceImpl(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String addUser(UserDto userDto) throws UserException {
        AssertUtils.notNull(userDto, userDto.getUsername(), userDto.getPassword());
        Optional<ApplicationUser> user = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()));

        if (user.isPresent()) {
            throw new UserException(String.format("Requested user - '%s' already exists.", userDto.getUsername()));
        } else {
            ApplicationUser appUser = new ApplicationUser()
                    .setUsername(userDto.getUsername())
                    .setPassword(userDto.getPassword());
            userRepository.save(appUser);
        }
        return null;
    }

    @Override
    @Transactional
    public WalletDetails createUserWallet(UserDto userDto, WalletLevel level) throws PaymentException {
        AssertUtils.notNull(userDto, userDto.getUsername(), userDto.getPassword());
        Optional<ApplicationUser> user = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()));
        if (user.isPresent()) {
            WalletDto walletDto = new WalletDto()
                    .setWalletLevel(level)
                    .setUserName(userDto.getUsername());
            return paymentService.createUsersWallet(walletDto);
        }
        return null;
    }
}
