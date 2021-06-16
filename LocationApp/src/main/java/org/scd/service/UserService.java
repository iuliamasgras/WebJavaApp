package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.User;
import org.scd.model.dto.LoginCredentialsDTO;
import org.scd.model.dto.RegisterCredentialsDTO;

import java.util.List;

public interface UserService {
    /**
     * Get existing list of users from database
     *
     * @return
     */
    List<User> getUsers();

    /**
     * Login existing user in the application
     *
     * @param loginCredentialsDTO - credentuials DTO
     * @return
     */
    User doLogin(final LoginCredentialsDTO loginCredentialsDTO) throws BusinessException;

    User doRegister(final RegisterCredentialsDTO registerCredentialsDTO) throws BusinessException;
}
