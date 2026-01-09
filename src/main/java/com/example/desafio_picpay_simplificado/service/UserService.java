package com.example.desafio_picpay_simplificado.service;

import com.example.desafio_picpay_simplificado.dto.UserDTO;
import com.example.desafio_picpay_simplificado.dto.UserDTOResponse;
import com.example.desafio_picpay_simplificado.exceptions.*;
import com.example.desafio_picpay_simplificado.model.user.User;
import com.example.desafio_picpay_simplificado.model.user.UserType;
import com.example.desafio_picpay_simplificado.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public User saveUser(User user){
        userRepository.saveUser(user);
        return user;
    }

    public UserDTOResponse createUser(UserDTO userDTO) throws Exception{
        User user = new User(userDTO);

        validateEmail(userDTO);
        validateDocument(userDTO);

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        User savedUser = this.saveUser(user);

        return UserDTOResponse.fromEntity(savedUser);

    }

    public void validateEmail(UserDTO userDTO) throws Exception{
        if(userRepository.findUserByEmail(userDTO.email()).isPresent()) {
            throw new EmailJaCadastradoException("Este e-mail já está cadastrado.");
        }
    }

    public void validateDocument(UserDTO userDTO) throws Exception{
        if(userRepository.findUserByDocument(userDTO.document()).isPresent()) {
            throw new CPFOuCNPJJaCadastradoException("Este CPF/CNPJ já está cadastrado.");
        }
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


    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        return UserDTO.fromEntity(user);
    }
}
