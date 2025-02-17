package com.systango.springbutt.api.v1.controller;

import com.systango.springbutt.api.v1.request.WalletRequest;
import com.systango.springbutt.dto.model.payment.WalletDto;
import com.systango.springbutt.dto.response.Response;
import com.systango.springbutt.mapper.WalletMapper;
import com.systango.springbutt.service.exception.PaymentException;
import com.systango.springbutt.service.payment.PaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Arpit Khandelwal.
 */
@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/registerwallet")
    public ResponseEntity<Object> registerWallet(@RequestBody @Valid WalletRequest walletRequest) throws PaymentException {
        WalletDto walletDto = new WalletDto()
                .setUserName(walletRequest.getUsername())
                .setWalletLevel(walletRequest.getWalletLevel());
        paymentService.createUsersWallet(walletDto);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/wallet/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> getWalletDetails(@PathVariable String username) throws PaymentException {
        return Response.ok().setPayload(WalletMapper.mapWallet(paymentService.getWalletDetails(username)));
    }

}
