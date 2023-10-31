package it.euris.stazioneconcordia.service.trelloService;

import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;

import java.util.List;

public interface LabelsTrelloService {

    List<LabelsTrelloDto> getLabelsFromTrelloBoard(String idBoard);

}
