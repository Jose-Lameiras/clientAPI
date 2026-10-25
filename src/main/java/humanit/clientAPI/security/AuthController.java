package humanit.clientAPI.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import humanit.clientAPI.dto.LoginRequestDTO;
import humanit.clientAPI.exception.ApiException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtEncoder jwtEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                Instant now = Instant.now();
                JwsHeader header = JwsHeader.with(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build();
                JwtClaimsSet claims = JwtClaimsSet.builder()
                        .issuer("self")
                        .issuedAt(now)
                        .expiresAt(now.plus(1, ChronoUnit.HOURS))
                        .subject(userDetails.getUsername())
                        .claim("roles", userDetails.getAuthorities())
                        .build();

                String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();

                return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", username
                ));
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiException(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "Invalid credentials", "/api/auth/login")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "An error occurred during login", "/api/auth/login")
            );
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ApiException(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", "Authentication failed", "/api/auth/login")
        );
    }
}
