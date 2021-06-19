package com.geongo.MinesweeperOnline.repos;

import com.geongo.MinesweeperOnline.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();
    List<User> findAllByOrderByLevelDesc();
}
