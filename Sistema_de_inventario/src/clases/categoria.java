package clases;

import java.util.Date;

public class Categoria implements iSerializable{

    private int id;
    private String nombre;
    private Date creado_el;
    private Usuario creado_por;

    //Constructor
    public Categoria(int id, String nombre, Date creado_el, Usuario creado_por) {
        this.id = id;
        this.nombre = nombre;
        this.creado_el = creado_el;
        this.creado_por = creado_por;
    }

    //Metodos
    public int contar_articulos_total() {
        return 0;
    }

    public int contar_x_estado() {
        return 0;
    }

    public void eliminar() {
    }

    public void guardar() {

    }

    //Encapsulamiento
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
     * @return the creado_el
     */
    public Date getCreado_el() {
        return creado_el;
    }

    /**
     * @param creado_el the creado_el to set
     */
    public void setCreado_el(Date creado_el) {
        this.creado_el = creado_el;
    }

    /**
     * @return the creado_por
     */
    public Usuario getCreado_por() {
        return creado_por;
    }

    /**
     * @param creado_por the creado_por to set
     */
    public void setCreado_por(Usuario creado_por) {
        this.creado_por = creado_por;
    }

}
