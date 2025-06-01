package challenge.forumhub.app.controller;

import challenge.forumhub.app.controller.doc.ReplyControllerDoc;
import challenge.forumhub.app.dto.reply.ReplyDetailsDTO;
import challenge.forumhub.app.dto.reply.ReplyRequestDTO;
import challenge.forumhub.app.dto.reply.ReplySummaryDTO;
import challenge.forumhub.app.dto.reply.ReplyUpdateDTO;
import challenge.forumhub.app.entity.Reply;
import challenge.forumhub.app.mapper.ReplyMapper;
import challenge.forumhub.app.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController implements ReplyControllerDoc {

    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    @PostMapping
    public ResponseEntity<ReplyDetailsDTO> create(@RequestBody @Valid ReplyRequestDTO dto){
        Reply createdReply = replyService.create(dto);
        ReplyDetailsDTO responseDTO = replyMapper.toDetailsDTO(createdReply);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdReply.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReplySummaryDTO>> getAllReplies(){
        List<ReplySummaryDTO> replies = replyService.getAll()
                .stream()
                .map(replyMapper::toSummaryDTO)
                .sorted(Comparator.comparing(ReplySummaryDTO::replyId))
                .toList();
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyDetailsDTO> getReplyById(@PathVariable("replyId") long id){
        Reply reply = replyService.getReplyById(id);
        return ResponseEntity.ok(replyMapper.toDetailsDTO(reply));
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<ReplyDetailsDTO> updateReply(@PathVariable("replyId") long id,
                                                       @RequestBody @Valid ReplyUpdateDTO dto){
        Reply updatedReply = replyService.updateReply(id, dto);
        return ResponseEntity.ok(replyMapper.toDetailsDTO(updatedReply));
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable("replyId") long id){
        replyService.deleteReply(id);
        return ResponseEntity.noContent().build();
    }

}
