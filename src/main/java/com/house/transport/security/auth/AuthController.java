package com.house.transport.security.auth;

import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import com.house.transport.repository.CustomerRepository;
import com.house.transport.repository.MoverRepository;
import com.house.transport.security.token.RefreshToken;
import com.house.transport.security.token.RefreshTokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MoverRepository moverRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;

    private static final Logger logger = LogManager.getLogger(AuthController.class);
    @PostMapping("/register/customer")
    public AuthResponse registerCustomer(@RequestBody Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        String token = generateToken(customer.getEmail(), "CUSTOMER");
        RefreshToken refreshToken = refreshTokenService.createRefreshTokenForCustomer(customer);
        return new AuthResponse(token, refreshToken.getToken());
    }

    @PostMapping("/register/mover")
    public AuthResponse registerMover(@RequestBody Mover mover){
        mover.setPassword(passwordEncoder.encode(mover.getPassword()));
        moverRepository.save(mover);
        String token = generateToken(mover.getEmail(), "MOVER");
        RefreshToken refreshToken = refreshTokenService.createRefreshTokenForMover(mover);
        return new AuthResponse(token, refreshToken.getToken());
    }

    @PostMapping("/authenticate/customer")
    public AuthResponse authenticateCustomer(@RequestBody AuthRequest authRequest){
        return authenticateAndGenerateToken(authRequest.getEmail(), authRequest.getPassword(), "CUSTOMER");
    }

    @PostMapping("/authenticate/mover")
    public AuthResponse authenticateMover(@RequestBody AuthRequest authRequest){
        return authenticateAndGenerateToken(authRequest.getEmail(), authRequest.getPassword(), "MOVER");
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestBody String refreshToken){
        if(refreshTokenService.validateRefreshToken(refreshToken)){
            RefreshToken token = refreshTokenService.findToken(refreshToken);
            String newJwtToken = "";
            if(token.getCustomer() != null){
                newJwtToken = generateToken(token.getCustomer().getEmail(), "CUSTOMER");
            }else if(token.getMover() != null){
                newJwtToken = generateToken(token.getMover().getEmail(), "MOVER");
            }
            return new AuthResponse(newJwtToken, refreshToken);
        }else{
            throw new RuntimeException("Refresh token is expired");
        }
    }

    private AuthResponse authenticateAndGenerateToken(String email, String password, String role){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        String token = generateToken(email, role);
        logger.info("[AuthController][authenticateAndGenerateToken] Access Token: " + token);
        logger.info("[AuthController][authenticateAndGenerateToken] Role: " + role);
        RefreshToken refreshToken = null;
        if(role.equals("CUSTOMER")){
            Customer customer = customerRepository.findByEmail(email).orElseThrow();
            logger.info("[AuthController][authenticateAndGenerateToken] Customer Name: " + customer.getName());
            refreshToken = refreshTokenService.createRefreshTokenForCustomer(customer);
        }else if(role.equals("MOVER")){
            Mover mover = moverRepository.findByEmail(email).orElseThrow();
            logger.info("[AuthController][authenticateAndGenerateToken] Customer Name: " + mover.getUsername());
            refreshToken = refreshTokenService.createRefreshTokenForMover(mover);
        }
        logger.info("[AuthController][authenticateAndGenerateToken] Refresh Token: " + refreshToken);
        return new AuthResponse(token, refreshToken.getToken());

    }
    private String generateToken(String email, String role){
        Instant now = Instant.now();
        long expiry = 3600L;
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(email)
                .claim("roles", role)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
