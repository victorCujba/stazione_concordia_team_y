package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;

import java.util.List;

public interface LabelsTrelloService {

    List<LabelsTrelloDto> getLabelsByIdBoard(String idBoard);
    void deleteALabel(String idLabel);
    void createANewLabel( LabelsTrelloDto labelsTrelloDto);
    void updateALabel( LabelsTrelloDto labelsTrelloDto);


}
