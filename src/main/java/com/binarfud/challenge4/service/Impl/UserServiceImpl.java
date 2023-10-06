package com.binarfud.challenge4.service.Impl;

import com.binarfud.challenge4.model.Users;
import com.binarfud.challenge4.repository.UsersRepository;
import com.binarfud.challenge4.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<Users> findAllUsers() {
        try {
            log.info("Retrieving all available users.");
            return usersRepository.getAllUsersAvailable();
        } catch (Exception e) {
            log.error("Error while retrieving all users: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean addNewUser(Users users) {
        try {
            log.info("Adding a new user.");
            return Optional.ofNullable(users)
                    .map(newUsers -> usersRepository.save(newUsers))
                    .map(Objects::nonNull)
                    .orElseGet(() -> {
                        log.error("Failed adding a new User");
                        return Boolean.FALSE;
                    });
        } catch (Exception e) {
            log.error("Error while adding a new user: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void deleteUser(Long users_id) {
        try {
            log.info("Deleting user with ID: {}.", users_id);
            usersRepository.deleteById(users_id);
        } catch (Exception e) {
            log.error("Error while deleting user: {}", e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Boolean updateUserbyId(Long user_id, String username, String email, String password) {
        try {
            log.info("Updating user with ID: {}.", user_id);
            int rowsUpdated = usersRepository.updateById(user_id, username, email, password);
            return rowsUpdated > 0;
        } catch (Exception e) {
            log.error("Error while updating user: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Page<Users> getUserPaged(int page) {
        try {
            log.info("Retrieving paged users for page {}.", (page + 1));
            return usersRepository.findAllWithPaging(PageRequest.of(page, 2));
        } catch (Exception e) {
            log.error("Error while retrieving paged users: {}", e.getMessage(), e);
            return Page.empty();
        }
    }
}
