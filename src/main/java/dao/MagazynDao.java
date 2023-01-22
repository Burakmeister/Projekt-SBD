
package dao;

import java.util.ArrayList;
import java.util.List;
import map.Adres;
import map.Magazyn;
import org.hibernate.Session;

public class MagazynDao extends DAO<Magazyn> {

    public MagazynDao() {
        this.setmodelClass(map.Magazyn.class);
    }

    public ArrayList<Magazyn> getAll() {
        Session session = this.getSession();
        session.beginTransaction();
        ArrayList<Magazyn> cat = null;
        cat = (ArrayList<Magazyn>) session.createQuery(
                " select cat "
                + "from map.Magazyn cat ")
                .getResultList();
        session.getTransaction().commit();
        session.close();
        if (cat != null) {
            return cat;
        }

        return null;
    }

    public Magazyn addMagazyn(Magazyn magazyn) {
        Session session = this.getSession();
        session.beginTransaction();
        session.persist(magazyn);
        session.getTransaction().commit();
        session.close();
        return null;
    }
    
    public Magazyn getMagazyn(int pojemnosc, Adres adres) {
        System.out.println(pojemnosc + "  " + adres);
        Session session = this.getSession();
        session.beginTransaction();
        Magazyn magazyn = null;
        magazyn = (Magazyn) session.createQuery(
                " select magazyn "
                + "from map.Magazyn magazyn "
                + "where magazyn.pojemnosc = :pojemnosc and magazyn.adres =:adres")
                .setParameter("pojemnosc", pojemnosc)
                .setParameter("adres", adres)
                .uniqueResult();

        session.getTransaction().commit();
        if (magazyn != null) {
            System.out.println("yep");
            return magazyn;
        }

        return null;
    }
}
