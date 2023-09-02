package com.dao;

import com.modelo.Huesped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class HuespedDao {

    EntityManager em;

    public HuespedDao(EntityManager em) {
        this.em = em;
    }

    public void createHuesped(Huesped huesped){
        this.em.persist(huesped);
    }
    public List<Huesped> consultarTodos() {
        String jpql = "SELECT H FROM Huesped AS H";
        return em.createQuery(jpql, Huesped.class).getResultList();
    }

    public Huesped consultaPorId(int id) {
        em.getTransaction().begin();
        Huesped result = em.find(Huesped.class, id);
        em.getTransaction().commit();
        em.clear();
        return result;


    }

    public void editar(Huesped huesped, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono) {
        em.getTransaction().begin();
        huesped = em.merge(huesped);

        huesped.setNombre(nombre);
        huesped.setApellido(apellido);
        huesped.setFechaNacimiento(fechaNacimiento);
        huesped.setNacionalidad(nacionalidad);
        huesped.setTelefono(telefono);

        em.getTransaction().commit();
        em.clear();

    }

    public void eliminar(Huesped huesped){
        em.getTransaction().begin();
        huesped = em.merge(huesped);
        em.remove(huesped);
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

    //Buscador que busca por el apellido del huesped o por su id
    public List<Huesped> buscador(String busqueda) {
        StringBuilder jpql = new StringBuilder("SELECT H FROM Huesped H ");
        //String jpql = "SELECT H FROM Huesped H where apellido like :busqueda OR id=:idB";

        if (busqueda != null && !busqueda.trim().isEmpty() && !isNumeric(busqueda)) {
            jpql.append("where apellido like :busqueda ");
        }
        if (busqueda != null && !busqueda.trim().isEmpty() && isNumeric(busqueda)) {
            jpql.append("where id=:idB ");
        }
        TypedQuery<Huesped> query = em.createQuery(jpql.toString(), Huesped.class);


        if (busqueda != null && !busqueda.trim().isEmpty() && !isNumeric(busqueda)) {
            query.setParameter("busqueda", "%" + busqueda + "%");
        }
        if (busqueda != null && !busqueda.trim().isEmpty() && isNumeric(busqueda)) {
            query.setParameter("idB",  Integer.parseInt(busqueda));
        }
        System.out.println(query);
        return query.getResultList();

    }






    /*public String createHuesped(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, Long telefono){
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Huesped.class)
                .buildSessionFactory();
        Session session =  sessionFactory.openSession();
        try {
            Huesped huesped = new Huesped(nombre, apellido, fechaNacimiento, nacionalidad,telefono);
            session.beginTransaction();
            session.save(huesped);

            session.getTransaction().commit();

            session.close();


            return "se creo el huesped";

        }catch (Exception e){
            e.printStackTrace();
        }

        return "error";
    }*/
}
