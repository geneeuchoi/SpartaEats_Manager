package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.dto.BestMenusResponseDto;
import like.heocholi.spartaeats.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import like.heocholi.spartaeats.entity.OrderMenu;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

    @Query(value = "SELECT " +
            "new like.heocholi.spartaeats.dto.BestMenusResponseDto(om.menu, SUM(om.count))"+
            " FROM OrderMenu om" +
            " WHERE om.menu IN :menuList" +
            " GROUP BY om.menu" +
            " ORDER BY SUM(om.count) DESC" +
            " LIMIT 5")
    Optional<List<BestMenusResponseDto>> getBestMenus(List<Menu> menuList);
}
