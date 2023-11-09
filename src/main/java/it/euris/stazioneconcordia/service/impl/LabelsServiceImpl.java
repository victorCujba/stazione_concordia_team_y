package it.euris.stazioneconcordia.service.impl;

import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.LabelsRepository;
import it.euris.stazioneconcordia.service.LabelsService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class LabelsServiceImpl implements LabelsService {

    LabelsRepository labelsRepository;

    @Override
    public Labels insert(Labels labels) {
        if (labels.getId() != null) {
            throw new IdMustBeNullException();
        }
        return labelsRepository.save(labels);
    }
    @Override
    public Labels update(Labels labels) {
        if (labels.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return labelsRepository.save(labels);
    }

    @Override
    public Labels findById(Long idLabels) {
        return labelsRepository.findById(idLabels).orElse(Labels.builder().build());
    }

    @Override
    public Labels getLabelByIdTrelloFromDb(String idTrello) {
        return labelsRepository.getLabelByIdTrello(idTrello);
    }

    @Override
    public List<String> getAllIdTrelloForLabels() {
        return labelsRepository.getAllIdTrelloLabels();
    }

    @Override
    public Long getDefaultLabelId() {
        return labelsRepository.getDefaultLabel();
    }

}
