package view.SacPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import view.MainFrame;
import view.SacPackage.Button;
import view.PopUps;
import dao.AdresDao;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import map.Adres;
import dao.SposobRealizacjiDao;
import dao.UzytkownikDao;
import dao.ZamowienieDao;
import java.util.List;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import map.Produkt;
import map.SposobRealizacji;
import map.Uzytkownik;
import map.Zamowienie;


/**
 *
 * @author gs
 */
public class OrderPanel extends javax.swing.JPanel {

    private ArrayList<Adres> adresy;    // tutaj zapisywane będą adresy brane z bazy i z tabeli utworzony jest JList
    private List<SposobRealizacji> sposobyRealizacji;     // a tutaj sposoby realizacji
    private List<Produkt> produkty;
    private float sumaZamowienie;
    
    public OrderPanel(List<Produkt> produkty) {
        initComponents();
        init();
        this.produkty = produkty;
    }

    private String czyWniesienie(boolean b) {   // do ładnego sformatowania tekstu przy sposobach realizacji
        if(b) {
            return(", z wniesieniem");
        }
        return(", bez wniesienia");
    }
    
    private void init() {
        this.setBackground(Color.BLACK);
        
        JLabel adLabel = new JLabel("Dodaj adres lub wybierz z listy:");
        adLabel.setFont(new Font("sansserif", 1, 36));
        adLabel.setForeground(Color.WHITE);
        Dimension size = adLabel.getPreferredSize();
        adLabel.setBounds(150, 100, size.width, size.height);
        this.add(adLabel);
        
        MyTextField txtCity = new MyTextField();
        txtCity.setHint("Miasto");
        txtCity.setBackground(Color.WHITE);
        txtCity.setBounds(150, 170, 400, 45);
        this.add(txtCity);
        
        MyTextField txtStreet = new MyTextField();
        txtStreet.setHint("Ulica");
        txtStreet.setBackground(Color.WHITE);
        txtStreet.setBounds(150, 230, 400, 45);
        this.add(txtStreet);
        
        MyTextField txtCode = new MyTextField();
        txtCode.setHint("Kod pocztowy");
        txtCode.setBackground(Color.WHITE);
        txtCode.setBounds(150, 290, 400, 45);
        this.add(txtCode);
        
        MyTextField txtNumber = new MyTextField();
        txtNumber.setHint("Numer budynku");
        txtNumber.setBackground(Color.WHITE);
        txtNumber.setBounds(150, 350, 400, 45);
        this.add(txtNumber);
        
        MyTextField txtNrlocal = new MyTextField();
        txtNrlocal.setHint("Numer lokalu (jeśli brak, pozostaw puste)");
        txtNrlocal.setBackground(Color.WHITE);
        txtNrlocal.setBounds(150, 410, 400, 45);
        this.add(txtNrlocal);
        
        JList<String> adresList = new JList<String>();   // lista adresów, zwykła, od razu wypisana
        //
        //  Uzyskaj z bazy adresy i wpisz je do tablicy adresy
        //
        AdresDao adresDao = new AdresDao();
        adresy = adresDao.getAll();     // przekaż wszystkie adresy do tablicy
        
        DefaultListModel adresModel = new DefaultListModel();   // ponieważ JList nie może brać danych z typu ArrayList, musimy przekonwertować
        adresModel.addElement("Wybieram opcję nowego adresu");      // musi być tak bo inaczej się nie da, jeśli chcemy dodać adres wpisując to zaznaczamy tą opcję
        for (Adres adres : adresy) {
            adresModel.addElement("Miasto: " + adres.getMiasto() + ", kod pocztowy: " + adres.getKodPocztowy() + ", ulica: " + adres.getUlica() + ", numer budynku: " + adres.getNrBudynku() + ", numer lokalu: " + adres.getNrLokalu());
        }
        
        adresList.setModel(adresModel);  //  ustaw otrzymane adresy w tablicy do listy
        size = adresList.getPreferredSize();
        adresList.setBounds(600, 170, 550, size.height);
        this.add(adresList);
        
        adresList.addListSelectionListener(new ListSelectionListener() {     // wybrany adres przypisz do zamówienia
            @Override
            public void valueChanged(ListSelectionEvent e) {    // kiedy użytkownik wybierze coś z listy 
                
            }
        });
        
        JLabel delivLabel = new JLabel("Wybierz sposób realizacji:");   // wybranie sposobu realizacji zmienić na listę - która pobiera dane z tabeli sposobrealizacji (string sposReal, koszt, int czasWysylki, boolean wniesienie)
        delivLabel.setFont(new Font("sansserif", 1, 36));
        delivLabel.setForeground(Color.WHITE);
        size = delivLabel.getPreferredSize();
        delivLabel.setBounds(150, 510, size.width, size.height);
        this.add(delivLabel);
        
        
        JList<String> realizacjaList = new JList<String>();   // lista sposobów realizacji
        //
        //  Uzyskaj z bazy sposoby i wpisz je do tablicy sposobyRealizacji
        //
        SposobRealizacjiDao sposobRealizacjiDao = new SposobRealizacjiDao();
        sposobyRealizacji = sposobRealizacjiDao.getAll();     // przekaż wszystkie sposoby do tablicy
        
        DefaultListModel sposobModel = new DefaultListModel();   // ponieważ JList nie może brać danych z typu ArrayList, musimy przekonwertować
        for (SposobRealizacji sr : sposobyRealizacji) {
            if("Odbiór osobisty".equals(sr.getSposReal())) {    // przy odbiorze osobistym nie trzeba pozostałych informacji
                sposobModel.addElement(sr.getSposReal());
            } 
            else {
                sposobModel.addElement(sr.getSposReal() + ", koszt: " + sr.getKoszt() + " zł, czas wysyłki: " + sr.getCzasWysylki() + " dni" + czyWniesienie(sr.isWniesienie()));
            }  
        }
        
        realizacjaList.setModel(sposobModel);  //  ustaw otrzymane sposoby w tablicy do listy
        size = realizacjaList.getPreferredSize();
        realizacjaList.setBounds(150, 600, 400, size.height);
        this.add(realizacjaList);
        
        realizacjaList.addListSelectionListener(new ListSelectionListener() {     // wybrany sposob przypisz do zamówienia
            @Override
            public void valueChanged(ListSelectionEvent e) {    // kiedy użytkownik wybierze coś z listy 
                
            }
        });
        
        JLabel infoLabel = new JLabel("Uwagi do zamówienia:");   // użytkownik może wpisać swoje uwagi w textfield, informacje z niego są przypisywane do zamówienia
        infoLabel.setFont(new Font("sansserif", 1, 36));
        infoLabel.setForeground(Color.WHITE);
        size = infoLabel.getPreferredSize();
        infoLabel.setBounds(150, 710, size.width, size.height);
        this.add(infoLabel);
        
        MyTextField uwagiField = new MyTextField();
        uwagiField.setHint("Wpisz uwagi do zamówienia");
        uwagiField.setBackground(Color.WHITE);
        uwagiField.setBounds(150, 800, 990, 45);
        this.add(uwagiField);
        
         // Pobranie produktów z koszyka
        //MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
        //produkty = mf.getProductsFromCart();
        
        // Obliczenie całkowitego kosztu zamówienia
        int i = 0;
        for (Produkt p : produkty) {
            sumaZamowienie += p.getCena() * (int) (p.getLiczbaSztuk());
            i++;
        }
        
        MyTextField cenaField = new MyTextField();
        cenaField.setHint("Całkowity koszt zamówienia: " + sumaZamowienie);
        cenaField.setBackground(Color.WHITE);
        cenaField.setBounds(150, 890, 990, 45);
        this.add(cenaField);

        Button orderButton = new Button();
        orderButton.setBackground(new Color(196, 53, 53));
        orderButton.setForeground(new Color(250, 250, 250));
        orderButton.setText("Złóż zamówienie");
        orderButton.setBounds(150, 980, 180, 35);
        this.add(orderButton, "w 40%, h 40");
        
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    // tutaj będzie trzeba dodać funkcjonalność związaną z dodaniem do listy zamówień danego zamówienia, powrót do widoku sklepu
                //
                // Jeśli nie wybrano adresu/nie wypełniono wszystkich pól adresu, nie wybrano sposobu realizacji zwróć popup
                //
                if(adresList.getSelectedIndex() == -1 || realizacjaList.getSelectedIndex() == -1 || txtCity.getText() == "" || txtStreet.getText() == "" || txtNumber.getText() == "" || txtCode.getText() == "" || txtNrlocal.getText() == "") {
                    JOptionPane.showMessageDialog(null, "Nie podano wszystkich informacji potrzebnych do złożenia zamówienia.", "", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    // Gdy wybrano opcję dodaj nowy adres dodaj go do tabeli adresów

                    // Tutaj w ten sposób oszukujemy, tworząć obiekt adres nawet jeśli nie trzeba bo jak w ifie to potem go nie przekaże do zamówienia (pierwszy if z zamówieniem)
                    int code = 0;
                    int nr = 0;
                    Adres adres = new Adres(txtCity.getText(), code, txtStreet.getText(), txtNumber.getText(), nr);
                    if(adresList.getSelectedIndex() == 0)   {   // jeśli wybrano pierwszą pozycję na liście, a zawsze nią będzie "dodaj nowy adres"
                        if(!(txtCity.getText().matches("[a-zA-Z]+")) || !(txtStreet.getText().matches("[a-zA-Z]+")) || !(txtNumber.getText().matches("[0-9]+"))) {    // jeśli wprowadzono nieprawidłowe dane
                            JOptionPane.showMessageDialog(null, "Wprowadzono nieprawidłowy format danych w wpisanym adresie.", "", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        
                        AdresDao adresDao = new AdresDao();
                        try {
                            code = Integer.parseInt(txtCode.getText());     // musi być tak bo inaczej czemuś wywala error
                            nr = Integer.parseInt(txtNrlocal.getText());
                        } catch (NumberFormatException ne) {
                            JOptionPane.showMessageDialog(null, "Wprowadzono nieprawidłowy format danych w wpisanym adresie.", "", JOptionPane.INFORMATION_MESSAGE);
                            return;     // wyjdz z scope eventu
                        }
                        
                        adres = new Adres(txtCity.getText(), code, txtStreet.getText(), txtNumber.getText(), nr);
                        adresDao.addAdres(adres);  
                    }
                
                    //
                    // Tutaj funkcjonalność dodania do listy zamówień danego zamówienia
                    //
                    AdresDao adresDao = new AdresDao();
                    SposobRealizacjiDao sposobRealizacjiDao = new SposobRealizacjiDao();
                    ZamowienieDao zamowienieDao = new ZamowienieDao();
                    
                    if(adresList.getSelectedIndex() == 0) {
                        Zamowienie zamowienie = new Zamowienie(uwagiField.getText(), adres, sposobRealizacjiDao.getById((long) realizacjaList.getSelectedIndex()), PanelLoginAndRegister.getUzytkownik());
                        zamowienie.setProdukt(produkty);    // nowe
                        zamowienieDao.addZamowienie(zamowienie);
                    }
                    else {
                        Zamowienie zamowienie = new Zamowienie(uwagiField.getText(), adresDao.getById((long) adresList.getSelectedIndex()), sposobRealizacjiDao.getById((long) realizacjaList.getSelectedIndex()), PanelLoginAndRegister.getUzytkownik());
                        zamowienie.setProdukt(produkty);
                        zamowienieDao.addZamowienie(zamowienie);
                    }
                                
                    JOptionPane.showMessageDialog(null, "Dodano zamówienie do listy zamówień.", "", JOptionPane.INFORMATION_MESSAGE);
                    MainFrame frame = (MainFrame) (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent) e.getSource());  // zdobądź rodzica (czyli JFrame)
                    frame.returnToShop();
                } 
            }
        });
        
        Button backButton = new Button();
        backButton.setBackground(new Color(196, 53, 53));
        backButton.setForeground(new Color(250, 250, 250));
        backButton.setText("Wróć do koszyka");
        backButton.setBounds(380, 980, 200, 35);
        this.add(backButton);
        
        backButton.addActionListener(new ActionListener() {     // po naciśnięciu powrót do koszyka
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame frame = (MainFrame) (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent) e.getSource());  // zdobądź rodzica (czyli JFrame)
                frame.returnToCart();
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
