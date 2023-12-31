package service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.models.UserActivity;
import service.repositories.UserRepository;
import java.util.*;

@RestController
public class JobDecisionController {

    private final UserRepository userRepository;
    public JobDecisionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value="/applyJob", produces="application/json")
    public ResponseEntity<?> applyJob(@RequestBody Map<String,List<String>> info) {
        List<String> infoList = info.get("info");
        String companyName = infoList.get(3);
        RestTemplate template = new RestTemplate();
        ResponseEntity<?> response = null;
        if(Objects.equals(companyName, "facebook")){
            response =
                    template.postForEntity("http://localhost:8081/applyJob", infoList, Map.class);
        } else if (Objects.equals(companyName, "amazon")) {
            response =
                    template.postForEntity("http://localhost:8082/applyJob", infoList, Map.class);
        } else if (Objects.equals(companyName, "apple")) {
            response =
                    template.postForEntity("http://localhost:8083/applyJob", infoList, Map.class);
        } else if (Objects.equals(companyName, "netflix")) {
            response =
                    template.postForEntity("http://localhost:8084/applyJob", infoList, Map.class);
        } else if (Objects.equals(companyName, "google")) {
            response =
                    template.postForEntity("http://localhost:8085/applyJob", infoList, Map.class);
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }

        if (response.getStatusCode().equals(HttpStatus.CREATED)) {
            try{
                updateUserActivity(infoList);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(Collections.singletonMap("success", true));
            }catch (Exception e){
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("success", false));
            }
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }

    private void updateUserActivity(List<String> info){
        String email = info.get(1);
        String date = info.get(2);
        UserActivity existingUserActivity = userRepository.findByEmail(info.get(1));
        Map<String, Object> newJob = new HashMap<>();
        newJob.put("jobID", info.get(0));
        newJob.put("companyName", info.get(3));
        newJob.put("salary", Double.parseDouble(info.get(4)));
        newJob.put("title", info.get(5));
        newJob.put("location", info.get(6));
        String[] skillsArray = info.get(7).split(",");
        newJob.put("skills", Arrays.asList(skillsArray));
        newJob.put("description", info.get(8));
        if (existingUserActivity != null) {
            existingUserActivity.getJobsApplied().add(newJob);
            userRepository.save(existingUserActivity);
        } else {
            List<Map<String, Object>> jobsApplied = new ArrayList<>();
            jobsApplied.add(newJob);
            UserActivity newUserActivity = new UserActivity(email, date, jobsApplied);
            userRepository.save(newUserActivity);
        }
    }
}