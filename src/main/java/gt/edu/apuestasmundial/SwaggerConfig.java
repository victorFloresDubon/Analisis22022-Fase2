package gt.edu.apuestasmundial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api () {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(getApiInfo())
                .select()
                .apis(
                        RequestHandlerSelectors
                                .basePackage("gt.edu.apuestasmundial")
                )
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Apuestas Qatar 2022 API",
                "Servicio RESTFUL API de fase II, proyecto Análisis de Sistemas II UMG",
                "1.0",
                "Términos de servicio",
                new Contact("GRUPO 2", "UMG", ""),
                "Licencia de API",
                "URL de licencia de API",
                Collections.emptyList()
        );
    }


}
