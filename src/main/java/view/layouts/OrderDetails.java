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
import map.Zamowienie;
import view.Image;
import view.Letter;
import view.MainFrame;
import static view.layouts.ListPanel.scale;

public class OrderDetails extends JPanel implements ActionListener {

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

    private Zamowienie order;

    public OrderDetails(Zamowienie order) {
        this.order = order;
        this.setLayout(null);

        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(Color.WHITE);

        this.upPanel = new JPanel();
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
            sum.setText(String.valueOf(Math.round(sumFloat * 100) / 100.0) + "0z" + Letter.ELL.getLetter());
        } else {
            sum.setText(String.valueOf(Math.round(sumFloat * 100) / 100.0) + "z" + Letter.ELL.getLetter());
        }

        if (massFloat * 100 % 10 == 0) {
            mass.setText(String.valueOf(Math.round(massFloat * 100) / 100.0) + "0kg");
        } else {
            mass.setText(String.valueOf(Math.round(massFloat * 100) / 100.0) + "kg");
        }
    }

    public void makeCategoryPanel() {
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

        JLabel tmp1 = new JLabel("Warto" + Letter.SII.getLetter() + Letter.CI.getLetter() + ":");
        tmp1.setFont(font);
        tmp1.setForeground(Color.white);

        JLabel tmp2 = new JLabel("Liczno" + Letter.SII.getLetter() + Letter.CI.getLetter() + ":");
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
        this.refreshCategoryPanel();
    }

    private void makeMainPanel() {
        this.mainPanel = new Cart(new Dimension(getPreferredSize().width - 3 * borderPx - this.getPreferredSize().width / 10,
                this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx), 20, 2);
        this.mainPanel.setLayout(null);
        this.mainPanel.setBackground(new Color(188, 69, 69));

        this.mainPanel.setLayout(null);
        this.mainPanel.setBackground(new Color(188, 69, 69));
    }

    private void makeUpPanel() {
        Adres adres = this.order.getAdres();
        
        this.returnButton = new JButton(Image.RETURN.icon);
        this.returnButton.setBackground(Color.black);
        this.returnButton.setBounds(borderPx,
                borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx);
        this.returnButton.addActionListener(this);

        JPanel address = new JPanel(new GridLayout(2, 6));
        address.setBackground(Color.BLACK);
        address.setBounds(this.upPanel.getPreferredSize().height, 0,
                this.upPanel.getPreferredSize().width - 2 * this.upPanel.getPreferredSize().height, this.upPanel.getPreferredSize().height);

        JLabel city = new JLabel("Miasto:");
        city.setFont(font);
        city.setForeground(Color.WHITE);
        cityField = new JTextField();
        cityField.setFont(font);
        cityField.setText(adres.getMiasto());

        JLabel zipCode = new JLabel("Kod pocztowy:");
        zipCode.setFont(font);
        zipCode.setForeground(Color.WHITE);
        zipCodeField = new JTextField();
        zipCodeField.setFont(font);
        zipCodeField.setText(String.valueOf(adres.getKodPocztowy()));

        JLabel street = new JLabel("Ulica:");
        street.setFont(font);
        street.setForeground(Color.WHITE);
        streetField = new JTextField();
        streetField.setFont(font);
        streetField.setText(adres.getUlica());

        JLabel buildNum = new JLabel("Nr budynku:");
        buildNum.setFont(font);
        buildNum.setForeground(Color.WHITE);
        buildNumField = new JTextField();
        buildNumField.setFont(font);
        buildNumField.setText(adres.getNrBudynku());

        JLabel apartNum = new JLabel("Nr lokalu:");
        apartNum.setFont(font);
        apartNum.setForeground(Color.WHITE);
        apartNumField = new JTextField();
        apartNumField.setFont(font);


        address.add(city);
        address.add(zipCode);
        address.add(street);
        address.add(buildNum);
        address.add(apartNum);

        if (adres.getNrLokalu() != null) {
            apartNumField.setText(String.valueOf(adres.getNrLokalu()));
        }

        address.add(cityField);
        address.add(zipCodeField);
        address.add(streetField);
        address.add(buildNumField);
        address.add(apartNumField);

        this.upPanel.add(address);

        this.upPanel.setBackground(Color.BLACK);
        this.upPanel.add(this.returnButton);
        this.upPanel.add(this.saveButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.returnButton) {
            MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            mf.returnToUserFromOrderDetails();
        }
    }

}
