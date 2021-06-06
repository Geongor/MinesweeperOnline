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
        User user = match.getUser();
        cellsToChange.put("experience", String.valueOf(userService.addExperience(match, user)));
        userService.addMoney(user, 500);
        userService.save(user);

        return cellsToChange;
    }

    public List<Match> getAllMatchesByUser(User user){
        return matchRepository.findAllByUser(user);
    }
}
