package com.firesale.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firesale.api.dto.auction.AuctionDTO;
import com.firesale.api.exception.GlobalExceptionHandler;
import com.firesale.api.mapper.AuctionMapper;
import com.firesale.api.mapper.BidMapper;
import com.firesale.api.model.Auction;
import com.firesale.api.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class AuctionControllerTests {
    private MockMvc mvc;
    @Mock
    private AuctionService auctionService;
    @Mock
    private UserService userService;
    @Mock
    private AuctionMapper auctionMapper;
    @Mock
    private ImageService imageService;
    @Mock
    private AuctionNotificationService auctionNotificationService;
    @Mock
    private BidService bidService;
    @Mock
    private BidMapper bidMapper;

    @InjectMocks
    private AuctionController auctionController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(auctionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void all() throws Exception {
        // given
        Auction a = new Auction();
        a.setName("test");
        a.setId(1L);

        AuctionDTO aDTO = new AuctionDTO();
        aDTO.setName("test");
        aDTO.setId(1L);

        doReturn(Arrays.asList(a)).when(auctionService).getAuctions();
        doReturn(aDTO).when(auctionMapper).toDTO(any(Auction.class), any(AuctionService.class));


        // when
        MockHttpServletResponse response = mvc.perform(
                get("/auctions")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("test");
    }
}
