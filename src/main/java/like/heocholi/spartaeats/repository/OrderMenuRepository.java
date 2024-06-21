package like.heocholi.spartaeats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import like.heocholi.spartaeats.entity.OrderMenu;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
