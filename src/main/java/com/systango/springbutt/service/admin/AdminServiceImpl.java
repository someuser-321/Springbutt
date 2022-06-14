package com.systango.springbutt.service.admin;

import com.systango.springbutt.domain.model.Faq;
import com.systango.springbutt.domain.repository.FaqRepository;
import com.systango.springbutt.dto.model.FaqDto;
import com.systango.springbutt.service.exception.AdminException;
import com.systango.springbutt.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Arpit Khandelwal.
 */
@Component
public class AdminServiceImpl implements AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final FaqRepository faqRepository;

    @Autowired
    public AdminServiceImpl(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Override
    @Transactional
    public String addFaq(FaqDto faqDto) throws AdminException {
        AssertUtils.notNull(faqDto, faqDto.getQuestion());

        Optional<Faq> faq = Optional.ofNullable(faqRepository.findByQuestion(faqDto.getQuestion()));
        if (faq.isPresent()) {
            LOGGER.debug("FAQ with Question:'{}' and Answer: '{}' already exists", faqDto.getQuestion(), faqDto.getAnswer());
            throw new AdminException(String.format("FAQ with Question:'%s' and Answer: '%s' already exists", faqDto.getQuestion(), faqDto.getAnswer()));
        } else {
            //persist the new faq
            Faq newFaq = new Faq()
                    .setQuestion(faqDto.getQuestion())
                    .setAnswer(faqDto.getAnswer());
            faqRepository.save(newFaq);
        }
        return "FAQ added successfully";
    }
}
