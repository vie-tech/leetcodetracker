package com.leetTracker.leetcodeTracker.service;


import com.leetTracker.leetcodeTracker.dto.LoginUserRequest;
import com.leetTracker.leetcodeTracker.dto.RegisterUserRequest;
import com.leetTracker.leetcodeTracker.model.UserAccount;
import com.leetTracker.leetcodeTracker.repository.UserAccountRepository;
import com.leetTracker.leetcodeTracker.utilities.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    public void registerUserAccount(RegisterUserRequest request) {

        UserAccount user = UserAccount.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userAccountRepository.save(user);
        emailService.sendSignupNotificationToUser(); //Add params here and
        // initialize
    }

    public String loginUser(LoginUserRequest request) {

        Authentication auth =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserAccount user = (UserAccount) auth.getPrincipal();
        return jwtUtil.generateToken(user.getUsername(),
                user.getUserPublicID());
    }

    public UserAccount getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserAccount) authentication.getPrincipal();
        }
        return null;
    }

}
