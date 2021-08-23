package io.builders.sbootclientes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("io.builders.sbootclientes.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.apiEndPointsInfo());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("sboot-clientes")
				.description("Serviço responsável para manutenção dos dados de clientes.")
	            .contact(new Contact("Ronivon Sampaio", "http://br.linkedin.com/in/ronivon", "ronivon.peixoto@gmail.com"))
	            .version("0.0.1-SNAPSHOT")
	            .build();
	}
}