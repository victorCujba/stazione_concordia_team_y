package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static it.euris.stazioneconcordia.service.trello.utils.TrelloConstants.*;

public interface CommentTrelloService {

    List<CommentTrelloDto> getCommentsFromCardByIdCard(String idCard);
    void createANewCommentOnACard(String idCard, CommentTrelloDto commentTrelloDto);
    void deleteACommentOnACard(String idComment);
    void updateACommentOnACard(String idCard, CommentTrelloDto commentTrelloDto);
}
