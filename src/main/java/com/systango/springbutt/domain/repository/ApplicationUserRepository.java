package com.systango.springbutt.domain.repository;

import com.systango.springbutt.domain.model.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    /**
     * Finds a user by userName field.
     *
     * @param username
     * @return
     */
    ApplicationUser findByUsername(String username);
}
