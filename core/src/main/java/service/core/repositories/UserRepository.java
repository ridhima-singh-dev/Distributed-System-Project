package service.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import service.core.models.Job;
import service.core.models.UserActivity;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserActivity, String> {
    Optional<UserActivity> findByEmail(String email);
}
