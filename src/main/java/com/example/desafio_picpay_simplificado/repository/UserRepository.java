package com.example.desafio_picpay_simplificado.repository;

import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByDocument(String document);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);
    void saveUser(User user);
    @Query("SELECT new com.example.desafio_picpay_simplificado.dto.UserDTO(u.firstName, u.lastName, u.document, u.balance, u.email, u.password, u.userType) " +
            "FROM users u")
    List<UserDTO> findAllAsDTO();

}
