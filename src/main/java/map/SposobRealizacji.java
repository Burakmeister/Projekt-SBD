package map;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "SposobRealizacji")
public class SposobRealizacji {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSposobRealizacji", unique = true, nullable = false)
	private int idSposobRealizacji;

	@Column(name = "sposReal", unique = true, nullable = false)
	private String sposReal;

	@Column(name = "koszt", unique = true, nullable = false)
	private float koszt;

	@Column(name = "czasWysylki", unique = true, nullable = true)
	private int czasWysylki;

	@Column(name = "wniesienie", unique = true, nullable = true)
	private boolean wniesienie;

	@OneToMany(mappedBy = "sposobRealizacji")
	private List<Zamowienie> zamowienie = new ArrayList<Zamowienie>();

	public SposobRealizacji() {
	}

	public SposobRealizacji(String sposReal,
			int koszt, int czasWysylki, boolean wniesienie) {
		this.sposReal = sposReal;
		this.koszt = koszt;
		this.czasWysylki = czasWysylki;
		this.wniesienie = wniesienie;
	}

	public int getIdSposobRealizacji() {
		return idSposobRealizacji;
	}

	public void setIdSposobRealizacji(int idSposobRealizacji) {
		this.idSposobRealizacji = idSposobRealizacji;
	}

	public String getSposReal() {
		return sposReal;
	}

	public void setSposReal(String sposReal) {
		this.sposReal = sposReal;
	}

	public float getKoszt() {
		return koszt;
	}

	public void setKoszt(int koszt) {
		this.koszt = koszt;
	}

	public int getCzasWysylki() {
		return czasWysylki;
	}

	public void setCzasWysylki(int czasWysylki) {
		this.czasWysylki = czasWysylki;
	}

	public boolean isWniesienie() {
		return wniesienie;
	}

	public void setWniesienie(boolean wniesienie) {
		this.wniesienie = wniesienie;
	}

	public List<Zamowienie> getZamowienie() {
		return this.zamowienie;
	}

	public void setZamowienie(List<Zamowienie> zamowienie) {
		this.zamowienie = zamowienie;
	}

}
