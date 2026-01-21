package com.su8y.bootstrap.batch;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "team_batch")
@Getter
@NoArgsConstructor
public class TeamWithBatchSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<MemberWithBatchSize> members = new ArrayList<>();

    public TeamWithBatchSize(String name) {
        this.name = name;
    }
}
