package service.core;
public class Job {
    public Job(String title, String companyName, String companyDescription, double salary, String[] skills  ) {
        this.title = title;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.salary = salary;
        this.skills = skills;
    }
    public Job() {

    };

    public String title;
    public String companyName;
    public String companyDescription;
    public double salary;
    public String[] skills;
}
