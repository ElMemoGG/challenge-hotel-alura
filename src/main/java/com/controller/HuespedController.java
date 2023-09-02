package com.controller;

import com.dao.HuespedDao;
import com.modelo.Huesped;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class HuespedController {

    HuespedDao huespedDao;
    EntityManager em;

    public HuespedController(EntityManager em) {
        this.huespedDao = new HuespedDao(em);
        this.em = em;
    }



    public Huesped guardar(String nombre, String apellido, LocalDate fechaN, String nacionalidad, String telefono){
        Huesped huesped = new Huesped(nombre, apellido, fechaN, nacionalidad, telefono);
        em.getTransaction().begin();
        huespedDao.createHuesped(huesped);
        em.getTransaction().commit();
        em.close();
        return huesped;
    }

    public void cargarTabla (JTable table, DefaultTableModel modeloHuesped){
        modeloHuesped.setRowCount(0);
        var productos = huespedDao.consultarTodos();
        productos.forEach(producto -> modeloHuesped.addRow(new Object[]{producto.getId(), producto.getNombre(),
                producto.getApellido(), producto.getFechaNacimiento(), producto.getNacionalidad(), producto.getTelefono()}));
    }

    public void eliminar(int id){
        Huesped hues = huespedDao.consultaPorId(id);
        System.out.println(hues.toString());
         huespedDao.eliminar( hues);

    }

    public void editar(int id, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono){
        Huesped hues = huespedDao.consultaPorId(id);
        huespedDao.editar( hues, nombre, apellido, fechaNacimiento, nacionalidad, telefono);

    }

    public void cargarBusqueda (JTable table, DefaultTableModel modeloHuesped, String busqueda){
        modeloHuesped.setRowCount(0);
        var productos = huespedDao.buscador(busqueda);
        productos.forEach(producto -> modeloHuesped.addRow(new Object[]{producto.getId(), producto.getNombre(),
                producto.getApellido(), producto.getFechaNacimiento(), producto.getNacionalidad(), producto.getTelefono()}));
    }

}
