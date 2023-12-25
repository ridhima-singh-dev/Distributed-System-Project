package service.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import service.core.Job;

public interface JobRepository extends MongoRepository<Job, String> {
    // Custom queries or methods if needed
}

