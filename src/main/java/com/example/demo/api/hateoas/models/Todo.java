package com.example.demo.api.hateoas.models;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@Document("TodoCollection")
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String name;
	private String description;
	private Date dueDate;
	private String status;
	
	public Todo(String name, String description, Date dueDate, String status) {
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
	}
	
	public Todo() {
		
	}
	

}
