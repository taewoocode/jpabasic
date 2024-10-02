package jpabook.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    //Autowired를 하게 되면 SpringBean에 등록되어 있는 memberRepository에 Injection하게 된다.
    @Autowired
    private MemberRepository memberRepository;

    //회원 가입
    @Transactional //(readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember( member );
        //검
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원 검증
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName( member.getName() );
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException( "이미 존재하는 회원입니다." );
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //하나의 회원만 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne( memberId );
    }
}
