package it.euris.stazioneconcordia.controller.trelloController;

import it.euris.stazioneconcordia.data.model.Lists;
import it.euris.stazioneconcordia.data.trelloDto.ListsTrelloDto;
import it.euris.stazioneconcordia.service.BoardService;
import it.euris.stazioneconcordia.service.ListsService;
import it.euris.stazioneconcordia.service.trelloService.ListsTrelloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/from-trello/lists")
public  class ListsTrelloController {
    private ListsService listsService;
    private ListsTrelloService listsTrelloService;
    private BoardService boardService;


    public List<ListsTrelloDto> getAllListsFromBoard(String idBoard) {
        return listsTrelloService.getListsByIdBoard(idBoard);
    }
    public List<ListsTrelloDto> getListsFromTrelloBoard(String idBoard) {
        return listsTrelloService.getListsByIdBoard(idBoard);
    }
    public void findIfExistANewListOfDBAndPutOnTrello(){
        List<Lists> listsOfDB =listsService.findAll();
        listsOfDB.forEach(lists -> {
            if(lists.getIdTrello()==null){
                insertNewListFromDbToTrello(lists.getId());
             listsService.deleteById(lists.getId());
            }
        });
    }
    public void listsCompareToLists(Lists newLists, Lists existingList ){

        if (newLists.getDateLastActivity().isAfter(existingList.getDateLastActivity())||
                newLists.getDateLastActivity().isEqual(existingList.getDateLastActivity() )){

            newLists.setId(existingList.getId());
            newLists.setDateLastActivity(LocalDateTime.now());
            listsService.update(newLists);

        }else{
            updateAListFromDbToTrello(existingList.getId());
        }
    }
    @PostMapping("/insert-new-list-to-trello")
    public void insertNewListFromDbToTrello(@RequestParam Long idList) {
        ListsTrelloDto listsTrelloDto=listsService.findById(idList).toDto().toTrelloDto();
        Lists lists =listsService.findById(idList);


        listsTrelloDto.setIdBoard(boardService.findById(lists.getBoard().getId()).getIdTrello());
        listsTrelloDto.setName(lists.getName());
        listsTrelloService.createANewList(listsTrelloDto);
    }
    @PutMapping("/close-a-list-to-trello")
    public void closeAListToTrello(@RequestParam Long idList) {
        ListsTrelloDto listsTrelloDto=listsService.findById(idList).toDto().toTrelloDto();
        listsTrelloDto.setClosed(String.valueOf(true));
        Lists lists = listsService.findById(idList);

        listsTrelloService.closeAList(lists.getIdTrello(),listsTrelloDto);
    }
    @PutMapping("/update-list-to-trello")
    public void updateAListFromDbToTrello(@RequestParam Long idList) {
        ListsTrelloDto listsTrelloDto= listsService.findById(idList).toDto().toTrelloDto();
        Lists list =listsService.findById(idList);

        listsTrelloDto.setIdBoard(list.getBoard().getIdTrello());

        listsTrelloService.updateAList(list.getIdTrello(),listsTrelloDto);
    }
}

