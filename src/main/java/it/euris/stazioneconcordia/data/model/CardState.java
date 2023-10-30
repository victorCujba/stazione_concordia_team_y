package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.CardStateDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import it.euris.stazioneconcordia.data.enums.ListLabel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_state")
public class CardState implements Model {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_card")
    private Card card;

    @Column(name = "from_list")
    @Enumerated(EnumType.STRING)
    private ListLabel fromList;

    @Column(name = "to_list")
    @Enumerated(EnumType.STRING)
    private ListLabel toList;

    @Column(name = "date_last_update")
    private LocalDateTime dateLastUpdate;

    @Override
    public CardStateDTO toDto() {
        return CardStateDTO
                .builder()
                .id(id)
                .idCard(numberToString(card.getId()))
                .fromList(listLabelToString(fromList))
                .toList(listLabelToString(toList))
                .dateLastUpdate(localDateTimeToString(dateLastUpdate))
                .build();
    }


}
