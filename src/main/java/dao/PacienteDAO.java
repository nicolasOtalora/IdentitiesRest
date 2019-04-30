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

/**
 *
 * @author white
 */

public class PacienteDAO {
    private static final Map<String, Paciente> empMap = new HashMap<String, Paciente>();
    static {
        initEmps();
    }
 
    private static void initEmps() {
        Paciente p1 = new Paciente("Nicolas", "Otalora", "Calle falsa", "11/08/1997", "321338", "1018", "private");
        empMap.put(p1.getMedicare(), p1);
    }
    
    public static Paciente addPaciente(Paciente emp) {
        empMap.put(emp.getMedicare(), emp);
        return emp;
    }
    
    public static Paciente getPaciente(String empNo) {
        return empMap.get(empNo);
    }
    
    public static List<Paciente> getAllPacientes() {
        Collection<Paciente> c = empMap.values();
        List<Paciente> list = new ArrayList<Paciente>();
        list.addAll(c);
        return list;
    }
    
    
    
    
}
