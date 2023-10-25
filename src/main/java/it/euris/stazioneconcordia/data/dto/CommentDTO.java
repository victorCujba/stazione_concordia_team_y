package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Comment;
import it.euris.stazioneconcordia.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToBoolean;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.stringToLocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO implements Dto {

    private String id;
    private String date;
    private String commentBody;
    private String deleted;
    private String idCard;
    private String idUser;


    @Override
    public Comment toModel() {
        return Comment
                .builder()
                .id(id)
                .date(stringToLocalDateTime(date))
                .commentBody(commentBody)
                .deleted(stringToBoolean(deleted))
                .card(Card.builder().id(idCard).build())
                .user(User.builder().id(idUser).build())
                .build();
    }
}
