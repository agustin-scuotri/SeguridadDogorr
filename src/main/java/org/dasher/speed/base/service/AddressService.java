package org.dasher.speed.base.service;

import org.dasher.speed.base.domain.Address;
import org.dasher.speed.base.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository repo;

    public AddressService(AddressRepository repo) {
        this.repo = repo;
    }

    public List<Address> findAll()               { return repo.findAll(); }
    public Optional<Address> findById(Long id)   { return repo.findById(id); }
    public Address save(Address address)         { return repo.save(address); }
    public void deleteById(Long id)              { repo.deleteById(id); }
    public List<Address> findByCity(String city) { return repo.findByCity(city); }
}
