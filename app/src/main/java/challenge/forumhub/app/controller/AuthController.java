package challenge.forumhub.app.controller;

import challenge.forumhub.app.controller.doc.AuthControllerDoc;
import challenge.forumhub.app.dto.auth.LoginRequestDTO;
import challenge.forumhub.app.dto.auth.RegisterRequestDTO;
import challenge.forumhub.app.dto.auth.TokenResponseDTO;
import challenge.forumhub.app.dto.user.UserDetailsDTO;
import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.mapper.UserMapper;
import challenge.forumhub.app.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDetailsDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        User createdUser = authenticationService.register(registerRequestDTO);
        return ResponseEntity.ok(userMapper.toDetailsDTO(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        String token = authenticationService.authenticate(
                loginRequestDTO.email(), loginRequestDTO.password());
        return ResponseEntity.ok(new TokenResponseDTO(token, 864000));
    }
}
