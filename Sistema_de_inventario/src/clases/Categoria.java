package clases;

import Interfaces.ISerrializable;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.Date;
import java.util.Iterator;
import org.bson.Document;
import conn.Conn;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class Categoria implements ISerrializable{

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

    public void eliminar() {
    }

    @Override
    public void guardar() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = documento.getCollection("categorias");
        Document doc = new Document();
        doc.put("nombre", nombre);
        doc.put("modificado_el", new Date());
        if (_id == null) {
            doc.put("creado_el", new Date());
            doc.put("creado_por", Conn.user_logged.getId());
            col.insertOne(doc);
        } else {
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
    
    
    /* public void Refresh(){
        
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("categorias");
        FindIterable users_in_db = col.find();
        Document o_db = (Document) users_in_db.first();

        if (users_in_db.iterator().hasNext()) {
            Categoria u_db = new Categoria(o_db);
            u_db.getNombre();
            u_db.getCreado_el();
            u_db.getCreado_por();
        }

    } */
    
    @Override
    public String toString() {
        return nombre;
    }
}