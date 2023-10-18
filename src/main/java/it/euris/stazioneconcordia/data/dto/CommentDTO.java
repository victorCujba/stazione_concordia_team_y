package it.euris.stazioneconcordia.data.dto;

import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
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
    private String idCard;
    private String idUser;
    private String date;
    private String commentBody;
    private String deleted;


    @Override
    public Comment toModel() {
        return Comment
                .builder()
                .id(stringToLong(id))
                .idCard(stringToLong(idCard))
                .idUser(stringToLong(idUser))
                .date(stringToLocalDateTime(date))
                .commentBody(commentBody)
                .deleted(stringToBoolean(deleted))
                .build();
    }
}
