package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.menu.MenuAddRequestDto;
import like.heocholi.spartaeats.dto.menu.MenuResponseDto;
import like.heocholi.spartaeats.dto.menu.MenuUpdateRequestDto;
import like.heocholi.spartaeats.security.UserDetailsImpl;
import like.heocholi.spartaeats.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    MenuService menuService;

    // R
    //메뉴 단건 조회
    @GetMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> getMenu(@PathVariable Long storeId, @PathVariable Long menuId) {

        MenuResponseDto menu = menuService.getMenu(storeId,menuId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menu.getStoreName() + "]에 ["+ menu.getName()+"] 메뉴 조회가 완료되었습니다.")
                        .data(menu)
                        .build()
        );
    }

    //메뉴 전체 조회
    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseMessage> getMenus(@PathVariable Long storeId) {
        List<MenuResponseDto> menus = menuService.getMenus(storeId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menus.get(0).getStoreName() +"]의 모든 메뉴 조회가 완료되었습니다.")
                        .data(menus)
                        .build()
        );
    }

    // C
    // 메뉴추가
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<ResponseMessage> addMenu(@PathVariable Long storeId, @RequestBody MenuAddRequestDto requestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        MenuResponseDto menu = menuService.addMenu(storeId,requestDto,userDetails.getManager());

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menu.getStoreName() + "]에 ["+ menu.getName()+"] 메뉴가 추가되었습니다.")
                        .build()
        );
    }

    // U
    // 메뉴 수정
    @PutMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> updateMenu(@PathVariable Long storeId,@PathVariable Long menuId,@RequestBody MenuUpdateRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        MenuResponseDto menu = menuService.updateMenu(storeId,menuId,requestDto,userDetails.getManager());

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menu.getStoreName() + "]에 ["+ menu.getId()+" / "+menu.getName()+ "] 메뉴가 수정되었습니다.")
                        .build()
        );
    }

    // D
    @DeleteMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> deleteMenu(@PathVariable Long storeId,@PathVariable Long menuId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long deleteId = menuService.deleteMenu(storeId,menuId,userDetails.getManager());

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("[" + deleteId + "](이)가 삭제되었습니다.")
                        .build()
        );
    }










}
