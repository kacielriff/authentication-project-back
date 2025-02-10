package com.kacielriff.authentication_project.service;

import com.kacielriff.authentication_project.dto.*;
import com.kacielriff.authentication_project.entity.User;
import com.kacielriff.authentication_project.enums.EmailType;
import com.kacielriff.authentication_project.enums.TokenType;
import com.kacielriff.authentication_project.exception.ConflictException;
import com.kacielriff.authentication_project.exception.NotFoundException;
import com.kacielriff.authentication_project.exception.UnauthorizedException;
import com.kacielriff.authentication_project.repository.UserRepository;
import com.kacielriff.authentication_project.security.TokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public LoginResponseUserDTO login(LoginUserDTO loginUserDTO) throws Exception {
        User user = userRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("Email não encontrado"));

        if (!user.isActive()) {
            throw new AuthenticationException("Usuário inativo");
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            User authenticatedUser = (User) authentication.getPrincipal();

            String token = tokenService.generateToken(user);

            return new LoginResponseUserDTO(authenticatedUser.getId(), authenticatedUser.getEmail(), token);
        } catch (BadCredentialsException | AuthenticationCredentialsNotFoundException e) {
            throw new UnauthorizedException("Usuário ou senha inválido(s)");
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException("Usuário não encontrado");
        } catch (Exception e) {
            throw new Exception("Erro interno do servidor");
        }
    }

    public MessageDTO create(CreateUserDTO createUserDTO) throws Exception {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new ConflictException("Email já cadastrado");
        }

        User user = new User();

        user.setName(createUserDTO.getName());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setActive(false);

        User savedUser = userRepository.save(user);

        String token = tokenService.generateValidationToken(savedUser, TokenType.ACCOUNT_CONFIRMATION);

        try {
            emailService.generateEmailContent(savedUser.getEmail(), EmailType.ACCOUNT_CONFIRMATION, token);
        } catch (Exception e) {
            throw new Exception("Erro ao enviar email.");
        }

        return new MessageDTO("Um email para confirmação de conta foi encaminhado com sucesso.");
    }

    public MessageDTO forgotPassword(ForgotPasswordDTO forgotPasswordDTO) throws Exception {
        User user = userRepository.findByEmail(forgotPasswordDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        if (!user.isActive()) {
            throw new AuthenticationException("Usuário inativo.");
        }

        String token = tokenService.generateValidationToken(user, TokenType.PASSWORD_RECOVERY);

        try {
            emailService.generateEmailContent(user.getEmail(), EmailType.PASSWORD_RECOVERY, token);
        } catch (Exception e) {
            throw new Exception("Erro ao enviar email.");
        }

        return new MessageDTO("Um email para recuperação de senha encaminhado com sucesso.");
    }

    public MessageDTO recoverPassword(RecoverPasswordDTO recoverPasswordDTO) throws Exception {
        Claims claims = tokenService.decryptToken(recoverPasswordDTO.getToken());

        Long userId;

        try {
            userId = Long.valueOf(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new Exception("Formato do ID inválido.");
        }

        String email = claims.get("email", String.class);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        if (email == null)
            throw new NotFoundException("Usuário não encontrado.");

        // caso em que o email presente no JWT Token seja diferente do email referente ao ID
        if (!user.getEmail().equals(email)) {
            throw new UnauthorizedException("Email inválido.");
        }

        user.setPassword(passwordEncoder.encode(recoverPasswordDTO.getPassword()));
        userRepository.save(user);

        return new MessageDTO("Senha alterada com sucesso.");
    }

    public LoginResponseUserDTO changePassword(ChangePasswordDTO changePasswordDTO) throws Exception {
        User user = userRepository.findById(getIdLoggedUser())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        userRepository.save(user);

        String token = tokenService.generateToken(user);

        return new LoginResponseUserDTO(user.getId(), user.getEmail(), token);
    }

    public MessageDTO confirmAccount(AccountCreationValidationDTO accountCreationValidationDTO) throws Exception {
        Claims claims = tokenService.decryptToken(accountCreationValidationDTO.getToken());

        Long userId;

        try {
            userId = Long.valueOf(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new Exception("Formato do ID inválido.");
        }

        String email = claims.get("email", String.class);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        if (email == null)
            throw new NotFoundException("Usuário não encontrado.");

        // caso em que o email presente no JWT Token seja diferente do email referente ao ID
        if (!user.getEmail().equals(email)) {
            throw new UnauthorizedException("Email inválido.");
        }

        user.setActive(true);
        userRepository.save(user);

        try {
            emailService.generateEmailContent(user.getEmail(), EmailType.REGISTRATION_SUCCESS, "");
        } catch (Exception e) {
            throw new Exception("Erro ao enviar email.");
        }

        return new MessageDTO("Usuário confirmado com sucesso.");
    }

    public LoggedUserDTO getLoggedUser() throws Exception {
        User user = userRepository.findById(getIdLoggedUser())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        return new LoggedUserDTO(user.getId(), user.getName(), user.getEmail());
    }

    public Long getIdLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) throw new RuntimeException("Acesso não autorizado");

        return Long.valueOf((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    protected User getById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }
}
