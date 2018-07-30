package com.jpa.jpatest.domain;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OToM_TeamRepositoryTest {

    @Autowired
    private OToM_TeamRepository teamRepository;

    @Autowired
    private OToM_MemberRepository memberRepository;

    @Test
    public void 일대다_단방향_UPDATE_한번더_발생하는지() {
        OToM_Member member1 = new OToM_Member();
        member1.setUsername("member1");

        OToM_Member member2 = new OToM_Member();
        member2.setUsername("member2");

        List<OToM_Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);

        OToM_Team team = new OToM_Team();
        team.setName("team");
        team.setMembers(members);

//        memberRepository.save(member1);
//        memberRepository.save(member2);

        teamRepository.save(team);
        // CascadeType.ALL 해야 들어간다.
    }
}