package clases;

import Interfaces.ISerrializable;

public class Estado implements ISerrializable{

    private int id;
    private String nombre;
    private String descripcion;

    public Estado(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Metodos
    
    public int contar_articulos_total(){
        return  0;
    }
    
    public void eliminar(){
        
    }
    
    public void guardar(){
        
    }
    
    
    
    
    
    /**
     //Encapsulacion
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
