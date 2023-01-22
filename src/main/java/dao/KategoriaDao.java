package dao;

import dao.DAO;
import java.util.ArrayList;
import java.util.List;
import map.Kategoria;
import org.hibernate.Session;

/**
 *
 * @author bdawi
 */
public class KategoriaDao extends DAO<Kategoria> {

    public KategoriaDao() {
        this.setmodelClass(map.Kategoria.class);
    }

    public ArrayList<Kategoria> getAll() {
        Session session = this.getSession();
        session.beginTransaction();
        ArrayList<Kategoria> cat = null;
        cat = (ArrayList<Kategoria>) session.createQuery(
                " select cat "
                + "from map.Kategoria cat ")
                .getResultList();
        session.getTransaction().commit();
        if (cat != null) {
            return cat;
        }

        return null;
    }

    public Kategoria getKategoria(String nazwaKategorii, String opisKategorii) {
        System.out.println(nazwaKategorii + "  " + opisKategorii);
        Session session = this.getSession();
        session.beginTransaction();
        Kategoria cat = null;
        cat = (Kategoria) session.createQuery(
                " select cat "
                + "from map.Kategoria cat "
                + "where cat.nazwaKategorii = :nazwaKategorii and cat.opisKategorii =:opisKategorii")
                .setParameter("nazwaKategorii", nazwaKategorii)
                .setParameter("opisKategorii", opisKategorii)
                .uniqueResult();

        session.getTransaction().commit();
        if (cat != null) {
            System.out.println(cat.getNazwaKategorii() + "  " + cat.getOpisKategorii());
            return cat;
        }

        return null;
    }

    public Kategoria addKategoria(String nazwaKategorii, String opisKategorii) {
        Session session = this.getSession();
        session.beginTransaction();
        Kategoria cat = new Kategoria();
        cat.setNazwaKategorii(nazwaKategorii);
        cat.setOpisKategorii(opisKategorii);
        session.persist(cat);
        session.getTransaction().commit();
        session.close();
        return null;
    }

    public Kategoria findKategoria(String nazwaKategorii) {
        System.out.println(nazwaKategorii);
        Session session = this.getSession();
        session.beginTransaction();
        Kategoria cat = null;
        cat = (Kategoria) session.createQuery(
                " select cat"
                + "from map.Kategoria cat "
                + "where cat.nazwaKategorii = :nazwaKategorii")
                .setParameter("nazwaKategorii", nazwaKategorii)
                .uniqueResult();

        session.getTransaction().commit();
        if (cat != null) {
            System.out.println(cat.getOpisKategorii() + " " + cat.getIdKategoria());
            return cat;
        }
        return null;
    }
}
