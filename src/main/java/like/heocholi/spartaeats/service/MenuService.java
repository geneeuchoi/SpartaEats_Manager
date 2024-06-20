package like.heocholi.spartaeats.service;

import java.util.List;

import org.springframework.stereotype.Service;

import like.heocholi.spartaeats.dto.menu.MenuAddRequestDto;
import like.heocholi.spartaeats.dto.menu.MenuResponseDto;
import like.heocholi.spartaeats.dto.menu.MenuUpdateRequestDto;
import like.heocholi.spartaeats.entity.Manager;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.entity.Store;
import like.heocholi.spartaeats.exception.MenuException;
import like.heocholi.spartaeats.repository.MenuRepository;
import like.heocholi.spartaeats.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        managerCheck(store, manager.getId());
        
        Menu saveMenu = menuRepository.save(new Menu(requestDto.getName(), requestDto.getPrice(), store));
        
        return new MenuResponseDto(saveMenu);
    }

    public MenuResponseDto updateMenu(Long storeId,Long menuId, MenuUpdateRequestDto requestDto,Manager manager) {

        Store store = findStoreById(storeId);
        managerCheck(store, manager.getId());

        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).
                orElseThrow(()-> new IllegalArgumentException("음식점에 해당 메뉴가 존재하지 않습니다."));

        menu.update(requestDto.getName(),requestDto.getPrice());

        return new MenuResponseDto(menu);
    }

    public MenuResponseDto deleteMenu(Long storeId,Long menuId,Manager manager) {
        Store store = findStoreById(storeId);
        managerCheck(store, manager.getId());

        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).
                orElseThrow(()-> new MenuException("삭제할 해당 메뉴가 존재하지 않습니다."));

        menuRepository.delete(menu);

        return new MenuResponseDto(menu);
    }


    /* util */

    private Store findStoreById(Long storeId) {
		return storeRepository.findById(storeId).orElseThrow(() -> new MenuException("선택한 음식점이 존재하지 않습니다."));
    }

    private void managerCheck(Store store, Long managerId){
        if(!store.getManager().getId().equals(managerId)){
            throw new MenuException("해당 음식점의 매니저만 메뉴를 수정할 수 있습니다.");
        }
    }


}
