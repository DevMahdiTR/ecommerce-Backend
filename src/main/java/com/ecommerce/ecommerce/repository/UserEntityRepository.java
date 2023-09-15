package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.user.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
@Transactional(readOnly = true)
public interface UserEntityRepository extends JpaRepository<UserEntity ,Integer> {
    @Query(value = "SELECT U FROM UserEntity  U WHERE U.id = :id")
    Optional<UserEntity> fetchUserWithId(@Param("id") UUID id);
    @Query(value = "SELECT U FROM UserEntity U WHERE  U.email = :email ")
    Optional<UserEntity> fetchUserWithEmail(@Param("email") String email);

    @Query("SELECT U FROM UserEntity U " +
            "WHERE (COALESCE(:fullName, U.firstName) IS NULL OR LOWER(U.firstName) LIKE CONCAT('%', LOWER(:firstName), '%')) " +
            "AND (COALESCE(:address, U.address) IS NULL OR LOWER(U.address) LIKE CONCAT('%', LOWER(:address), '%')) " +
            "AND (COALESCE(:lastName, U.lastName) IS NULL OR LOWER(U.lastName) LIKE CONCAT('%', LOWER(:lastName), '%')) " +
            "AND (COALESCE(:email, U.email) IS NULL OR LOWER(U.email) LIKE CONCAT('%', LOWER(:email), '%')) " +
            "AND (COALESCE(:phoneNumber, U.phoneNumber) IS NULL OR LOWER(U.phoneNumber) LIKE CONCAT('%', LOWER(:phoneNumber), '%')) " +
            "AND U.role.name != 'ADMIN'")
    List<UserEntity> searchUsers(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("address") String address,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber
    );
    @Query(value = "SELECT U FROM UserEntity U where U.role.name != 'ADMIN' order by U.id ")
    List<UserEntity> fetchAllUsers(Pageable pageable);
    @Query(value = "SELECT EXISTS(SELECT U FROM UserEntity U WHERE  U.email = :email) AS RESULT")
    Boolean isEmailRegistered(@Param("email")String email);
    @Query(value = "SELECT EXISTS(SELECT U FROM UserEntity U WHERE  U.phoneNumber = :phoneNumber) AS RESULT")
    Boolean isPhoneNumberRegistered(@Param("phoneNumber")String phoneNumber);
    @Query(value = "SELECT COUNT(U) FROM UserEntity U")
    long getTotalUserEntityCount();
}
