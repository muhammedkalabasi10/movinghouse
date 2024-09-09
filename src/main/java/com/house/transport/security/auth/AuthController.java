package com.house.transport.security.auth;

import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import com.house.transport.security.token.RefreshToken;
import com.house.transport.security.token.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register/customer")
    public AuthResponse registerCustomer(@RequestBody Customer customer){
        Customer savedCustomer = authService.saveCustomer(customer);
        String token = authService.generateToken(savedCustomer.getEmail(), "CUSTOMER");
        RefreshToken refreshToken = refreshTokenService.createRefreshTokenForCustomer(savedCustomer);
        return new AuthResponse(token, refreshToken.getToken());
    }

    @PostMapping("/register/mover")
    public AuthResponse registerMover(@RequestBody Mover mover){
        Mover savedMover = authService.saveMover(mover);
        String token = authService.generateToken(savedMover.getEmail(), "MOVER");
        RefreshToken refreshToken = refreshTokenService.createRefreshTokenForMover(savedMover);
        return new AuthResponse(token, refreshToken.getToken());
    }

    @PostMapping("/authenticate/customer")
    public AuthResponse authenticateCustomer(@RequestBody AuthRequest authRequest){
        return authService.authenticateAndGenerateToken(authRequest.getEmail(), authRequest.getPassword(), "CUSTOMER");
    }

    @PostMapping("/authenticate/mover")
    public AuthResponse authenticateMover(@RequestBody AuthRequest authRequest){
        return authService.authenticateAndGenerateToken(authRequest.getEmail(), authRequest.getPassword(), "MOVER");
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestBody String refreshToken){
        if(refreshTokenService.validateRefreshToken(refreshToken)){
            RefreshToken token = refreshTokenService.findToken(refreshToken);
            String newJwtToken = "";
            if(token.getCustomer() != null){
                newJwtToken = authService.generateToken(token.getCustomer().getEmail(), "CUSTOMER");
            }else if(token.getMover() != null){
                newJwtToken = authService.generateToken(token.getMover().getEmail(), "MOVER");
            }
            return new AuthResponse(newJwtToken, refreshToken);
        }else{
            throw new RuntimeException("Refresh token is expired");
        }
    }
    @PostMapping("/logoutuser")
    public ResponseEntity<String> logout(@RequestBody String refreshToken) {
        try {
            if (refreshTokenService.deleteRefreshToken(refreshToken)){
                return ResponseEntity.ok("Logout successful");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logout failed");
        }
    }
}
