package like.heocholi.spartaeats.service;

import like.heocholi.spartaeats.constants.ErrorType;
import like.heocholi.spartaeats.dto.BestMenusResponseDto;
import like.heocholi.spartaeats.entity.Menu;
import like.heocholi.spartaeats.exception.AnalyticsException;
import like.heocholi.spartaeats.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMenuService {

    private final OrderMenuRepository orderMenuRepository;

    // 인기 메뉴 조회
    public List<BestMenusResponseDto> getBestMenuList(List<Menu> menuList) {
        return orderMenuRepository.getBestMenus(menuList).orElseThrow(
                () -> new AnalyticsException(ErrorType.NOT_FOUND_MENUS)
        );
    }

}
