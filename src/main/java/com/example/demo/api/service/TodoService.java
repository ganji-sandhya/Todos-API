package com.example.demo.api.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.stereotype.Service;

import com.example.demo.api.controller.TodoController;
import com.example.demo.api.hateoas.models.Todo;
import com.example.demo.dao.TodoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoService implements ITodoService {

	@Autowired
	TodoRepository todoRepository;

	public CollectionModel<Todo> getTodoList() {

		Flux<Todo> todos = todoRepository.findAll();
		CollectionModel<Todo> model = CollectionModel.of(todos.toIterable())
				.add(Link.of("/todos", IanaLinkRelations.SELF));
		return model;

	}

	public Mono<Todo> addTodoItem(Todo todo) {

		return todoRepository.insert(todo);
	}

	public Mono<EntityModel<Todo>> saveTodoItem(String id, Todo todo) {
		// TODO Auto-generated method stub
		return todoRepository.findById(id).flatMap(requestedTodo -> {

			if (todo.getName() != null) {
				requestedTodo.setName(todo.getName());
			}

			if (todo.getDescription() != null) {
				requestedTodo.setDescription(todo.getDescription());
			}

			if (todo.getDueDate() != null) {
				requestedTodo.setDueDate(todo.getDueDate());
			}

			if (todo.getStatus() != null) {
				requestedTodo.setStatus(todo.getStatus());
			}
			return getResponse(todoRepository.save(requestedTodo));
		});

	}

	public Mono<EntityModel<Todo>> getTodoItem(String id) {

		return getResponse(
				todoRepository.findById(id));
	}

	public Mono<Void> deleteTodoItem(String id) {

		return todoRepository.findById(id).flatMap(todo -> {
			System.out.println(todo);
			return todoRepository.delete(todo);

		});

	}

	private Mono<EntityModel<Todo>> getResponse(Mono<Todo> todo) {

		return todo.map(result -> {
			if (result instanceof Todo) {
				return EntityModel.of(result)
						.add(linkTo(methodOn(TodoController.class).getTodoItem(result.getId())).withSelfRel())
						.add(linkTo(methodOn(TodoController.class).save(result.getId(), result))
								.withRel(LinkRelation.of("update")));

			} else {
				throw new NullPointerException("item not found");
			}
		}).switchIfEmpty(Mono.error(new RuntimeException("could not get todo item")));

	}

}
