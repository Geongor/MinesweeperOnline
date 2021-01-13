package com.geongo.MinesweeperOnline.repos;

import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("matchRepository")
public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> findAllByUser(User user);
}
