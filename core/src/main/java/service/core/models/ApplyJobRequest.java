package service.core.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "JobPostings")
public class ApplyJobRequest {
    @Id
    private String email;
    private String jobID;

    public ApplyJobRequest(String jobID, String email) {
        this.jobID = jobID;
        this.email = email;
    }

    public ApplyJobRequest() {}

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
