package io.reactivestax.spring_boot_app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivestax.spring_boot_app.domain.Address;
import io.reactivestax.spring_boot_app.domain.Employee;
import io.reactivestax.spring_boot_app.dto.AddressDTO;
import io.reactivestax.spring_boot_app.dto.EmployeeDTO;
import io.reactivestax.spring_boot_app.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressService addressService;

    @Test
    public void whenGetRequestToAddress_thenCorrectResponse() throws Exception {

        AddressDTO address1 = new AddressDTO();
        address1.setId(10L);
        address1.setStreet("abc street");
        address1.setCity("abc City");
        address1.setState("abc State");
        address1.setZipCode("12345");
        address1.setZipCodeTwo("67890");

        AddressDTO address2 = new AddressDTO();
        address2.setId(20L);
        address2.setStreet("def street");
        address2.setCity("def City");
        address2.setState("def State");
        address2.setZipCode("67890");
        address2.setZipCodeTwo("12345");

        List<AddressDTO> address = Arrays.asList(address1, address2);
        when(addressService.findAll()).thenReturn(address);

        ObjectMapper objectMapper = new ObjectMapper();
        String addressJsonResponse = objectMapper.writeValueAsString(address);

        mockMvc.perform(get("/api/addresses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(addressJsonResponse));
    }

    @Test
    public void whenGetRequestToAddressById_thenCorrectResponse() throws Exception {
        AddressDTO address1 = new AddressDTO();
        address1.setId(10L);
        address1.setStreet("abc street");
        address1.setCity("abc City");
        address1.setState("abc State");
        address1.setZipCode("12345");
        address1.setZipCodeTwo("67890");

        when(addressService.findById(10L)).thenReturn(Optional.of(address1));

        ObjectMapper objectMapper = new ObjectMapper();
        String addressJsonResponse = objectMapper.writeValueAsString(address1);

        mockMvc.perform(get("/api/addresses/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(addressJsonResponse));
    }

    @Test
    public void whenPostRequestToAddress_thenCorrectResponse() throws Exception {
        String addressJson = """
                   {
                        "street": "Vastrapur",
                        "city": "ahmedabad",
                        "state": "ON",
                        "zipCode": "45678",
                        "zipCodeTwo": "12345"
                    }
                """;

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(10L);
        addressDTO.setStreet("Vastrapur");
        addressDTO.setCity("ahmedabad");
        addressDTO.setState("ON");
        addressDTO.setZipCode("45678");
        addressDTO.setZipCodeTwo("12345");

        when(addressService.save(any(addressDTO.getClass()))).thenReturn(addressDTO);

        mockMvc.perform(post("/api/addresses")
                .content(addressJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(addressJson));
    }

    @Test
    public void whenPutRequestToAddress_thenCorrectResponse() throws Exception {
        String addressJson = """
                   {
                        "street": "Vastrapur",
                        "city": "ahmedabad",
                        "state": "ON",
                        "zipCode": "45678",
                        "zipCodeTwo": "12345"
                    }
                """;

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(10L);
        addressDTO.setStreet("Vastrapur");
        addressDTO.setCity("ahmedabad");
        addressDTO.setState("ON");
        addressDTO.setZipCode("45678");
        addressDTO.setZipCodeTwo("12345");

        when(addressService.save(any(addressDTO.getClass()))).thenReturn(addressDTO);
        when(addressService.findById(anyLong())).thenReturn(Optional.of(addressDTO));

        mockMvc.perform(put("/api/addresses/10")
                        .content(addressJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(addressJson));

    }

    @Test
    public void whenPutRequestToAddress_thenNotFoundResponse() throws Exception {
        String addressJson = """
                   {
                        "street": "Vastrapur",
                        "city": "ahmedabad",
                        "state": "ON",
                        "zipCode": "45678",
                        "zipCodeTwo": "12345"
                    }
                """;
        when(addressService.findById(20L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/addresses/20")
                        .content(addressJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void whenDeleteRequestToAddressById_thenCorrectResponse() throws Exception {
        AddressDTO addressDTO = new AddressDTO();
        when(addressService.findById(anyLong())).thenReturn(Optional.of(addressDTO));
        mockMvc.perform(delete("/api/addresses/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        when(addressService.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/addresses/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}