package com.camunda.demo.worker;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camunda.demo.service.DocumentCreationService;

import io.camunda.client.api.response.DocumentReferenceResponse;
import io.camunda.spring.client.annotation.JobWorker;
import io.camunda.spring.client.annotation.Variable;
import lombok.extern.java.Log;

@Component
@Log
public class DocumentUploadWorker {

    @Autowired
    private DocumentCreationService documentCreationService;

    @JobWorker(type = "createDocument", autoComplete = true, fetchAllVariables = false, fetchVariables = "customerName")
    public Map<String, Object> uploadDocument(@Variable String customerName) {
        DocumentReferenceResponse doc = this.documentCreationService.createAndUploadDocument(customerName);
        log.info("Document upload worker executed for name: " + customerName);

        log.info("Now completing the job with document reference: " + doc.getDocumentId());
        return Map.of("customerDocument", new DocumentReferenceResponse[] { doc });
    }

}
