package com.camunda.demo.worker;

import java.util.Map;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.java.Log;

@Component
@Log
public class DemoWorker {

    @JobWorker(type = "demoWorker")
    public Map<String, Object> runDemoTask(){
        log.info("execute demo worker ");
        return Map.of("demoExecuted", true);
    }
}
