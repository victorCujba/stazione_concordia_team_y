package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> findAll();

    Comment insert(Comment comment);

    Comment update(Comment comment);

    Boolean deleteById(Long idComment);

    Comment findById(Long idComment);

    Comment getLastComment();


}
