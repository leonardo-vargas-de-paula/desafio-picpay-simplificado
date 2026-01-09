package com.example.desafio_picpay_simplificado.service;

import com.example.desafio_picpay_simplificado.dto.NotificationDTO;
import com.example.desafio_picpay_simplificado.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;



    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(User user, String message) throws Exception{
        String email = user.getEmail();
        NotificationDTO notificationDTO = new NotificationDTO(email, message);
        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify/", notificationDTO, String.class);
        System.out.println("erro ao enviar notificacao");

        if (!(notificationResponse.getStatusCode() == HttpStatus.OK)){
            throw new Exception("Serviço indisponível.");
        }



    }
}
