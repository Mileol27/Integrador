package clases;

import java.util.Date;

public class evento implements iSerializable{

    private Date creado_el;
    private usuario creado_por;
    private articulo articulo;

    //Constructor
    public evento(Date creado_el, usuario creado_por, articulo articulo) {
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
    public usuario getCreado_por() {
        return creado_por;
    }

    /**
     * @param creado_por the creado_por to set
     */
    public void setCreado_por(usuario creado_por) {
        this.creado_por = creado_por;
    }

    /**
     * @return the articulo
     */
    public articulo getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the articulo to set
     */
    public void setArticulo(articulo articulo) {
        this.articulo = articulo;
    }

}
