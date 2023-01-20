package com.magdic.vjezba4.components.services;

import com.magdic.vjezba4.components.entities.Address;
import com.magdic.vjezba4.components.entities.Client;
import com.magdic.vjezba4.components.repositories.AddressRepository;
import com.magdic.vjezba4.components.repositories.ClientRepository;
import com.magdic.vjezba4.components.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DeviceRepository deviceRepository;


    public Iterable<Client> getAllClients() { return clientRepository.findAll(); }


    public Client getClientById(Long id) throws Exception{

        Client Client = clientRepository.findById(id).orElse(null);
        if(Client == null) {
            throw new Exception("Could not find Client");
        }

        return Client;
    }

    public Client createClient(Long addressId, Client newClient) throws Exception {
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address == null) {
            throw new Exception("Address does not exists");
        }
        Client client = clientRepository.findClientByAddress(address);
        if(client != null){
            throw new Exception("Client with address already exists");
        }

        newClient.setAddress(address);

        return clientRepository.save(newClient);

    }

    public Client updateClient(Long id, Client updatedClient) throws Exception {
        Client oldClient = clientRepository.findById(id).orElse(null);
        if(oldClient == null) {
            throw new Exception("Could not find Client");
        }
        updatedClient.setId(oldClient.getId());
        return clientRepository.save(updatedClient);
    }

    public void deleteClient(Long id) throws Exception{
        Client deleteClient = clientRepository.findById(id).orElse(null);
        if(deleteClient == null) {
            throw new Exception("Could not find Client");
        }

        clientRepository.delete(deleteClient);
    }

    public List<Client> getAllClientsList()
    {
        return clientRepository.findAll();
    }

    public Page<Client> findPageable(int pageNo, int pageSize, String sortField, String sortDirection)
    {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return clientRepository.findAll(pageable);
    }

}