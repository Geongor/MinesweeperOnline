package com.geongo.MinesweeperOnline.repos;

import com.geongo.MinesweeperOnline.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
