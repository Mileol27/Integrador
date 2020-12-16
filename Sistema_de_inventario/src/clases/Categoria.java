package clases;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import java.util.Date;
import org.bson.Document;
import conn.Conn;
import java.util.List;
import org.bson.types.ObjectId;
import Interfaces.IBdMethods;

public class Categoria implements IBdMethods{

    private ObjectId _id;
    private String nombre;
    private Date creado_el;
    private Date modificado_el;
    private Usuario creado_por;

    //Constructor
    public Categoria(ObjectId _id){
        this._id = _id;
    }   

    public Categoria(String nombre) {
        this.nombre = nombre;
    } 
    public Categoria(ObjectId _id,String nombre) {
        this._id = _id;
        this.nombre = nombre;
    } 
    
    public Categoria(Document ob){
        this._id = ob.getObjectId("_id");
        this.nombre = (String) ob.get("nombre");
        this.creado_el = (Date) ob.get("creado_el");
        this.modificado_el = (Date) ob.get("modificado_el");
        if (ob.get("creado_por_obj") == null) {
            this.creado_por = new Usuario(ob.getObjectId("creado_por"));
        } else {
        this.creado_por = new Usuario((Document) ((List) ob.get("creado_por_obj")).get(0));
        }
        
    }

    //Metodos
    public int contar_articulos_total() {
        return 0;
    }

    public int contar_x_estado() {
        return 0;
    }

    @Override
    public void eliminar() {
        MongoCollection<Document> col = Conn.getCollection("categorias");
        col.deleteOne(new BasicDBObject("_id", _id));
    }

    @Override
    public void guardar() {
        MongoCollection<Document> col = Conn.getCollection("categorias");
        Document doc = new Document();
        doc.put("nombre", nombre);
        doc.put("modificado_el", new Date());
        if (_id == null) { // Crear
            doc.put("creado_el", new Date());
            doc.put("creado_por", Conn.user_logged.getId());
            col.insertOne(doc);
        } else {  // Actualizar
            col.updateOne(new BasicDBObject("_id", _id), new BasicDBObject("$set", doc));
        }
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    @Override
    public String toString() {
        return nombre;
    }
}