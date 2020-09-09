package io.github.pyvesb.spring_content_length_sample;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Config {

    @Bean
    RouterFunction<ServerResponse> handlerRouterFunction(Handler handler) {
        return RouterFunctions.route(POST("/data"), handler::handle);
    }

}
