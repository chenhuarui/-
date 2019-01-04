package service.Impl;

import dao.UserDao;
import entity.UserEntity;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserEntity isExistsUser(String userName, String password) {
        return userDao.isExistsUser(userName, password);
    }

    @Override
    public List<UserEntity> getPerson(String department) {
        return userDao.getPerson(department);
    }

    @Override
    public UserEntity selectUser(String name) {
        return userDao.selectUser(name);
    }
}
