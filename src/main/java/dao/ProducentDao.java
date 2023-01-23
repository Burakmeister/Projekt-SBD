package dao;

import java.util.ArrayList;
import java.util.List;
import map.Producent;
import org.hibernate.Session;

public class ProducentDao extends DAO<Producent> {

    public ProducentDao() {
        this.setmodelClass(map.Producent.class);
    }

    public List<Producent> getAll() {
        ArrayList<Producent> cat;
        try (Session session = this.getSession()) {
            session.beginTransaction();
            cat = null;
            cat = (ArrayList<Producent>) session.createQuery(
                    " select cat "
                            + "from map.Producent cat ")
                    .getResultList();
            session.getTransaction().commit();
        }
        if (cat != null) {
            return cat;
        }
        return null;
    }

    public Producent getProducent(String nazwaProducenta, String kraj, String opisProducenta) {
        System.out.println(nazwaProducenta + "  " + kraj + "  " + opisProducenta);
        Producent boss;
        try (Session session = this.getSession()) {
            session.beginTransaction();
            boss = null;
            boss = (Producent) session.createQuery(
                    " select boss "
                            + "from map.Producent boss "
                            + "where boss.nazwaProducenta =:nazwaProducenta and boss.kraj =:kraj and boss.opisProducenta =:opisProducenta")
                    .setParameter("nazwaProducenta", nazwaProducenta)
                    .setParameter("kraj", kraj)
                    .setParameter("opisProducenta", opisProducenta)
                    .uniqueResult();
            session.getTransaction().commit();
        }
        if (boss != null) {
            return boss;
        }
        return null;
    }

    public Producent getProducent(String nazwaProducenta) {
        System.out.println(nazwaProducenta);
        Producent boss;
        try (Session session = this.getSession()) {
            session.beginTransaction();
            boss = null;
            boss = (Producent) session.createQuery(
                    " select boss "
                            + "from map.Producent boss "
                            + "where boss.nazwaProducenta =:nazwaProducenta")
                    .setParameter("nazwaProducenta", nazwaProducenta)
                    .uniqueResult();
            session.getTransaction().commit();
        }
        if (boss != null) {
            return boss;
        }
        return null;
    }

    public Producent addProducent(String nazwaProducenta, String kraj, String opisProducenta) {

        System.out.println(nazwaProducenta + "  " + kraj + "  " + opisProducenta);
        try (Session session = this.getSession()) {
            session.beginTransaction();
            Producent boss = new Producent();
            boss.setNazwaProducenta(nazwaProducenta);
            boss.setKraj(kraj);
            boss.setOpisProducenta(opisProducenta);
            session.persist(boss);
            session.getTransaction().commit();
        }
        return null;
    }
}
