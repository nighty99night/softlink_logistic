package ru.kalinichenko.softlink_logistic.entity.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kalinichenko.softlink_logistic.entity.cargo.Cargo;
import ru.kalinichenko.softlink_logistic.entity.order.address.OrderAddress;

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
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private Cargo cargo;

    @ManyToOne
    private DeliveryType type;

    @ManyToOne
    private OrderAddress address;
}
