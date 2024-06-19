package like.heocholi.spartaeats.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import like.heocholi.spartaeats.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByUserId(String username);
}