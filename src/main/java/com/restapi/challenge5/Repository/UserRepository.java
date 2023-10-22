package com.restapi.challenge5.Repository;

import com.restapi.challenge5.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true,
            value = "select * from users order by user_id asc")
    List<Users> getAllUsersAvailable();
    @Query(nativeQuery = true,
            value = "SELECT u FROM users u WHERE u.username = :username")
    List<Users> findByUsername(@Param("username") String username);
}
