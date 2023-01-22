package view.layouts;

import dao.AdresDao;
import dao.MagazynDao;
import dao.ProduktDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.View;
import map.Adres;
import map.Magazyn;
import map.Produkt;
import view.Image;
import view.Letter;
import view.MainFrame;
import static view.layouts.ListPanel.scale;

public class WarehouseLayout extends JPanel implements ActionListener {

    public static final int borderPx = Toolkit.getDefaultToolkit().getScreenSize().width / 500;
    private Font font = new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (40 * scale));

    private JScrollPane scroll;
    private Cart mainPanel;

    private JPanel upPanel;
    private JButton returnButton;
    private JButton saveButton;

    private JButton addMagazin;
    private JLabel logo;
    private JTextField cityField;
    private JTextField zipCodeField;
    private JTextField streetField;
    private JTextField buildNumField;
    private JTextField apartNumField;
    private JTextField capacityField;

    private JPanel categoryPanel = new JPanel();
    private JLabel quantity;
    private JLabel sum;
    private JLabel mass;
    private ArrayList<JButton> warehousesBut;
    private ArrayList<Magazyn> warehouses = new ArrayList<>();
    private int idxWarehouse = 0;

    public WarehouseLayout() {
        this.setLayout(null);

        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(Color.WHITE);

        this.upPanel = new JPanel();
//        {
//            @Override
//            public void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.drawImage(Image.LOGO.icon.getImage(), Toolkit.getDefaultToolkit().getScreenSize().width / 2, -borderPx, null);
//            }
//        };
        this.categoryPanel = new JPanel();

//		this.categoryPanel.repaint();
        this.makeMainPanel();

        this.upPanel.setLayout(null);
        this.categoryPanel.setLayout(new FlowLayout(View.VERTICAL));

        this.scroll = new JScrollPane(mainPanel);
//		this.scroll.add(mainPanel);
        this.scroll.setVisible(true);
        this.scroll.getVerticalScrollBar().setUnitIncrement(16);

        this.upPanel.setBounds(borderPx, borderPx, this.getPreferredSize().width - 2 * borderPx, this.getPreferredSize().height / 8);
        this.categoryPanel.setBounds(borderPx, this.getPreferredSize().height / 8 + 2 * ShopLayout.borderPx,
                this.getPreferredSize().width / 10, this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx);
        this.scroll.setBounds(this.getPreferredSize().width / 10 + 2 * borderPx, this.getPreferredSize().height / 8 + 2 * borderPx,
                getPreferredSize().width - 3 * borderPx - this.getPreferredSize().width / 10, this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx);

        makeCategoryPanel();
        makeUpPanel();

        this.add(upPanel);
        this.add(categoryPanel);
        this.add(scroll);
    }

    public void addProduct(Produkt produkt) {
        this.mainPanel.addProdukt(produkt, false);
    }

    public void refreshCategoryPanel() {
        float sumFloat = 0;
        int numOf = 0;
        float massFloat = 0;
        ArrayList<Produkt> products = (ArrayList<Produkt>) this.mainPanel.getProducts();
        ArrayList<Integer> numOfProducts = (ArrayList<Integer>) this.mainPanel.getNumOfProducts();
        int i = 0;
        for (Produkt p : products) {
            sumFloat += p.getCena() * (int) (numOfProducts.get(i));
            numOf += (int) (numOfProducts.get(i));
            massFloat += p.getMasa() * (int) (numOfProducts.get(i));
            i++;
        }
        quantity.setText(String.valueOf(numOf));
        if (sumFloat * 100 % 10 == 0) {
            sum.setText(String.valueOf(Math.round(sumFloat * 100) / 100.0) + "0z"+Letter.ELL.getLetter());
        } else {
            sum.setText(String.valueOf(Math.round(sumFloat * 100) / 100.0) + "z"+Letter.ELL.getLetter());
        }

        if (massFloat * 100 % 10 == 0) {
            mass.setText(String.valueOf(Math.round(massFloat * 100) / 100.0) + "0kg");
        } else {
            mass.setText(String.valueOf(Math.round(massFloat * 100) / 100.0) + "kg");
        }
    }

    public void makeCategoryPanel() {
        this.categoryPanel.setBackground(Color.black);
        addMagazin = new JButton("<html>" + "  Dodaj" + "<br>" + "magazyn" + "</html>");
        addMagazin.setPreferredSize(new Dimension(categoryPanel.getSize().width, (int) (3 * 40 * scale)));
        addMagazin.addActionListener(this);
        addMagazin.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 40)));
        this.categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        sum = new JLabel();
        sum.setFont(font);
        sum.setForeground(Color.white);
        sum.setPreferredSize(new Dimension(this.categoryPanel.getSize().width - 2 * borderPx, this.categoryPanel.getSize().width / 3));

        quantity = new JLabel();
        quantity.setFont(font);
        quantity.setForeground(Color.white);
        quantity.setPreferredSize(new Dimension(this.categoryPanel.getSize().width - 2 * borderPx, this.categoryPanel.getSize().width / 3));

        mass = new JLabel();
        mass.setFont(font);
        mass.setForeground(Color.white);
        mass.setPreferredSize(new Dimension(this.categoryPanel.getSize().width - 2 * borderPx, this.categoryPanel.getSize().width / 3));

        JLabel tmp1 = new JLabel("Warto"+Letter.SII.getLetter()+Letter.CI.getLetter()+":");
        tmp1.setFont(font);
        tmp1.setForeground(Color.white);

        JLabel tmp2 = new JLabel("Liczno"+Letter.SII.getLetter()+Letter.CI.getLetter()+":");
        tmp2.setFont(font);
        tmp2.setForeground(Color.white);

        JLabel tmp3 = new JLabel("Masa:");
        tmp3.setFont(font);
        tmp3.setForeground(Color.white);

        this.categoryPanel.add(addMagazin);
        this.categoryPanel.add(tmp1);
        this.categoryPanel.add(sum);
        this.categoryPanel.add(tmp2);
        this.categoryPanel.add(quantity);
        this.categoryPanel.add(tmp3);
        this.categoryPanel.add(mass);
        this.categoryPanel.setBackground(Color.black);

        MagazynDao dao = new MagazynDao();
        List<Magazyn> magazyny = dao.getAll();
        this.warehousesBut = new ArrayList<>();
        for (Magazyn m : magazyny) {
            JButton temp = new JButton(m.toString());
            this.warehousesBut.add(temp);
            this.warehouses.add(m);
            temp.setBackground(Color.BLACK);
            temp.setForeground(Color.WHITE);
            temp.addActionListener((ActionEvent e) -> {
                if (e.getSource() == temp) {
                    for (JButton b : this.warehousesBut) {
                        b.setEnabled(true);
                    }
                    temp.setEnabled(false);
                    idxWarehouse = Integer.parseInt(temp.getText().substring(3)) - 1;
                    this.refreshUpPanel();
                    this.mainPanel.removeAll();
                    for (Produkt p : this.warehouses.get(idxWarehouse).getProdukt()) {
                        this.mainPanel.addProdukt(p, false);
                    }
                    refreshCategoryPanel();
                }
            });
            temp.setPreferredSize(new Dimension(categoryPanel.getSize().width, (int) (2 * 40 * scale)));
            temp.setFont(font);
            this.categoryPanel.add(temp, BorderLayout.CENTER);
        }
        if (!this.warehousesBut.isEmpty()) {
            this.warehousesBut.get(0).setEnabled(false);
            for (Produkt produkt : this.warehouses.get(0).getProdukt()) {
                this.mainPanel.addProdukt(produkt, false);
            }
        }
        this.refreshCategoryPanel();
    }

    private void makeMainPanel() {
        this.mainPanel = new Cart(new Dimension(getPreferredSize().width - 3 * borderPx - this.getPreferredSize().width / 10,
                this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx), 20, true);
        this.mainPanel.setLayout(null);
        this.mainPanel.setBackground(new Color(188, 69, 69));

        this.mainPanel.setLayout(null);
        this.mainPanel.setBackground(new Color(188, 69, 69));
    }

    private void makeUpPanel() {
        this.returnButton = new JButton(Image.RETURN.icon);
        this.returnButton.setBackground(Color.black);
        this.returnButton.setBounds(borderPx,
                borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx);
        this.returnButton.addActionListener(this);

        this.saveButton = new JButton(Image.EDIT_SAVE.icon);
        this.saveButton.setBackground(Color.black);
        this.saveButton.setBounds(
                this.upPanel.getPreferredSize().width - this.upPanel.getPreferredSize().height + ShopLayout.borderPx,
                borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx);
        this.saveButton.addActionListener(this);

        JPanel address = new JPanel(new GridLayout(2, 6));
        address.setBackground(Color.BLACK);
        address.setBounds(this.upPanel.getPreferredSize().height, 0,
                this.upPanel.getPreferredSize().width - 2 * this.upPanel.getPreferredSize().height, this.upPanel.getPreferredSize().height);

        JLabel city = new JLabel("Miasto:");
        city.setFont(font);
        city.setForeground(Color.WHITE);
        cityField = new JTextField();
        cityField.setFont(font);
        cityField.setText(this.warehouses.get(this.idxWarehouse).getAdres().getMiasto());

        JLabel zipCode = new JLabel("Kod pocztowy:");
        zipCode.setFont(font);
        zipCode.setForeground(Color.WHITE);
        zipCodeField = new JTextField();
        zipCodeField.setFont(font);
        zipCodeField.setText(String.valueOf(this.warehouses.get(this.idxWarehouse).getAdres().getKodPocztowy()));

        JLabel street = new JLabel("Ulica:");
        street.setFont(font);
        street.setForeground(Color.WHITE);
        streetField = new JTextField();
        streetField.setFont(font);
        streetField.setText(this.warehouses.get(this.idxWarehouse).getAdres().getUlica());

        JLabel buildNum = new JLabel("Nr budynku:");
        buildNum.setFont(font);
        buildNum.setForeground(Color.WHITE);
        buildNumField = new JTextField();
        buildNumField.setFont(font);
        buildNumField.setText(this.warehouses.get(this.idxWarehouse).getAdres().getNrBudynku());

        JLabel apartNum = new JLabel("Nr lokalu:");
        apartNum.setFont(font);
        apartNum.setForeground(Color.WHITE);
        apartNumField = new JTextField();
        apartNumField.setFont(font);

        JLabel capacity = new JLabel("Pojemno"+Letter.SII.getLetter()+Letter.CI.getLetter()+":");
        capacity.setFont(font);
        capacity.setForeground(Color.WHITE);
        capacityField = new JTextField();
        capacityField.setFont(font);
        capacityField.setText(String.valueOf(this.warehouses.get(this.idxWarehouse).getPojemnosc()));

        address.add(city);
        address.add(zipCode);
        address.add(street);
        address.add(buildNum);
        address.add(apartNum);
        address.add(capacity);

        if (this.warehouses.get(this.idxWarehouse).getAdres().getNrLokalu() != null) {
            apartNumField.setText(String.valueOf(this.warehouses.get(this.idxWarehouse).getAdres().getNrLokalu()));
        }

        address.add(cityField);
        address.add(zipCodeField);
        address.add(streetField);
        address.add(buildNumField);
        address.add(apartNumField);
        address.add(capacityField);

        this.upPanel.add(address);

        this.upPanel.setBackground(Color.BLACK);
        this.upPanel.add(this.returnButton);
        this.upPanel.add(this.saveButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.returnButton) {
            MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            Adres adresTemp = this.warehouses.get(this.idxWarehouse).getAdres();
            this.cityField.setText(adresTemp.getMiasto());
            this.zipCodeField.setText(String.valueOf(adresTemp.getKodPocztowy()));
            this.streetField.setText(adresTemp.getUlica());
            this.buildNumField.setText(adresTemp.getNrBudynku());
            if (adresTemp.getNrLokalu() != null) {
                this.apartNumField.setText(String.valueOf(adresTemp.getNrLokalu()));
            } else {
                this.apartNumField.setText("");
            }
            this.capacityField.setText(String.valueOf(this.warehouses.get(this.idxWarehouse).getPojemnosc()));

            List<Produkt> products = this.warehouses.get(this.idxWarehouse).getProdukt();
            this.mainPanel.removeAll();
            for (Produkt p : products) {
                this.mainPanel.addProdukt(p, false);
            }
            mf.returnToShopFromCart();
        }
        if (e.getSource() == this.addMagazin) {
            makeAddMagazine();
        }
        if (e.getSource() == this.saveButton) {
            MagazynDao mDao = new MagazynDao();
            AdresDao aDao = new AdresDao();
            Adres adresTemp = this.warehouses.get(this.idxWarehouse).getAdres();
            Magazyn magazynTemp = this.warehouses.get(this.idxWarehouse);
            try {
                int tmp = Integer.parseInt(this.zipCodeField.getText());
                adresTemp.setKodPocztowy(tmp);
                adresTemp.setMiasto(this.cityField.getText());
                adresTemp.setNrBudynku(this.buildNumField.getText());
                adresTemp.setUlica(this.streetField.getText());

//            address, cityField, zipCodeField, streetField, buildNumField, apartNumField
                if (apartNumField.getText().equals("")) {
                    adresTemp.setNrLokalu(null);
                } else {
                    tmp = Integer.parseInt(this.apartNumField.getText());
                    adresTemp.setNrLokalu(tmp);
                }

                adresTemp.setZamowienie(null);
                aDao.update(adresTemp);

            } catch (NumberFormatException ex) {
                System.out.print("Nieprawid"+Letter.ELL.getLetter()+"owe dane!");
                this.wrongDataPopUp();
                adresTemp = this.warehouses.get(this.idxWarehouse).getAdres();
                this.cityField.setText(adresTemp.getMiasto());
                this.zipCodeField.setText(String.valueOf(adresTemp.getKodPocztowy()));
                this.streetField.setText(adresTemp.getUlica());
                this.buildNumField.setText(adresTemp.getNrBudynku());
                if (adresTemp.getNrLokalu() != null) {
                    this.apartNumField.setText(String.valueOf(adresTemp.getNrLokalu()));
                } else {
                    this.apartNumField.setText("");
                }
                this.capacityField.setText(String.valueOf(this.warehouses.get(this.idxWarehouse).getPojemnosc()));
            } finally {
                List<Produkt> products = new ArrayList<>();
                List<Produkt> tempProductsCur;       //produkty po zmianach
                List<Integer> tempCur;               //liczba produktow po zmianach

                tempProductsCur = this.mainPanel.getProducts();
                tempCur = this.mainPanel.getNumOfProducts();

                for (int i = 0; i < tempProductsCur.size(); i++) {
                    for (int n = 0; n < tempCur.get(i); n++) {
                        products.add(tempProductsCur.get(i));
                    }
                }
                magazynTemp.setPojemnosc(Integer.parseInt(this.capacityField.getText()));
                magazynTemp.setProdukt(products);
                this.warehouses.set(this.idxWarehouse, magazynTemp);
                mDao.update(magazynTemp);

                ProduktDao pDao = new ProduktDao();
                ArrayList<Produkt> productsTmp = pDao.getAll();
                for (int i = 0; i < productsTmp.size(); i++) {
                    if (productsTmp.get(i).getMagazyn() != null) {
                        int temp = productsTmp.get(i).getMagazyn().size();
                        productsTmp.get(i).setLiczbaSztuk(temp);
                        pDao.update(productsTmp.get(i));
                    }
                }
                MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
                mf.updateAvailability();
            }
        }
    }

    private void refreshUpPanel() {
        cityField.setText(this.warehouses.get(this.idxWarehouse).getAdres().getMiasto());
        System.out.println(this.warehouses.get(this.idxWarehouse).getAdres().getMiasto());
        zipCodeField.setText(String.valueOf(this.warehouses.get(this.idxWarehouse).getAdres().getKodPocztowy()));
        streetField.setText(this.warehouses.get(this.idxWarehouse).getAdres().getUlica());
        buildNumField.setText(this.warehouses.get(this.idxWarehouse).getAdres().getNrBudynku());
        if (this.warehouses.get(this.idxWarehouse).getAdres().getNrLokalu() != null) {
            apartNumField.setText(String.valueOf(this.warehouses.get(this.idxWarehouse).getAdres().getNrLokalu()));
        } else {
            apartNumField.setText("");
        }
    }

    private void makeAddMagazine() {
        JTextField cityField = new JTextField();
        JTextField zipCodeField = new JTextField();
        JTextField streetField = new JTextField();
        JTextField buildNumField = new JTextField();
        JTextField apartNumField = new JTextField();
        JSpinner volumeSpinner = new JSpinner();

        JPanel address = new JPanel(new GridLayout(2, 6));
        JLabel city = new JLabel("Miasto:");
        city.setFont(font);
        city.setForeground(Color.BLACK);
        cityField = new JTextField();
        cityField.setFont(font);
        cityField.setText("");

        JLabel zipCode = new JLabel("Kod pocztowy:");
        zipCode.setFont(font);
        zipCode.setForeground(Color.BLACK);
        zipCodeField = new JTextField();
        zipCodeField.setFont(font);
        zipCodeField.setText("");

        JLabel street = new JLabel("Ulica:");
        street.setFont(font);
        street.setForeground(Color.BLACK);
        streetField = new JTextField();
        streetField.setFont(font);
        streetField.setText("");

        JLabel buildNum = new JLabel("Nr budynku:");
        buildNum.setFont(font);
        buildNum.setForeground(Color.BLACK);
        buildNumField = new JTextField();
        buildNumField.setFont(font);
        buildNumField.setText("");

        JLabel apartNum = new JLabel("Nr lokalu:");
        apartNum.setFont(font);
        apartNum.setForeground(Color.BLACK);
        address.add(apartNum);
        apartNumField = new JTextField();
        apartNumField.setFont(font);
        apartNumField.setText("");

        JLabel volume = new JLabel("Pojemno"+Letter.SII.getLetter()+Letter.CI.getLetter());
        volume.setFont(font);
        volume.setForeground(Color.BLACK);

        SpinnerModel model = new SpinnerNumberModel(1, 1, 1000000, 1);
        volumeSpinner = new JSpinner(model);
        volumeSpinner.setFont(font);

        address.add(city);
        address.add(zipCode);
        address.add(street);
        address.add(buildNum);
        address.add(apartNum);
        address.add(volume);

        address.add(cityField);
        address.add(zipCodeField);
        address.add(streetField);
        address.add(buildNumField);
        address.add(apartNumField);
        address.add(volumeSpinner);

        addMagazinePopUp(address, cityField, zipCodeField, streetField, buildNumField, apartNumField, volumeSpinner);
        this.refreshUpPanel();
    }

    private void addMagazinePopUp(JPanel address, JTextField cityField,
            JTextField zipCodeField,
            JTextField streetField,
            JTextField buildNumField,
            JTextField apartNumField,
            JSpinner volumeSpinner) {
        int result = JOptionPane.showConfirmDialog(null, address,
                "Dodaj magazyn", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if ("".equals(cityField.getText())) {
                this.emptyFieldPopUp("miasto");
                addMagazinePopUp(address, cityField, zipCodeField, streetField, buildNumField, apartNumField, volumeSpinner);
            } else if ("".equals(zipCodeField)) {
                this.emptyFieldPopUp("kod pocztowy");
                addMagazinePopUp(address, cityField, zipCodeField, streetField, buildNumField, apartNumField, volumeSpinner);
            } else if ("".equals(streetField)) {
                this.emptyFieldPopUp("ulica");
                addMagazinePopUp(address, cityField, zipCodeField, streetField, buildNumField, apartNumField, volumeSpinner);
            } else if ("".equals(buildNumField)) {
                this.emptyFieldPopUp("nr budynku");
                addMagazinePopUp(address, cityField, zipCodeField, streetField, buildNumField, apartNumField, volumeSpinner);
            } else if ("".equals(volumeSpinner)) {
                this.emptyFieldPopUp("pojemno"+Letter.SII.getLetter()+Letter.CI.getLetter());
                addMagazinePopUp(address, cityField, zipCodeField, streetField, buildNumField, apartNumField, volumeSpinner);
            } else {
                try {
                    MagazynDao dao = new MagazynDao();
                    AdresDao adresDao = new AdresDao();
                    Adres adres;
                    if (apartNumField.getText().equals("")) {
                        adres = new Adres(cityField.getText(), Integer.parseInt(zipCodeField.getText()), streetField.getText(),
                                buildNumField.getText());
                    } else {
                        adres = new Adres(cityField.getText(), Integer.parseInt(zipCodeField.getText()), streetField.getText(),
                                buildNumField.getText(), Integer.parseInt(apartNumField.getText()));
                    }
                    adresDao.addAdres(adres);
                    Magazyn mag = new Magazyn((Integer) volumeSpinner.getValue(), adres);
                    dao.addMagazyn(mag);

                    mag = dao.getMagazyn((Integer) volumeSpinner.getValue(), adres);

                    JButton temp = new JButton(mag.toString());
                    this.warehousesBut.add(temp);
                    this.warehouses.add(mag);
                    temp.setBackground(Color.BLACK);
                    temp.setForeground(Color.WHITE);
                    temp.addActionListener((ActionEvent e) -> {
                        if (e.getSource() == temp) {
                            for (JButton b : this.warehousesBut) {
                                b.setEnabled(true);
                            }
                            temp.setEnabled(false);
                            idxWarehouse = Integer.parseInt(temp.getText().substring(3)) - 1;
                            this.refreshUpPanel();
                            this.mainPanel.removeAll();
                            for (Produkt p : this.warehouses.get(idxWarehouse).getProdukt()) {
                                this.mainPanel.addProdukt(p, false);
                            }
                        }
                        refreshCategoryPanel();
                    });
                    temp.setPreferredSize(new Dimension(categoryPanel.getSize().width, (int) (2 * 40 * scale)));
                    temp.setFont(font);
                    this.categoryPanel.add(temp, BorderLayout.CENTER);
                    this.categoryPanel.validate();
                } catch (NumberFormatException e) {
                    System.out.println("Wprowadzono niew"+Letter.ELL.getLetter()+"a"+Letter.SII.getLetter()+"ciwe dane!");
                    this.wrongDataPopUp();
                    addMagazinePopUp(address, cityField, zipCodeField, streetField, buildNumField, apartNumField, volumeSpinner);
                }
            }
        }
    }

    private void emptyFieldPopUp(String data) {
        JOptionPane.showMessageDialog(null, "Pole " + data + " jest puste!", "", JOptionPane.ERROR_MESSAGE);
    }

    private void wrongDataPopUp() {
        JOptionPane.showMessageDialog(null, "Wprowadzono nieprawid"+Letter.ELL.getLetter()+"owy typ danych!", "", JOptionPane.ERROR_MESSAGE);
    }

    public void addWarehouseProduct(Produkt produkt) {
        Magazyn magazyn = this.warehouses.get(this.idxWarehouse);
        magazyn.getProdukt().add(produkt);
    }

    public void refreshWarehouses() {
        MagazynDao mDao = new MagazynDao();
        this.warehouses = mDao.getAll();
        this.mainPanel.removeAll();
        for (Produkt produkt : this.warehouses.get(this.idxWarehouse).getProdukt()) {
            this.mainPanel.addProdukt(produkt, false);
        }
    }

    public void refreshWarehouses(Magazyn m) {
        MagazynDao mDao = new MagazynDao();
        this.warehouses = mDao.getAll();
        this.mainPanel.removeAll();
        for (Produkt produkt : this.warehouses.get(this.idxWarehouse).getProdukt()) {
            this.mainPanel.addProdukt(produkt, false);
        }
    }
}
