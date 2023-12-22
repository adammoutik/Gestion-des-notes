/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Handlers.interfaces;

import Handlers.Model.User;
import java.util.List;

/**
 *
 * @author pc
 */
public interface IUser {
    public void insertUser(User user);
    public void deleteUser(int id);
    public void updateUser(User user);
    public User findUser(int id);
    public List<User> findAllUsers();
}
