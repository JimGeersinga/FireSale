package com.firesale.api.service;

import com.firesale.api.exception.ResourceNotFoundException;
import com.firesale.api.model.Address;
import com.firesale.api.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        // Setup mock repository
        Address address = new Address();
        doReturn(Optional.of(address)).when(addressRepository).findById(1L);

        // Execute service call
        Optional<Address> returnedAddress = Optional.ofNullable(addressService.findAddressById(1L));

        // Assert response
        Assertions.assertTrue(returnedAddress.isPresent(), "Address was not found");
        Assertions.assertSame(returnedAddress.get(), address, "The address returned was not the same as the mock");
        verify(addressRepository).findById(any(Long.class));
    }

    @Test
    @DisplayName("Test findById Failure")
    void testFindByIdFailure() {
        // Setup mock repository
        doReturn(Optional.ofNullable(null)).when(addressRepository).findById(1L);

        // Execute service call
        var exception = assertThrows(ResourceNotFoundException.class, () ->addressService.findAddressById(1L));

        // Assert response
        var expectedMessage = String.format("No address exists for id: %d", 1L);
        var actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(addressRepository).findById(any(Long.class));
    }

    @Test
    @DisplayName("Test update Success")
    void testUpdateAddress() {
        // Setup mock repository
        Address address = this.get();
        doReturn(address).when(addressRepository).save(address);
        doReturn(Optional.of(address)).when(addressRepository).findById(1L);

        // Execute service call
        Address returnedAddress = addressService.updateAddress(1L, address);

        // Assert response
        Assertions.assertTrue(returnedAddress != null, "Address was not found");
        Assertions.assertSame(returnedAddress, address, "The address returned was not the same as the mock");
        verify(addressRepository).findById(any(Long.class));
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    @DisplayName("Test update Failure")
    void testUpdateAddressFailure() {
        // Setup mock repository
        Address address = this.get();

        doReturn(Optional.ofNullable(null)).when(addressRepository).findById(1L);

        // Execute service call
        var exception = assertThrows(ResourceNotFoundException.class, () ->addressService.updateAddress(1L, address));

        // Assert response
        var expectedMessage = String.format("No address exists for id: %d", 1L);
        var actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(addressRepository).findById(any(Long.class));
    }


    @Test
    @DisplayName("Test patch Success")
    void testPatchAddress() {
        // Setup mock repository
        Address address = this.get();
        doReturn(address).when(addressRepository).save(address);
        doReturn(Optional.of(address)).when(addressRepository).findById(1L);

        // Execute service call
        Address returnedAddress = addressService.patchAddress(1L, address);

        // Assert response
        Assertions.assertTrue(returnedAddress != null, "Address was not found");
        Assertions.assertSame(returnedAddress, address, "The address returned was not the same as the mock");
        verify(addressRepository).findById(any(Long.class));
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    @DisplayName("Test patch Failure")
    void testPatchAddressFailure() {
        // Setup mock repository
        Address address = this.get();

        doReturn(Optional.ofNullable(null)).when(addressRepository).findById(1L);

        // Execute service call
        var exception = assertThrows(ResourceNotFoundException.class, () ->addressService.patchAddress(1L, address));

        // Assert response
        var expectedMessage = String.format("No address exists for id: %d", 1L);
        var actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(addressRepository).findById(any(Long.class));
    }


    private Address get()
    {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Achterwetering");
        address.setHouseNumber("23");
        address.setPostalCode("2871RK");
        address.setCity("Schoonhoven");
        address.setCountry("Nederland");
        return  address;
    }



}
