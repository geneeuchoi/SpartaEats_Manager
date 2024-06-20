package like.heocholi.spartaeats.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.Store;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Page<Order> findAllByStore(Store store, Pageable pageable);
}
