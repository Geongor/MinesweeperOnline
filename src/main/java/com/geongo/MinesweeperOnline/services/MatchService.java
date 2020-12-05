package com.geongo.MinesweeperOnline.services;

import com.geongo.MinesweeperOnline.entity.Match;
import org.springframework.stereotype.Service;

public interface MatchService {

    boolean saveMatch(Match match);
}
