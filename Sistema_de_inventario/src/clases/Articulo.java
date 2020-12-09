package clases;

import Interfaces.ISerrializable;
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
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class Articulo implements ISerrializable {

    private ObjectId _id;
    private String descripcion;
    private Date creado_el;
    private Usuario creado_por;
    private String marca;
    private String modelo;
    private String num_serie;
    private Categoria categoria;
    private Date f_modiciacion;
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
        this.f_modiciacion = ob.getDate("f_modiciacion");
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

    public void eliminar() {
    }

    @Override
    public void guardar() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = documento.getCollection("articulos");
        Document doc = new Document();
        doc.put("descripcion", descripcion);
        doc.put("creado_el", new Date());
        doc.put("creado_por", Conn.user_logged.getId());
        doc.put("marca", marca);
        doc.put("modelo", modelo);
        doc.put("num_serie", num_serie);
        doc.put("categoria", categoria.getId());
        doc.put("f_modiciacion", new Date());
        doc.put("observaciones", observaciones);
        doc.put("estado", estado.getId());
        col.insertOne(doc);
    }

    public void ediArti(ObjectId id, String descripcion, String marca, String modelo, String num_serie, Categoria categoria, Date f_modiciacion, String observaciones, Estado estado) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = documento.getCollection("articulos");

        Document found = (Document) col.find(new Document("_id", id)).first();
        if (found != null) {
            System.out.println("se encontró articulo");
            //      Bson updatedvalue = new Document("descripcion", descripcion).append("marca", marca).append("modelo", modelo).append("num_serie", num_serie).append("categoria", categoria).append("f_modiciacion", f_modiciacion).append("observaciones", observaciones).append("estado", estado);
            Bson updatedvalue = new Document("descripcion", descripcion).append("marca", marca).append("modelo", modelo).append("num_serie", num_serie).append("f_modiciacion", f_modiciacion).append("observaciones", observaciones).append("categoria", categoria.getId()).append("estado", estado.getId());
            Bson updateoperation = new Document("$set", updatedvalue);
            col.updateOne(found, updateoperation);
            System.out.println("se actualizó UwU");

        } else {
            System.out.println("no hay naaa");
        }
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

    public Date getF_modiciacion() {
        return f_modiciacion;
    }

    public void setF_modiciacion(Date f_modiciacion) {
        this.f_modiciacion = f_modiciacion;
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
