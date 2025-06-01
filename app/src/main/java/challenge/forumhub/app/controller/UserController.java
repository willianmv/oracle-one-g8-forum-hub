package challenge.forumhub.app.controller;

import challenge.forumhub.app.controller.doc.UserControllerDoc;
import challenge.forumhub.app.dto.user.UserDetailsDTO;
import challenge.forumhub.app.dto.user.UserSummaryDTO;
import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.mapper.UserMapper;
import challenge.forumhub.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerDoc {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers(){
        List<UserSummaryDTO> users = userService.getAll()
                .stream()
                .map(userMapper::toSummaryDTO)
                .sorted(Comparator.comparing(UserSummaryDTO::id))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable("userId") long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toDetailsDTO(user));
    }
}
