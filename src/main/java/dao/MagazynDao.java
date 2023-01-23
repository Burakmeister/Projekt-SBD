
package dao;

import java.util.ArrayList;
import map.Adres;
import map.Magazyn;
import org.hibernate.Session;

public class MagazynDao extends DAO<Magazyn> {

    public MagazynDao() {
        this.setmodelClass(map.Magazyn.class);
    }

    public ArrayList<Magazyn> getAll() {
        ArrayList<Magazyn> cat;
        try (Session session = this.getSession()) {
            session.beginTransaction();
            cat = null;
            cat = (ArrayList<Magazyn>) session.createQuery(
                    " select cat "
                            + "from map.Magazyn cat ")
                    .getResultList();
            session.getTransaction().commit();
        }
        if (cat != null) {
            return cat;
        }
        return null;
    }

    public Magazyn addMagazyn(Magazyn magazyn) {
        try (Session session = this.getSession()) {
            session.beginTransaction();
            session.persist(magazyn);
            session.getTransaction().commit();
        }
        return null;
    }
    
    public Magazyn getMagazyn(int pojemnosc, Adres adres) {
        System.out.println(pojemnosc + "  " + adres);
        Magazyn magazyn;
        try (Session session = this.getSession()) {
            session.beginTransaction();
            magazyn = null;
            magazyn = (Magazyn) session.createQuery(
                    " select magazyn "
                            + "from map.Magazyn magazyn "
                            + "where magazyn.pojemnosc = :pojemnosc and magazyn.adres =:adres")
                    .setParameter("pojemnosc", pojemnosc)
                    .setParameter("adres", adres)
                    .uniqueResult();
            session.getTransaction().commit();
        }
        if (magazyn != null) {
            return magazyn;
        }

        return null;
    }
}
