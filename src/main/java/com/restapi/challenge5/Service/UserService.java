package com.restapi.challenge5.Service;


import com.restapi.challenge5.Model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<Users> findAllUsers();

    List<Users> findByUsername(String username);

    Optional<Users> findById(Long id);
    Boolean addNewUser(Users users);

    void deleteUser(Long users_id);

    void deleteAllUser();

    void updateUserbyId(Users user);
//    Page<User> getUserPaged(int page);
}
