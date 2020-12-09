package clases;

import Interfaces.ISerrializable;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conn.Conn;
import java.util.Iterator;
import org.bson.Document;
import org.bson.codecs.Decoder;
import org.bson.types.ObjectId;

public class Usuario implements ISerrializable{

    private ObjectId _id;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private boolean es_admin;
    private boolean activo;

    //Constructores
    public Usuario(ObjectId _id){
        this._id = _id;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Usuario(Document ob) {
        this._id = ob.getObjectId("_id");
        this.nombre = (String) ob.get("nombre");
        this.apellido = (String) ob.get("apellido");
        this.username = (String) ob.get("username");
        this.password = (String) ob.get("password");
    }

    public void eliminar() {
    }

    public void guardar() {

    }

    public ObjectId getId() {
        return _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEs_admin() {
        return es_admin;
    }

    public void setEs_admin(boolean es_admin) {
        this.es_admin = es_admin;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Usuario autenticar() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("users");
        FindIterable users_in_db = col.find(new Document("username", username));
        Document o_db = (Document) users_in_db.first();
        
        if (o_db != null) {
            Usuario u_db = new Usuario(o_db);
            if (u_db.password.equals(password)) {
                Conn.user_logged = u_db;
                return u_db;
            } else {
                return null;
            }
            
        } else {
            return null;
        }
    }
    
    /*  public Usuario getNombreUsuarioIngresado() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("users");
        FindIterable users_in_db = col.find(new Document("nombre", nombre));
        Document o_db = (Document) users_in_db.first();

        if (o_db != null) {
            Usuario u_db = new Usuario(o_db);
            return u_db.getNombre().equals(nombre) ? u_db : null;
            
        } else {
            return null;
        }
        
    }  */
    
    /* public void AddUser(String name,  String ape, String usu, String pass){
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = documento.getCollection("users");
        
        Document doc = new Document();
        doc.put("nombre", name);
        doc.put("apellido", ape);
        doc.put("username", usu);
        doc.put("password", pass);
        col.insertOne(doc);
    } */
    
}

