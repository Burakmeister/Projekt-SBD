package dao;

import java.util.List;
import map.Zamowienie;
import map.Zamowienie;
import org.hibernate.Session;

public class ZamowienieDao extends DAO<Zamowienie>{
    public ZamowienieDao(){
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
}