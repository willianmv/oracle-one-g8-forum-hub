package challenge.forumhub.app.service;

import challenge.forumhub.app.dto.auth.RegisterRequestDTO;
import challenge.forumhub.app.entity.Profile;
import challenge.forumhub.app.entity.Role;
import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.exception.ResourceAlreadyExistsException;
import challenge.forumhub.app.exception.ResourceNotFoundException;
import challenge.forumhub.app.repository.ProfileRepository;
import challenge.forumhub.app.repository.UserRepository;
import challenge.forumhub.app.security.ForumUserDetails;
import challenge.forumhub.app.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User register(RegisterRequestDTO requestDto) {
        if(userExistsByEmail(requestDto.email()))
            throw new ResourceAlreadyExistsException(
                    "Já existe um usuário registrado com o e-mail: "+requestDto.email());

        Profile profile = profileRepository.findByRoleAndActiveTrue(Role.MEMBER)
                .orElseThrow(() -> new ResourceNotFoundException("Profile não encontrado"));

        User newUser = User.builder()
                .name(requestDto.name())
                .email(requestDto.email())
                .password(passwordEncoder.encode(requestDto.password()))
                .profiles(Set.of(profile))
                .active(true)
                .build();
        return userRepository.save(newUser);
    }

    public String authenticate(String email, String password) {
        Authentication authenticateUser = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = (ForumUserDetails) authenticateUser.getPrincipal();
        var claims = getExtraClaims(user);
        return jwtService.buildToken(claims, user);
    }

    private Map<String, Object> getExtraClaims(ForumUserDetails userDetails){
        var claims = new HashMap<String, Object>();
        claims.put("id", userDetails.getId());
        return claims;
    }

    private boolean userExistsByEmail(String email){
        return userRepository.existsByEmailAndActiveTrue(email);
    }
}
