/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import clases.Articulo;
import clases.Categoria;
import clases.Estado;
import clases.Usuario;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
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
    public static Articulo articulo_categoria;
    
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
    
    public static ArrayList<Estado> listar_estados() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("estados");
        if (col.countDocuments() == 0) {
            new Estado("Funcional", "Un artículo totalmente operativo").guardar();
            new Estado("Averiado", "Un artículo que dejó de estar operativo y ya no se puede usar").guardar();
            new Estado("Extraviado", "Un artículo extraviado").guardar();
        }
        FindIterable categorias_in_bd = col.find();
        ArrayList<Estado> estados = new ArrayList<>();
        Iterator it = categorias_in_bd.iterator();
        while (it.hasNext()) {
            estados.add(new Estado((Document) it.next()));
        }
        return estados;
    }
    
    public static ArrayList<Articulo> listar_articulos() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("articulos");
        FindIterable categorias_in_bd = col.find();
        ArrayList<Articulo> articulos = new ArrayList<>();
        Iterator it = categorias_in_bd.iterator();
        while (it.hasNext()) {
            articulos.add(new Articulo((Document) it.next()));
        }
        return articulos;
    }
    
    public static ArrayList<Articulo> listar_articulos_cat() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("articulos");
        FindIterable categorias_in_bd = col.find(new Document("categoria",Conn.articulo_categoria));
        ArrayList<Articulo> articulos = new ArrayList<>();
        Iterator it = categorias_in_bd.iterator();
        while (it.hasNext()) {
            articulos.add(new Articulo((Document) it.next()));
        }
        return articulos;
    }
    
    public static ArrayList<Articulo> listar_articulos_por_categoria(Categoria categoria) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("articulos");
        MongoCursor<Document> cursor = col.find(eq("categoria", categoria.getId())).iterator();
         ArrayList<Articulo> articulos = new ArrayList<>();
        try{
        while (cursor.hasNext()) {
             articulos.add(new Articulo((Document) cursor.next()));
        //System.out.println(cursor.next().toJson());
        }
        } finally {
        cursor.close();
        return articulos;
        }
    }

    
}
