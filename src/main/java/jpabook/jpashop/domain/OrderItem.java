package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //생성 로
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem( item );
        orderItem.setOrderPrice( orderPrice );
        orderItem.setCount( count );

        item.removeStock( count );
        return orderItem;
    }

    public void cancel() {
        //재고수량 원복
        getItem().addStock( count );
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
