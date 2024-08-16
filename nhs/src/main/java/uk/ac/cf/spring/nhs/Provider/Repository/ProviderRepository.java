package uk.ac.cf.spring.nhs.Provider.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.ac.cf.spring.nhs.Provider.Entity.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    
}
