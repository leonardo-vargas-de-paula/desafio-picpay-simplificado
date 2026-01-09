package com.example.desafio_picpay_simplificado.service;

import com.example.desafio_picpay_simplificado.dto.TransactionDTO;
import com.example.desafio_picpay_simplificado.exceptions.TransacaoNaoAutorizadaException;
import com.example.desafio_picpay_simplificado.model.transaction.Transaction;
import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;
    private final NotificationService notificationService;

    public TransactionService(UserService userService, TransactionRepository transactionRepository, RestTemplate restTemplate, NotificationService notificationService) {
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
        this.notificationService = notificationService;
    }

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception{
        User sender = this.userService.findUserById(transactionDTO.sender());
        User receiver = this.userService.findUserById(transactionDTO.receiver());
        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value());
        String messageReceiver = "Transação recebida.";
        String messageSender = "Transação efetuada.";

        if(!isAuthorized){
            throw new TransacaoNaoAutorizadaException("Transação nao autorizada.");
        }

        userService.validateTransaction(sender, transactionDTO.value() );

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.transactionRepository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        notificationService.sendNotification(receiver, messageReceiver);
        notificationService.sendNotification(sender, messageSender);

        return transaction;

    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        try {
            ResponseEntity<Map> authorized = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

            if (authorized.getStatusCode() == HttpStatus.OK) {
                //            String message = (String) authorized.getBody().get("message");
                //
                //            return "Autorizado".equalsIgnoreCase(message);
                Map<String, Object> data = (Map<String, Object>) authorized.getBody().get("data");
                return data != null && Boolean.TRUE.equals(data.get("authorization"));
            }
            return false;


        }catch(HttpClientErrorException e){

            return false;
        }catch (Exception e){

            return false;
        }


        }
}

