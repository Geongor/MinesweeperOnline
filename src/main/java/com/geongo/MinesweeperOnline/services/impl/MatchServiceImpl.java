package com.geongo.MinesweeperOnline.services.impl;

import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.repos.MatchRepository;
import com.geongo.MinesweeperOnline.services.MatchService;
import com.geongo.MinesweeperOnline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserService userService;

    @Override
    public Map<String, String> saveMatch(Match match, Map<String, String> cellsToChange) {

        matchRepository.save(match);
        int experience = userService.addExperience(match);
        cellsToChange.put("record", String.valueOf(userService.updateRecord(match.getUser(), experience)));
        cellsToChange.put("experience", String.valueOf(experience));
        cellsToChange.put("money", String.valueOf(userService.addMoney(match)));
        userService.save(match.getUser());

        return cellsToChange;
    }

    public List<Match> getAllMatchesByUser(User user){
        return matchRepository.findAllByUser(user);
    }
}
