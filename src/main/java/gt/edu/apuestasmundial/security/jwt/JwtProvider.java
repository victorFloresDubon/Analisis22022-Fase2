package gt.edu.apuestasmundial.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "roles";

    private final JwtProperties properties;
    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        var secret = Base64.getEncoder().encodeToString(this.properties.getSecretKey().getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String crearToken(Authentication authentication){
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Claims claims = Jwts.claims().setSubject(username);
        if(!authorities.isEmpty()){
            claims.put(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        }

        Date ahora = new Date();
        // El tiempo de validez será de 20 minutos
        Date expiracion = new Date(ahora.getTime() + this.properties.getExpiration() * 20);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(this.secretKey, SignatureAlgorithm.HS256)
                .compact();

    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();

        Object authoritiesClaim = claims.get(AUTHORITIES_KEY);

        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validarToken(String token){
        try {
            Jws<Claims> claims = Jwts
                    .parserBuilder().setSigningKey(this.secretKey).build()
                    .parseClaimsJws(token);
            // parseClaimsJws verificará la fecha de expiración
            log.info("fecha de expiración: {}", claims.getBody().getExpiration());
            return true;
        } catch(JwtException | IllegalArgumentException e){
            log.error("Token JWT inválido {}", e.getMessage());
        }
        return false;
    }
}
