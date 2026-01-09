package com.example.desafio_picpay_simplificado.service;

import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.exceptions.OperacaoNaoPermitidaException;
import com.example.desafio_picpay_simplificado.exceptions.RecursoNaoEncontradoException;
import com.example.desafio_picpay_simplificado.exceptions.SaldoInsuficienteException;
import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;
import com.example.desafio_picpay_simplificado.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new OperacaoNaoPermitidaException("Transação não autorizada, usuário lojista.");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }

    }

    //evitar que transaction service tenha acesso ao repo de user
    public User findUserById(Long id) throws Exception{
        return this.userRepository.findUserById(id)
                .orElseThrow(()->new RecursoNaoEncontradoException("Usuário não encontrado | Id: "+id));
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User createUser(UserDTO userDTO){
        User user = new User(userDTO);
        this.saveUser(user);
        return user;

    }

    public UserDTO updateUser(Long id, UserDTO userDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setBalance(userDTO.balance());
        user.setDocument(userDTO.document());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());

        userRepository.save(user);

        return UserDTO.fromEntity(user);

    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAllAsDTO();
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        return UserDTO.fromEntity(user);
    }


    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException
                        ("Usuário não encontrado."));

        userRepository.delete(user);
    }

}
