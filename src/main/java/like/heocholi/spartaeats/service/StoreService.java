package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.StoreRequestDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.repository.StoreRepository;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreResponseDto createStore(StoreRequestDto requestDto, UserDetailsImpl userDetails) {
        Manager manager = userDetails.getManager();
        Store store = new Store(requestDto, manager);
        Store savedStore = storeRepository.save(store);
        return new StoreResponseDto(savedStore);
    }

    public List<StoreResponseDto> readAllStore(UserDetailsImpl userDetails) {
        Manager manager = userDetails.getManager();
        List<Store> storeList = storeRepository.findByManagerId(manager.getId());
        return storeList.stream()
                .map(StoreResponseDto::new)
                .toList();
    }

    public StoreResponseDto readStore(UserDetailsImpl userDetails, Long storeId) {
        Manager manager = userDetails.getManager();
        Store store = findStore(storeId, manager);
        return new StoreResponseDto(store);
    }

    @Transactional
    public StoreResponseDto updateStore(StoreRequestDto requestDto, UserDetailsImpl userDetails, Long storeId) {
        Manager manager = userDetails.getManager();

        Store store = findStore(storeId, manager);

        store.update(requestDto);
        Store updatedStore = storeRepository.save(store);

        return new StoreResponseDto(updatedStore);
    }

    @Transactional
    public Long deleteStore(UserDetailsImpl userDetails, Long storeId) {
        Manager manager = userDetails.getManager();
        Store store = findStore(storeId, manager);
        storeRepository.delete(store);
        return store.getId();
    }

    public Store findStore(Long storeId, Manager manager) {
        return storeRepository.findByIdAndManagerId(storeId, manager.getId())
                .orElseThrow(()-> new IllegalStateException("해당 가게가 없습니다."));
    }


}
