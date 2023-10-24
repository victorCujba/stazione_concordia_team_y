package it.euris.stazioneconcordia;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Stazione Concordia Project API", version = "1.0", description = "Stazione Concordia management"))
public class StazioneConcordiaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StazioneConcordiaApplication.class, args);


    }
}
