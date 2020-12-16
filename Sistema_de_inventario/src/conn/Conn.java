/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import clases.Articulo;
import clases.Categoria;
import clases.Estado;
import clases.EvActualizacion;
import clases.EvCreacion;
import clases.EvEliminacion;
import clases.Evento;
import clases.Usuario;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import org.bson.Document;
import java.util.Iterator;
import org.bson.conversions.Bson;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 *
 * @author Jhonatan
 */
public class Conn {
    public static Usuario user_logged;
    //public static Articulo articulo_categoria;
    
    public static MongoCollection<Document> getCollection(String col) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase documento = mongoClient.getDatabase("inventario");
        return documento.getCollection(col);
    }
    
    public static ArrayList<Categoria> listar_categorias(Usuario user) {
        MongoCollection<Document> col = getCollection("categorias");
        Bson filtros = new Document();
        if (user != null) {
            filtros = Filters.and(filtros, new Document("creado_por", user.getId()));
        }
        List aggregate = Arrays.asList(
            new Document(
                "$lookup", new Document("from", "users")
                        .append("localField", "creado_por")
                        .append("foreignField", "_id")
                        .append("as", "creado_por_obj")
            ),
            Aggregates.match(filtros)
        );
        AggregateIterable categorias_in_bd = col.aggregate(aggregate);
        ArrayList<Categoria> categorias = new ArrayList<>();
        Iterator it = categorias_in_bd.iterator();
        while (it.hasNext()) {
            categorias.add(new Categoria((Document) it.next()));
        }
        return categorias;
    }
    
    public static ArrayList<Estado> listar_estados() {
        MongoCollection<Document> col = getCollection("estados");
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
    
    public static ArrayList<Articulo> listar_articulos(Usuario user, Categoria cat, Estado st) {
        MongoCollection<Document> col = getCollection("articulos");
        Bson filtros = new Document();
        if (cat != null) {
            System.out.println("cat: " + cat.getId() );
            filtros = Filters.and(filtros, new Document("categoria", cat.getId()));
        }
        if (st != null) {
            filtros = Filters.and(filtros, new Document("estado", st.getId()));
        }
        if (user != null) {
            filtros = Filters.and(filtros, new Document("creado_por", user.getId()));
        }
        List aggregate = Arrays.asList(
            new Document(
                "$lookup", new Document("from", "estados")
                        .append("localField", "estado")
                        .append("foreignField", "_id")
                        .append("as", "estado_obj")
            ),
            new Document(
                "$lookup", new Document("from", "categorias")
                        .append("localField", "categoria")
                        .append("foreignField", "_id")
                        .append("as", "categoria_obj")
            ),
            Aggregates.match(filtros)
        );
        AggregateIterable categorias_in_bd = col.aggregate(aggregate);
        ArrayList<Articulo> articulos = new ArrayList<>();
        Iterator it = categorias_in_bd.iterator();
        while (it.hasNext()) {
            articulos.add(new Articulo((Document) it.next()));
        }
        return articulos;
    }

    public static ArrayList<Usuario> listar_users() {
        MongoCollection<Document> col = getCollection("users");
        if (col.countDocuments() == 0) {
            Usuario admin = new Usuario("admin", "admin", "admin", "admin",new Date());
            admin.setActivo(true);
            admin.setAdmin(true);
            admin.guardar();
        }
        FindIterable categorias_in_bd = col.find();
        ArrayList<Usuario> usuario = new ArrayList<>();
        Iterator it = categorias_in_bd.iterator();
        while (it.hasNext()) {
            usuario.add(new Usuario((Document) it.next()));
        }
        return usuario;
    }
    
    public static ArrayList<Evento> get_log(GregorianCalendar filtro) {
        MongoCollection<Document> col = getCollection("eventos");
        // Filtrar por mes y año....
        int year = filtro.get(Calendar.YEAR);
        int mes = filtro.get(Calendar.MONTH) + 1;
        Bson filtros = Filters.and(
            Filters.eq("mes", mes),
            Filters.eq("year", year)
        );
        List aggregate = Arrays.asList(
            new Document("$addFields", new Document("mes", new Document("$month", "$creado_el"))),
            new Document("$addFields", new Document("year", new Document("$year", "$creado_el"))),
            Aggregates.match(filtros)
        );
        AggregateIterable eventos_in_bd = col.aggregate(aggregate);
        ArrayList<Evento> eventos = new ArrayList<>();
        Iterator it = eventos_in_bd.iterator();
        while (it.hasNext()) {
            Document ob = (Document) it.next();
            String tipo = ob.getString("tipo");
            switch(tipo) {
                case "C" : eventos.add(new EvCreacion(ob)); break;
                case "D" : eventos.add(new EvEliminacion(ob)); break;
                case "U" : eventos.add(new EvActualizacion(ob)); break;
            }
        }
        return eventos;
    }
    
    public static ArrayList<Document> get_detalle_resumen_general() {
        MongoCollection<Document> col = getCollection("articulos");
        List aggregate = Arrays.asList(
            new Document(
                "$group", new Document("_id", new Document("categoria", "$categoria").append("estado", "$estado"))
                        .append("total", new Document("$sum", 1))
                ),
            new Document(
                "$lookup", new Document("from", "estados")
                        .append("localField", "_id.estado")
                        .append("foreignField", "_id")
                        .append("as", "estado_list")
            ),
            new Document(
                "$lookup", new Document("from", "categorias")
                        .append("localField", "_id.categoria")
                        .append("foreignField", "_id")
                        .append("as", "categoria_list")
            ),
            new Document("$addFields", new Document("estado_obj", new Document("$first", "$estado_list"))),
            new Document("$addFields", new Document("categoria_obj", new Document("$first", "$categoria_list"))),
            new Document(
                "$group", new Document("_id", "$categoria_obj")
                        .append("items", new Document("$addToSet", new Document("name", "$estado_obj.nombre").append("value", "$total")))
                ),
            new Document(
                "$project", new Document(
                    "tmp", new Document(
                        "$arrayToObject", new Document(
                            "$zip", new Document("inputs", Arrays.asList("$items.name", "$items.value"))))
                    )
                ),
            new Document("$addFields", new Document("tmp._id", "$_id._id")),
            new Document("$addFields", new Document("tmp.nombre", "$_id.nombre")),
            new Document("$replaceRoot", new Document("newRoot", "$tmp"))
        );
        ArrayList<Document> docs = new ArrayList<>();
        AggregateIterable ag =  col.aggregate(aggregate);
        
        Iterator it = ag.iterator();
        System.out.println("========== DETALLE RESUMEN ==========");
        while (it.hasNext()) {
            Document ob = (Document) it.next();
            docs.add(ob);
            System.out.println(ob.toJson());
        }
        return docs;
    }
    
    
   
}
