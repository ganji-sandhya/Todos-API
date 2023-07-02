package com.example.demo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.api.hateoas.models.Todo;
import com.example.demo.api.service.TodoService;

import reactor.core.publisher.Mono;

@RestController
public class TodoController {

	@Autowired
	private TodoService todoService;

	@GetMapping(value = "/todos", produces = { "application/hal+json" })
	ResponseEntity<CollectionModel<Todo>> getTodoList() {

		ResponseEntity<CollectionModel<Todo>> response = new ResponseEntity<CollectionModel<Todo>>(todoService.getTodoList(), HttpStatus.OK);

		return response;

	}

	@GetMapping(value = "/todos/{id}", consumes = { "application/json" })
	public ResponseEntity<Mono<EntityModel<Todo>>> getTodoItem(@PathVariable("id") String id) {
		ResponseEntity<Mono<EntityModel<Todo>>> response;
		try {
		response = new ResponseEntity<Mono<EntityModel<Todo>>>(todoService.getTodoItem(id),
				HttpStatus.OK);
		return response;
		} catch(NullPointerException e) {
			response = new ResponseEntity<Mono<EntityModel<Todo>>>(Mono.empty(),
					HttpStatus.NOT_FOUND);
			return response;
		}
	}

	@PostMapping(value = "/todos", consumes = { "application/json" })
	ResponseEntity<Mono<Todo>> create(@RequestBody Todo todo) {

		ResponseEntity<Mono<Todo>> response = new ResponseEntity<Mono<Todo>>(todoService.addTodoItem(todo),
				HttpStatus.CREATED);
		return response;
	}

	@PutMapping(value = "/todos/{id}", consumes = { "application/json" })
	public ResponseEntity<Mono<EntityModel<Todo>>> save(@PathVariable("id") String id, @RequestBody Todo todo) {
		ResponseEntity<Mono<EntityModel<Todo>>> response = new ResponseEntity<Mono<EntityModel<Todo>>>(todoService.saveTodoItem(id, todo),
				HttpStatus.OK);
		return response;
	}

	@DeleteMapping(value = "/todos/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") String id) {
		todoService.deleteTodoItem(id).subscribe();
	}
}
