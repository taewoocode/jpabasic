package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // ManyToMany 관계에서 'categories'는 Category 클래스에 있어야 합니다.
    @ManyToMany
    @JoinTable(
            name = "category_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    // 부모 카테고리와의 관계 (한 Item은 하나의 부모 카테고리만 가질 수 있음)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    //==비즈니스 로직==//
    //재고수량 증가

    /**
     * setXXX으로 재고를 관리하는 것이 아닌 비즈니스 로직을 통해서 수량을 관리한다.
     * 외부에서 계산에 초점을 두기보다는 스스로 재고를 관리하게끔 설계를 변경한다.
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException( "need more Stock" );
        }
        this.stockQuantity = restStock;
    }
}
