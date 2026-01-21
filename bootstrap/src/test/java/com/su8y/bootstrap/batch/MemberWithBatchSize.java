package com.su8y.bootstrap.batch;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member_batch")
@Getter
@NoArgsConstructor
public class MemberWithBatchSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamWithBatchSize team;

    public MemberWithBatchSize(String name, TeamWithBatchSize team) {
        this.name = name;
        this.team = team;
    }
}
