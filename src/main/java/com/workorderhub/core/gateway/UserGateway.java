package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.User;
import com.workorderhub.core.entity.UserRoleEnum;

import java.util.List;

public interface UserGateway {
    List<User> getAllUser();
    List<User> getUsersByRole(UserRoleEnum userRole);
    User getUser(String name, String email);
    User getUserByCredentials(int idCredentials);
    int insertUser(User user);
    boolean deleteUser(User user);
    boolean updateUser(User user);
}
