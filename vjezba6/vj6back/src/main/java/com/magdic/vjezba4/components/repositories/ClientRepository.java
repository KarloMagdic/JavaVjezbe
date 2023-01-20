package com.magdic.vjezba4.components.repositories;

import com.magdic.vjezba4.components.entities.Address;
import com.magdic.vjezba4.components.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByAddress(Address address);
}
