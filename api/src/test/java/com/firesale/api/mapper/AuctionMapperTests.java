package com.firesale.api.mapper;

import com.firesale.api.model.*;
import com.firesale.api.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = {AuctionMapperImpl.class, BidMapperImpl.class, UserMapperImpl.class, AddressMapperImpl.class})
public class AuctionMapperTests {

    @Autowired
    private AuctionMapper auctionMapper;

    @Mock
    UserService userService;

    @Test
    void testAuctionMap() {
        // Setup mock repository
        Auction auction = this.getAuction(1L);
        doReturn(auction.getUser()).when(userService).findUserById(anyLong());
        // Execute
        var dto = auctionMapper.toDTO(auction);
        // Assert response
        Assertions.assertEquals(auction.getName(), dto.getName(), "The address returned was not the same as the mock");
        Assertions.assertEquals(auction.getDescription(), dto.getDescription(), "The address returned was not the same as the mock");
        Assertions.assertEquals(auction.getIsFeatured(), dto.getIsFeatured(), "The address returned was not the same as the mock");

    }





    private Auction getAuction(Long id)
    {
        Image i = new Image();
        i.setId(id);
        i.setPath(new byte[0]);
        i.setSort(1);
        i.setType("test");

        Tag t = new Tag();
        t.setName("test-tag");
        t.setId(id);

        Category c = new Category();
        c.setName("test-tag");
        c.setId(id);

        Address address = new Address();
        address.setId(id);
        address.setCity("getCity");
        address.setCountry("getCountry");
        address.setHouseNumber("setHouseNumber");
        address.setPostalCode("setPostalCode");

        User user = new User();
        user.setId(id);
        user.setDisplayName("setDisplayName");
        user.setFirstName("setFirstName");
        user.setAddress(address);
        user.setEmail("setEmail");
        user.setLastName("setLastName");

        Bid b = new Bid();
        b.setValue(15d);
        b.setUser(user);


        Auction auction = new Auction();
        auction.setName("test " + id);
        auction.setIsFeatured(true);
        auction.setMinimalBid(10d);
        auction.setEndDate(LocalDateTime.now());
        auction.setStartDate(LocalDateTime.now());
        auction.setDescription("Description " + id);
        auction.setId(id);

        auction.setUser(user);
        auction.setBids(Arrays.asList(b));
        auction.setFinalBid(b);
        auction.setCategories(Arrays.asList(c));
        auction.setImages(Arrays.asList(i));
        auction.setIsDeleted(false);
        auction.setIsFeatured(true);
        auction.setStatus(AuctionStatus.CLOSED);

        return auction;
    }

}
