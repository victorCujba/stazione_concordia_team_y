package it.euris.stazioneconcordia.model;

import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Dto;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "comment_body")
    private String commentBody;

    @Column(name = "description" )
    private String description;

    @Column(name = "deleted")
    private Boolean deleted=false;

    @Column(name = "id_card")
    private Long idCard;

    @Column(name = "id_user")
    private Long idUser;


    @Override
    public CommentDTO toDto() {
        return CommentDTO
                .builder()
                .id(numberToString(id))
                .idCard(numberToString(idCard))
                .idUser(numberToString(idUser))
                .date(localDateTimeToString(date))
                .commentBody(commentBody)
                .deleted(booleanToString(deleted))
                .build();
    }
}
