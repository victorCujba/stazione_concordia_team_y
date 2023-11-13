package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.model.User;
import it.euris.stazioneconcordia.data.trelloDto.CommentDataDTO;
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
                .user(User.builder().idTrello(idUser).build())
                .build();
    }

    @Override
    public CommentTrelloDto toTrelloDto() {
        return CommentTrelloDto.builder()
                .id(id)
                .idMemberCreator(idUser)
                .data(CommentDataDTO.builder().text(commentBody).card(CardDTO.builder().idTrello(idCard).build()).build())
                .date(date)
                .build();
    }
}
