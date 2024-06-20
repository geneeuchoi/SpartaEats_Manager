package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByStoreId(Long storeId);
    Optional<Menu> findByStoreIdAndId(Long storeId, Long menuId);
    Optional<Menu> findByStoreIdAndName(Long storeId, String name);
}
