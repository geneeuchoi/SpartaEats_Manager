package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.BestMenusResponseDto;
import like.heocholi.spartaeats.dto.DailyOrdersResponseDto;
import like.heocholi.spartaeats.dto.VipResponseDto;
import like.heocholi.spartaeats.entity.*;
import like.heocholi.spartaeats.repository.MenuRepository;
import like.heocholi.spartaeats.repository.OrderMenuRepository;
import like.heocholi.spartaeats.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    //TODO: Service로 리팩토링 해야겠죠..?
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderMenuRepository orderMenuRepository;
    // TODO: ------------------------

    private final StoreService storeService;

    public List<VipResponseDto> getVipList(Long storeId, Manager manager) {
        Store store = storeService.findStore(storeId, manager);
        // TODO: return Optional 걸고, Exception
        List<Object[]> vipAndOrderCountList = orderRepository.getVipAndOrderCountList(store);

        List<VipResponseDto> responseDtoList = new ArrayList<>();

        for (Object[] objects : vipAndOrderCountList) {
            System.out.println("customerId = " + ((Customer) objects[0]).getUserId() + ", order count: " + objects[1]);
            responseDtoList.add(new VipResponseDto((Customer) objects[0], (Long) objects[1]));
        }

        return responseDtoList;
    }


    public List<BestMenusResponseDto> getBestMenus(Long storeId, Manager manager){
        List<Menu> menuList = menuRepository.findAllByStoreId(storeId);
        // TODO: return Optional 걸고, Exception
        List<Object[]> bestMenus = orderMenuRepository.findBestMenus(menuList);

        List<BestMenusResponseDto> bestMenuList = new ArrayList<>();
        for (Object[] objects : bestMenus) {
            System.out.println("menu id = " + ((Menu) objects[0]).getId() + ", menu name: " + ((Menu) objects[0]).getName() +", order count: " + (Long) objects[1]);
            bestMenuList.add(new BestMenusResponseDto((Menu) objects[0], (Long) objects[1]));
        }

        return bestMenuList;
    }


    public List<DailyOrdersResponseDto> getOrdersByDate(Long storeId, Manager manager){
        Store store = storeService.findStore(storeId, manager);

        List<Object[]> ordersByDate = orderRepository.findOrdersByDate(store);

        List<DailyOrdersResponseDto> dailyOrderList = new ArrayList<>();

        for (Object[] objects : ordersByDate) {
            Date date = (Date) objects[0];
            Long orderCount = (Long) objects[1];
            Long sales = (Long) objects[2];
            System.out.println("날짜: " + date + ", 주문 개수: " + orderCount + ", 총 판매 금액: " + sales);
            dailyOrderList.add(new DailyOrdersResponseDto(date, orderCount, sales));
        }

        return dailyOrderList;
    }
}
