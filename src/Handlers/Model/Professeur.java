/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers.Model;

/**
 *
 * @author pc
 */
public class Professeur extends User {
    private Class providedClass;

    Professeur(Class providedClass){
        super();
        this.providedClass = providedClass;
    }

    public Class getProvidedClass() {
        return providedClass;
    }

    public void setProvidedClass(Class providedClass) {
        this.providedClass = providedClass;
    }
}
