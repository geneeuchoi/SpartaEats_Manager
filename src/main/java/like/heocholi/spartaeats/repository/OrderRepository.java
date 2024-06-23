package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Order;
import like.heocholi.spartaeats.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Page<Order> findAllByStore(Store store, Pageable pageable);

	@Query(value = "SELECT " +
			"o.customer, COUNT(o.customer)"+
			" FROM Order o" +
			" WHERE o.store = :store" +
			" GROUP BY o.customer" +
			" ORDER BY COUNT(o.customer) DESC" +
			" LIMIT 5")
	Optional<List<Object[]>> getVipAndOrderCountList(Store store);


	@Query(value = "SELECT " +
			"DATE(o.createdAt), COUNT(o), SUM(o.totalPrice) " +
			"FROM Order o " +
			"WHERE o.store = :store " +
			"GROUP BY DATE(o.createdAt)")
	Optional<List<Object[]>> getDailySales(Store store);
}
