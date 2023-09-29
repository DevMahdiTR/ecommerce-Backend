package com.ecommerce.ecommerce.model.order;


import com.ecommerce.ecommerce.model.article.Article;
import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

@Data
@Entity
@Table(name = "sub_orders")
public class SubOrder {

    @SequenceGenerator(
            name = "sub_order_sequel",
            sequenceName = "sub_order_sequel",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "sub_order_sequel")
    @Column(name = "id" , nullable = false , unique = true)
    private long id;


    @Column(name = "quantity" , nullable = false)
    private int quantity;

    @OneToOne
    private Article article;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
