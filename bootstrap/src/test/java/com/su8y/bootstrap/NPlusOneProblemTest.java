package com.su8y.bootstrap;

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
@EntityScan(basePackages = "com.su8y.bootstrap")
@EnableJpaRepositories(basePackages = "com.su8y.bootstrap")
public class NPlusOneProblemTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setUp() {
        // Create and persist test data
        Team teamA = new Team("Team A");
        em.persist(teamA);

        Member member1 = new Member("Member 1", teamA);
        Member member2 = new Member("Member 2", teamA);
        em.persist(member1);
        em.persist(member2);

        Team teamB = new Team("Team B");
        em.persist(teamB);

        Member member3 = new Member("Member 3", teamB);
        Member member4 = new Member("Member 4", teamB);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("N+1 문제가 발생하는 테스트")
    void testNPlusOneProblem() {
        System.out.println("--- Starting the test that demonstrates the N+1 problem ---");

        // 1. First query to fetch all teams
        List<Team> teams = teamRepository.findAll();

        // 2. N additional queries to fetch members for each team
        for (Team team : teams) {
            // Accessing the members collection triggers a lazy loading query for each team
            System.out.println("Team: " + team.getName() + ", Members count: " + team.getMembers().size());
        }

        System.out.println("--- Test finished ---");
    }

    @Test
    @DisplayName("Fetch Join으로 N+1 문제 해결")
    void solveNPlusOneWithFetchJoin() {
        System.out.println("--- Starting Fetch Join test ---");
        List<Team> teams = teamRepository.findAllWithFetchJoin();
        for (Team team : teams) {
            System.out.println("Team: " + team.getName() + ", Members count: " + team.getMembers().size());
        }
        System.out.println("--- Fetch Join test finished ---");
    }

    @Test
    @DisplayName("Entity Graph로 N+1 문제 해결")
    void solveNPlusOneWithEntityGraph() {
        System.out.println("--- Starting Entity Graph test ---");
        List<Team> teams = teamRepository.findAllWithEntityGraph();
        for (Team team : teams) {
            System.out.println("Team: " + team.getName() + ", Members count: " + team.getMembers().size());
        }
        System.out.println("--- Entity Graph test finished ---");
    }

    @Test
    @DisplayName("Batch Size로 N+1 문제 해결")
    void solveNPlusOneWithBatchSize() {
        System.out.println("--- Starting Batch Size test ---");
        // @BatchSize(size = 10) 어노테이션을 Team 엔티티의 members 컬렉션에 추가했습니다.
        // 이제 teamRepository.findAll()을 호출하여 팀 목록을 조회하면,
        // 첫 번째 쿼리는 팀 목록을 가져옵니다.
        // 그 후, members 컬렉션에 접근할 때 N+1 쿼리가 발생하는 대신,
        // BatchSize에 지정된 크기만큼의 Team ID를 모아서 한 번의 IN절 쿼리로 member 목록을 가져옵니다.
        // 이 예제에서는 팀이 2개이므로 members를 가져오는 쿼리가 한 번만 더 실행됩니다. (총 2개의 쿼리)
        List<Team> teams = teamRepository.findAll();
        for (Team team : teams) {
            System.out.println("Team: " + team.getName() + ", Members count: " + team.getMembers().size());
        }
        System.out.println("--- Batch Size test finished ---");
    }
}
