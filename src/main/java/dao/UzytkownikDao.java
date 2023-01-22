package dao;

import java.util.Date;
import map.Uzytkownik;
import java.util.List;
import map.SposobRealizacji;
import map.Uzytkownik;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UzytkownikDao extends DAO<Uzytkownik> {

    public UzytkownikDao() {
        this.setmodelClass(map.Uzytkownik.class);
    }

    public Uzytkownik getUser(String password, String login) {
        System.out.println(password + "  " + login);
        Session session = this.getSession();
        session.beginTransaction();
        Uzytkownik user = null;
        user = (Uzytkownik) session.createQuery(
                " select user "
                + "from map.Uzytkownik user "
                + "where user.nickname = :login and user.password =:password")
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult();

        session.getTransaction().commit();
        if (user != null) {
            System.out.println(user.getImie() + "  " + user.getNazwisko());
            return user;
        }
        return null;
    }

    public Uzytkownik addUser(String firstname, String surname, String login, String password, Date dataZalozeniaKonta, String email, boolean admin) {
        Session session = this.getSession();
        session.beginTransaction();
        Uzytkownik user = new Uzytkownik();
        user.setImie(firstname);
        user.setNazwisko(surname);
        user.setNickname(login);
        user.setPassword(password);
        user.setDataZalozeniaKonta(dataZalozeniaKonta);
        user.setEmail(email);
        user.setUprawnieniaAdministratora(admin);
        session.persist(user);
        session.getTransaction().commit();
        session.close();
        return null;
    }
}
