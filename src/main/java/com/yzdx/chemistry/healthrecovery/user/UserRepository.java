package com.yzdx.chemistry.healthrecovery.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByUserIdAndPassword(String userId, String password);

    @Transactional
    @Query(value="update users set photo=:photo where user_id=:userId", nativeQuery=true)
    @Modifying
    public void updatePhoto(@Param("userId") String userId, @Param("photo") String photo);
}