package clases;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import conn.Conn;
import org.bson.Document;

public class EvCreacion extends Evento{
    
    public EvCreacion(Articulo articulo) {
        super(articulo);
    }
    
    @Override
    public void guardar() {
        MongoCollection<Document> col = Conn.getCollection("eventos");
        Document doc = new Document();
        doc.put("creado_el", super.getCreado_el());
        doc.put("creado_por", Conn.user_logged.getId());
        doc.put("articulo", super.getArticulo().getId());
        doc.put("tipo", "C");
        if (super.getId() == null) {
            col.insertOne(doc);
        } else {
            col.updateOne(new BasicDBObject("_id", super.getId()), new BasicDBObject("$set", doc));
        }
    }
 
}
