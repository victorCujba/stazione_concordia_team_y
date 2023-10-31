package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;

import java.util.List;

public interface CommentTrelloService {

    List<CommentTrelloDto> getAllCommentsFromCardByIdCard(String idCard);
}
