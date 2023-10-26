package it.euris.stazioneconcordia.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToBoolean;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO implements Dto {

    private String id;
    private String date;
    private DataDTO data;
    private String deleted;
//    private CardSupportDTO card;
    private String idMemberCreator;


    @Override
    public Comment toModel() {
        return Comment
                .builder()
                .id(id)
                .date(stringToLocalDateTime(date))
                .commentBody(data.getText())
                .deleted(stringToBoolean(deleted))
//                .card(Card.builder().id(card.getId()).build())
                .user(User.builder().id(idMemberCreator).build())
                .build();
    }

    @Override
    public String toString() {
        return "CommentDTO {" +
                "id = " + id + '\'' + '\n' +
                "date = " + date + '\'' + '\n' +
                "commentBody = " + data.getText() + '\'' + '\n' +
                "deleted = " + deleted + '\'' + '\n' +
//                "idCard = " + card + '\'' + '\n' +
                "idUser = " + idMemberCreator + '\'' + '\n' +
                '}' + '\n';

    }
}
