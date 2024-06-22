package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import like.heocholi.spartaeats.entity.OrderMenu;

import java.util.List;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

    @Query(value = "SELECT " +
            "om.menu, SUM(om.count) "+
            " FROM OrderMenu om" +
            " WHERE om.menu IN :menuList" +
            " GROUP BY om.menu" +
            " ORDER BY SUM(om.count) DESC" +
            " LIMIT 5")
    List<Object[]> findBestMenus(List<Menu> menuList);
}
