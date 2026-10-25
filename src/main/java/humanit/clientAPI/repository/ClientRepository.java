package humanit.clientAPI.repository;

import humanit.clientAPI.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByTaxIdentifier(String taxIdentifier);
    Optional<Client> findByEmail(String email);
}
