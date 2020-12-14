package clases;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import conn.Conn;
import org.bson.Document;

public class EvActualizacion extends Evento{
    
    String detalle;

    public EvActualizacion(Articulo articulo) {
        super(articulo);
    }
    
    public EvActualizacion(Document ob) {
        super(ob);
        this.detalle = ob.getString("detalle");
    }
    
    @Override
    public void guardar() {
        MongoCollection<Document> col = Conn.getCollection("eventos");
        Document doc = new Document();
        doc.put("creado_el", super.getCreado_el());
        doc.put("creado_por", Conn.user_logged.getId());
        doc.put("articulo", super.getArticulo().getId());
        doc.put("detalle", detalle);
        doc.put("tipo", "U");
        if (super.getId() == null) {
            col.insertOne(doc);
        } else {
            col.updateOne(new BasicDBObject("_id", super.getId()), new BasicDBObject("$set", doc));
        }
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
