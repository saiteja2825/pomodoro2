package com.pomodoro2.app.Repositories;

import com.pomodoro2.app.entity.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<users,Long> {

    Optional<users> findByUsername(String username);
}
