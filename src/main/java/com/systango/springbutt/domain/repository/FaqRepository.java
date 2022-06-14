package com.systango.springbutt.domain.repository;

import com.systango.springbutt.domain.model.Faq;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Arpit Khandelwal.
 */
@Repository
public interface FaqRepository extends CrudRepository<Faq, Long> {

    @Override
    List<Faq> findAll();

    /**
     * Find a FAQ using the question string.
     *
     * @param question
     * @return
     */
    Faq findByQuestion(String question);

}

