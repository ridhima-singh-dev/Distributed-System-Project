package service.core.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "UserActivity")
public class UserActivity {
    @Field("email")
    @Indexed(unique = true)
    private String email;
    @Field("jobsApplied")
    private List<String> jobsApplied;
    @Field("jobsPosted")
    private List<String> jobsPosted;

    public UserActivity(String email, List<String> jobsApplied, List<String> jobsPosted ){
        this.email = email;
        this.jobsApplied = jobsApplied != null ? jobsApplied : new ArrayList<>();
        this.jobsPosted = jobsPosted != null ? jobsPosted : new ArrayList<>();
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

    public List<String> getJobsPosted() {
        return jobsPosted!= null ? jobsPosted : new ArrayList<>();
    }

    public void setJobsPosted(List<String> jobsPosted) {
        this.jobsPosted = jobsPosted;

    }
}
