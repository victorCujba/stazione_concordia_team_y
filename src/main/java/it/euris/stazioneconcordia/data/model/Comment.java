package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.CommentDTO;
import it.euris.stazioneconcordia.data.dto.DataDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
//@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE id = ?")
//@Where(clause = "deleted = false")
public class Comment implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_trello")
    private String idTrello;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "comment_body")
    private String commentBody;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("id_card")
    @JoinColumn(name = "id_card")
    private Card card;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("id_user")
    @JoinColumn(name = "id_user")
    private User user;


    @Override
    public CommentDTO toDto() {
        return CommentDTO
                .builder()
                .id(numberToString(id))
                .idTrello(idTrello)
                .idUser(user.getId())
                .idCard(numberToString(card.getId()))
                .date(localDateTimeToString(date))
                .commentBody(commentBody)
                .deleted(booleanToString(deleted))
                .build();
    }
}