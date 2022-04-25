/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.configuration;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    @Value("${VALID_AUTH_TOKEN:valid_token}")
    private String validAuthToken;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean isApiCall = request.getRequestURI().startsWith("/api/");
        String requestAuthToken = request.getHeader("Authorization");
        log.info(
                String.format(
                        "Request URI: %s. Request Auth Token: %s",
                        request.getRequestURI(), requestAuthToken));

        if (isApiCall && !Objects.equals(requestAuthToken, validAuthToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
