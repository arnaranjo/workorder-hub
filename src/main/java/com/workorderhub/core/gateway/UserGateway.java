package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.User;
import com.workorderhub.core.entity.UserRoleEnum;

import java.util.List;

public interface UserGateway {
    List<User> GetAllUser();
    List<User> GetUsersByRole(UserRoleEnum userRole);
    User GetUser(String name, String email);
    User GetUserByCredentials(int idCredentials);
    int InsertUser(User user);
    boolean DeleteUser(User user);
    boolean UpdateUser(User user);
}
