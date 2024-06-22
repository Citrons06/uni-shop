package my.unishop.product.domain.cart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.unishop.admin.BaseEntity;
import my.unishop.product.domain.item.entity.Item;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
public class CartItem extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    private Integer totalPrice;
    private Integer itemCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}