    package com.authx.configurations;

    import java.util.List;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
    import org.springframework.web.filter.CorsFilter;

    import com.authx.jwt.JwtFilter;
    import com.authx.services.AppUserDetailsService;

    import lombok.RequiredArgsConstructor;

    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class SecurityConfig {

        private final AppUserDetailsService appUserDetailsService;

        private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
        private final JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
        {
            http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/register","/send-reset-password","/reset-password","/logout")
                        .permitAll().anyRequest().authenticated())
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class) 
                .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint));
                
            return http.build();           
        }

        @Bean
        public PasswordEncoder passwordEncoder()
        {
            return new BCryptPasswordEncoder();
        } 
    
        @Bean
        public CorsFilter corsFilter(){
            return new CorsFilter(corsConfigurationSource());
        }
    
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:5173"));
            config.setAllowedMethods(List.of("GET", "PUT","POST","DELETE","PATCH","OPTIONS"));
            config.setAllowedHeaders(List.of("Authorization","ContentType"));
            config.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource  source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config); 
            return source;
        }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }

        // @Bean
        // public AuthenticationManager authenticationManager(){
        //     DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //     authenticationProvider.setUserDetailsService(appUserDetailsService);
        //     authenticationProvider.setPasswordEncoder(passwordEncoder());

        //     return new ProviderManager(authenticationProvider);
        // }
    }
