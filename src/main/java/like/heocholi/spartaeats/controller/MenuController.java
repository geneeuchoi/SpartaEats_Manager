package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.menu.MenuAddRequestDto;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    MenuService menuService;

    // R
    //메뉴 단건 조회
    @GetMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> getMenu(@PathVariable Long storeId, @PathVariable Long menuId) {
        Menu menu = menuService.getMenu(storeId,menuId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menu.getStore().getName() + "]에 ["+ menu.getName()+"] 메뉴 조회가 완료되었습니다.")
                        .data(menu)
                        .build()
        );
    }

    //메뉴 전체 조회
    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseMessage> getMenus(@PathVariable Long storeId) {
        List<Menu> menus = menuService.getMenus(storeId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(menus.get(0).getStore().getName() +"의 모든 메뉴 조회가 완료되었습니다.")
                        .data(menus)
                        .build()
        );
    }

    // C
    // 메뉴추가
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<ResponseMessage> addMenu(@PathVariable Long storeId,@RequestBody MenuAddRequestDto requestDto) {

        Menu menu = menuService.addMenu(storeId,requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menu.getStore().getName() + "]에 ["+ menu.getName()+"] 메뉴가 추가되었습니다.")
                        .build()
        );
    }

    // U
    // 메뉴 수정
    @PutMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> updateMenu(@PathVariable Long storeId,@PathVariable Long menuId,@RequestBody MenuAddRequestDto requestDto) {

        Menu menu = menuService.updateMenu(storeId,menuId,requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menu.getStore().getName() + "]에 ["+ menu.getName()+"] 메뉴가 수정되었습니다.")
                        .build()
        );
    }

    // D
    @DeleteMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> deleteMenu(@PathVariable Long storeId,@PathVariable Long menuId) {

        String message = menuService.deleteMenu(storeId,menuId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("[" + message + "](이)가 삭제되었습니다.")
                        .build()
        );
    }










}
