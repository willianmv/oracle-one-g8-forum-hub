package challenge.forumhub.app.service;

import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.exception.ResourceNotFoundException;
import challenge.forumhub.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAllByActiveTrue();
    }

    public User getUserById(long id) {
        return userRepository.findByIdAndActiveTrue(id).
                orElseThrow(() -> new ResourceNotFoundException("User não encontrado com ID: "+id));
    }
}
