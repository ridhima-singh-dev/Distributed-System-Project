package service.employee.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.core.models.Job;
import service.core.repositories.JobRepository;
import java.util.List;

@RestController
public class JobFindController {
    @Autowired
    private JobRepository jobRepository;

    @GetMapping(value="/findAllJobs", produces="application/json")
    public List<Job> findAllJobs() {
        return jobRepository.findAll();
    }

    @GetMapping(value="/findJob", produces="application/json")
    public List<Job> findJobBySkills(@RequestParam("skills") List<String> skills) {
        List<Job> jobsBySkill = jobRepository.findBySkillsIn(skills);
        return jobsBySkill;
    }

}