package iuh.fit.fashionecommercewebsitebackend.configs;

import iuh.fit.fashionecommercewebsitebackend.oauth2.Oauth2LoginSuccess;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.UserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailService userDetailService;
    private final Filter filter;
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final Oauth2LoginSuccess oauth2LoginSuccess;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        author -> {
                            author.requestMatchers("/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html").permitAll();
                            author.requestMatchers("/api/v1/auth/**", "/api/v1/room-chats/**").permitAll();
                            author.requestMatchers(HttpMethod.GET,
                                    "/api/v1/products/**",
                                    "/api/v1/addresses/**",
                                    "/api/v1/categories/**",
                                    "/api/v1/product-details/**",
                                    "/api/v1/providers/**",
                                    "/api/v1/colors/**",
                                    "/api/v1/sizes/**",
                                    "/api/v1/vouchers/user/**",
                                    "/api/v1/users/**",
                                    "/api/v1/brands/**",
                                    "/ws/**"
                                    ).permitAll();
                            author.requestMatchers("/api/v1/orders/user/**").hasRole("USER");
                            author.requestMatchers(
                                    "/api/v1/users/**",
                                    "/api/v1/order-details/**",
                                    "/api/v1/messages/**"
                                    ).authenticated();
                            author.anyRequest().hasRole("ADMIN");
                        }
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2-> oauth2.successHandler(oauth2LoginSuccess))
                .exceptionHandling(exHandler -> exHandler
                        .authenticationEntryPoint(authenticationEntryPoint())  // Trả về 401 khi xác thực thất bại
                        .accessDeniedHandler(accessDeniedHandler())  // Trả về 403 khi người dùng không có quyền truy cập
                )
                .build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Mã 403
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Mã 401
        };
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
