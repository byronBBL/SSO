package cn.cqu.edu.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
