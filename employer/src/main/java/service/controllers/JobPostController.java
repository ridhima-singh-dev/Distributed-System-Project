package service.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.core.models.Job;

@RestController
public class JobPostController {
//    private Map<String, Job> quotations = new TreeMap<>();
    @Value("${server.port}")
    private int port;

    @PostMapping(value="/postJob", consumes="application/json")
    public ResponseEntity<Job> createQuotation(
            @RequestBody Job info) {
        //ADD This to MongoDb

//        Quotation quotation = service.generateQuotation(info);
//        quotations.put(quotation.reference, quotation);
        String url = "http://"+getHost()+"/quotations/";
//                + quotation.reference;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", url)
                .header("Content-Location", url)
                .body(info);
    }

    private String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress() + ":" + port;
        } catch (UnknownHostException e) {
            return "localhost:" + port;
        }
    }
}