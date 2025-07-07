package com.camunda.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.DocumentReferenceResponse;
import lombok.extern.java.Log;

@Component
@Log
public class DocumentCreationService {

    @Autowired
    private CamundaClient camundaClient;

    public DocumentReferenceResponse createAndUploadDocument(String name) {
        log.info("Creating document for name: " + name);
        // create text file
        String fileName = name + ".txt";
        String content = "This is a test document for " + name;
        String contentType = "text/plain";
        // create document
        DocumentReferenceResponse doc = camundaClient.newCreateDocumentCommand()//
                .content(content)//
                .fileName(fileName)//
                .contentType(contentType)//
                .send().join();
        log.info("Document uploaded successfully: " + fileName);
        return doc;
    }
}
