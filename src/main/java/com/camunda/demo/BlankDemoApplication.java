package com.camunda.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.spring.client.annotation.Deployment;

@SpringBootApplication
@Deployment(resources = {"classpath:bpmn/doNothing.bpmn"})
public class BlankDemoApplication {

public static void main(String[] args) {
		SpringApplication.run(BlankDemoApplication.class, args);
	}

}
