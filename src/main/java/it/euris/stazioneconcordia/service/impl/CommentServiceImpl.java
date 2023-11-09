package it.euris.stazioneconcordia.service.impl;


import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CardRepository;
import it.euris.stazioneconcordia.repository.CommentRepository;
import it.euris.stazioneconcordia.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;
    CardRepository cardRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment insert(Comment comment) {
        if (comment.getId() != null) {
            throw new IdMustBeNullException();
        }
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        if (comment.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return commentRepository.save(comment);
    }

    @Override
    public Boolean deleteById(Long idComment) {
        commentRepository.deleteById(idComment);
        return commentRepository.findById(idComment).isEmpty();
    }

    @Override
    public Comment findById(Long idComment) {
        return commentRepository.findById(idComment).orElse(Comment.builder().build());
    }

    @Override
    public Comment getLastComment(Card card) {
        Comment lastComment = commentRepository.findLastCommentByIdCard(card.getId());
        if (lastComment == null) {
            System.out.println("There is no comments in this card");
            return null;
        } else {
            return lastComment;
        }
    }

    @Override
    public Integer insertComment(Comment comment) {
//        Long cardId = cardRepository.getCardByIdTrello(comment.getCard().getIdTrello());
//        Integer insertComment = commentRepository.insertComment(
//                comment.getIdTrello(),
//                comment.getDate(),
//                comment.getCommentBody(),
//                comment.getDeleted(),
////                cardId,
////                comment.getUser().getId()
//
//        );
//        if (insertComment != 1) {
//            System.out.println("Comment non inserted");
//        } else {
//            System.out.println("Comment created successfully!!! ");
//        }
//        return insertComment;
        return  null;
    }

}