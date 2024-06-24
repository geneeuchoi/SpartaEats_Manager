package like.heocholi.spartaeats.service;

import java.util.List;

import like.heocholi.spartaeats.constants.ErrorType;
import org.springframework.stereotype.Service;

import like.heocholi.spartaeats.dto.MenuAddRequestDto;
import like.heocholi.spartaeats.dto.MenuResponseDto;
import like.heocholi.spartaeats.dto.MenuUpdateRequestDto;
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


    public MenuResponseDto getMenu(Long storeId, Long menuId,Manager manager) {
        Store store = findStoreById(storeId);
        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).orElseThrow(() -> new MenuException(ErrorType.NOT_FOUND_MENU));
        managerCheck(store.getManager().getId(), manager.getId());

        return new MenuResponseDto(menu);
    }

    public List<MenuResponseDto> getMenus(Long storeId,Manager manager) {
        Store store = findStoreById(storeId);
        List<MenuResponseDto> menuList = menuRepository.findAllByStoreId(storeId).stream().map(MenuResponseDto::new).toList();
        managerCheck(store.getManager().getId(), manager.getId());

        if(menuList.size()==0){
            throw new MenuException(ErrorType.NOT_FOUND_MENUS);
        }

        return menuList;
    }

    @Transactional
    public MenuResponseDto addMenu(Long storeId, MenuAddRequestDto requestDto, Manager manager) {

        Store store = findStoreById(storeId);
        managerCheck(store.getManager().getId(), manager.getId());
        menuDuplicateCheck(storeId, requestDto.getName());

        Menu saveMenu = menuRepository.save(new Menu(requestDto.getName(), requestDto.getPrice(), store));
        
        return new MenuResponseDto(saveMenu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long storeId,Long menuId, MenuUpdateRequestDto requestDto,Manager manager) {

        Store store = findStoreById(storeId);
        managerCheck(store.getManager().getId(), manager.getId());
        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).
                orElseThrow(()-> new MenuException(ErrorType.NOT_FOUND_MENU));

        menuDuplicateCheck(storeId, requestDto.getName());

        menu.update(requestDto.getName(),requestDto.getPrice());

        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuResponseDto deleteMenu(Long storeId,Long menuId,Manager manager) {
        Store store = findStoreById(storeId);
        managerCheck(store.getManager().getId(), manager.getId());

        Menu menu = menuRepository.findByStoreIdAndId(storeId,menuId).
                orElseThrow(()-> new MenuException(ErrorType.NOT_FOUND_MENU));

        menuRepository.delete(menu);

        return new MenuResponseDto(menu);
    }


    /* util */

    // Store id로 가져오기
    private Store findStoreById(Long storeId) {
		return storeRepository.findById(storeId).orElseThrow(() -> new MenuException(ErrorType.NOT_FOUND_STORE));
    }

    // 음식점 매니저인지 체크
    private void managerCheck(Long storeManagerId, Long managerId){
        if(!storeManagerId.equals(managerId)){
            throw new MenuException(ErrorType.NOT_EQUAL_MANAGER);
        }
    }

    // 메뉴 이름 중복 확인 ( 등록, 수정시 )
    private void menuDuplicateCheck(Long storeId, String menuName){
        if(menuRepository.findByStoreIdAndName(storeId,menuName).isPresent()){
            throw new MenuException(ErrorType.DUPLICATE_MENU);
        }
    }

    // 가게의 메뉴 리스트 조회
    public List<Menu> findAllByStoreId(Long storeId){
        return menuRepository.findAllByStoreId(storeId);
    }
}
