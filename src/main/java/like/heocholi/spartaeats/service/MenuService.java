package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.repository.MenuRepository;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public ResponseEntity<ResponseMessage> getMenu(Long storeId, Long menuId) {

        Store store =storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("선택한 음식점이 존재하지 않습니다."));
        Menu menu = menuRepository.findByStoreIdAndMenuId(storeId,menuId).orElseThrow(() -> new IllegalArgumentException("음식점에 해당 메뉴가 존재하지 않습니다."));

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(store.getName()+" 에 "+menu.getName()+" 조회가 완료되었습니다.")
                        .data(menu)
                        .build()
        );
    }

    public ResponseEntity<ResponseMessage> getMenus(Long storeId) {

        Store store =storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("선택한 음식점이 존재하지 않습니다."));
        List<Menu> menus = menuRepository.findAllByStoreId(storeId).orElseThrow(() -> new IllegalArgumentException("음식점에 메뉴가 존재하지 않습니다."));

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(store.getName()+" 에 모든 메뉴 조회가 완료되었습니다.")
                        .data(menus)
                        .build()
        );
    }


}
