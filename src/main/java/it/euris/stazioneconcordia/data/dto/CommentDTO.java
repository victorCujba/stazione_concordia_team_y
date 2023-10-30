package it.euris.stazioneconcordia.data.dto;

import com.google.gson.annotations.SerializedName;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.TrelloDto;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.data.trelloDto.CommentTrelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO implements Dto {

    private String id;
    private String idTrello;
    private String date;
    private String commentBody;
    private String deleted;
    private String idUser;
    private String idCard;


    @Override
    public Comment toModel() {
        return Comment
                .builder()
                .id(stringToLong(id))
                .idTrello(idTrello)
                .date(stringToLocalDateTime(date))
                .commentBody(commentBody)
                .deleted(stringToBoolean(deleted))
                .card(Card.builder().idTrello(idCard).build())
                .user(User.builder().id(idUser).build())
                .build();
    }

    @Override
    public CommentTrelloDto toTrelloDto() {
        return CommentTrelloDto.builder()
                .id(id)
                .idMemberCreator(idUser)
                .idCard(idCard)
                .text(commentBody)
                .date(date)
                .build();
    }
}
