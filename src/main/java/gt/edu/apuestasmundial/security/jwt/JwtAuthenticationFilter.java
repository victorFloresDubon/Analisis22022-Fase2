package gt.edu.apuestasmundial.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    public static final String PREFIJO_HEADER = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try{
            String token = getTokenFromRequest((HttpServletRequest) request);
            log.info("Extracting token from HttpServletRequest: {}", token);

            if(token != null && jwtProvider.validarToken(token)){
                Authentication auth = jwtProvider.getAuthentication(token);

                if(auth != null && !(auth instanceof AnonymousAuthenticationToken)){
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e){
            log.error("Fallo en método dofilter {} ", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header != null && header.startsWith(PREFIJO_HEADER)){
            return header.replace(PREFIJO_HEADER, "");
        } else {
            return null;
        }
    }
}
