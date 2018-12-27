package com.elefth.reactiveapi.repository;

import com.elefth.reactiveapi.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author <a href="mailto:george@elefth.com">Eleftheriadis Georgios</a>
 */
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findByName(String name);
}
