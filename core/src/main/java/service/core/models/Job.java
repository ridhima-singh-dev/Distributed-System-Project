package service.core.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "jobs")
public class Job {
    @Id
    private String jobID;
    private String title;
    private String companyName;
    private String companyDescription;
    private double salary;
    private List<String> skills;
    private List<String> applicants;

    public Job(String jobID, String title, String companyName, String companyDescription, double salary, List<String> skills, List<String> applicants) {
        this.jobID = jobID;
        this.title = title;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.salary = salary;
        this.skills = skills;
        this.applicants = applicants;
    }

    public Job() {}

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<String> applicants) {
        this.applicants = applicants;
    }
}
