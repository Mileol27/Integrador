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
import java.util.Iterator;
import org.bson.Document;
import org.bson.codecs.Decoder;

public class Usuario implements ISerrializable{

    private String _id;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    // private boolean activo;

    //Constructores
    public Usuario(){
        
    }

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public Usuario(String nombre,String username,String password) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;

    }
    
    
    
    public Usuario(String _id, String nombre, String apellido, String username) {
        this._id = _id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Usuario(Document ob) {
        this._id = (String) ob.get("_id").toString();
        this.nombre = (String) ob.get("nombre");
        this.apellido = (String) ob.get("apellido");
        this.username = (String) ob.get("username");
        this.password = (String) ob.get("password");
    }

    public void eliminar() {
    }

    public void guardar() {

    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public Usuario autenticar() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("users");
        FindIterable users_in_db = col.find(new Document("username", username));
        Document o_db = (Document) users_in_db.first();

        if (o_db != null) {
            Usuario u_db = new Usuario(o_db);
            return u_db.getPassword().equals(password) ? u_db : null;
            
        } else {
            return null;
        }
    }
    
    public Usuario getNombreUsuarioIngresado() {
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
        
    }
}

