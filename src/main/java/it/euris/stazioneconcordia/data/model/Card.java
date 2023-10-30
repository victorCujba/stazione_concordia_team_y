package it.euris.stazioneconcordia.data.model;

import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.archetype.Model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static it.euris.stazioneconcordia.utility.DataConversionUtils.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
@SQLDelete(sql = "UPDATE card SET closed = true WHERE id = ?")
@Where(clause = "closed = false")
public class Card implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_db")
    private Long idDb;

    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private Long position;

    @Column(name = "description")
    private String description;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "date_last_activity")
    private LocalDateTime dateLastActivity;

    @Column(name = "closed")
    @Builder.Default
    private Boolean closed = false;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "id_list")
    private Lists list;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<CardState> stateHistory;

    @ManyToOne
    @JoinColumn(name = "id_label")
    private Labels labels;

    @Override
    public CardDTO toDto() {
        return CardDTO.builder()
                .id(id)
                .name(name)
                .position(numberToString(position))
                .idLabels(List.of(labels.getId()))
                .description(description)
                .closed(booleanToString(closed))
                .expirationDate(localDateTimeToString(expirationDate))
                .dateLastActivity(localDateTimeToString(dateLastActivity))
                .idList(list.getId())
                .build();
    }

    public List<String> getCommentStrings() {
        if (comments != null) {
            return comments.stream()
                    .map(Comment::getCommentBody)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


}