package it.euris.stazioneconcordia.controller.trelloController;

import it.euris.stazioneconcordia.data.model.Labels;
import it.euris.stazioneconcordia.data.trelloDto.LabelsTrelloDto;
import it.euris.stazioneconcordia.data.trelloDto.UserTrelloDto;
import it.euris.stazioneconcordia.service.LabelsService;
import it.euris.stazioneconcordia.service.UserService;
import it.euris.stazioneconcordia.service.trelloService.LabelsTrelloService;
import it.euris.stazioneconcordia.service.trelloService.UserTrelloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello/label")
public class LabelsTrelloController {
    private LabelsTrelloService labelsTrelloService;
    private LabelsService labelsService;
    public void findIfExistANewLabelOfDBAndPutOnTrello(){
        List<Labels> labelsOfDB =labelsService.findAll();
        labelsOfDB.forEach(labels -> {
            if(labels.getIdTrello().isEmpty()){
                insertNewLabelToTrello(labels.getId()); }
        });
    }
    @DeleteMapping("/delete-a-label-to-trello")
    public void deleteALabelToTrello(@RequestParam Long idLabel) {
        Labels labels = labelsService.findById(idLabel);

        labelsTrelloService.deleteALabel(labels.getIdTrello());
    }
    @PostMapping("/insert-new-label-to-trello")
    public void insertNewLabelToTrello(@RequestParam Long idLabel) {
        LabelsTrelloDto labelsTrelloDto= labelsService.findById(idLabel).toDto().toTrelloDto();
        Labels labels =labelsService.findById(idLabel);

        labelsTrelloDto.setIdBoard(labels.getBoard().getIdTrello());
        labelsTrelloDto.setName(labels.getName());
        labelsTrelloDto.setColor(labels.getColor());

        labelsTrelloService.createANewLabel(labelsTrelloDto);
    }
    @PutMapping("/update-a-label-to-trello")
    public void updateALabelToTrello(@RequestParam Long idLabel) {
        LabelsTrelloDto labelsTrelloDto= labelsService.findById(idLabel).toDto().toTrelloDto();
        Labels labels =labelsService.findById(idLabel);

        labelsTrelloDto.setIdBoard(labels.getBoard().getIdTrello());
        labelsTrelloDto.setName(labels.getName());
        labelsTrelloDto.setColor(labels.getColor());

        labelsTrelloService.createANewLabel(labelsTrelloDto);
    }
    public List<LabelsTrelloDto> getLabelsFromTrelloBoard(String idBoard) {
        return labelsTrelloService.getLabelsByIdBoard(idBoard);

    }



}
