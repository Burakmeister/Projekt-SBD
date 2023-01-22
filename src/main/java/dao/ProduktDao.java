
package dao;

import java.util.ArrayList;
import java.util.List;
import map.Kategoria;
import map.Producent;
import map.Produkt;
import map.Produkt;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProduktDao extends DAO<Produkt> {

    public ProduktDao() {
        this.setmodelClass(map.Produkt.class);
    }

    public ArrayList<Produkt> getAll() {
        Session session = this.getSession();
        session.beginTransaction();
        ArrayList<Produkt> cat = (ArrayList<Produkt>) session.createQuery(
                " select p "
                + "from map.Produkt p ")
                .getResultList();
        session.getTransaction().commit();
        session.close();
        if (cat != null) {
            return cat;
        }

        return null;
    }
        public Produkt addProdukt(String nazwaProduktu, float cena,
            String opis, float masa, Kategoria kategoria, Producent producent, int liczbaSztuk, String nazwaObrazka) {
        Session session = this.getSession();
        session.beginTransaction();
        Produkt cat = new Produkt();
        cat.setCena(cena);
        cat.setKategoria(kategoria);
        cat.setLiczbaSztuk(liczbaSztuk);
        cat.setNazwaObrazka(nazwaObrazka);
        cat.setNazwaProduktu(nazwaProduktu);
        cat.setOpis(opis);
        cat.setProducent(producent);
        session.persist(cat);
        session.getTransaction().commit();
        session.close();
        return null;
    } 
}

