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

    @Column(name = "closed")
    @Builder.Default
    private Boolean closed = false;

    @Column(name = "position")
    private Long position;

    @Column(name = "id_board")
    private Long idBoard;


    @Override
    public ListDTO toDto() {
        return ListDTO.builder()
                .id(numberToString(id))
                .idBoard(numberToString(idBoard))
                .name(name)
                .closed(booleanToString(closed))
                .position(numberToString(position))
                .build();
    }
}

