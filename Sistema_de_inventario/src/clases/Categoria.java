package clases;

import Interfaces.ISerrializable;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.Iterator;
import org.bson.Document;
import conn.Conn;
import org.bson.types.ObjectId;

public class Categoria implements ISerrializable{

    private ObjectId _id;
    private String nombre;
    private Date creado_el;
    private Usuario creado_por;

    //Constructor
    public Categoria(ObjectId _id){
        this._id = _id;
    }   

    public Categoria(String nombre) {
        this.nombre = nombre;
    }    
    
    public Categoria(Document ob){
        this._id = ob.getObjectId("_id");
        this.nombre = (String) ob.get("nombre");
        this.creado_el = (Date) ob.get("creado_el");
        this.creado_por = new Usuario(ob.getObjectId("creado_por"));
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
        doc.put("creado_el", new Date());
        doc.put("creado_por", Conn.user_logged.getId());
        col.insertOne(doc);
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