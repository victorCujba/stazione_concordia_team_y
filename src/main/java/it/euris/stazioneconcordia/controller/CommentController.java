package it.euris.stazioneconcordia.controller;

import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    CommentService commentService;

    @GetMapping("/v1")
    public List<CommentDTO> getAllComments() {
        return commentService.findAll().stream().map(Comment::toDto).toList();
    }

    @PostMapping("/v1")
    public CommentDTO saveComment(@RequestBody CommentDTO commentDTO) {
        try {
            Comment comment = commentDTO.toModel();
            return commentService.insert(comment).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/v1")
    public CommentDTO updateComment(@RequestBody CommentDTO commentDTO) {
        try {
            Comment comment = commentDTO.toModel();
            return commentService.update(comment).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/v1/{id}")
    public Boolean deleteComment(@PathVariable("id") Long idComment) {
        return commentService.deleteById(idComment);
    }

    @GetMapping("/v1/{id}")
    public CommentDTO getCommentById(@PathVariable("id") Long idComment) {
        return commentService.findById(idComment).toDto();
    }

    @GetMapping("/v1/last-comment")
    public CommentDTO getLastComment() {
        return commentService.getLastComment().toDto();
    }

}
