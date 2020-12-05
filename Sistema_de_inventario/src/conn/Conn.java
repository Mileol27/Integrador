/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import clases.Categoria;
import clases.Usuario;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;
import java.util.Iterator;
import org.bson.types.ObjectId;


/**
 *
 * @author Jhonatan
 */
public class Conn {
    public static Usuario user_logged;
    
    public static ArrayList<Categoria> listar_categorias() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("categorias");
        FindIterable categorias_in_bd = col.find();
        ArrayList<Categoria> categorias = new ArrayList<>();
        Iterator it = categorias_in_bd.iterator();
        while (it.hasNext()) {
            categorias.add(new Categoria((Document) it.next()));
        }
        return categorias;
    }
}
