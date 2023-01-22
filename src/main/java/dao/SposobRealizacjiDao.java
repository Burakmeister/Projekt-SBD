package dao;

import java.util.ArrayList;
import java.util.List;
import map.Adres;
import map.SposobRealizacji;
import map.SposobRealizacji;
import org.hibernate.Session;

public class SposobRealizacjiDao extends DAO<SposobRealizacji>{
    public SposobRealizacjiDao(){
        this.setmodelClass(map.SposobRealizacji.class);
    }

    public ArrayList<SposobRealizacji> getAll() {
        Session session = this.getSession();
        session.beginTransaction();
        ArrayList<SposobRealizacji> cat = (ArrayList<SposobRealizacji>) session.createQuery(
                " select p "
                + "from map.SposobRealizacji p ")
                .getResultList();
        session.getTransaction().commit();
        if (cat != null) {
            return cat;
        }

        return null;
    }
}
