/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tuserlate file, choose Tools | Tuserlates
 * and open the tuserlate in the editor.
 */
package com.dekses.jersey.docker.demo;

import dao.UsuarioDAO;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import vo.Documento;
import vo.Permiso;
import vo.User;

/**
 *
 * @author white
 */
@Path("usuarios")
public class DocumentosREST {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User addUser(User user) {
        System.out.println("docs "+user.getDocumentos().length);
        return UsuarioDAO.addUser(user);
    }

    @GET
    @Path("/{user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getUser(@PathParam("user") String userName) {
        return UsuarioDAO.getUser(userName);
    }

    @GET
    @Path("/{user}/documentos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Documento> getDocumentos(@PathParam("user") String userName) {
        return UsuarioDAO.getAllDocumentos(userName);
    }

    @GET
    @Path("/{user}/permisos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Permiso> getPermisos(@PathParam("user") String userName) {
        return UsuarioDAO.getAllPermisos(userName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public List<User> getUsers_JSON() {
        List<User> listOfCountries = UsuarioDAO.getAllUsers();
        return listOfCountries;
    }

    @PUT
    @Path("/{user}/update")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUsuario(User user, @PathParam("user") String usuario) {
        System.out.println("REST");
        UsuarioDAO.updateUsuario(user, usuario);
        return user;
    }

}
