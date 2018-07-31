package com.spring.sse.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author mkarki
 */
@Configuration
public class AppRoutingConfiguration implements WebFluxConfigurer {
	private static final String CONTEXT_PATH = "/sf";

	/*@Bean
	public RouterFunction<ServerResponse> routeSFRequests(SFController sfController) {
		return RouterFunctions
				.route(POST("/"), sfController::addToDB);
	}*/
}
