package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.User;
import org.scd.model.dto.LoginCredentialsDTO;
import org.scd.model.dto.RegisterCredentialsDTO;
import org.scd.model.security.Role;
import org.scd.repository.RoleRepository;
import org.scd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User doLogin(LoginCredentialsDTO loginCredentialsDTO) throws BusinessException {
        if (Objects.isNull(loginCredentialsDTO)) {
            throw new BusinessException(401,"Body null!");
        }

        if (Objects.isNull(loginCredentialsDTO.getEmail())) {
            throw new BusinessException(400,"Email can not be null!");

        }

        if (Objects.isNull(loginCredentialsDTO.getPassword())) {
            throw new BusinessException(400,"Password can not be null!");
        }

        final User existingUser = userRepository.findByEmail(loginCredentialsDTO.getEmail());
        if (Objects.isNull(existingUser)) {
            throw new BusinessException(401,"Bad credentials!");
        }

        if (!passwordEncoder.matches(loginCredentialsDTO.getPassword(), existingUser.getPassword())) {
            throw new BusinessException(401,"Bad credentials!");
        }

        return existingUser;
    }

    public User doRegister(RegisterCredentialsDTO registerCredentialsDTO) throws BusinessException {
        if (Objects.isNull(registerCredentialsDTO)) {
            throw new BusinessException(401,"Body null!");
        }
        if (Objects.isNull(registerCredentialsDTO.getEmail())) {
            throw new BusinessException(400,"Email can not be null!");
        }
        if (Objects.isNull(registerCredentialsDTO.getPassword())) {
            throw new BusinessException(400,"Password can not be null!");
        }
        if (Objects.isNull(registerCredentialsDTO.getFirstName())) {
            throw new BusinessException(400,"First Name can not be null!");
        }
        if (Objects.isNull(registerCredentialsDTO.getLastName())) {
            throw new BusinessException(400,"Last Name can not be null!");
        }
        final User insertUser = new User();
        insertUser.setEmail(registerCredentialsDTO.getEmail());
        insertUser.setFirstName(registerCredentialsDTO.getFirstName());
        insertUser.setLastName(registerCredentialsDTO.getLastName());
        insertUser.setPassword(passwordEncoder.encode(registerCredentialsDTO.getPassword()));
        final Role basicUserRole = roleRepository.findById(new Long(2)).get();
        insertUser.setRoles(new HashSet(Arrays.asList(basicUserRole)));
        userRepository.save(insertUser);
        return insertUser;
    }
}
