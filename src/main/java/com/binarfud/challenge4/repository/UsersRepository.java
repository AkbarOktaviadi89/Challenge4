package com.binarfud.challenge4.repository;

import com.binarfud.challenge4.model.Product;
import com.binarfud.challenge4.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true,
            value = "select * from users order by user_id asc")
    List<Users> getAllUsersAvailable();
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "UPDATE users SET username = :username, email_address = :email ,password = :password WHERE user_id = :user_id")
    int updateById(@Param("user_id") Long user_id, @Param("username") String username, @Param("email") String email, @Param("password") String password);

    @Query(nativeQuery = true, value = "select * from users")
    Page<Users> findAllWithPaging(Pageable pageable);
}
