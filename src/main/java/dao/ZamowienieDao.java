package dao;

import java.util.ArrayList;
import java.util.List;
import map.Zamowienie;
import map.Zamowienie;
import org.hibernate.Session;

public class ZamowienieDao extends DAO<Zamowienie> {

    public ZamowienieDao() {
        this.setmodelClass(map.Zamowienie.class);
    }

    public Zamowienie addZamowienie(Zamowienie zamowienie) {
        Session session = this.getSession();
        session.beginTransaction();
        session.persist(zamowienie);
        session.getTransaction().commit();
        session.close();
        return null;
    }

    public ArrayList<Zamowienie> getAll() {
        Session session = this.getSession();
        session.beginTransaction();
        ArrayList<Zamowienie> order = null;
        order = (ArrayList<Zamowienie>) session.createQuery(
                " select order "
                + "from map.Zamowienie order ")
                .getResultList();
        session.getTransaction().commit();
        session.close();
        if (order != null) {
            return order;
        }

        return null;
    }
}
