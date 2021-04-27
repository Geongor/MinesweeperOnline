package com.geongo.MinesweeperOnline.services.impl;

import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.repos.MatchRepository;
import com.geongo.MinesweeperOnline.services.MatchService;
import com.geongo.MinesweeperOnline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserService userService;

    @Override
    public boolean saveMatch(Match match) {

        matchRepository.save(match);
        User user = match.getUser();
        user.addExperience(300);
        userService.addMoney(user, 500);
        userService.saveUser(user);

        return true;
    }

    public List<Match> getAllMatchesByUser(User user){
        return matchRepository.findAllByUser(user);
    }
}
