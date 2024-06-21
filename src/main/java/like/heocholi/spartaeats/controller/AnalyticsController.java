package like.heocholi.spartaeats.controller;

import like.heocholi.spartaeats.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;


}
