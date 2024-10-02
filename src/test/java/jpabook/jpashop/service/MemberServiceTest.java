package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;


    @Test
    @Rollback(value = false) //enforce Query
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName( "kim" );

        //when
        Long saveId = memberService.join( member );

        //then
        em.flush(); //DB insert query
        assertEquals( member, memberRepository.findOne( saveId ) );
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("kim1");
        member2.setName("kim1"); // 중복된 이름으로 설정

        // when
        memberService.join(member1);
        try {
            memberService.join(member2); // Exception 발생 예상
        } catch (IllegalStateException e) {
            // then
            // 예외가 발생했으므로 테스트 성공
            return;
        }
        // 예외가 발생하지 않았다면 테스트 실패
        fail("예외가 발생해야 합니다.");
    }
}
