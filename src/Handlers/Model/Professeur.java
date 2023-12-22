/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers.Model;

import java.util.HashSet;

/**
 *
 * @author pc
 */
public class Professeur extends User {

    private int pf_id;
    private int user_id;
    private Class providedClass;

    public Professeur(int pf_id, int user_id, Class providedClass, int id, String username, String password, String role, String first_name, String last_name) {
        super(id, first_name, last_name, username, password, role);
        this.pf_id = pf_id;
        this.user_id = user_id;
        this.providedClass = providedClass;

    }

    public int getPf_id() {
        return pf_id;
    }

    public void setPf_id(int pf_id) {
        this.pf_id = pf_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public Class getProvidedClass() {
        return providedClass;
    }

    public void setProvidedClass(Class providedClass) {
        this.providedClass = providedClass;
    }
}
