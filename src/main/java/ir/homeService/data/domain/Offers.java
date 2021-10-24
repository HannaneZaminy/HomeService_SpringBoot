package ir.homeService.data.domain;

import ir.homeService.data.enums.OfferSituation;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Orders orders;
    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date SubmitOffer;
    @Column
    private Double offerPrice;
    @Column
    private Long durationOfWork;
    @Temporal(value = TemporalType.TIME)
    private Date startTime;
    @ManyToOne
    private Expert expert;
    @Enumerated(EnumType.STRING)
    private OfferSituation offerSituation;

    public Integer getId() {
        return id;
    }

    public Offers setId(Integer id) {
        this.id = id;
        return this;
    }

    public Orders getOrders() {
        return orders;
    }

    public Offers setOrders(Orders orders) {
        this.orders = orders;
        return this;
    }

    public Date getSubmitOffer() {
        return SubmitOffer;
    }

    public Offers setSubmitOffer(Date submitOffer) {
        SubmitOffer = submitOffer;
        return this;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public Offers setOfferPrice(Double offerPrice) {
        this.offerPrice = offerPrice;
        return this;
    }

    public Long getDurationOfWork() {
        return durationOfWork;
    }

    public Offers setDurationOfWork(Long durationOfWork) {
        this.durationOfWork = durationOfWork;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Offers setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Expert getExpert() {
        return expert;
    }

    public Offers setExpert(Expert expert) {
        this.expert = expert;
        return this;
    }

    public OfferSituation getOfferSituation() {
        return offerSituation;
    }

    public Offers setOfferSituation(OfferSituation offerSituation) {
        this.offerSituation = offerSituation;
        return this;
    }
}
