package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.ListsDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "list")
@SQLDelete(sql = "UPDATE list SET closed = true WHERE id = ?")
@Where(clause = "closed = false")
public class Lists implements Model {
    @Id
    @Column(name = "id")
    private String id;

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
                .id(id)
                .idBoard(board.getId())
                .name(name)
                .closed(booleanToString(closed))
//                .idLabel(listLabelToString(label))
                .position(numberToString(position))
                .build();
    }
}

