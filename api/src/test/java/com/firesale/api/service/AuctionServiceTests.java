package com.firesale.api.service;

import com.firesale.api.dto.TagDTO;
import com.firesale.api.dto.auction.AuctionFilterDTO;
import com.firesale.api.dto.auction.CreateAuctionDTO;
import com.firesale.api.exception.ResourceNotFoundException;
import com.firesale.api.mapper.AuctionMapper;
import com.firesale.api.model.*;
import com.firesale.api.model.Tag;
import com.firesale.api.repository.AuctionRepository;
import com.firesale.api.repository.CategoryRepository;
import com.firesale.api.repository.UserRepository;
import com.firesale.api.security.Guard;
import com.firesale.api.security.UserPrincipal;
import com.firesale.api.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTests {
    @InjectMocks
    private AuctionService auctionService;

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AuctionMapper auctionMapper;
    @Mock
    private TagService tagService;

    @Mock
    private AuctionNotificationService auctionNotificationService;

    @Mock
    private UserRepository userRepository;
    private static MockedStatic<Guard> mockedGuard;

    @BeforeAll
    public static void init() {
        mockedGuard = mockStatic(Guard.class);
    }

    @AfterAll
    public static void close() {
        mockedGuard.close();
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        // Setup mock repository
        Auction auction = new Auction();
        doReturn(Optional.of(auction)).when(auctionRepository).findById(1L);

        // Execute service call
        Optional<Auction> returnedAuction = Optional.ofNullable(auctionService.findAuctionById(1));

        // Assert response
        Assertions.assertTrue(returnedAuction.isPresent(), "Auction was not found");
        Assertions.assertSame(returnedAuction.get(), auction, "The auction returned was not the same as the mock");
        verify(auctionRepository).findById(any(Long.class));
    }


    @Test
    @DisplayName("Test filterAuctions 1 Success")
    void filterAuctions1() {
        // Setup mock repository
        AuctionFilterDTO filter = new AuctionFilterDTO();

        long[] ar = {1L, 2L, 3L};
        String[] ars = {"1L", "2L", "3L"};
        filter.setCategories(ar);
        filter.setTags(ars);
        filter.setName("ar");

        var auctions = getEmptyAuctions();

        doReturn(auctions).when(auctionRepository).findAuctionsByTagsLikeAndCategoriesANDNameLike(any(), any(), any());

        // Execute service call
        var returnedAuctions = auctionService.filterAuctions(filter);

        // Assert response
        Assertions.assertTrue(!returnedAuctions.isEmpty(), "Auctions not found");
        Assertions.assertSame(auctions, returnedAuctions, "The auction returned was not the same as the mock");
        verify(auctionRepository).findAuctionsByTagsLikeAndCategoriesANDNameLike(any(), any(), any());
    }

    @Test
    @DisplayName("Test filterAuctions 2 Success")
    void filterAuctions2() {
        // Setup mock repository
        AuctionFilterDTO filter = new AuctionFilterDTO();

        long[] ar = {1L, 2L, 3L};
        String[] ars = {"1L", "2L", "3L"};
        filter.setCategories(ar);
        filter.setTags(ars);
        filter.setName(null);

        var auctions = getEmptyAuctions();

        doReturn(auctions).when(auctionRepository).findAuctionsByTagsLikeAndCategoriesLike(any(), any());

        // Execute service call
        var returnedAuctions = auctionService.filterAuctions(filter);

        // Assert response
        Assertions.assertTrue(!returnedAuctions.isEmpty(), "Auctions not found");
        Assertions.assertSame(auctions, returnedAuctions, "The auction returned was not the same as the mock");
        verify(auctionRepository).findAuctionsByTagsLikeAndCategoriesLike(any(), any());
    }

    @Test
    @DisplayName("Test filterAuctions 3 Success")
    void filterAuctions3() {
        // Setup mock repository
        AuctionFilterDTO filter = new AuctionFilterDTO();

        long[] ar = {1L, 2L, 3L};
        String[] ars = {"1L", "2L", "3L"};
        filter.setCategories(ar);
        filter.setTags(null);
        filter.setName("null");

        var auctions = getEmptyAuctions();

        doReturn(auctions).when(auctionRepository).findAuctionsByCategoriesLikeAndNameLike(any(), any());

        // Execute service call
        var returnedAuctions = auctionService.filterAuctions(filter);

        // Assert response
        Assertions.assertTrue(!returnedAuctions.isEmpty(), "Auctions not found");
        Assertions.assertSame(auctions, returnedAuctions, "The auction returned was not the same as the mock");
        verify(auctionRepository).findAuctionsByCategoriesLikeAndNameLike(any(), any());
    }

    @Test
    @DisplayName("Test filterAuctions 4 Success")
    void filterAuctions4() {
        // Setup mock repository
        AuctionFilterDTO filter = new AuctionFilterDTO();

        long[] ar = {1L, 2L, 3L};
        String[] ars = {"1L", "2L", "3L"};
        filter.setCategories(null);
        filter.setTags(ars);
        filter.setName("null");

        var auctions = getEmptyAuctions();

        doReturn(auctions).when(auctionRepository).findAuctionsByTagsLikeAndNameLike(any(), any());

        // Execute service call
        var returnedAuctions = auctionService.filterAuctions(filter);

        // Assert response
        Assertions.assertTrue(!returnedAuctions.isEmpty(), "Auctions not found");
        Assertions.assertSame(auctions, returnedAuctions, "The auction returned was not the same as the mock");
        verify(auctionRepository).findAuctionsByTagsLikeAndNameLike(any(), any());
    }

    @Test
    @DisplayName("Test filterAuctions 5 Success")
    void filterAuctions5() {
        // Setup mock repository
        AuctionFilterDTO filter = new AuctionFilterDTO();

        long[] ar = {1L, 2L, 3L};
        String[] ars = {"1L", "2L", "3L"};
        filter.setCategories(ar);
        filter.setTags(null);
        filter.setName(null);

        var auctions = getEmptyAuctions();

        doReturn(auctions).when(auctionRepository).findAuctionsByCategories(any());

        // Execute service call
        var returnedAuctions = auctionService.filterAuctions(filter);

        // Assert response
        Assertions.assertTrue(!returnedAuctions.isEmpty(), "Auctions not found");
        Assertions.assertSame(auctions, returnedAuctions, "The auction returned was not the same as the mock");
        verify(auctionRepository).findAuctionsByCategories(any());
    }

    @Test
    @DisplayName("Test filterAuctions 6 Success")
    void filterAuctions6() {
        // Setup mock repository
        AuctionFilterDTO filter = new AuctionFilterDTO();

        long[] ar = {1L, 2L, 3L};
        String[] ars = {"1L", "2L", "3L"};
        filter.setCategories(null);
        filter.setTags(ars);
        filter.setName(null);

        var auctions = getEmptyAuctions();

        doReturn(auctions).when(auctionRepository).findAuctionsByTags(any());

        // Execute service call
        var returnedAuctions = auctionService.filterAuctions(filter);

        // Assert response
        Assertions.assertTrue(!returnedAuctions.isEmpty(), "Auctions not found");
        Assertions.assertSame(auctions, returnedAuctions, "The auction returned was not the same as the mock");
        verify(auctionRepository).findAuctionsByTags(any());
    }

    @Test
    @DisplayName("Test filterAuctions 7 Success")
    void filterAuctions7() {
        // Setup mock repository
        AuctionFilterDTO filter = new AuctionFilterDTO();

        long[] ar = {1L, 2L, 3L};
        String[] ars = {"1L", "2L", "3L"};
        filter.setCategories(null);
        filter.setTags(null);
        filter.setName("null");

        var auctions = getEmptyAuctions();

        doReturn(auctions).when(auctionRepository).findAuctionsByNameLike(any());

        // Execute service call
        var returnedAuctions = auctionService.filterAuctions(filter);

        // Assert response
        Assertions.assertTrue(!returnedAuctions.isEmpty(), "Auctions not found");
        Assertions.assertSame(auctions, returnedAuctions, "The auction returned was not the same as the mock");
        verify(auctionRepository).findAuctionsByNameLike(any());
    }

    @Test
    @DisplayName("Test filterAuctions 8 Success")
    void filterAuctions8() {
        // Setup mock repository
        AuctionFilterDTO filter = new AuctionFilterDTO();
        Page<Auction> auctionPage = Mockito.mock(Page.class);
        long[] ar = {1L, 2L, 3L};
        String[] ars = {"1L", "2L", "3L"};
        filter.setCategories(null);
        filter.setTags(null);
        filter.setName(null);

        var auctions = getEmptyAuctions();

        doReturn(auctionPage).when(auctionRepository).findActiveAuctions(any());
        doReturn(auctions).when(auctionPage).getContent();
        // Execute service call
        var returnedAuctions = auctionService.filterAuctions(filter);

        // Assert response
        Assertions.assertTrue(!returnedAuctions.isEmpty(), "Auctions not found");
        Assertions.assertSame(auctions, returnedAuctions, "The auction returned was not the same as the mock");
        verify(auctionRepository).findActiveAuctions(any());
    }




    @Test
    @DisplayName("Test getActiveAuctions Success")
    void testFindActive() {
        // Setup mock repository
        Auction a = new Auction();
        a.setId(1L);
        a.setName("test");
        a.setBids(new ArrayList<>());
        a.setTags(new ArrayList<>());
        a.setEndDate(LocalDateTime.now().plusDays(1));
        a.setStartDate(LocalDateTime.now());
        a.setStatus(AuctionStatus.READY);
        a.setDescription("Description");
        List<Auction> auctions = (Arrays.asList(a));
        Page<Auction> pagedAuctions = new PageImpl(auctions);
        doReturn(pagedAuctions).when(auctionRepository).findActiveAuctions(PageRequest.of(0, 10));
        // Execute service call
        Collection<?> returnedAuction = auctionService.getActiveAuctions(PageRequest.of(0, 10));
        // Assert response
        Assertions.assertNotNull(returnedAuction, "Auction was not found");
        Assertions.assertSame(returnedAuction.toArray()[0], a, "The auction returned was not the same as the mock");
        verify(auctionRepository).findActiveAuctions(any(Pageable.class));
    }

    @Test
    @DisplayName("Test findByUserId Success")
    void testFindByUserId() {
        // Setup mock repository
        Auction a = new Auction();
        a.setId(1L);
        a.setName("test");
        a.setBids(new ArrayList<>());
        a.setTags(new ArrayList<>());
        a.setEndDate(LocalDateTime.now().plusDays(1));
        a.setStartDate(LocalDateTime.now());
        a.setStatus(AuctionStatus.READY);
        a.setDescription("Description");
        List<Auction> auctions = (Arrays.asList(a));
        doReturn(auctions).when(auctionRepository).findByUserIdAndIsDeletedFalseOrderByEndDateDesc(1L);
        // Execute service call
        var returnedAuction = auctionService.getAuctionsByUserId(1l);
        // Assert response
        Assertions.assertNotNull(returnedAuction, "Auction was not found");
        Assertions.assertSame(returnedAuction.toArray()[0], a, "The auction returned was not the same as the mock");
        verify(auctionRepository).findByUserIdAndIsDeletedFalseOrderByEndDateDesc(any(Long.class));
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        // Setup mock repository
        doReturn(Optional.empty()).when(auctionRepository).findById(1L);

        // Execute service call
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            auctionService.findAuctionById(1l);
        });

        // Assert response
        assertThat(exception.getMessage()).isEqualTo("Resource was not found: [No auction exists for id: 1]");
        verify(auctionRepository).findById(any(Long.class));
    }

    @Test
    @DisplayName("Test create Success")
    void create() {
        // Setup mock repository
        String email = "test@test.nl";
        var user = new User();
        user.setId(1L);
        UserPrincipal principal = new UserPrincipal(user);



        try(MockedStatic<SecurityUtil> dummy = Mockito.mockStatic(SecurityUtil.class)){
            dummy.when(() -> SecurityUtil.getSecurityContextUser()).thenReturn(principal);
            doReturn(getEmptyCategories()).when(categoryRepository).findByIdIn(any());
            when(auctionMapper.toModel(any(CreateAuctionDTO.class))).thenAnswer((answer) -> {
                var dto = (CreateAuctionDTO)answer.getArguments()[0];
                return new Auction();
            });
            doReturn(new User()).when(userRepository).getOne(any(Long.class));
            when(tagService.getTagByName(anyString())).thenReturn(null, new Tag());
            doReturn(new Tag()).when(tagService).createTag(any(String.class));
            when(auctionRepository.save(any(Auction.class))).thenAnswer((answer) -> {
                return (Auction)answer.getArguments()[0];
            });
            CreateAuctionDTO dto = new CreateAuctionDTO();
            var tag1 = new TagDTO();
            tag1.setName("test");
            var tag2 = new TagDTO();
            tag2.setName("test2");
            dto.setTags(Arrays.asList(tag1, tag2));
            dto.setCategories(Arrays.asList(1L , 2L));

            auctionService.create(dto);
            verify(auctionRepository).save(any(Auction.class));
            verify(tagService).createTag(any(String.class));
            verify(tagService).createTag(any(String.class));
        }
    }


    @Test
    @DisplayName("Test patch empty Success")
    void testPatchEmpty() {
        // Setup mock repository
        mockedGuard.when(Guard::isAdmin).thenReturn(true);
        mockedGuard.when(() -> Guard.isSelf(anyLong())).thenReturn(false);
        Auction auction = this.getEmptyAuction(1L);
        User u = new User();
        u.setId(1L);
        auction.setUser(u);
        doReturn(Optional.of(auction)).when(auctionRepository).findById(anyLong());
        when(auctionRepository.save(any(Auction.class))).thenAnswer((answer) -> {
            return (Auction)answer.getArguments()[0];
        });
        // Execute service call
        var returnedAuction = auctionService.patchAuction(1L, auction);
        // Assert response
        Assertions.assertTrue(returnedAuction != null, "Auction was not found");
        Assertions.assertSame(auction.getId(), returnedAuction.getId(), "The auction returned was not the same as the mock");

    }

    @Test
    @DisplayName("Test patch Success")
    void testPatch() {
        // Setup mock repository
        mockedGuard.when(Guard::isAdmin).thenReturn(true);
        mockedGuard.when(() -> Guard.isSelf(anyLong())).thenReturn(false);
        Auction auction = this.getEmptyAuction(1L);
        User u = new User();
        u.setId(1L);
        auction.setUser(u);


        auction.setStartDate(LocalDateTime.of(3000, 1, 1,1 , 1));
        auction.setName("test");
        auction.setDescription("test");
        auction.setDescription("test");
        auction.setEndDate(LocalDateTime.of(3000, 1, 1,1 , 1));
        auction.setMinimalBid(10d);
        auction.setIsFeatured(true);
        auction.setStatus(AuctionStatus.CLOSED);


        doReturn(Optional.of(auction)).when(auctionRepository).findById(anyLong());

        when(auctionRepository.save(any(Auction.class))).thenAnswer((answer) -> {
            return (Auction)answer.getArguments()[0];
        });
        doNothing().when(auctionNotificationService).sendStatusNotification(anyLong(),any(AuctionStatus.class));

        // Execute service call
        var returnedAuction = auctionService.patchAuction(1L, auction);

        // Assert response
        Assertions.assertTrue(returnedAuction != null, "Auction was not found");
        Assertions.assertSame(auction.getId(), returnedAuction.getId(), "The auction returned was not the same as the mock");

    }









    private Category getEmpty(Long id)
    {
        Category category = new Category();
        category.setName("test " + id);
        category.setId(id);
        category.setArchived(false);
        category.setAuctions(new ArrayList<>());
        return category;
    }



    private List<Category> getEmptyCategories()
    {
        ArrayList<Category> categories = new ArrayList<>();
        for(int i = 1; i <= 10; i++)
        {
            var c = getEmpty(Long.valueOf(i));
            categories.add(c);
        }
        return categories;
    }

    private Auction getEmptyAuction(Long id)
    {
        Auction auction = new Auction();
        auction.setName("test " + id);
        auction.setId(id);
        return auction;
    }



    private List<Auction> getEmptyAuctions()
    {
        ArrayList<Auction> auctions = new ArrayList<>();
        for(int i = 1; i <= 10; i++)
        {
            var auction = getEmptyAuction(Long.valueOf(i));
            auctions.add(auction);
        }
        return auctions;
    }

}
