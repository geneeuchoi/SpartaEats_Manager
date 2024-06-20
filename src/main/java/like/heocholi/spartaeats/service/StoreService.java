package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.StoreRequestDto;
import like.heocholi.spartaeats.dto.StoreResponseDto;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.repository.StoreRepository;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreResponseDto createStore(StoreRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Manager manager = userDetails.getManager();
        Store store = new Store(requestDto, manager);
        Store savedStore = storeRepository.save(store);
        return new StoreResponseDto(savedStore);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> readAllStore(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Manager manager = userDetails.getManager();
        List<Store> storeList = storeRepository.findByManagerId(manager.getId());
        return storeList.stream()
                .map(StoreResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public StoreResponseDto readStore(@AuthenticationPrincipal UserDetailsImpl userDetails, Long storeId) {
        Manager manager = userDetails.getManager();
        Store store = storeRepository.findByIdAndManagerId(storeId, manager.getId())
                .orElseThrow(()-> new IllegalStateException("해당 가게가 없습니다."));
        return new StoreResponseDto(store);
    }

    @Transactional
    public StoreResponseDto updateStore(StoreRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, Long storeId) {
        Manager manager = userDetails.getManager();

        Store store = storeRepository.findByIdAndManagerId(storeId, manager.getId())
                .orElseThrow(()-> new IllegalStateException("해당 가게가 없습니다."));

        store.update(requestDto);
        Store updatedStore = storeRepository.save(store);

        return new StoreResponseDto(updatedStore);
    }

    @Transactional
    public Long deleteStore(@AuthenticationPrincipal UserDetailsImpl userDetails, Long storeId) {
        Manager manager = userDetails.getManager();
        Store store = storeRepository.findByIdAndManagerId(storeId, manager.getId())
                .orElseThrow(()-> new IllegalStateException("해당 가게가 없습니다."));
        storeRepository.delete(store);
        return store.getId();
    }


}
