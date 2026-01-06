package com.example.desafio_picpay_simplificado.service;

import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;
import com.example.desafio_picpay_simplificado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Transação não autorizada.");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente.");
        }

    }

    //evitar que transaction service tenha acesso ao repo de user
    public User findUserById(Long id) throws Exception{
        return this.userRepository.findUserById(id)
                .orElseThrow(()->new Exception("Usuário não encontrado"));
    }

    public void saveUser(User user){
        userRepository.save(user);
    }


}
