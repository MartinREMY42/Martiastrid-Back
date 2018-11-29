package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.UserDao;
import eu.busi.martiastrid.exception.PizzaDatabaseException;
import eu.busi.martiastrid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void saveNewUser(User user) throws PizzaDatabaseException {
        userDao.saveNewUser(user);
    }
}
