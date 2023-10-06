package com.binarfud.challenge4.service;

import com.binarfud.challenge4.model.Product;
import com.binarfud.challenge4.model.Users;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<Users> findAllUsers();
    Boolean addNewUser(Users users);

    void deleteUser(Long users_id);

    Boolean updateUserbyId(Long user_id, String username, String email, String password);
    Page<Users> getUserPaged(int page);
}
