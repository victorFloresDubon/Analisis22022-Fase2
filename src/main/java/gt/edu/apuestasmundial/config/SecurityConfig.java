package gt.edu.apuestasmundial.config;

import gt.edu.apuestasmundial.repository.UsuarioRepository;
import gt.edu.apuestasmundial.security.jwt.JwtAuthenticationFilter;
import gt.edu.apuestasmundial.security.jwt.JwtEntryPoint;
import gt.edu.apuestasmundial.security.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    JwtEntryPoint jwtEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtProvider tProvider) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(c -> c.authenticationEntryPoint(jwtEntryPoint))
                .authorizeRequests(c -> c
                        .antMatchers("/**/auth/**","/**/cloudinary/upload/**").permitAll()
                        .antMatchers("/**/swagger-ui/**",
                                "/**/swagger-resources/**",
                                "/**/v3/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(tProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    UserDetailsService customUserDetailService(UsuarioRepository usuarios){
        return (username) ->
            usuarios.findByNombre(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario: "+username+" no encontrado!"));

    }

    @Bean
    AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder encoder){
        return authentication -> {
            String username = String.valueOf(authentication.getPrincipal());
            String password = String.valueOf(authentication.getCredentials());

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(!encoder.matches(password, userDetails.getPassword())){
                throw new BadCredentialsException("Credenciales erróneas");
            }

            if(!userDetails.isEnabled()){
                throw  new DisabledException("Cuenta de usuario no está activa");
            }

            return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
        };

    }
}
