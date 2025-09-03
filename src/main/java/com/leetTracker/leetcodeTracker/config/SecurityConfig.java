package com.leetTracker.leetcodeTracker.config;


import com.leetTracker.leetcodeTracker.filter.JwtAuthFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                 .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for
                 // stateless API
                 .cors(Customizer.withDefaults())
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/api/user/login", "/api/user" +
                                 "/register", "/api/auth/validate/session",
                                 "/wss/**")
                         .permitAll()
                         .anyRequest()
                         .authenticated()
                 )
                 .exceptionHandling(ex -> ex
                         .authenticationEntryPoint((request, response,
                                                    authException) ->
                                 response.sendError(401, "Unauthorized"))
                 );

         http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
         http.addFilterBefore(jwtAuthFilter,
                 UsernamePasswordAuthenticationFilter.class);


         return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
       public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
           DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
           provider.setUserDetailsService(userDetailsService);
           provider.setPasswordEncoder(passwordEncoder);
           return provider;
       }


       @Bean
       AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
           return config.getAuthenticationManager();
       }


}
