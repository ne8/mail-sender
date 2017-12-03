package ro.neo.mailsender.config;


import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public Docket api() {
        return
                new Docket(DocumentationType.SWAGGER_2)
                        .groupName("mail-sender")
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(
                                "ro.neo.mailsender.controller"
                        ))
                        .paths(PathSelectors.any())
                        .build()
                        .apiInfo(apiInfo("Mail API", "Mail API powered by spring mail and postgresql"));
    }

    private ApiInfo apiInfo(final String title, final String description) {
        final Contact contact = new Contact("Catalin Patrut", "",
                "cpatrut@outlook.com");
        return new ApiInfo(title, description, "1.0", "terms of controller url",
                contact, "license", "license url");
    }
}
