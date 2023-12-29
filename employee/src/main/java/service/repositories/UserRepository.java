package service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import service.models.UserActivity;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserActivity, String> {
    Optional<UserActivity> findByEmail(String email);
}
