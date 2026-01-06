package com.example.desafio_picpay_simplificado.service;

import com.example.desafio_picpay_simplificado.dto.TransactionDTO;
import com.example.desafio_picpay_simplificado.model.transaction.Transaction;
import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    public TransactionService(UserService userService, TransactionRepository transactionRepository, RestTemplate restTemplate) {
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    public void createTransaction(TransactionDTO transaction) throws Exception{
        User sender = this.userService.findUserById(transaction.sender());
        User receiver = this.userService.findUserById(transaction.receiver());
        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());

        userService.validateTransaction(sender, transaction.value() );

        if(!isAuthorized){
            throw new Exception("Transação nao autorizada.");
        }



    }

    public boolean authorizeTransaction(User sender, BigDecimal value){

        ResponseEntity<Map> authorized = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if(authorized.getStatusCode() == HttpStatus.OK ){
//            String message = (String) authorized.getBody().get("message");
//
//            return "Autorizado".equalsIgnoreCase(message);
            Map<String, Object> data = (Map<String, Object>) authorized.getBody().get("data");
            return data != null && Boolean.TRUE.equals(data.get("authorization"));
            }
            return false;

    }
}
