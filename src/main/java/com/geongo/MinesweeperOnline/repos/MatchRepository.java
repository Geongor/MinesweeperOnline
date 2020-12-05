package com.geongo.MinesweeperOnline.repos;

import com.geongo.MinesweeperOnline.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("matchRepository")
public interface MatchRepository extends CrudRepository<Match, Long> {
}
