package iuh.fit.fashionecommercewebsitebackend.repositories;


import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    boolean existsByProviderName(String providerName);
}