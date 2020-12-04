/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author Jhonatan
 * @param <T>
 */

public interface ISerrializable<T> {

    public void guardar();
    public void eliminar();

    /* public static <T> ArrayList<T> leer_todo(Class<T> type) {
        String simpleName = type.getClass().getSimpleName();
        ArrayList<T> listado;
        try {
            FileInputStream file = new FileInputStream(simpleName + ".ser");
            try (ObjectInputStream stream = new ObjectInputStream(file)) {
                listado = (ArrayList<T>) stream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            listado = null;
        }        
        return (listado!=null) ? listado : new ArrayList<>();
    } */

}
