package clases;

import java.util.Date;

public class Evento {

    private Date creado_el;
    private Usuario creado_por;
    private Articulo articulo;

    //Constructor
    public Evento(Date creado_el, Usuario creado_por, Articulo articulo) {
        this.creado_el = creado_el;
        this.creado_por = creado_por;
        this.articulo = articulo;
    }

    //Metodos
    public void eliminar() {
    }

    public void guardar() {

    }

    // Encapculación
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

    /**
     * @return the Articulo
     */
    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the Articulo to set
     */
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

}