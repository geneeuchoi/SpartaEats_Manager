package like.heocholi.spartaeats.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import like.heocholi.spartaeats.dto.BestMenusResponseDto;
import like.heocholi.spartaeats.dto.CustomerResponseDTO;
import like.heocholi.spartaeats.dto.DailySalesResponseDto;
import like.heocholi.spartaeats.dto.VipResponseDto;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.entity.Pick;
import like.heocholi.spartaeats.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j(topic = "Analytics")
public class AnalyticsService {
    private final StoreService storeService;
    private final OrderService orderService;
    private final OrderMenuService orderMenuService;
    private final MenuService menuService;


    //가게별 찜하기 수 보기
    public Long getPickCount(Long storeId, Manager manager) {
        Store store = storeService.findStore(storeId, manager);

        List<Pick> pickList = store.getPickList();

        return pickList.stream().filter(Pick::isPick).count();
    }

    //찜하기 한 customer 리스트 보기
    public List<CustomerResponseDTO> getPickCustomers(Long storeId, Manager manager) {
        Store store = storeService.findStore(storeId, manager);

        List<Pick> pickList = store.getPickList().stream().filter(Pick::isPick).toList();
        List<Customer> customerList = pickList.stream().map(Pick::getCustomer).toList();

        return customerList.stream().map(CustomerResponseDTO::new).toList();
    }

    /**
     * 가게의 VIP 고객 리스트 조회
     * @param storeId 가게Id
     * @param manager 매니저 정보
     * @return (VIP 고객 ID, 주문량) 5개
     */
    public List<VipResponseDto> getVipList(Long storeId, Manager manager) {
        Store store = storeService.findStore(storeId, manager);

        List<VipResponseDto> vipList = orderService.getVipList(store);

        for (VipResponseDto vipResponseDto : vipList) {
            log.info("customerId = " + vipResponseDto.getVipId() + ", order count: " + vipResponseDto.getOrderCount());
        }

        return vipList;
    }

    /**
     * 인기메뉴 조회
     * @param storeId 가게Id
     * @param manager 매니저 정보
     * @return (인기메뉴의 Id, 이름, 판매수) 5개
     */
    public List<BestMenusResponseDto> getBestMenus(Long storeId, Manager manager){
        List<Menu> menuList = menuService.findAllByStoreId(storeId);

        List<BestMenusResponseDto> bestMenuList = orderMenuService.getBestMenuList(menuList);

        for (BestMenusResponseDto bestMenusResponseDto : bestMenuList) {
            log.info("menu id = " + bestMenusResponseDto.getMenuId() + ", menu name: " + bestMenusResponseDto.getMenuName() +", order count: " + bestMenusResponseDto.getOrderCount());
        }

        return bestMenuList;
    }

    /**
     * 가게의 날짜별 주문건수 조회
     * @param storeId 가게Id
     * @param manager 매니저 정보
     * @return (날짜, 주문건수, 매출) 리스트
     */
    public List<DailySalesResponseDto> getDailySales(Long storeId, Manager manager){
        Store store = storeService.findStore(storeId, manager);

        List<DailySalesResponseDto> dailySalesList = orderService.getDailySales(store);

        for (DailySalesResponseDto dailySales : dailySalesList) {
            log.info("날짜: " + dailySales.getDate() + ", 주문 개수: " + dailySales.getOrderCount() + ", 총 판매 금액: " + dailySales.getSales());
        }

        return dailySalesList;
    }
}