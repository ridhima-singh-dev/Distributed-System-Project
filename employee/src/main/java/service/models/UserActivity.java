package service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "UserActivity")
public class UserActivity {
    @Id
    private String email;
    @Field("jobsApplied")
    private List<String> jobsApplied;

    public UserActivity(String email, List<String> jobsApplied ){
        this.email = email;
        this.jobsApplied = jobsApplied != null ? jobsApplied : new ArrayList<>();
    }

    public UserActivity(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getJobsApplied() {
        return jobsApplied != null ? jobsApplied : new ArrayList<>();
    }

    public void setJobsApplied(List<String> jobsApplied) {
        this.jobsApplied = jobsApplied;
    }

}
