package com.systango.springbutt.domain.repository;

import com.systango.springbutt.domain.model.payment.WalletDetails;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface WalletRepository extends CrudRepository<WalletDetails, Long> {
    /**
     * List the wallet details for a given userID
     *
     * @param userID
     * @return
     */
    WalletDetails findByApplicationUser_Id(Long userID);
}
