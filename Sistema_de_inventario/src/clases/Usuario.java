package clases;

import Gui.Interfaz;
import static Gui.Interfaz.jLabel1;
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
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.bson.Document;
import org.bson.codecs.Decoder;
import org.bson.types.ObjectId;

public class Usuario implements ISerrializable{

    private ObjectId _id;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private Date f_registro;
    private boolean es_admin;
    private boolean activo;

    //Constructores
    
    public Usuario(){}
    
    public Usuario(ObjectId _id){
        this._id = _id;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Usuario(String nombre, String apellido, String username, String password,Date f_registro) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.f_registro = f_registro;

    }

    public Usuario(ObjectId _id, String nombre, String apellido, String username, String password, Date f_registro) {
        this._id = _id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.f_registro = f_registro;
    }
    
    
    
    public Usuario(Document ob) {
        this._id = ob.getObjectId("_id");
        this.nombre = ob.getString("nombre");
        this.apellido = ob.getString("apellido");
        this.username = ob.getString("username");
        this.password = ob.getString("password");
        this.f_registro = ob.getDate("f_registro");
        this.es_admin = ob.getBoolean("es_admin", es_admin);
        this.activo = ob.getBoolean("activo", activo);
    }

    public void eliminar() {
    }

    @Override
    public void guardar() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = documento.getCollection("users");
        Document doc = new Document();
        doc.put("nombre", nombre);
        doc.put("apellido", apellido);
        doc.put("username", username);
        doc.put("password", password);
        doc.put("f_registro", f_registro);
        doc.put("es_admin", es_admin);
        doc.put("activo", activo);
        if (_id == null) {
            col.insertOne(doc);
        } else {
            col.updateOne(new BasicDBObject("_id", _id), new BasicDBObject("$set", doc));
        }
    }

    public ObjectId getId() {
        return _id;
    }
    
    public void setId(ObjectId _id) {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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
            if (u_db.password.equals(password) && u_db.activo == true) {
                Conn.user_logged = u_db;
                
                            
                
                return u_db;
            } else {
                return null;
            }
            
        } else {
            return null;
        }
    }
    
   /* public void AddUser(String nombre,String apellido,String username, String password){
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = documento.getCollection("users");
        FindIterable users_in_db = col.find(new Document("es_admin", true));
        Document o_db = (Document) users_in_db.first();
        
        Document doc = new Document();
        if(o_db != null){
            doc.put("nombre", nombre);
            doc.put("apellido", apellido);
            doc.put("username", username);
            doc.put("password", password);
            col.insertOne(doc);
        }else{
            JOptionPane.showMessageDialog(null, "Usted no es un administrador");
        }
    }/*/
    
}

