package map;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Produkt")
public class Produkt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProdukt", unique = true, nullable = false)
    private int idProduktu;

    @Column(name = "nazwaProduktu", unique = true, nullable = false)
    private String nazwaProduktu;

    @Column(name = "cena", unique = false, nullable = false)
    private float cena;

    @Column(name = "opis", unique = false, nullable = false)
    private String opis;

    @Column(name = "masa", unique = false, nullable = true)
    private float masa;

    @Column(name = "LiczbaSztuk", unique = true, nullable = true)
    private int liczbaSztuk;

    @Column(name = "nazwaObrazka", unique = true, nullable = true)
    private String nazwaObrazka;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Kategoria_idKategoria")
    private Kategoria kategoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Producent_idProducent")
    private Producent producent;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sztukiproduktu",
            joinColumns = {
                @JoinColumn(name = "Produkt_idProdukt")},
            inverseJoinColumns = {
                @JoinColumn(name = "Zamowienie_idZamowienie")}
    )
    private List<Zamowienie> zamowienie;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produktmagazyn",
            joinColumns = {
                @JoinColumn(name = "Produkt_idProdukt")},
            inverseJoinColumns = {
                @JoinColumn(name = "Magazyn_idMagazynu")}
    )
    private List<Magazyn> magazyn;

    public Produkt() {
    }

    public Produkt(String nazwaProduktu, float cena,
            String opis, float masa, Kategoria kategoria, Producent producent, int liczbaSztuk, String nazwaObrazka) {
        this.nazwaProduktu = nazwaProduktu;
        this.cena = cena;
        this.opis = opis;
        this.masa = masa;
        this.kategoria = kategoria;
        this.producent = producent;
        this.liczbaSztuk = liczbaSztuk;
        this.nazwaObrazka = nazwaObrazka;

    }

    public Produkt setIdProduktu(int idProduktu) {
        this.idProduktu = idProduktu;
        return this;
    }

    public String getNazwaProduktu() {
        return nazwaProduktu;
    }

    public void setNazwaProduktu(String nazwaProduktu) {
        this.nazwaProduktu = nazwaProduktu;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public float getMasa() {
        return masa;
    }

    public void setMasa(float masa) {
        this.masa = masa;
    }

    public String getnazwaObrazka() {
        return nazwaObrazka;
    }

    public void setNazwaObrazka() {
        this.nazwaObrazka = nazwaObrazka;
    }

    public String getNazwaObrazka() {
        return this.nazwaObrazka;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    public Producent getProducent() {
        return producent;
    }

    public void setProducent(Producent producent) {
        this.producent = producent;
    }

    public int getLiczbaSztuk() {
        return liczbaSztuk;
    }

    public void setLiczbaSztuk(int liczbaSztuk) {
        this.liczbaSztuk = liczbaSztuk;
    }

    //public List<SztukiProduktu> getSztukiProduktu() {
    //	return sztukiProduktu;
    //}
    public List<Zamowienie> getzamowienie() { //
        return zamowienie;
    }

    //public void setSztukiProduktu(List<SztukiProduktu> sztukiProduktu) {
    //	this.sztukiProduktu = sztukiProduktu;
    //}
    public void setZamowienie(List<Zamowienie> zamowienie) { //
        this.zamowienie = zamowienie;
    }

    //public List<ProduktMagazyn> getProduktMagazyn() {
    //	return produktMagazyn;
    //}
    public List<Magazyn> getMagazyn() {
        return magazyn;
    }

    //public void setProduktMagazyn(List<ProduktMagazyn> produktMagazyn) {
    //	this.produktMagazyn = produktMagazyn;
    //}
    public void setMagazyn(List<Magazyn> magazyn) {
        this.magazyn = magazyn;
    }

    public int getIdProduktu() {
        return idProduktu;
    }

    public void setNazwaObrazka(String nazwaObrazka) {
        this.nazwaObrazka = nazwaObrazka;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produkt other = (Produkt) obj;
        return this.idProduktu == other.idProduktu;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idProduktu;
        return hash;
    }
}
