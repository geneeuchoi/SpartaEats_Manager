package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.dto.ResponseMessage;
import like.heocholi.spartaeats.dto.menu.MenuAddRequestDto;
import like.heocholi.spartaeats.dto.menu.MenuResponseDto;
import like.heocholi.spartaeats.dto.menu.MenuUpdateRequestDto;
import like.heocholi.spartaeats.entity.Menu;
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

        MenuResponseDto responseDto = menuService.getMenu(storeId,menuId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+responseDto.getStoreName() + "]에 ["+ responseDto.getName()+"] 메뉴 조회가 완료되었습니다.")
                        .data(responseDto)
                        .build()
        );
    }

    //메뉴 전체 조회
    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseMessage> getMenus(@PathVariable Long storeId) {
        List<MenuResponseDto> menuResponseDtoList = menuService.getMenus(storeId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+menuResponseDtoList.get(0).getStoreName() +"]의 모든 메뉴 조회가 완료되었습니다.")
                        .data(menuResponseDtoList)
                        .build()
        );
    }

    // C
    // 메뉴추가
    @PostMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseMessage> addMenu(@PathVariable Long storeId, @RequestBody MenuAddRequestDto requestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        MenuResponseDto responseDto = menuService.addMenu(storeId,requestDto,userDetails.getManager());

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+responseDto.getStoreName() + "]에 ["+ responseDto.getName()+"] 메뉴가 추가되었습니다.")
                        .data(responseDto)
                        .build()
        );
    }

    // U
    // 메뉴 수정
    @PutMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> updateMenu(@PathVariable Long storeId,@PathVariable Long menuId,@RequestBody MenuUpdateRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        MenuResponseDto responseDto = menuService.updateMenu(storeId,menuId,requestDto,userDetails.getManager());

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("["+responseDto.getStoreName() + "]에 ["+ responseDto.getId()+" / "+responseDto.getName()+ "] 메뉴가 수정되었습니다.")
                        .data(responseDto)
                        .build()
        );
    }

    // D
    @DeleteMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseMessage> deleteMenu(@PathVariable Long storeId,@PathVariable Long menuId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MenuResponseDto deleteMenu = menuService.deleteMenu(storeId,menuId,userDetails.getManager());

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseMessage.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("[" + deleteMenu.getName() + "](이)가 삭제되었습니다.")
                        .data(deleteMenu.getId())
                        .build()
        );
    }










}
