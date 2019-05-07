/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import vo.Paciente;
import java.util.ArrayList;
import java.util.Collection;
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

/**
 *
 * @author white
 */
public class PacienteDAO {

    private final static String HOST = "localhost";
    private final static int PORT = 27017;

    private static final Map<String, Paciente> empMap = new HashMap<String, Paciente>();
//    static {
//        initEmps();
//    }
// 
//    private static void initEmps() {
//        Paciente p1 = new Paciente("Nicolas", "Otalora", "Calle falsa", "11/08/1997", "321338", "1018", "private");
//        empMap.put(p1.getMedicare(), p1);
//    }

    public static Paciente addPaciente(Paciente emp) {
        System.out.println("Add Paciente");
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);

            DB db = mongoClient.getDB("sampledb");

            DBCollection coll = db.getCollection("pacientes");
            DBObject doc = new BasicDBObject("fName", emp.getfName())
                    .append("sName", emp.getsName());

            coll.insert(doc);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": "
                    + e.getMessage());
        }
        return emp;
    }

    public static List getPaciente(String fName) {
        Paciente paciente;
        List<Paciente> list = new ArrayList<>();
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);
            DB db = mongoClient.getDB("sampledb");

            DBCollection coll = db.getCollection("pacientes");
                        DBObject query = new BasicDBObject("fName", fName);//filtrando

            DBCursor cursor = coll.find(query);
            try {
                while (cursor.hasNext()) {
                    DBObject object = cursor.next();
                    System.out.println(object);

                    paciente = new Paciente(
                            object.get("fName").toString(), object.get("sName").toString()
                    );
                    list.add(paciente);
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

    public static List<Paciente> getAllPacientes() {
        Paciente paciente;
        List<Paciente> list = new ArrayList<>();
        try {

            MongoClient mongoClient = new MongoClient(HOST, PORT);
            DB db = mongoClient.getDB("sampledb");

            DBCollection coll = db.getCollection("pacientes");
            DBCursor cursor = coll.find();
            try {
                while (cursor.hasNext()) {
                    DBObject object = cursor.next();
                    System.out.println(object);

                    paciente = new Paciente(
                            object.get("fName").toString(), object.get("sName").toString()
                    );
                    list.add(paciente);
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

}
