package challenge.forumhub.app.service;

import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.exception.ResourceNotFoundException;
import challenge.forumhub.app.repository.UserRepository;
import challenge.forumhub.app.security.ForumUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserService {

    private final UserRepository userRepository;

    public User getAuthenticatedUserEntity(){
        long id = getAuthenticatedUserId();
        return userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado pelo ID: "+id));
    }

    public boolean hasAnyRole(String... roles){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        Arrays.stream(roles)
                                .anyMatch(role -> authority.getAuthority()
                                        .equals("ROLE_"+ role)));
    }

    public void verifyOwnerOrModeratorOrAdmin(long resourceOwnerId){
        long authenticatedUserId = getAuthenticatedUserId();
        if(authenticatedUserId != resourceOwnerId && !hasAnyRole("MODERATOR", "ADMIN")){
            throw new AuthorizationDeniedException("É necessário ser dono do recurso ou ter perfil de MODERATOR/ADMIN para acessar");
        }
    }

    public long getAuthenticatedUserId(){
        ForumUserDetails userDetails = getAuthenticatedUserDetails();
        return userDetails.getId();
    }

    public String getAuthenticatedUserEmail(){
        ForumUserDetails userDetails = getAuthenticatedUserDetails();
        return userDetails.getUsername();
    }

    private ForumUserDetails getAuthenticatedUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ForumUserDetails) authentication.getPrincipal();
    }

}
