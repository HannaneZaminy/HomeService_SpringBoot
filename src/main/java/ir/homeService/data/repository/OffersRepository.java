package ir.homeService.data.repository;

import ir.homeService.data.domain.Offers;
import ir.homeService.data.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OffersRepository extends JpaRepository<Offers, Integer>, JpaSpecificationExecutor<Offers> {

    Optional<Offers> findByOrders(Orders order);


//    void saveNewOffer(Offers offers);
//    void deleteOffer(Offers offers);
//    void updateOffer(Offers offers);
//    List<Offers> fetchAllOffers();
//    static Specification<Offers> findOffersByOrders(Orders orders){
//        return (root, cq, cb) -> {
//            Predicate predicate = null;
//            if (orders != null) {
//                predicate = cb.equal(root.get("orders"), orders);
//            }
//            return cq.where(predicate).getRestriction();
//        };
//    }
}
