package com.example.demo.api.service;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.example.demo.api.hateoas.models.Todo;

import reactor.core.publisher.Mono;

public interface ITodoService {

	public CollectionModel<Todo> getTodoList();
	
	public Mono<Todo> addTodoItem(Todo todo);
	
	public Mono<EntityModel<Todo>> saveTodoItem(String id, Todo todo);
	
	public Mono<EntityModel<Todo>> getTodoItem(String id);
	
	public Mono<Void> deleteTodoItem(String id);
}
