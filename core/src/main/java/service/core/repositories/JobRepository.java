package service.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import service.core.models.Job;
import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findBySkillsIn(List<String> skills);
}
