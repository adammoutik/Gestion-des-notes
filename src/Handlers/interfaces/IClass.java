package Handlers.interfaces;

import Handlers.Model.Class;
import Handlers.Model.User;

import java.util.List;

public interface IClass {
    public void insertClass(Class clazz);
    public void deleteClass(int id);
    public void updateClass(Class clazz);
    public Class findClass(int id);
    public List<Class> findAllClasses();
    public Class findClassbyName(String name);
    public int getClassIdByProfId(int profId);


}
