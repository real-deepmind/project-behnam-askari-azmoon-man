package realdeepmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import realdeepmind.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
