package dao;

import entity.UserEntity;

import java.util.List;

public interface UserDao {

    UserEntity isExistsUser(String userName, String password);

    List<UserEntity> getPerson(String department);

    UserEntity selectUser(String name);

}
