package com.eywatech.eywarent.payment.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.eywatech.pandora.response.InternalApiResponse;

@FeignClient(
        name = "rent-service" // Eureka servis adı veya application.yml'de service-id
)
public interface HgsClient {

    @PostMapping("/api/v1/hgs/updateForPayment")
    InternalApiResponse<Void> updateForPayment(@RequestBody List<String> hgsIdList);

}