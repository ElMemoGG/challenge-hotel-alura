package com.controller;

import com.dao.ReservaDao;
import com.modelo.Huesped;
import com.modelo.Reserva;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservasController {

    ReservaDao reservaDao;
    EntityManager em;

    public ReservasController(EntityManager em) {

        this.reservaDao = new ReservaDao(em);
        this.em = em;
    }

    public ReservasController() {

    }

    public void guardar(LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor, String formaPago, Huesped huesped){
        Reserva reserva = new Reserva(fechaEntrada, fechaSalida, valor, formaPago, huesped);

        em.getTransaction().begin();
        reservaDao.guardar(reserva);
        em.getTransaction().commit();
        em.close();
    }



    public BigDecimal calcValor(long fechaEntrada, long fechaSalida){
        long startTime = fechaEntrada;
        long endTime = fechaSalida;
        long diasDesde = (long) Math.floor(startTime / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
        long diasHasta = (long) Math.floor(endTime / (1000*60*60*24)); // convertimos a dias, para que no afecten cambios de hora
        long dias = (diasHasta - diasDesde) * 1500;
        return new BigDecimal(dias);
    }

    public void cargarTabla (JTable table, DefaultTableModel modelo){
        modelo.setRowCount(0);
        var productos = reservaDao.consultarTodos();
        productos.forEach(producto -> modelo.addRow(new Object[]{
                producto.getId(), producto.getFechaEntrada(),
                producto.getFechaSalida(), producto.getValor(),
                producto.getFormaPago(),
                producto.getHuesped().getId()}));
    }

    public void eliminar(int id){
        Reserva res = reservaDao.consultaPorId(id);
        System.out.println(res.toString());
        reservaDao.eliminar( res);

    }

    public void editar(int id, LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor, String formaPago){
        Reserva res = reservaDao.consultaPorId(id);
        reservaDao.editar( res, fechaEntrada, fechaSalida, valor, formaPago);

    }

    public void cargarBusqueda (JTable table, DefaultTableModel modelo, String busqueda){
        modelo.setRowCount(0);
        var productos = reservaDao.buscar(busqueda);
        productos.forEach(producto -> modelo.addRow(new Object[]{
                producto.getId(), producto.getFechaEntrada(),
                producto.getFechaSalida(), producto.getValor(),
                producto.getFormaPago(),
                producto.getHuesped().getId()}));
    }


}
