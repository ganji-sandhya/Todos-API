package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.example.demo.dao")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

	@Value("${spring.data.mongodb.database}")
	private String dbName;

	@Value("${spring.data.mongodb.password}")
	private String password;

	private static final String CONNECTION_STRING = "mongodb+srv://sandhyaganji:<password>@cluster0.ionahfc.mongodb.net/<dbName>?retryWrites=true&w=majority";

	@Override
	public MongoClient reactiveMongoClient() {
		
		ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING.replace("<password>", password).replace("<dbName>", dbName)))
                .serverApi(serverApi)
                .build();
		return MongoClients.create(settings);
	}

	@Override
	protected String getDatabaseName() {
		// TODO Auto-generated method stub
		return dbName;
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
	}
	

	@Bean(destroyMethod="close")
	public MongoClient mongoClient() {
		return reactiveMongoClient();
	}
	
}
