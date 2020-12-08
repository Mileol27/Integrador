package clases;

import Interfaces.ISerrializable;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conn.Conn;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Estado implements ISerrializable{

    private ObjectId _id;
    private String nombre;
    private String descripcion;
    
    public Estado(ObjectId _id) {
        this._id = _id;
    }

    public Estado(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
 

 
    
    public Estado(Document ob) {
        this._id = ob.getObjectId("_id");
        this.nombre = ob.getString("nombre");
        this.descripcion = ob.getString("descripcion");
    }

    public int contar_articulos_total(){
        return  0;
    }
    
    public void eliminar(){
        
    }
    
    @Override
    public void guardar(){
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = documento.getCollection("estados");
        Document doc = new Document();
        doc.put("nombre", nombre);
        doc.put("descripción", descripcion);
        col.insertOne(doc);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return nombre;
    }

}
