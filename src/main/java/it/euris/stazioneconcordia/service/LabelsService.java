package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.dto.LabelsDTO;
import it.euris.stazioneconcordia.data.model.Labels;

public interface LabelsService {
     Labels insert(Labels labels);
    Labels findById(String idLabels);

    Labels[] getLabelsFromTrelloBoard(String idBoard, String key, String token);
}


