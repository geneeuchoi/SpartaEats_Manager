package like.heocholi.spartaeats.service;

import java.util.List;

import like.heocholi.spartaeats.constants.ErrorType;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;


    public MenuResponseDto getMenu(Long storeId, Long menuId) {
        Store store = findStoreById(storeId);
        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).orElseThrow(() -> new MenuException(ErrorType.NOT_FOUND_MENU));

        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> getMenus(Long storeId) {
        Store store = findStoreById(storeId);
        List<MenuResponseDto> menuList = menuRepository.findAllByStoreId(storeId).stream().map(MenuResponseDto::new).toList();

        if(menuList.isEmpty()){
            throw new MenuException(ErrorType.NOT_FOUND_MENUS);
        }

        return menuList;
    }

    @Transactional
    public MenuResponseDto addMenu(Long storeId, MenuAddRequestDto requestDto, Manager manager) {

        Store store = findStoreById(storeId);
        managerCheck(store, manager.getId());

        if(menuRepository.findByStoreIdAndName(storeId,requestDto.getName()).isEmpty()){
            throw new MenuException(ErrorType.DUPLICATE_MENU);
        }
        Menu saveMenu = menuRepository.save(new Menu(requestDto.getName(), requestDto.getPrice(), store));
        
        return new MenuResponseDto(saveMenu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long storeId,Long menuId, MenuUpdateRequestDto requestDto,Manager manager) {

        Store store = findStoreById(storeId);
        managerCheck(store, manager.getId());

        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).
                orElseThrow(()-> new MenuException(ErrorType.NOT_FOUND_MENU));

        if(menuRepository.findByStoreIdAndName(storeId,requestDto.getName()).isEmpty()){
            throw new MenuException(ErrorType.DUPLICATE_MENU);
        }

        menu.update(requestDto.getName(),requestDto.getPrice());

        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuResponseDto deleteMenu(Long storeId,Long menuId,Manager manager) {
        Store store = findStoreById(storeId);
        managerCheck(store, manager.getId());

        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).
                orElseThrow(()-> new MenuException(ErrorType.NOT_FOUND_MENU));

        menuRepository.delete(menu);

        return new MenuResponseDto(menu);
    }


    /* util */

    private Store findStoreById(Long storeId) {
		return storeRepository.findById(storeId).orElseThrow(() -> new MenuException(ErrorType.NOT_FOUND_STORE));
    }

    private void managerCheck(Store store, Long managerId){
        if(!store.getManager().getId().equals(managerId)){
            throw new MenuException(ErrorType.NOT_EQUAL_MANAGER);
        }
    }


}
