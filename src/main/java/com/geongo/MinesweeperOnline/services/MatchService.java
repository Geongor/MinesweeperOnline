package com.geongo.MinesweeperOnline.services;

import com.geongo.MinesweeperOnline.entity.Match;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface MatchService {

    Map<String, String> saveMatch(Match match, Map<String, String> cellsToChange);
}
