package service;

import entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity isExistsUser(String userName, String password);

    List<UserEntity> getPerson(String department);

    UserEntity selectUser(String name);
}
