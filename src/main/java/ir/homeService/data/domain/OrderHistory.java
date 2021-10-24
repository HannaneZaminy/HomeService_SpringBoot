package ir.homeService.data.domain;

import ir.homeService.data.enums.OrderSituation;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Orders order;
    @Column
    @Enumerated(value = EnumType.STRING)
    private OrderSituation orderSituation;
    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date changeDate;

    public Integer getId() {
        return id;
    }

    public OrderHistory setId(Integer id) {
        this.id = id;
        return this;
    }

    public Orders getOrder() {
        return order;
    }

    public OrderHistory setOrder(Orders order) {
        this.order = order;
        return this;
    }

    public OrderSituation getOrderSituation() {
        return orderSituation;
    }

    public OrderHistory setOrderSituation(OrderSituation orderSituation) {
        this.orderSituation = orderSituation;
        return this;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public OrderHistory setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
        return this;
    }
}
