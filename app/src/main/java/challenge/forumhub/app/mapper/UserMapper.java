package challenge.forumhub.app.mapper;

import challenge.forumhub.app.dto.user.UserDetailsDTO;
import challenge.forumhub.app.dto.user.UserSummaryDTO;
import challenge.forumhub.app.entity.Course;
import challenge.forumhub.app.entity.Reply;
import challenge.forumhub.app.entity.Topic;
import challenge.forumhub.app.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserSummaryDTO toSummaryDTO(User user){

        Set<Topic> activeTopics = user.getCreatedTopics().stream().filter(Topic::getActive).collect(Collectors.toSet());
        int topicCount = activeTopics.isEmpty() ? 0 : activeTopics.size();

        Set<Reply> activeReplies = user.getReplies().stream().filter(Reply::getActive).collect(Collectors.toSet());
        int replyCount = activeReplies.isEmpty() ? 0 : activeReplies.size();

        return new UserSummaryDTO(user.getId(), user.getEmail(), topicCount, replyCount);
    }

    public UserDetailsDTO toDetailsDTO(User user){
        Set<Topic> activeTopics = user.getCreatedTopics() == null ? Set.of()
                : user.getCreatedTopics().stream().filter(Topic::getActive).collect(Collectors.toSet());

        Set<Reply> activeReplies = user.getReplies() == null ? Set.of()
                : user.getReplies().stream().filter(Reply::getActive).collect(Collectors.toSet());

        Set<String> topicTitles = activeTopics.stream().map(Topic::getTitle).collect(Collectors.toSet());
        Set<String> replyTitles = activeReplies.stream().map(Reply::getTitle).collect(Collectors.toSet());

        Set<String> hasTopicsInCourses = activeTopics.stream()
                .map(Topic::getCourse)
                .map(Course::getName)
                .collect(Collectors.toSet());

        return new UserDetailsDTO(user.getId(),
                user.getCreatedAt(), user.getEmail(),
                user.getName(), hasTopicsInCourses,
                topicTitles, replyTitles);
    }

}
