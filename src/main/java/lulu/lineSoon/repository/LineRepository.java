package lulu.lineSoon.repository;

import lulu.lineSoon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface LineRepository extends JpaRepository<User, Long> {
    Optional<User> findTopByNicknameAndStatusOrderByCreatedAtDesc(String nickname, String status);
    List<User> findAllByStatusOrderByCreatedAtAsc(String status);
}
