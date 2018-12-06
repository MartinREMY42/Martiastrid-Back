package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.UserDao;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void saveNewUser(User user) throws PizzaDatabaseException {
        userDao.saveNewUser(user);
    }

    public User getUser(String username){
        return userDao.getByUsername(username);
    }

    public List<Pizza> getPizzasFavorites(String username) throws PizzaDatabaseException {
        return userDao.getAllPizzasFavority(username).stream().collect(Collectors.toList());
    }

    public List<Pizza> switchPizzaFavoriteness(String username, int idPizza){
        return userDao.switchPizzaFavoriteness(username, idPizza).stream().collect(Collectors.toList());
    }
}
