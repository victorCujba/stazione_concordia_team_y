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
@Table(name = "key_token")
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
    @MapsId("id_board")
    @JoinColumn(name = "id_board")
    private Board board;

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
