package gt.edu.apuestasmundial.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class CloudinaryProperties {

    @Value("${cloud.cloud-name}")
    private String cloudName;
    @Value("${cloud.cloud-api-key}")
    private String apiKey;
    @Value("${cloud.cloud-api-secret}")
    private String apiSecret;
}
