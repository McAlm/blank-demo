package com.camunda.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.search.filter.VariableValueFilter;
import io.camunda.zeebe.client.api.search.response.ProcessInstance;
import io.camunda.zeebe.client.api.search.response.SearchQueryResponse;
import io.camunda.zeebe.client.api.search.response.UserTask;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import lombok.extern.java.Log;

@SpringBootApplication
@Log
@Deployment(resources = { "classpath:bpmn/doNothing.bpmn" })
public class BlankDemoApplication implements CommandLineRunner {

	@Autowired
	private ZeebeClient zeebe;

	public static void main(String[] args) {
		SpringApplication.run(BlankDemoApplication.class, args);
	}

	public void run(final String... args) {
		log.info("start process instance...");
		for (Integer i : Arrays.asList(1,2,3,4,5,6,7,8,9,10)) {
			
			zeebe.newCreateInstanceCommand().bpmnProcessId("DoNothing").latestVersion().variable("someVar", i).send();
					log.info("process instance started..." + i);
		}

		// SearchQueryResponse<UserTask> searchQueryResponse = zeebe.newUserTaskQuery()
        //         .filter(userTaskFilter -> userTaskFilter.assignee("demo"))
        //         .sort(s -> s.creationDate().asc())
        //         .page(p -> p.limit(100))
        //         .send().join();


		// SearchQueryResponse<ProcessInstance> processInstances = zeebe.newProcessInstanceQuery()
        //         .filter(f -> f.variable(new VariableValueFilter() {

		// 			@Override
		// 			public VariableValueFilter name(String value) {
				
		// 			}

		// 			@Override
		// 			public VariableValueFilter eq(Object value) {
		// 				// TODO Auto-generated method stub
		// 				throw new UnsupportedOperationException("Unimplemented method 'eq'");
		// 			}

		// 			@Override
		// 			public VariableValueFilter gt(Object value) {
		// 				// TODO Auto-generated method stub
		// 				throw new UnsupportedOperationException("Unimplemented method 'gt'");
		// 			}

		// 			@Override
		// 			public VariableValueFilter gte(Object value) {
		// 				// TODO Auto-generated method stub
		// 				throw new UnsupportedOperationException("Unimplemented method 'gte'");
		// 			}

		// 			@Override
		// 			public VariableValueFilter lt(Object value) {
		// 				// TODO Auto-generated method stub
		// 				throw new UnsupportedOperationException("Unimplemented method 'lt'");
		// 			}

		// 			@Override
		// 			public VariableValueFilter lte(Object value) {
		// 				// TODO Auto-generated method stub
		// 				throw new UnsupportedOperationException("Unimplemented method 'lte'");
		// 			}
					
		// 		}))
        //         .sort(s -> s.startDate().asc())
        //         .page(p -> p.limit(100))
        //         .send().join();
	}
}
