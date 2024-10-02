package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //ComponentScan -> SpringBean
public class MemberRepository{

    //JPA
    @PersistenceContext
    private EntityManager em;

    //save
    public void save(Member member) {
        em.persist( member );
    }

    //check
    public Member findOne(Long id) {
        return em.find( Member.class, id );
    }

    //ListCheck
    public List<Member> findAll() {
        return em.createQuery( "select m from Member m ", Member.class )
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery( "select m from Member m where m.name = :name", Member.class )
                .setParameter( "name", name )
                .getResultList();
    }
}
