package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.CommentDTO;
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

    @Column(name = "deleted")
    private Boolean deleted=false;

    @ManyToOne
    @MapsId("id_card")
    @JoinColumn(name = "id_card")
    private Card card;

    @ManyToOne
    @MapsId("id_user")
    @JoinColumn(name = "id_user")
    private User user;


    @Override
    public CommentDTO toDto() {
        return CommentDTO
                .builder()
                .id(numberToString(id))
                .idCard(numberToString(card.getId()))
                .idUser(numberToString(user.getId()))
                .date(localDateTimeToString(date))
                .commentBody(commentBody)
                .deleted(booleanToString(deleted))
                .build();
    }
}