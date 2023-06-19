package ru.kalinichenko.softlink_logistic.entity.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kalinichenko.softlink_logistic.entity.User;
import ru.kalinichenko.softlink_logistic.entity.cargo.Cargo;
import ru.kalinichenko.softlink_logistic.entity.order.address.OrderAddress;

import java.math.BigDecimal;

@Entity(name = "orders")
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String details;
    @ManyToOne
    @JoinColumn(nullable = false)
    private OrderStatus status;
    @OneToOne(cascade = CascadeType.ALL)
    private Cargo cargo;
    @ManyToOne
    private DeliveryType type;
    @ManyToOne(cascade = CascadeType.ALL)
    private OrderAddress address;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    @Column
    private BigDecimal price;
}
