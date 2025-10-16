package com.gbill.createfinalconsumerbill.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gbill.createfinalconsumerbill.modeldto.promortion.ApplyPromotionRequest;
import com.gbill.createfinalconsumerbill.modeldto.promortion.ApplyPromotionResponse;
import com.gbill.createfinalconsumerbill.modeldto.promortion.PromotionAvailableRequest;
import com.gbill.createfinalconsumerbill.modeldto.promortion.PromotionAvailableResponse;

@FeignClient(name = "promotionClient", url = "${feign.client.config.promotion.url}")
public interface PromotionClient {
    
    @PostMapping("/integracion/facturacion/promociones-disponibles")
    PromotionAvailableResponse getAvailablePromotions(@RequestBody PromotionAvailableRequest request);

    @PostMapping("/integracion/facturacion/aplicar-promocion")
    ApplyPromotionResponse applyPromotion(@RequestBody ApplyPromotionRequest request);

}
