package com.dao;

import com.modelo.Huesped;
import com.modelo.Reserva;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReservaDao {
    private EntityManager em;

    public ReservaDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Reserva reserva) {
        this.em.persist(reserva);

    }

    public List<Reserva> consultarTodos() {
        String jpql = "SELECT R FROM Reserva AS R";
        return em.createQuery(jpql, Reserva.class).getResultList();
    }
    public Reserva consultaPorId(int id) {
        em.getTransaction().begin();
        Reserva  result = em.find(Reserva.class, id);
        em.getTransaction().commit();
        em.clear();
        return result;

    }

    public void eliminar(Reserva reserva){
        em.getTransaction().begin();
        reserva = em.merge(reserva);
        em.remove(reserva);
        em.getTransaction().commit();
        em.clear();
    }

    public void editar(Reserva reserva, LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor, String formaPago) {
        em.getTransaction().begin();
        reserva = em.merge(reserva);

        reserva.setFechaEntrada(fechaEntrada);
        reserva.setFechaSalida(fechaSalida);
        reserva.setValor(valor);
        reserva.setFormaPago(formaPago);


        em.getTransaction().commit();
        em.clear();



    }
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false; //Error no es numerico
        }
        return true; //Es numerico
    }

    //Bucador que busca por id de la reserva o poer el nombre del huesped de la reserva
    public List<Reserva> buscar(String busqueda) {
        StringBuilder jpql = new StringBuilder("SELECT R FROM Reserva R ");


        if (busqueda != null && !busqueda.trim().isEmpty() && !isNumeric(busqueda)) {
            jpql.append("JOIN FETCH R.huesped where R.huesped.nombre like :busqueda ");
        }
        if (busqueda != null && !busqueda.trim().isEmpty() && isNumeric(busqueda)) {
            jpql.append("where id=:idB ");
        }
        TypedQuery<Reserva> query = em.createQuery(jpql.toString(), Reserva.class);


        if (busqueda != null && !busqueda.trim().isEmpty() && !isNumeric(busqueda)) {
            query.setParameter("busqueda", "%" + busqueda + "%");
        }
        if (busqueda != null && !busqueda.trim().isEmpty() && isNumeric(busqueda)) {
            query.setParameter("idB",  Integer.parseInt(busqueda));
        }
        System.out.println(query);
        return query.getResultList();

    }


}
