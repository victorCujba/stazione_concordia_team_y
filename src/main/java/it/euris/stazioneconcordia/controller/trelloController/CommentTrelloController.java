package it.euris.stazioneconcordia.controller.trelloController;

import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.data.trelloDto.CommentDataDTO;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import it.euris.stazioneconcordia.data.trelloDto.ListsTrelloDto;
import it.euris.stazioneconcordia.service.CommentService;
import it.euris.stazioneconcordia.service.ListsService;
import it.euris.stazioneconcordia.service.trelloService.CommentTrelloService;
import it.euris.stazioneconcordia.service.trelloService.ListsTrelloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello/comment")
public class CommentTrelloController {
    private CommentTrelloService commentTrelloService;
    private CommentService commentService;

    public List<CommentTrelloDto> getCommentsFromCardByIdCard(String idCard) {
        return commentTrelloService.getCommentsFromCardByIdCard(idCard);
    }

    public void findIfExistANewCommentOfDBAndPutOnTrello() {
        List<Comment> commentOfDB = commentService.findAll();
        commentOfDB.forEach(comment -> {
            if (comment.getIdTrello().isEmpty()) {
                insertNewCommentOnACardsFromDbToTrello(comment.getId());
                commentService.deleteById(comment.getId());
            }
        });
    }

    @DeleteMapping("/delete-a-comment-on-a-card-to-trello")
    public void deleteACommentOnACardsToTrello(@RequestParam Long idComment) {
        Comment comment = commentService.findById(idComment);

        commentTrelloService.deleteACommentOnACard(comment.getIdTrello());
    }


    @PostMapping("/insert-new-comment-on-a-card-to-trello")
    public void insertNewCommentOnACardsFromDbToTrello(@RequestParam Long idComment) {
        CommentTrelloDto commentTrelloDto = commentService.findById(idComment).toDto().toTrelloDto();
        Comment comment = commentService.findById(idComment);
        commentTrelloDto.setData(CommentDataDTO.builder().card(comment.getCard().toDto()).build());

        commentTrelloService.createANewCommentOnACard(comment.getCard().getIdTrello(), commentTrelloDto);
    }

    @PutMapping("/update-a-comment-to-trello")
    public void updateACommentToTrello(@RequestParam Long idComment) {
        CommentTrelloDto commentTrelloDto = commentService.findById(idComment).toDto().toTrelloDto();
        Comment comment = commentService.findById(idComment);

        commentTrelloDto.setId(comment.getIdTrello());
        commentTrelloDto.setData(CommentDataDTO.builder().card(comment.getCard().toDto()).build());

        commentTrelloService.updateACommentOnACard(comment.getIdTrello(), commentTrelloDto);
    }

    public void commentCompareToComment(Comment newComment, Comment existingComment) {

        if (newComment.getDate().isAfter(existingComment.getDate()) ||
                newComment.getDate().isEqual(existingComment.getDate())) {

            newComment.setId(existingComment.getId());
            newComment.setDate(LocalDateTime.now());
            commentService.update(newComment);

        } else {
            updateACommentToTrello(existingComment.getId());
        }
    }

}