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
        String companyName = infoList.get(2);
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
        Optional<UserActivity> existingUserActivityEmail = userRepository.findByEmail(info.get(1));
        if (existingUserActivityEmail.isPresent()) {
            UserActivity existingUserActivity = existingUserActivityEmail.get();
            existingUserActivity.getJobsApplied().add(info.get(0));
            userRepository.save(existingUserActivity);
        } else {
            UserActivity newUserActivity = new UserActivity(email, Collections.singletonList(info.get(0)));
            userRepository.save(newUserActivity);
        }
    }
}
