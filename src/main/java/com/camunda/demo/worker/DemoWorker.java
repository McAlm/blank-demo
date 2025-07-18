package com.camunda.demo.worker;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;
import io.camunda.spring.client.annotation.JobWorker;
import io.camunda.spring.client.annotation.Variable;
import lombok.extern.java.Log;

@Component
@Log
public class DemoWorker {

    @JobWorker(type = "simpleWorker")
    public Map<String, Object> runSimpleWorker(@Variable Integer someVar) {
        log.info("execute demo worker with value " + someVar);
        return Map.of("demoExecuted", true);
    }

    @JobWorker(type = "asyncWorker", maxJobsActive = 3, autoComplete = false, fetchVariables = {"someVar"})
    public void runAsyncWorker(ActivatedJob job, JobClient jobClient)
            throws Exception {

        log.info(Thread.currentThread().getName() + "execute demo worker with key " + job.getKey());

        CompletableFuture<String> future = calculateAsync(job.getVariable("someVar").toString());
        future.thenApply(s -> {
            jobClient.newCompleteCommand(job)//
                    .variable("result", s)//
                    .send()//
                    .exceptionally(throwable -> {
                        throw new RuntimeException("Could not complete job " + job, throwable);
                    });
            ;
            log.info(Thread.currentThread().getName() + " completed job " + job.getKey());
            return null;
        });

    }

    public CompletableFuture<String> calculateAsync(String myVariable) throws InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> runBusinessLogic(myVariable));
        return completableFuture;
    }

    private String runBusinessLogic(String s) {
        try {
            log.info(Thread.currentThread().getName() + " goes to sleep now...");
            Thread.sleep(10000);
            log.info(Thread.currentThread().getName() + " woke up...");
            return "hello " + s;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
