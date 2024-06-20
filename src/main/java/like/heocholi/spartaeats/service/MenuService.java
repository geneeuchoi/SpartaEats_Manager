package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.menu.MenuAddRequestDto;
import like.heocholi.spartaeats.dto.menu.MenuResponseDto;
import like.heocholi.spartaeats.dto.menu.MenuUpdateRequestDto;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.repository.MenuRepository;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto getMenu(Long storeId, Long menuId) {

        findStoreById(storeId);
        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).orElseThrow(() -> new IllegalArgumentException("음식점에 해당 메뉴가 존재하지 않습니다."));

        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> getMenus(Long storeId) {

        findStoreById(storeId);
        List<Menu> menus = menuRepository.findAllByStoreId(storeId);
        if(menus.isEmpty()) throw new IllegalArgumentException("음식점에 메뉴를 등록해주세요.");


        return menuRepository.findAllByStoreId(storeId).stream().map(MenuResponseDto::new).toList();
    }

    public MenuResponseDto addMenu(Long storeId, MenuAddRequestDto requestDto, Manager manager) {

        Store store = findStoreById(storeId);
        managerCheck(store.getManager().getId(), manager.getId());

        Menu menu = menuRepository.findByStoreIdAndName(storeId,requestDto.getName()).
                orElseThrow(()->new IllegalArgumentException("해당 음식점에 이미 ["+requestDto.getName()+"]가 있습니다."));

        menuRepository.save(new Menu(requestDto.getName(),requestDto.getPrice(),store));

        return new MenuResponseDto(menu);
    }

    public MenuResponseDto updateMenu(Long storeId,Long menuId, MenuUpdateRequestDto requestDto,Manager manager) {

        Store store = findStoreById(storeId);
        managerCheck(store.getManager().getId(), manager.getId());

        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).orElseThrow(()-> new IllegalArgumentException("음식점에 해당 메뉴가 존재하지 않습니다."));

        menu.update(requestDto.getName(),requestDto.getPrice());

        return new MenuResponseDto(menu);
    }

    public Long deleteMenu(Long storeId,Long menuId,Manager manager) {
        Store store = findStoreById(storeId);
        managerCheck(store.getManager().getId(), manager.getId());

        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).orElseThrow(()-> new IllegalArgumentException("삭제할 해당 메뉴가 존재하지 않습니다."));

        menuRepository.delete(menu);

        return menu.getId();
    }


    /* util */

    private Store findStoreById(Long storeId) {
        Store store =storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("선택한 음식점이 존재하지 않습니다."));

        return store;
    }

    private void managerCheck(Long storeId,Long managerId){
        if(!storeId.equals(managerId)) {
            throw new IllegalArgumentException("유저가 일치하지 않습니다.");
        }
    }


}
