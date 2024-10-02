package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long Id;

    private String name;

    //내장타입
    @Embedded
    private Address address;

    //하나의 회원이 여러개의 상품을 주문한다. 1 : N
    //연관관계의 주인이 아닌 거울
    //오더테이블의 멤버필드에 의해 매핑이 되는 존재
    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

}
