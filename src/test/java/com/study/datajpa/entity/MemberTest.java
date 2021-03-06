package com.study.datajpa.entity;

import com.study.datajpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;
    
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamA);
        Member member3 = new Member("member3", 10, teamB);
        Member member4 = new Member("member4", 10, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush(); // 영속성 컨텍스트에 있는 인스턴스를 강제로 insert 한다.
        em.clear(); // 쿼리를 날린 후 영속성 컨텍스트 안에 있는 캐쉬를 클리어 한다.

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member :: " + member);
            System.out.println(" --> member.team :: " + member.getTeam());
        }
    }
    
    @Test
    public void JpaEventBaseEntity() throws Exception {
        // given
        Member member = new Member("member1");
        memberRepository.save(member);
        
        Thread.sleep(100);
        member.setUsername("member2");
        
        em.flush();
        em.clear();
        
        // when
        Member findMember = memberRepository.findById(member.getId()).get();
    
        // then
        System.out.println("getCreateDate : " + findMember.getCratedDate());
        System.out.println("getUpdateDate : " + findMember.getLastModifiedDate());
        System.out.println("getCreatedBy : " + findMember.getCreatedBy());
        System.out.println("getLastModifiedBy : " + findMember.getLastModifiedBy());
    }
}