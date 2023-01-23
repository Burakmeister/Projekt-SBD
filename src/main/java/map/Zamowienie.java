package map;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "Zamowienie")
public class Zamowienie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idZamowienia", unique = true, nullable = false)
    private int idZamowienia;

    @CreationTimestamp
    @Column(name = "dataGodzina")
    private Date dataGodzina;

    @Column(name = "uwagi", nullable = false)
    private String uwagiDoZamowienia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Adres_idAdres")
    private Adres adres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Sposob_Realizacji_idSposob_Realizacji")
    private SposobRealizacji sposobRealizacji;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uzytkownik_idUzytkownik")
    private Uzytkownik uzytkownik;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sztukiproduktu",
            joinColumns = {
                @JoinColumn(name = "Zamowienie_idZamowienie")},
            inverseJoinColumns = {
                @JoinColumn(name = "Produkt_idProdukt")}
    )
    private List<Produkt> produkt = new ArrayList<Produkt>();  // nowe

    public Zamowienie() {
    }

    public Zamowienie(String uwagiDoZamowienia,
            Adres adres, SposobRealizacji sposobRealizacji, Uzytkownik uzytkownik) {
//		this.dataGodzina = dataGodzina;    Date dataGodzina,
        this.uwagiDoZamowienia = uwagiDoZamowienia;
        this.adres = adres;
        this.sposobRealizacji = sposobRealizacji;
        this.uzytkownik = uzytkownik;
    }

    public int getIdZamowienia() {
        return idZamowienia;
    }

    public void setIdZamowienia(int idZamowienia) {
        this.idZamowienia = idZamowienia;
    }

    public Date getDataGodzina() {
        return dataGodzina;
    }

    public void setDataGodzina(Date dataGodzina) {
        this.dataGodzina = dataGodzina;
    }

    public String getUwagiDoZamowienia() {
        return uwagiDoZamowienia;
    }

    public void setUwagiDoZamowienia(String uwagiDoZamowienia) {
        this.uwagiDoZamowienia = uwagiDoZamowienia;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public SposobRealizacji getSposobRealizacji() {
        return sposobRealizacji;
    }

    public void setSposobRealizacji(SposobRealizacji sposobRealizacji) {
        this.sposobRealizacji = sposobRealizacji;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    //public List<SztukiProduktu> getSztukiProduktu() {
    //	return this.sztukiProduktu;
    //}
    public List<Produkt> getProdukt() {
        return this.produkt;
    }
    //public List<SztukiProduktu> getSztukiProduktu() {
    //	return this.sztukiProduktu;
    //}

    //public void setSztukiProduktu(List<SztukiProduktu> sztukiProduktu) {
    //	this.sztukiProduktu = sztukiProduktu;
    //}
    public void setProdukt(List<Produkt> produkt) {
        this.produkt = produkt;  //nowe
    }

    @Override
    public String toString() {
        float sum = 0;
        for (Produkt p : this.produkt) {
            sum += p.getCena();
        }
        return "id zamówienia: " + idZamowienia + ", data i godzina: " + dataGodzina + ", kwota: " + String.format("%.2g%n", sum);
    }

}
