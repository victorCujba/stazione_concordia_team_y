package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.LabelsDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.numberToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "labels")
public class Labels implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_trello")
    private String idTrello;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "uses")
    private Long uses;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "id_board")
    private Board board;

    @OneToMany(mappedBy = "labels", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Card> cards;

    @Override
    public LabelsDTO toDto() {
        return LabelsDTO
                .builder()
                .id(numberToString(id))
                .idTrello(idTrello)
                .idTrelloBoard(board.getIdTrello())
                .name(name)
                .color(color)
                .uses(uses)
                .build();
    }
}
