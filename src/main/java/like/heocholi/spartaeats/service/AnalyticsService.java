package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.CustomerResponseDTO;
import like.heocholi.spartaeats.entity.Customer;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Pick;
import like.heocholi.spartaeats.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalyticsService {
    private final StoreService storeService;

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
}