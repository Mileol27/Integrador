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

public class Categoria implements ISerrializable{

    private String _id;
    private String nombre;
    private Date creado_el;
    private Usuario creado_por;

    //Constructor
    public Categoria(){
        
    }   
    
    
    public Categoria(String id, String nombre, Date creado_el, Usuario creado_por) {
        this._id = id;
        this.nombre = nombre;
        this.creado_el = creado_el;
        this.creado_por = creado_por;
    }
    
    public Categoria(Document ob){
        this._id = (String) ob.get("_id").toString();
        this.nombre = (String) ob.get("nombre");
        this.creado_el = (Date) ob.get("creado_el");
        this.creado_por = (Usuario) ob.get("creado_por");
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

    public void guardar() {

    }

    //Encapsulamiento
    public String getId() {
        return _id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this._id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the creado_el
     */
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
    public Usuario getCreado_por() {
        return creado_por;
    }

    /**
     * @param creado_por the creado_por to set
     */
    public void setCreado_por(Usuario creado_por) {
        this.creado_por = creado_por;
    }
    
    public void AddCat(String name, Date horalocal, String user) {
        
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = documento.getCollection("category");
        
        Document doc = new Document();
        doc.put("nombre", name);
        doc.put("creado_el", horalocal);
        doc.put("creado_por", user);
        col.insertOne(doc);

    }
    
    public void Refresh(){
        
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("category");
        FindIterable users_in_db = col.find();
        Document o_db = (Document) users_in_db.first();

        if (users_in_db.iterator().hasNext()) {
            Categoria u_db = new Categoria(o_db);
            u_db.getNombre();
            u_db.getCreado_el();
            u_db.getCreado_por();
        }

    }
}