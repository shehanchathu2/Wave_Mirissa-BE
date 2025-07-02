    package com.wave.Mirissa.Configurations;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
    import static org.springframework.security.config.Customizer.withDefaults;

    import java.util.List;

    import static org.springframework.security.config.Customizer.withDefaults;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class WebSecurityConfiguration {

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    //    @Bean
    //    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //        http
    //                .cors(withDefaults())
    //                .securityMatcher(new AntPathRequestMatcher("/**")) // optional: limits to match only certain paths
    //                .csrf(csrf -> csrf.disable())
    //                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //                .authorizeHttpRequests(auth -> auth
    //                        .requestMatchers("/register","/authentication","/users","/user/{id}","/product/**","/product/addproducts").permitAll()
    //                        .requestMatchers("/api/**").authenticated()
    //                );
    //        return http.build();
    //    }


        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .cors(withDefaults())
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/register",
                                    "/authentication",
                                    "/users",
                                    "/user/*",
                                    "/product/**",
                                    "/product/delete/**",
                                    "/AddCustomizations",
                                    "/Customizations/**",
                                    "/customizations",
                                    "/AllCustomizations",
                                    "/api/payments/**",
                                    "/api/payhere/hash"

                            ).permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .anyRequest().authenticated()
                    );

            return http.build();
        }


        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:8080"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        }









    }
