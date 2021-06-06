package com.geongo.MinesweeperOnline.services;

import com.geongo.MinesweeperOnline.entity.Match;
import com.geongo.MinesweeperOnline.entity.Role;
import com.geongo.MinesweeperOnline.entity.User;
import com.geongo.MinesweeperOnline.repos.RoleRepository;
import com.geongo.MinesweeperOnline.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service()
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean save(User user){
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public int addExperience(Match match, User user){


        int cellsCount = match.getFieldHeight() * match.getFieldWidth();
        int userExperience = (cellsCount - (cellsCount / match.getMinesCount()));
        if (userExperience + user.getExperience() >= 10000){
            user.setExperience(userExperience + user.getExperience() - 10000);
            user.setLevel(user.getLevel() + 1);
        } else {
            user.setExperience(userExperience + user.getExperience());
        }
        return userExperience;

    }

    public boolean addMoney(User user, int moneyAmount){

        user.setMoney(user.getMoney() + moneyAmount);
        //userRepository.save(user);

        return true;
    }

    public boolean reduceMoney(User user, int moneyAmount){

        if (user.getMoney() >= moneyAmount) {
            user.setMoney(user.getMoney() - moneyAmount);
            userRepository.save(user);

            return true;
        } else {
            return false;
        }
    }
}
