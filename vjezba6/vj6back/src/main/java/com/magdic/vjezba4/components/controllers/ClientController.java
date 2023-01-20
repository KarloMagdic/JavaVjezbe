package com.magdic.vjezba4.components.controllers;

import com.magdic.vjezba4.components.entities.Client;
import com.magdic.vjezba4.components.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8081/")
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    public ClientService clientService;

    @GetMapping()
    public Iterable<Client> getAll() { return clientService.getAllClients(); }

    @PostMapping("/{addressId}")
    public ResponseEntity<Client> createClient(@PathVariable Long addressId, @RequestBody Client newClient) {
        ResponseEntity<Client> response;
        try{
            response = ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(addressId, newClient));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        Client client;
        try {
            client = clientService.getClientById(id);
        } catch (Exception ex) {
            return null;
        }
        return client;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        ResponseEntity response;
        try {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.updateClient(id, updatedClient));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try{
            clientService.deleteClient(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Client");
    }


    @GetMapping("/thyme")
    public String getAllTh(Model model) {
        List<Client> listClient = clientService.getAllClientsList();
        model.addAttribute("listClient", listClient);
        return "clients";
    }

    @GetMapping("/page")
    public List<Client> findPaginated(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "3")int pageSize,
                                @RequestParam(name = "sortField", defaultValue = "id") String sortField,
                                @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection,
                                Model model) {
        Page<Client> page = clientService.findPageable(pageNo , pageSize, sortField, sortDirection);

        return page.getContent();
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView errorMessage(Exception ex)
    {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("error");

        return mav;
    }

}
