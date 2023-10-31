package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.CommentRepository;
import it.euris.stazioneconcordia.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import static it.euris.stazioneconcordia.trello.utils.TrelloConstants.*;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

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
        List<Comment> comments = commentRepository.findAll();
        Comment lastCommentOfACard = Comment
                .builder()
                .card(card)
                .date(LocalDateTime.MIN)
                .build();
        for (Comment comment : comments) {
            if (comment.getCard().equals(lastCommentOfACard.getCard())) {
                if (comment.getDate().isAfter(lastCommentOfACard.getDate())) {
                    lastCommentOfACard = comment;
                }
            }
        }
        return lastCommentOfACard;
    }

    @Override
    @SneakyThrows
    public List<CommentTrelloDto> getCommentsFromCardByIdCard(String idCard) {

        URI targetURI = new URI(buildUrlForGetCommentsFromCardByIdCard(idCard));
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CommentTrelloDto>>() {
        }.getType();

        return gson.fromJson(response.body(), listType);
    }

    private String buildUrlForGetCommentsFromCardByIdCard(String idCard) {
        return UriComponentsBuilder.fromHttpUrl(URL_API_TRELLO_GET_COMMENTS_BY_ID_CARD)
                .buildAndExpand(idCard, KEY_VALUE, TOKEN_VALUE).toString();
    }
}