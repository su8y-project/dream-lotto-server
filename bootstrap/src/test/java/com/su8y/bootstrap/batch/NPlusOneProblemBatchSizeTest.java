package com.su8y.bootstrap.batch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@DataJpaTest
@EntityScan(basePackages = "com.su8y.bootstrap.batch")
@EnableJpaRepositories(basePackages = "com.su8y.bootstrap.batch")
public class NPlusOneProblemBatchSizeTest {

    @Autowired
    private TeamWithBatchSizeRepository teamRepository;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setUp() {
        TeamWithBatchSize teamA = new TeamWithBatchSize("Team A");
        em.persist(teamA);

        MemberWithBatchSize member1 = new MemberWithBatchSize("Member 1", teamA);
        MemberWithBatchSize member2 = new MemberWithBatchSize("Member 2", teamA);
        em.persist(member1);
        em.persist(member2);

        TeamWithBatchSize teamB = new TeamWithBatchSize("Team B");
        em.persist(teamB);

        MemberWithBatchSize member3 = new MemberWithBatchSize("Member 3", teamB);
        MemberWithBatchSize member4 = new MemberWithBatchSize("Member 4", teamB);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("Batch Size로 N+1 문제 해결")
    void solveNPlusOneWithBatchSize() {
        System.out.println("--- Starting Batch Size test ---");
        List<TeamWithBatchSize> teams = teamRepository.findAll();
        for (TeamWithBatchSize team : teams) {
            System.out.println("Team: " + team.getName() + ", Members count: " + team.getMembers().size());
        }
        System.out.println("--- Batch Size test finished ---");
    }
}
