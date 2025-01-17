package io.reactivestax.spring_boot_app.repository;

import io.reactivestax.spring_boot_app.domain.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressRepositoryWithEmbeddedDBTest {
    @Autowired
    AddressRepository addressRepository;

    @Test
    public void testSaveAndRetrieveAddress() {
        Address address = new Address();
        address.setStreet("123 abc st.");
        address.setCity("abc city");
        address.setState("abc state");
        address.setZipCode("12345");

        Address savedAddress = addressRepository.save(address);
        Address foundAddress = addressRepository.findById(savedAddress.getId()).orElse(null);

        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getId()).isNotNull();
        assertThat(foundAddress.getZipCode()).isEqualTo(savedAddress.getZipCode());
    }

    @Test
    public void testDeleteAddress() {
        Address address = new Address();
        address.setStreet("123 abc st.");
        address.setCity("abc city");
        address.setState("abc state");
        address.setZipCode("12345");

        Address savedAddress = addressRepository.save(address);
        addressRepository.deleteById(savedAddress.getId());

        assertThat(addressRepository.findById(savedAddress.getId())).isEmpty();
    }

}
