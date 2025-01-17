package io.reactivestax.spring_boot_app.service;

import io.reactivestax.spring_boot_app.domain.Address;
import io.reactivestax.spring_boot_app.dto.AddressDTO;
import io.reactivestax.spring_boot_app.repository.AddressRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@SpringBootTest
@ActiveProfiles("test")
class AddressServiceTest {

    @Autowired
    AddressService addressService;

    @MockitoBean
    AddressRepository mockedAddressRepository;

    private static Address address;

    @BeforeAll
    static void beforeAll() {
        address = new Address();
        address.setId(10L);
        address.setStreet("abc street");
        address.setCity("abc city");
        address.setState("abc state");
        address.setZipCode("12345");
    }

    @Test
    public void testfindAll() {
        Mockito.when(mockedAddressRepository.findAll()).thenReturn(
                Arrays.asList(address));

        assertThat(addressService.findAll()).hasSize(1);
    }

    @Test
    public void testFindById() {
        Mockito.when(mockedAddressRepository.findById(10L)).thenReturn(Optional.of(address));

        Optional<AddressDTO> addressDTO = addressService.findById(10L);

        assertThat(addressDTO.isPresent());
        assertThat(addressDTO.get().getZipCode()).isEqualTo(address.getZipCode());
    }

    @Test
    public void testSave() {
        Mockito.when(mockedAddressRepository.save(any(Address.class))).thenReturn(address);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(10L);
        addressDTO.setStreet("abc street");
        addressDTO.setCity("abc city");
        addressDTO.setState("abc state");
        addressDTO.setZipCode("12345");
        AddressDTO savedAddress = addressService.save(addressDTO);
        assertThat(savedAddress.getZipCode()).isEqualTo(address.getZipCode());
    }

    @Test
    public void testDeleteById(){
        Mockito.doNothing().when(mockedAddressRepository).deleteById(anyLong());
        addressService.deleteById(10L);
        Mockito.verify(mockedAddressRepository,times(1)).deleteById(10L);
    }
}