package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.VipResponseDto;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    //TODO: OrderService로 리팩토링 해야겠죠..?
    private final OrderRepository orderRepository;
    private final StoreService storeService;

    public List<VipResponseDto> getVipList(Long storeId, Manager manager) {
        Store store = storeService.findStore(storeId, manager);

        List<Object[]> vipAndOrderCountList = orderRepository.getVipAndOrderCountList(store);

        List<VipResponseDto> responseDtoList = new ArrayList<>();

        for (Object[] objects : vipAndOrderCountList) {
            System.out.println("customerId = " + ((Customer) objects[0]).getUserId() + ", order count: " + objects[1]);
            responseDtoList.add(new VipResponseDto((Customer) objects[0], (Long) objects[1]));
        }

        return responseDtoList;
    }

}
