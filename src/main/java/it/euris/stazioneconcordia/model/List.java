package it.euris.stazioneconcordia.model;

import it.euris.stazioneconcordia.data.dto.ListDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.booleanToString;
import static it.euris.stazioneconcordia.utility.DataConversionUtils.numberToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "list")
public class List implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private Long position;

    @Column(name = "closed")
    @Builder.Default
    private Boolean closed = false;

    @ManyToOne
    @MapsId("id_board")
    @JoinColumn(name = "id_board")
    private Board board;


    @Override
    public ListDTO toDto() {
        return ListDTO.builder()
                .id(numberToString(id))
                .idBoard(numberToString(board.getId()))
                .name(name)
                .closed(booleanToString(closed))
                .position(numberToString(position))
                .build();
    }
}

