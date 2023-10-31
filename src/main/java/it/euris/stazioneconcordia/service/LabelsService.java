package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;
import lombok.SneakyThrows;

import java.util.List;

public interface LabelsService {
    Labels insert(Labels labels);

    Labels findById(Long idLabels);

    List<LabelsTrelloDto> getLabelsFromTrelloBoardByIdBoard(String idBoard);

}


