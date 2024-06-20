package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByIdAndManagerId(Long storeId, Long id);

    List<Store> findByManagerId(Long id);

}
