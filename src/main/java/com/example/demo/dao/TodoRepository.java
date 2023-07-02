package com.example.demo.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.api.hateoas.models.Todo;

import reactor.core.publisher.Flux;

public interface TodoRepository extends ReactiveMongoRepository<Todo, String> {

	@Query("{ 'name': ?0 }")
	Flux<Todo> findByName(final String name);
}
