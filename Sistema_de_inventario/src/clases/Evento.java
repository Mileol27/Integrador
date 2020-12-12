package clases;

import Interfaces.ISerrializable;
import conn.Conn;
import java.util.Date;
import org.bson.types.ObjectId;

public class Evento implements ISerrializable{
    
    private ObjectId _id;
    private Date creado_el;
    private Usuario creado_por;
    private Articulo articulo;

    //Constructor
    public Evento(Articulo articulo) {
        this.creado_por = Conn.user_logged;
        this.articulo = articulo;
    }

    //Metodos
    @Override
    public void eliminar() { }

    @Override
    public void guardar() {
        System.out.println("Can't save here");
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    
    public Date getCreado_el() {
        return creado_el;
    }

    public void setCreado_el(Date creado_el) {
        this.creado_el = creado_el;
    }

    public Usuario getCreado_por() {
        return creado_por;
    }

    public void setCreado_por(Usuario creado_por) {
        this.creado_por = creado_por;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

}
