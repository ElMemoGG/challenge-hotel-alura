package com.views;

import com.controller.HuespedController;
import com.dao.HuespedDao;
import com.dao.ReservaDao;
import com.modelo.Huesped;
import com.modelo.Reserva;
import com.utils.JPAUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        /*String huesped = new HuespedDao().createHuesped("Memo", "Navarro", LocalDate.now(), "Mexico", 232L);

        System.out.println("Hello world! " + huesped);*/

     /* EntityManager em = JPAUtils.getEntityManager();
      Huesped huesped = new Huesped("Memo", "Navarro", LocalDate.now(), "Mexico", "23423424");
      Reserva reserva = new Reserva(LocalDate.now(), LocalDate.now(),  new BigDecimal("10"), "Las nalgas", huesped);


        ReservaDao reservaDao = new ReservaDao(em);
        HuespedDao huespedDao = new HuespedDao(em);

      System.out.println(huesped.toString());
        em.getTransaction().begin();
       //huesped.agregarReserva(reserva);

        huespedDao.createHuesped(huesped);
        reservaDao.guardar(reserva);

        em.getTransaction().commit();
        em.close();*/
      EntityManager em = JPAUtils.getEntityManager();
      HuespedController huespedController = new HuespedController(em);

      huespedController.eliminar(3);
    }
}