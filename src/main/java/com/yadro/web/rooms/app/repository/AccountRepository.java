package com.yadro.web.rooms.app.repository;

import java.io.Serializable;
import java.util.List;

import com.yadro.web.rooms.app.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yadro.web.rooms.app.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Serializable> {
    Account findOneByUserName(String name);
    Account findOneByEmail(String email);
    Account findOneByUserNameOrEmail(String username, String email);
    Account findOneByToken(String token);
    Account findOneByUniversity(University university);
    List<Account> findByUniversity(University university);
    
    @Modifying
    @Transactional
    @Query("update Account a set a.email = :email, a.firstName = :firstName, "
            + "a.lastName = :lastName, a.address = :address, a.groupName = :groupName, "
    		+ "a.role = :role, a.university = :university "
            + "where a.userName = :userName")
    int updateUser(
            @Param("userName") String userName, 
            @Param("email") String email,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("address") String address,
            @Param("groupName") String groupName,
    		@Param("role") String role,
    		@Param("university") University university);
    @Modifying
    @Transactional
    @Query("update Account a set a.email = :email, a.firstName = :firstName, "
            + "a.lastName = :lastName, a.address = :address, a.groupName = :groupName, "
    		+ "a.password = :password, a.role = :role, a.university = :university "
            + "where a.userName = :userName")
    int updateUserWithPassword(
            @Param("userName") String userName, 
            @Param("email") String email,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("address") String address,
            @Param("groupName") String groupName,
    		@Param("password") String password,
    		@Param("role") String role,
    		@Param("university") University university);
    
    @Modifying
    @Transactional
    @Query("update Account a set a.lastLogin = CURRENT_TIMESTAMP where a.userName = ?1")
    int updateLastLogin(String userName);

    @Modifying
    @Transactional
    @Query("update Account a set a.countEvent = :countEvent where a.userName = :userName")
    int updateCountEvent(
            @Param("userName") String userName,
            @Param("countEvent") int countEvent);
}