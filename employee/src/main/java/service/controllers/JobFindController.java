package service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import service.core.models.Job;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;

@RestController
public class JobFindController {

    RestTemplate template = new RestTemplate();

    @GetMapping(value="/findAllJobs", produces="application/json")
    public List<Job> findAllJobs() {
        List<Job> jobs = new ArrayList<>();
        String[] serviceURLs = {
                "http://0.0.0.0:8081/findJobs",
                "http://0.0.0.0:8082/findJobs",
                "http://0.0.0.0:8083/findJobs",
                "http://0.0.0.0:8084/findJobs",
                "http://0.0.0.0:8085/findJobs"
        };

        for (String url : serviceURLs) {
            ResponseEntity<List> response = template.getForEntity(url, List.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                jobs.addAll(response.getBody());
            }
        }
        return jobs;
    }

    @GetMapping(value="/findJobsBySkills", produces="application/json")
    public List<Job> findJobBySkills(@RequestParam("skills") List<String> skills) {
        List<Job> jobs = new ArrayList<>();
        String[] serviceURLs = {
                "http://0.0.0.0:8081/findJobsBySkills",
                "http://0.0.0.0:8082/findJobsBySkills",
                "http://0.0.0.0:8083/findJobsBySkills",
                "http://0.0.0.0:8084/findJobsBySkills",
                "http://0.0.0.0:8085/findJobsBySkills"
        };

        for (String url : serviceURLs) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
            for (String skill : skills) {
                builder.queryParam("skills", skill);
            }

            ResponseEntity<List> response = template.getForEntity(builder.toUriString(), List.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                jobs.addAll(response.getBody());
            }
        }
        return jobs;
    }
}