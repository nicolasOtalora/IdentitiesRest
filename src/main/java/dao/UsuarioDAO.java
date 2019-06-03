/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tuserlate file, choose Tools | Tuserlates
 * and open the tuserlate in the editor.
 */
package dao;

import vo.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import vo.Documento;
import vo.Permiso;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import org.json.JSONObject;

/**
 *
 * @author white
 */
public class UsuarioDAO {

    private final static String HOST = "localhost";
    private final static int PORT = 27017;

    private static final Map<String, User> userMap = new HashMap<String, User>();
//    static {
//        initEmps();
//    }
// 
//    private static void initEmps() {
//        User p1 = new User("Nicolas", "Otalora", "Calle falsa", "11/08/1997", "321338", "1018", "private");
//        userMap.put(p1.getMedicare(), p1);
//    }

    public static User addUser(User user) {
        System.out.println("Add User");
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);

            DB db = mongoClient.getDB("sampledb");

            DBCollection coll = db.getCollection("users");
            List<DBObject> permisos = new ArrayList<>();
            for (int i = 0; i < user.getPermisos().length; i++) {
                permisos.add(new BasicDBObject("duenio", user.getPermisos()[i].getUsuario()));
                for (int j = 0; j < user.getPermisos()[i].getDocumentos().length; j++) {
                    permisos.add(new BasicDBObject("nombre", user.getPermisos()[i].getDocumentos()[j].getNombre()));
                    permisos.add(new BasicDBObject("url", user.getPermisos()[i].getDocumentos()[j].getUrl()));
                    permisos.add(new BasicDBObject("hash", user.getPermisos()[i].getDocumentos()[j].getHash()));
                }
            }
            List<DBObject> documentos = new ArrayList<>();
            System.out.println("LENGTH"+user.getDocumentos().length);
            for (int i = 0; i < user.getDocumentos().length; i++) {
                
                documentos.add(new BasicDBObject("nombre", user.getDocumentos()[i].getNombre()));
                documentos.add(new BasicDBObject("url", user.getDocumentos()[i].getUrl()));
                documentos.add(new BasicDBObject("hash", user.getDocumentos()[i].getHash()));
            }
            DBObject doc = new BasicDBObject("usuario", user.getUsuario())
                    .append("contrasena", user.getContrasena())
                    .append("documentos", documentos)
                    .append("permisos", permisos);

            coll.insert(doc);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }
        return user;
    }

    public static List getUser(String usuario) {
        User user;
        List<User> list = new ArrayList<>();
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);
            DB db = mongoClient.getDB("sampledb");

            DBCollection coll = db.getCollection("users");
            DBObject query = new BasicDBObject("usuario", usuario);//filtrando
            System.out.println("Query get " + query.toString());
            DBCursor cursor = coll.find(query);
            ArrayList<Documento> documentos = new ArrayList();
            ArrayList<?> arrayDocs = new ArrayList();

            System.out.println("");
            try {

                while (cursor.hasNext()) {
                    DBObject object = cursor.next();

//                    
                    arrayDocs = new Gson().fromJson(object.get("documentos").toString(), ArrayList.class);
                    for (int i = 0; i < arrayDocs.size(); i += 3) {
                        documentos.add(new Documento(arrayDocs.get(i).toString().substring(8, arrayDocs.get(i).toString().length() - 1),
                                arrayDocs.get(i + 1).toString().substring(5, arrayDocs.get(i + 1).toString().length() - 1), arrayDocs.get(i + 2).toString().substring(6,
                                arrayDocs.get(i + 2).toString().length() - 1)));
                    }
                    JSONArray json = new JSONArray(object.get("permisos").toString());
                    int contador = 0;
                    ArrayList<Permiso> permisos = new ArrayList<>();
                    ArrayList<Documento> docs = new ArrayList<>();

                    Permiso temp = new Permiso();
                    Documento tempDoc = new Documento();
                    for (int i = 0; i < json.length(); i++) {

                        JSONObject jsObj = json.getJSONObject(i);
                        if (jsObj.toString().substring(2, 8).equals("duenio")) {
                            System.out.println("Duenito");
                            if (i != 0) {

                                Documento[] docsFinal = new Documento[docs.size()];
                                for (int j = 0; j < docs.size(); j++) {
                                    docsFinal[j] = docs.get(j);
                                }
                                temp.setDocumentos(docsFinal);
                                permisos.add(temp);
                                docs = new ArrayList<>();
                            }
                            temp.setUsuario(jsObj.toString().substring(11, jsObj.toString().length() - 2));

                        } else if (contador == 0) {
                            tempDoc.setNombre(jsObj.toString().substring(11, jsObj.toString().length() - 2));
                            contador++;
                        } else if (contador == 1) {
                            tempDoc.setUrl(jsObj.toString().substring(8, jsObj.toString().length() - 2));

                            contador++;
                        } else if (contador == 2) {
                            tempDoc.setHash(jsObj.toString().substring(9, jsObj.toString().length() - 2));

                            docs.add((Documento) tempDoc.clone());

                            contador = 0;
                        }
                    }
                    Documento[] docsFinal = new Documento[docs.size()];
                    for (int j = 0; j < docs.size(); j++) {
                        docsFinal[j] = docs.get(j);
                    }
                    temp.setDocumentos(docsFinal);
                    permisos.add(temp);
                    Documento[] userDocs = new Documento[documentos.size()];

                    for (int i = 0; i < documentos.size(); i++) {
                        userDocs[i] = documentos.get(i);
                    }
                    Permiso[] userPerms = new Permiso[permisos.size()];
                    for (int i = 0; i < permisos.size(); i++) {
                        userPerms[i] = permisos.get(i);
                    }
                    user = new User(
                            object.get("usuario").toString(), object.get("contrasena").toString(),
                            userDocs, userPerms
                    );

                    System.out.println(object);

                    list.add(user);
                }
            } finally {

                cursor.close();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }

        return list;
    }

    public static List<User> getAllUsers() {
        User user;
        List<User> list = new ArrayList<>();
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);
            DB db = mongoClient.getDB("sampledb");
            DBCollection coll = db.getCollection("users");
            System.out.println(coll.getCount());
            DBCursor cursor = coll.find();
            ArrayList<?> arrayDocs = new ArrayList();

            ArrayList<Documento> documentos = new ArrayList();

            try {
                while (cursor.hasNext()) {
                    DBObject object = cursor.next();

//                    
                    arrayDocs = new Gson().fromJson(object.get("documentos").toString(), ArrayList.class);
                    for (int i = 0; i < arrayDocs.size(); i += 3) {
                        documentos.add(new Documento(arrayDocs.get(i).toString().substring(8, arrayDocs.get(i).toString().length() - 1),
                                arrayDocs.get(i + 1).toString().substring(5, arrayDocs.get(i + 1).toString().length() - 1), arrayDocs.get(i + 2).toString().substring(6,
                                arrayDocs.get(i + 2).toString().length() - 1)));
                    }
                    JSONArray json = new JSONArray(object.get("permisos").toString());
                    int contador = 0;
                    ArrayList<Permiso> permisos = new ArrayList<>();
                    ArrayList<Documento> docs = new ArrayList<>();

                    Permiso temp = new Permiso();
                    Documento tempDoc = new Documento();
                    for (int i = 0; i < json.length(); i++) {

                        JSONObject jsObj = json.getJSONObject(i);
                        if (jsObj.toString().substring(2, 8).equals("duenio")) {
                            System.out.println("Duenito");
                            if (i != 0) {

                                Documento[] docsFinal = new Documento[docs.size()];
                                for (int j = 0; j < docs.size(); j++) {
                                    docsFinal[j] = docs.get(j);
                                }
                                temp.setDocumentos(docsFinal);
                                permisos.add(temp);
                                docs = new ArrayList<>();
                            }
                            temp.setUsuario(jsObj.toString().substring(11, jsObj.toString().length() - 2));

                        } else if (contador == 0) {
                            tempDoc.setNombre(jsObj.toString().substring(11, jsObj.toString().length() - 2));
                            contador++;
                        } else if (contador == 1) {
                            tempDoc.setUrl(jsObj.toString().substring(8, jsObj.toString().length() - 2));

                            contador++;
                        } else if (contador == 2) {
                            tempDoc.setHash(jsObj.toString().substring(9, jsObj.toString().length() - 2));

                            docs.add((Documento) tempDoc.clone());

                            contador = 0;
                        }
                    }
                    Documento[] docsFinal = new Documento[docs.size()];
                    for (int j = 0; j < docs.size(); j++) {
                        docsFinal[j] = docs.get(j);
                    }
                    temp.setDocumentos(docsFinal);
                    permisos.add(temp);
                    Documento[] userDocs = new Documento[documentos.size()];

                    for (int i = 0; i < documentos.size(); i++) {
                        userDocs[i] = documentos.get(i);
                    }
                    Permiso[] userPerms = new Permiso[permisos.size()];
                    for (int i = 0; i < permisos.size(); i++) {
                        userPerms[i] = permisos.get(i);
                    }
                    user = new User(
                            object.get("usuario").toString(), object.get("contrasena").toString(),
                            userDocs, userPerms
                    );

                    System.out.println(object);

                    list.add(user);
                }
            } finally {
                cursor.close();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }
        return list;
    }

    public static List<Permiso> getAllPermisos(String user) {
        System.out.println("Get all permisos");
        Permiso permiso;
        List<Permiso> list = new ArrayList<>();
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);
            DB db = mongoClient.getDB("sampledb");
            DBCollection coll = db.getCollection("users");
            System.out.println(coll.getCount());
            DBObject query = new BasicDBObject("usuario", user);//filtrando

            DBCursor cursor = coll.find(query);
            try {
                while (cursor.hasNext()) {
                    DBObject object = cursor.next();
                    System.out.println("While");

                    JSONArray json = new JSONArray(object.get("permisos").toString());
                    int contador = 0;
                    ArrayList<Permiso> permisos = new ArrayList<>();
                    ArrayList<Documento> docs = new ArrayList<>();

                    Permiso temp = new Permiso();
                    Documento tempDoc = new Documento();
                    for (int i = 0; i < json.length(); i++) {

                        JSONObject jsObj = json.getJSONObject(i);

                        if (jsObj.toString().substring(2, 8).equals("duenio")) {
                            if (i != 0) {

                                Documento[] docsFinal = new Documento[docs.size()];
                                for (int j = 0; j < docs.size(); j++) {
                                    docsFinal[j] = docs.get(j);
                                }
                                temp.setDocumentos(docsFinal);
                                permisos.add(temp);
                                docs = new ArrayList<>();
                            }
                            temp.setUsuario(jsObj.toString().substring(11, jsObj.toString().length() - 2));

                        } else if (contador == 0) {
                            tempDoc.setNombre(jsObj.toString().substring(11, jsObj.toString().length() - 2));
                            contador++;
                        } else if (contador == 1) {
                            tempDoc.setUrl(jsObj.toString().substring(8, jsObj.toString().length() - 2));

                            contador++;
                        } else if (contador == 2) {
                            tempDoc.setHash(jsObj.toString().substring(9, jsObj.toString().length() - 2));

                            docs.add((Documento) tempDoc.clone());

                            contador = 0;
                        }
                    }
                    Documento[] docsFinal = new Documento[docs.size()];
                    for (int j = 0; j < docs.size(); j++) {
                        docsFinal[j] = docs.get(j);
                    }
                    temp.setDocumentos(docsFinal);
                    System.out.println("User " + temp.getUsuario());

                    System.out.println(object);

                    permiso = new Permiso(
                            temp.getUsuario(), docsFinal
                    );
                    list.add(permiso);
                }
            } finally {
                cursor.close();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }
        return list;
    }

    public static List<Documento> getAllDocumentos(String user) {
        List<Documento> documentos = new ArrayList();
        System.out.println("user "+user);
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);
            DB db = mongoClient.getDB("sampledb");
            DBCollection coll = db.getCollection("users");
            System.out.println(coll.getCount());
            DBObject query = new BasicDBObject("usuario", user);//filtrando
            ArrayList<?> arrayDocs = new ArrayList();

            DBCursor cursor = coll.find(query);
            try {
                while (cursor.hasNext()) {
                    System.out.println("While");
                    DBObject object = cursor.next();

                    arrayDocs = new Gson().fromJson(object.get("documentos").toString(), ArrayList.class);
                    for (int i = 0; i < arrayDocs.size(); i += 3) {
                        documentos.add(new Documento(arrayDocs.get(i).toString().substring(8, arrayDocs.get(i).toString().length() - 1),
                                arrayDocs.get(i + 1).toString().substring(5, arrayDocs.get(i + 1).toString().length() - 1), arrayDocs.get(i + 2).toString().substring(6,
                                arrayDocs.get(i + 2).toString().length() - 1)));
                    }
                    System.out.println(object);

                }
            } finally {
                cursor.close();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }
        return documentos;
    }

    public static void updateUsuario(User user, String usuario) {
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);

            DB db = mongoClient.getDB("sampledb");
            DBCollection coll = db.getCollection("users");

            BasicDBObject newDocument = new BasicDBObject();

            List<DBObject> permisos = new ArrayList<>();
            for (int i = 0; i < user.getPermisos().length; i++) {
                permisos.add(new BasicDBObject("duenio", user.getPermisos()[i].getUsuario()));
                for (int j = 0; j < user.getPermisos()[i].getDocumentos().length; j++) {
                    permisos.add(new BasicDBObject("nombre", user.getPermisos()[i].getDocumentos()[j].getNombre()));
                    permisos.add(new BasicDBObject("url", user.getPermisos()[i].getDocumentos()[j].getUrl()));
                    permisos.add(new BasicDBObject("hash", user.getPermisos()[i].getDocumentos()[j].getHash()));
                }
            }
            List<DBObject> documentos = new ArrayList<>();
            for (int i = 0; i < user.getDocumentos().length; i++) {
                documentos.add(new BasicDBObject("nombre", user.getDocumentos()[i].getNombre()));
                documentos.add(new BasicDBObject("url", user.getDocumentos()[i].getUrl()));
                documentos.add(new BasicDBObject("hash", user.getDocumentos()[i].getHash()));
            }

            newDocument.append("$set", new BasicDBObject().append("usuario",
                    user.getUsuario()).append("contrasena", user.getContrasena()).append("permisos", permisos)
                    .append("documentos", documentos));

            BasicDBObject searchQuery = new BasicDBObject().append("usuario",
                    usuario);
            System.out.println("query " + searchQuery.toString());
            coll.update(searchQuery, newDocument);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }
}
