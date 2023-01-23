
package dao;

import java.util.ArrayList;
import map.Kategoria;
import map.Producent;
import map.Produkt;
import org.hibernate.Session;

public class ProduktDao extends DAO<Produkt> {

    public ProduktDao() {
        this.setmodelClass(map.Produkt.class);
    }

    public ArrayList<Produkt> getAll() {
        ArrayList<Produkt> cat;
        try (Session session = this.getSession()) {
            session.beginTransaction();
            cat = (ArrayList<Produkt>) session.createQuery(
                    " select p "
                            + "from map.Produkt p ")
                    .getResultList();
            session.getTransaction().commit();
        }
        if (cat != null) {
            return cat;
        }

        return null;
    }
        public Produkt addProdukt(String nazwaProduktu, float cena,
            String opis, float masa, Kategoria kategoria, Producent producent, int liczbaSztuk, String nazwaObrazka) {
        try (Session session = this.getSession()) {
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
        }
        return null;
    } 
}

