package com.mateusjose98.management.commons.config;


import com.mateusjose98.management.model.Role;
import com.mateusjose98.management.model.User;
import com.mateusjose98.management.model.UserPrincipal;
import com.mateusjose98.management.repository.RoleRepository;
import com.mateusjose98.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final BCryptPasswordEncoder encoder;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final UserRepository<User> userRepository;
    private final RoleRepository<Role> roleRepository;

    private static final String[] PUBLIC_URLS = { "/users/verify/password/**",
            "/users/login/**", "/users/verify/code/**", "/users/register/**", "/users/resetpassword/**", "/users/verify/account/**",
            "/users/refresh/token/**", "/users/image/**", "/users/new/password/**", "/v3/api-docs/**", "/swagger-ui/**" };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http.csrf((csrf) -> csrf.disable());

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(PUBLIC_URLS).permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(HttpMethod.DELETE, "/users/delete/**").hasAnyAuthority("DELETE:USER")
                .requestMatchers(HttpMethod.DELETE, "/customer/delete/**").hasAnyAuthority("DELETE:CUSTOMER")
                .anyRequest().authenticated());
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler);
        });

        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(username -> {
            User user = userRepository.getUserByEmail(username);
            if(user == null) throw new UsernameNotFoundException("Usuário não existe!");
            return new UserPrincipal(user, roleRepository.getRoleByUserId(user.getId()));
        });
        authProvider.setPasswordEncoder(encoder);
        return new ProviderManager(authProvider);
    }

}
