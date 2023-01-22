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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "magazyn")
public class Magazyn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMagazynu", unique = true, nullable = false)
    private int idMagazynu;

    @Column(name = "pojemnosc", unique = false, nullable = false)
    private int pojemnosc;

    @OneToOne
    @JoinColumn(name = "Adres_idAdres", referencedColumnName = "idAdresu")
    private Adres adres;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produktmagazyn",
            joinColumns = {
                @JoinColumn(name = "Magazyn_idMagazynu")},
            inverseJoinColumns = {
                @JoinColumn(name = "Produkt_idProdukt")}
    )
    private List<Produkt> produkt = new ArrayList<Produkt>();

    public Magazyn() {
    }

    public Magazyn(int pojemnosc, Adres adres) {
        this.pojemnosc = pojemnosc;
        this.adres = adres;
    }

    public int getIdMagazynu() {
        return idMagazynu;
    }

    public void setIdMagazynu(int idMagazynu) {
        this.idMagazynu = idMagazynu;
    }

    public int getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(int pojemnosc) {
        this.pojemnosc = pojemnosc;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    @Override
    public String toString() {
        return "id=" + idMagazynu;
    }

    public void setProdukt(List<Produkt> prod) {
        this.produkt = prod;
    }

    public List<Produkt> getProdukt() {
        return this.produkt;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.idMagazynu;
        return hash;
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
        final Magazyn other = (Magazyn) obj;
        return this.idMagazynu == other.idMagazynu;
    }
    
    
}
