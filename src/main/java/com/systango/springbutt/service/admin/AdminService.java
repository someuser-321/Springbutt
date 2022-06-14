package com.systango.springbutt.service.admin;

import com.systango.springbutt.dto.model.FaqDto;
import com.systango.springbutt.service.exception.AdminException;

/**
 * Created by Arpit Khandelwal.
 */

public interface AdminService {

    /**
     * Add a new FAQ to the existing list of those
     *
     * @param faqDto
     * @return confirmation message
     */
    String addFaq(FaqDto faqDto) throws AdminException;
}
