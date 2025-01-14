package io.reactivestax.spring_boot_app.controller;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactivestax.spring_boot_app.dto.AddressDTO;
import io.reactivestax.spring_boot_app.service.AddressService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService service;

    @Autowired
    Environment environment;



    @Value("${spring.datasource.password}")
    String springApplicationName;


    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        log.debug("spring application name is "+ springApplicationName);
        //environment.getActiveProfiles()
        //environment.getProperty();
        //environment.getRequiredProperty();
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {

        Optional<AddressDTO> address = service.findById(id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AddressDTO createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        return service.save(addressDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDetails) {
        Optional<AddressDTO> address = service.findById(id);
        if (address.isPresent()) {
            AddressDTO updatedAddress = address.get();
            updatedAddress.setStreet(addressDetails.getStreet());
            updatedAddress.setCity(addressDetails.getCity());
            updatedAddress.setState(addressDetails.getState());
            updatedAddress.setZipCode(addressDetails.getZipCode());
            return ResponseEntity.ok(service.save(updatedAddress));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}