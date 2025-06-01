package challenge.forumhub.app.service;

import challenge.forumhub.app.dto.reply.ReplyRequestDTO;
import challenge.forumhub.app.dto.reply.ReplyUpdateDTO;
import challenge.forumhub.app.entity.Reply;
import challenge.forumhub.app.entity.Topic;
import challenge.forumhub.app.entity.TopicStatus;
import challenge.forumhub.app.entity.User;
import challenge.forumhub.app.exception.ResourceAlreadyExistsException;
import challenge.forumhub.app.exception.ResourceNotFoundException;
import challenge.forumhub.app.repository.ReplyRepository;
import challenge.forumhub.app.repository.TopicRepository;
import challenge.forumhub.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TopicService topicService;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Transactional
    public Reply create(ReplyRequestDTO dto) {
        Topic topic = validateTopic(dto.topicId());
        User user = authenticatedUserService.getAuthenticatedUserEntity();
        validateDuplicateToCreateReply(dto.title(), dto.solution());
        Reply reply = new Reply();
        reply.setTitle(dto.title());
        reply.setSolution(dto.solution());
        reply.setAuthor(user);
        reply.setTopic(topic);

        addReplyToTopic(topic, reply);
        return replyRepository.save(reply);
    }

    public List<Reply> getAll() {
        return replyRepository.findAllByActiveTrue();
    }

    public Reply getReplyById(long id) {
        return replyRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resposta não encontrada com ID: "+id));
    }

    @Transactional
    public Reply updateReply(long id,ReplyUpdateDTO dto) {
        Reply replyToUpdate = getReplyById(id);
        authenticatedUserService.verifyOwnerOrModeratorOrAdmin(replyToUpdate.getAuthor().getId());
        validateDuplicateToUpdateReply(dto.title(), dto.solution(), id);
        Topic topic = validateTopic(dto.topicId());
        replyToUpdate.setTitle(dto.title());
        replyToUpdate.setSolution(dto.solution());
        replyToUpdate.setTopic(topic);
        return replyRepository.save(replyToUpdate);
    }

    @Transactional
    public void deleteReply(long id) {
        Reply reply = getReplyById(id);
        authenticatedUserService.verifyOwnerOrModeratorOrAdmin(reply.getAuthor().getId());
        reply.setActive(false);
        replyRepository.save(reply);
        verifyActiveRepliesInTopic(reply.getTopic());
    }

    private void validateDuplicateToUpdateReply(String title,String solution, long id) {
        if(replyRepository.existsByTitleIgnoreCaseAndSolutionIgnoreCaseAndIdNot(title, solution, id))
            throw new ResourceAlreadyExistsException("Já existe um tópico com o mesmo título e mensagem");
    }

    private void validateDuplicateToCreateReply(String title, String solution) {
        if(replyRepository.existsByTitleIgnoreCaseAndSolutionIgnoreCase(title, solution))
            throw new ResourceAlreadyExistsException("Já existe um tópico com o mesmo título e mensagem");
    }

    private Topic validateTopic(long topicId) {
        return topicService.getTopicById(topicId);
    }

    private void addReplyToTopic(Topic topic, Reply reply){
        if(topic.getReplies() == null){
            topic.setReplies(new ArrayList<>());
        }
        topic.getReplies().add(reply);

        if(topic.getStatus() == TopicStatus.NO_REPLIES){
            topic.setStatus(TopicStatus.WITH_REPLIES);
        }
    }

    private void verifyActiveRepliesInTopic(Topic topic){
        boolean hasActiveReplies = topic.getReplies().stream().anyMatch(Reply::getActive);
        if(!hasActiveReplies){
            topic.setStatus(TopicStatus.NO_REPLIES);
            topicRepository.save(topic);
        }
    }
}
