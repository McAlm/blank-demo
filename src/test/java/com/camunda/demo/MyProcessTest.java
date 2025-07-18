package com.camunda.demo;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ProcessInstanceEvent;
import io.camunda.process.test.api.CamundaAssert;
import io.camunda.process.test.api.CamundaProcessTestContext;
import io.camunda.process.test.api.CamundaSpringProcessTest;

@SpringBootTest(properties = { "camunda.client.worker.defaults.enabled=false" // disable all job workers
})
@CamundaSpringProcessTest
public class MyProcessTest {

    @Autowired
    private CamundaClient client;
    @Autowired
    private CamundaProcessTestContext processTestContext;

    @Test
    void shouldCompleteProcessInstance() {
        // given: the processes are deployed

        // when
        final ProcessInstanceEvent processInstance = client.newCreateInstanceCommand().bpmnProcessId("DoNothing")
                .latestVersion().send().join();

        // then
        CamundaAssert.assertThat(processInstance).isActive();
        CamundaAssert.assertThat(processInstance).hasActiveElement("demoTask", 1);

        processTestContext.completeJob("simpleWorker", Map.of("someVar", 123));
        CamundaAssert.assertThat(processInstance).hasVariable("someVar", 123);
        CamundaAssert.assertThat(processInstance).isCompleted();
    }
}