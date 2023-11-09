package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Labels;

import java.util.List;

public interface LabelsService {
    Labels insert(Labels labels);

    Labels update(Labels labels);

    Labels findById(Long idLabels);

    Labels getLabelByIdTrelloFromDb(String idTrello);

    List<String> getAllIdTrelloForLabels();

    Long getDefaultLabelId();
}


