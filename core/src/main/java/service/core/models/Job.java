package service.core.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "JobPostings")
public class Job {
    @Id
    private String jobID;
    private String title;
    private String companyName;
    private String jobDescription;
    private double salary;
    private List<String> skills;
    private List<String> applicants;

    public Job(String title, String companyName, String jobDescription, double salary, List<String> skills, List<String> applicants) {
        this.title = title;
        this.companyName = companyName;
        this.jobDescription = jobDescription;
        this.salary = salary;
        this.skills = skills;
        this.applicants = applicants;
    }

    public Job() {}

    public String getJobID() {
        return jobID;
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

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
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
