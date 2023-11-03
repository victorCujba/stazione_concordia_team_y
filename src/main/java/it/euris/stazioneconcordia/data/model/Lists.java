package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.ListsDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "list")
public class Lists implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_trello")
    private String idTrello;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private Long position;

    @Column(name = "closed")
    @Builder.Default
    private Boolean closed = false;

    @Column(name = "label")
    @Enumerated(EnumType.STRING)
    private ListLabel label;

    @ManyToOne
    @JoinColumn(name = "id_board")
    private Board board;

    @OneToMany
    @JoinColumn(name = "id_list")
    private List<Card> cards;


    @Override
    public ListsDTO toDto() {
        return ListsDTO.builder()
                .id(numberToString(id))
                .idTrello(idTrello)
                .idTrelloBoard(board.getIdTrello())
                .name(name)
                .closed(booleanToString(closed))
                .position(numberToString(position))
                .build();
    }
}

