package com.elefth.reactiveapi;

import com.elefth.reactiveapi.handler.ProductHandler;
import com.elefth.reactiveapi.model.Product;
import com.elefth.reactiveapi.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ReactiveApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductRepository productRepository) {
        return args -> {
            Flux<Product> productFlux = Flux.just(
                    new Product(null, "Big Latte", 2.99),
                    new Product(null, "Big Decaf", 2.49),
                    new Product(null, "Green Tea", 1.99))
                    .flatMap(productRepository::save);

            productFlux
                    .thenMany(productRepository.findAll())
                    .subscribe(System.out::println);
        };
    }

    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
//        return route(GET("/products").and(accept(APPLICATION_JSON)), productHandler::getAllProducts)
//                .andRoute(POST("/products").and(contentType(APPLICATION_JSON)), productHandler::saveProduct)
//                .andRoute(DELETE("/products").and(accept(APPLICATION_JSON)), productHandler::deleteAllProducts)
//                .andRoute(GET("/products/events").and(accept(TEXT_EVENT_STREAM)), productHandler::getProductEvents)
//                .andRoute(GET("/products/{id}").and(accept(APPLICATION_JSON)), productHandler::getProduct)
//                .andRoute(PUT("/products/{id}").and(contentType(APPLICATION_JSON)), productHandler::updateProduct)
//                .andRoute(DELETE("/products/{id}").and(accept(APPLICATION_JSON)), productHandler::deleteProduct);

        return nest(path("/products"),
                nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
                        route(GET("/"), productHandler::getAllProducts)
                                .andRoute(method(HttpMethod.POST), productHandler::saveProduct)
                                .andRoute(DELETE("/"), productHandler::deleteAllProducts)
                                .andRoute(GET("/events"), productHandler::getProductEvents)
                                .andNest(path("/{id}"),
                                        route(method(HttpMethod.GET), productHandler::getProduct)
                                                .andRoute(method(HttpMethod.PUT), productHandler::updateProduct)
                                                .andRoute(method(HttpMethod.DELETE), productHandler::deleteProduct)
                                )
                )
        );
    }

}

