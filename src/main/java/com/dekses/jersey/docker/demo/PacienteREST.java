/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekses.jersey.docker.demo;

import dao.PacienteDAO;
import vo.Paciente;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author white
 */
@Path("pacientes")
public class PacienteREST {
    
   
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Paciente addPaciente(Paciente emp) {
        return PacienteDAO.addPaciente(emp);
    }
    
    @GET
    @Path("/{pacID}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Paciente getPaciente(@PathParam("pacID") String empNo) {
        return PacienteDAO.getPaciente(empNo);
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON )

    public List<Paciente> getPacientes_JSON() {
        List<Paciente> listOfCountries = PacienteDAO.getAllPacientes();
        return listOfCountries;
    }
    
}
