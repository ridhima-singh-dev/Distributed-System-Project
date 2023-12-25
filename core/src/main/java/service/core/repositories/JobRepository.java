package service.core.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;

import service.core.models.Job;

public interface JobRepository extends MongoRepository<Job, String> {
    // Custom queries or methods if needed
}

