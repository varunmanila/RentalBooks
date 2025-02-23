package com.example.RentalBooks.Security;

import com.example.RentalBooks.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private String rememberMeKey="mySuperSecureSecretKey123!";
    @Autowired
    @Lazy
    private UsersService usersService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http.csrf(csrf->csrf.disable());
     http.authenticationProvider(authenticationProvider());
     http.authorizeHttpRequests(auth -> auth
                           .requestMatchers("/auth/register", "/auth/login").permitAll()
                            .anyRequest().authenticated());
     http.httpBasic(Customizer.withDefaults());
     return http.build();
    }

    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(usersService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(AbstractHttpConfigurer::disable) // CSRF disabled for APIs
//                    .authorizeHttpRequests(auth -> auth
//                            .requestMatchers("/auth/register", "/auth/login").permitAll()
//                            .anyRequest().authenticated()
//                    )
//                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) // ✅ Maintain session
//                    .formLogin(form -> form
//                            .successHandler((request, response, authentication) -> {
//                                response.setContentType("application/json");
//                                response.getWriter().write("{\"message\": \"Login successful\"}");
//                            })
//                    )
//                    .logout(logout -> logout
//                            .logoutUrl("/auth/logout")
//                            .logoutSuccessUrl("/auth/login?logout")
//                            .invalidateHttpSession(true)
//                            .deleteCookies("JSESSIONID")
//                    );
//            return http.build();
//        }
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
//    CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter();
//    customAuthFilter.setAuthenticationManager(authManager);
//    customAuthFilter.setFilterProcessesUrl("/auth/login");  // Set login URL
//
//    http
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/auth/register", "/auth/login").permitAll()
//                    .anyRequest().authenticated()
//            )
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//            .addFilterAt(customAuthFilter, UsernamePasswordAuthenticationFilter.class)
//            .rememberMe(remember -> remember
//                    .key("rememberMeKey")
//                    .tokenValiditySeconds(604800)
//            )
//            .httpBasic(withDefaults())
//            .logout(logout -> logout
//                    .logoutUrl("/auth/logout")
//                    .logoutSuccessHandler((request, response, authentication) -> {
//                        response.setContentType("application/json");
//                        response.getWriter().write("{\"message\": \"Logout successful\"}");
//                    })
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID")
//            );
//
//    return http.build();
//}


        @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
