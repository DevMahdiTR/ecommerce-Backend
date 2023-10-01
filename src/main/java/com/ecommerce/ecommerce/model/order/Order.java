package com.ecommerce.ecommerce.model.order;


import com.ecommerce.ecommerce.model.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @SequenceGenerator(
            name = "order_sequel",
            sequenceName = "order_sequel",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "order_sequel")
    @Column(name ="id" ,unique = true , nullable = false)
    private long id;


    @Column(name = "delivered" , nullable = false)
    private boolean delivered = false;

    @Column(name = "address" , nullable = false)
    private String address;

    @Column(name = "payment_methode" ,nullable = false)
    private String paymentMethode;

    @Column(name = "total_price", nullable = false)
    private  float price = 0f;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<SubOrder> subOrders = new ArrayList<>();

    @OneToOne
    private UserEntity user;

}
