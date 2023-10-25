package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.LabelsDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "labels")
public class Labels implements Model {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "uses")
    private Long uses;

    @ManyToOne
    @JoinColumn(name = "id_board")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "id_card")
    private Card card;

//    @OneToMany(mappedBy = "id_card", fetch = FetchType.EAGER)
//    private List<Card> cards;

    @Override
    public LabelsDTO toDto() {
        return LabelsDTO
                .builder()
                .id(id)
                .idBoard(board.getId())
                .name(name)
                .color(color)
                .uses(uses)
                .build();
    }
}
