package service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "UserActivity")
public class UserActivity {
    @Id
    private String email;
    @Field("dateApplied")
    private String dateApplied;
    @Field("jobsApplied")
    private List<Map<String, Object>> jobsApplied;

    public UserActivity(String email, String dateApplied, List<Map<String, Object>> jobsApplied) {
        this.email = email;
        this.dateApplied = dateApplied;
        this.jobsApplied = jobsApplied != null ? jobsApplied : new ArrayList<>();
    }

    public void setDateApplied(String dateApplied) {
        this.dateApplied = dateApplied;
    }

    public void setJobsApplied(List<Map<String, Object>> jobsApplied) {
        this.jobsApplied = jobsApplied;
    }

    public UserActivity() {}

    public String getEmail() {
        return email;
    }

    public String getDateApplied() {
        return dateApplied;
    }

    public List<Map<String, Object>> getJobsApplied() {
        return jobsApplied != null ? jobsApplied : new ArrayList<>();
    }
}
