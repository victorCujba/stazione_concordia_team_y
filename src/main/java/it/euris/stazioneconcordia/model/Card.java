package it.euris.stazioneconcordia.model;

import it.euris.stazioneconcordia.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private Long position;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "descriptions")
    private String descriptions;

    @Column(name = "closed")
    @Builder.Default
    private Boolean closed = false;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "date_last_activity")
    private LocalDateTime dateLastActivity;

    @Builder.Default
    @OneToMany(mappedBy = "idCard", fetch = FetchType.EAGER)
    private List<Comment> commentList = new ArrayList<>();

}
