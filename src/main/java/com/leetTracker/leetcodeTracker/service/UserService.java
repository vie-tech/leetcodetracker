package com.leetTracker.leetcodeTracker.service;


import com.leetTracker.leetcodeTracker.dto.LoginUserRequest;
import com.leetTracker.leetcodeTracker.dto.RegisterUserRequest;
import com.leetTracker.leetcodeTracker.model.UserAccount;
import com.leetTracker.leetcodeTracker.repository.UserAccountRepository;
import com.leetTracker.leetcodeTracker.utilities.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void registerUserAccount(RegisterUserRequest request, HttpServletResponse response) {

        UserAccount user = UserAccount.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userAccountRepository.save(user);
        String token   = jwtUtil.generateToken(user.getUsername(), user.getUserPublicID());
        Cookie cookie = new Cookie("custom_access_cookie", token);
        response.addCookie(cookie);
        emailService.sendSignupNotificationToUser(); //Add params here and
        // initialize
    }

    public void loginUser(LoginUserRequest request,
                          HttpServletResponse response) {

        Authentication auth =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserAccount user = (UserAccount) auth.getPrincipal();
        String token = jwtUtil.generateToken(user.getUsername(),
                user.getUserPublicID());
        Cookie cookie = new Cookie("custom_access_cookie", token);
        response.addCookie(cookie);
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
