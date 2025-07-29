package org.dasher.speed.base.repository;

import org.dasher.speed.base.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCity(String city);
}
