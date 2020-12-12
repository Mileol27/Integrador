package clases;

import Interfaces.ISerrializable;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.Date;
import conn.Conn;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.conversions.Bson;

public class Articulo implements ISerrializable {

    private ObjectId _id;
    private String descripcion;
    private Date creado_el;
    private Usuario creado_por;
    private String marca;
    private String modelo;
    private String num_serie;
    private Categoria categoria;
    private Date modificado_el;
    private String observaciones;
    private Estado estado;

    //Constructor
    public Articulo(String descripcion, String marca, String modelo, String num_serie, Categoria categoria, String observaciones, Estado estado) {
        this.descripcion = descripcion;
        this.creado_por = Conn.user_logged;
        this.marca = marca;
        this.modelo = modelo;
        this.num_serie = num_serie;
        this.categoria = categoria;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public Articulo() {
    }

    public Articulo(ObjectId _id) {
        this._id = _id;
    }

    public Articulo(Document ob) {
        this._id = ob.getObjectId("_id");
        this.descripcion = ob.getString("descripcion");
        this.creado_el = ob.getDate("creado_el");
        this.creado_por = new Usuario(ob.getObjectId("creado_por"));
        this.marca = ob.getString("marca");
        this.modelo = ob.getString("modelo");
        this.num_serie = ob.getString("num_serie");
        if (ob.get("categoria_obj") == null) {
            this.categoria = new Categoria(ob.getObjectId("categoria"));
        } else {
            this.categoria = new Categoria((Document) ((List) ob.get("categoria_obj")).get(0));
        }
        this.modificado_el = ob.getDate("modificado_el");
        this.observaciones = ob.getString("observaciones");
        if (ob.get("estado_obj") == null) {
            this.estado = new Estado(ob.getObjectId("estado"));
        } else {
            this.estado = new Estado((Document) ((List) ob.get("estado_obj")).get(0));
        }
    }

    public static int contar_total() {
        return 0;
    }

    public static int contar_por_status(Estado status) {
        return 0;
    }

    public static int contar_por_categoria(Categoria status) {
        return 0;
    }

    @Override
    public void eliminar() {
        eliminar("---");
    }
    
    public void eliminar(String motivo) {
        MongoCollection<Document> col = Conn.getCollection("articulos");
        col.deleteOne(new BasicDBObject("_id", _id));
        EvEliminacion ev = new EvEliminacion(this);
        ev.guardar();
    }

    @Override
    public void guardar() {
        MongoCollection<Document> col = Conn.getCollection("articulos");
        Document doc = new Document();
        doc.put("descripcion", descripcion);
        doc.put("marca", marca);
        doc.put("modelo", modelo);
        doc.put("num_serie", num_serie);
        doc.put("categoria", categoria.getId());
        doc.put("f_modiciacion", new Date());
        doc.put("observaciones", observaciones);
        doc.put("estado", estado.getId());
        doc.put("modificado_el", new Date());
        if (_id == null) {
            doc.put("creado_el", new Date());
            doc.put("creado_por", Conn.user_logged.getId());
            col.insertOne(doc);
            EvCreacion ev = new EvCreacion(this);
            ev.guardar();
        } else {
            EvActualizacion ev = new EvActualizacion(this);
            ev.guardar();
            col.updateOne(new BasicDBObject("_id", _id), new BasicDBObject("$set", doc));
        }
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getId() {
        return _id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNum_serie() {
        return num_serie;
    }

    public void setNum_serie(String num_serie) {
        this.num_serie = num_serie;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getModificado_el() {
        return modificado_el;
    }

    public void setModificado_el(Date modificado_el) {
        this.modificado_el = modificado_el;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
