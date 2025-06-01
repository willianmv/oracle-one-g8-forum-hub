package challenge.forumhub.app.config;

import challenge.forumhub.app.entity.Profile;
import challenge.forumhub.app.entity.Role;
import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.repository.ProfileRepository;
import challenge.forumhub.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitializerConfig implements CommandLineRunner {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createProfilesIfNotExist();
        createDefaultUsersIfNotExist();
    }

    private void createProfilesIfNotExist() {
        for (Role role : Role.values()) {
            profileRepository.findByRoleAndActiveTrue(role).orElseGet(() -> {
                Profile profile = new Profile();
                profile.setRole(role);
                profile.setActive(true);
                log.info("Criando perfil: {}", role);
                return profileRepository.save(profile);
            });
        }
    }

    private void createDefaultUsersIfNotExist() {
        createUserIfNotExists(
                "Admin User",
                "admin@forumhub.com",
                Set.of(Role.ADMIN, Role.MODERATOR, Role.MEMBER)
        );

        createUserIfNotExists(
                "Moderator User",
                "moderator@forumhub.com",
                Set.of(Role.MODERATOR, Role.MEMBER)
        );

        createUserIfNotExists(
                "Regular User",
                "member@forumhub.com",
                Set.of(Role.MEMBER)
        );
    }

    private void createUserIfNotExists(String name, String email, Set<Role> roles) {
        if (userRepository.existsByEmailAndActiveTrue(email)) {
            log.info("Usuário já existe: {}", email);
            return;
        }

        log.info("Criando usuário: {} ({})", name, email);
        Set<Profile> profiles = new HashSet<>();
        for (Role role : roles) {
            profileRepository.findByRoleAndActiveTrue(role).ifPresent(profiles::add);
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode("12345678"))
                .profiles(profiles)
                .active(true)
                .build();
        userRepository.save(user);
    }

}
