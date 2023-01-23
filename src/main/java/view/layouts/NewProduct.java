package view.layouts;

import dao.KategoriaDao;
import dao.ProducentDao;
import dao.ProduktDao;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import map.Kategoria;
import map.Producent;
import map.Produkt;
import view.MainFrame;
import static view.layouts.ListPanel.scale;

public class NewProduct extends JPanel implements ActionListener {

    public static final int borderPx = Toolkit.getDefaultToolkit().getScreenSize().width / 500;
    private JButton returnButton;
    private int imageWidth = 100;
    private int imageHeight = 100;

    private Font font = new Font("Sans Serif", Font.BOLD, (int) (scale * 40));

    public NewProduct() {
        setForeground(Color.WHITE);
        this.setBackground(new Color(188, 69, 69));
        this.setBorder(new EmptyBorder(0, 0, 0, 0));

        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{500, 500, 0};
        gbl_contentPane.rowHeights = new int[]{119, 292, 295, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.5, 1.0, 1.0, Double.MIN_VALUE};
        this.setLayout(gbl_contentPane);

        JPanel panel = new JPanel();
        panel.setForeground(Color.WHITE);
        panel.setBackground(Color.BLACK);
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridwidth = 2;
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        add(panel, gbc_panel);
        panel.setLayout(new GridLayout(1, 3, 0, 0));

        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new BoxLayout(returnPanel, BoxLayout.Y_AXIS));
        returnPanel.setBackground(Color.BLACK);
        panel.add(returnPanel);

        returnButton = new JButton(view.Image.RETURN.icon);
        returnButton.setPreferredSize(new Dimension(imageWidth, imageHeight));
        returnButton.addActionListener(this);
        returnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        returnButton.setBackground(Color.BLACK);
        returnPanel.add(returnButton);

        JButton save = new JButton(view.Image.SAVE.icon);
        save.setPreferredSize(new Dimension(imageWidth, imageHeight));
        save.setFont(font);
        save.setAlignmentX(Component.LEFT_ALIGNMENT);
        save.setBackground(Color.BLACK);
        returnPanel.add(save);

        JLabel logo = new JLabel(view.Image.LOGO.icon);
        panel.add(logo);

        JTextField nameLabel = new JTextField("Wprowadź nazwę produktu");
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        nameLabel.setBackground(Color.black);
        panel.add(nameLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(128, 128, 128));
        GridBagConstraints gbc_imagePanel = new GridBagConstraints();
        gbc_imagePanel.insets = new Insets(0, 0, 5, 5);
        gbc_imagePanel.gridx = 0;
        gbc_imagePanel.gridy = 1;
        this.add(imagePanel, gbc_imagePanel);

        JTextField imageLabel = new JTextField();
        imageLabel.setText("Wprowadź nazwę obrazka");
        imageLabel.setFont(font);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(188, 69, 69));
        GridBagConstraints gbc_textPanel = new GridBagConstraints();
        gbc_textPanel.insets = new Insets(0, 0, 5, 0);
        gbc_textPanel.gridx = 1;
        gbc_textPanel.gridy = 1;
        this.add(textPanel, gbc_textPanel);
        textPanel.setLayout(new FlowLayout());

        JPanel textInputsPanel = new JPanel();
        textInputsPanel.setBackground(new Color(188, 69, 69));
        textInputsPanel.setLayout(new GridLayout(4, 3, 0, 0));  //new GridLayout(4, 2, 0, 0)
        textPanel.add(textInputsPanel);

        JLabel priceLabel = new JLabel("Cena: ");
        priceLabel.setFont(font);
        priceLabel.setForeground(Color.WHITE);
        textInputsPanel.add(priceLabel);

        JTextField price = new JTextField("Wprowadź cenę");
        price.setFont(font);
        textInputsPanel.add(price);

        JLabel producentLabel = new JLabel("Producent: ");
        producentLabel.setFont(font);
        producentLabel.setForeground(Color.WHITE);
        textInputsPanel.add(producentLabel);

        ProducentDao pDao = new ProducentDao();
        ArrayList<Producent> prod = (ArrayList<Producent>) pDao.getAll();
        JComboBox producent = new JComboBox();
        for (Producent p : prod) {
            producent.addItem(p);
        }
        producent.setFont(font);
        textInputsPanel.add(producent);

        JLabel categoryLabel = new JLabel("Kategoria: ");
        categoryLabel.setFont(font);
        categoryLabel.setForeground(Color.WHITE);
        textInputsPanel.add(categoryLabel);

        KategoriaDao kDao = new KategoriaDao();
        ArrayList<Kategoria> kat = (ArrayList<Kategoria>) kDao.getAll();
        JComboBox category = new JComboBox();
        for (Kategoria k : kat) {
            category.addItem(k);
        }
        category.setFont(font);
        textInputsPanel.add(category);
        JPanel descPanel = new JPanel();
        descPanel.setForeground(Color.WHITE);
        descPanel.setBackground(new Color(188, 69, 69));
        GridBagConstraints gbc_descPanel = new GridBagConstraints();
        gbc_descPanel.anchor = GridBagConstraints.PAGE_START;
        gbc_descPanel.gridwidth = 2;
        gbc_descPanel.fill = GridBagConstraints.BOTH;
        gbc_descPanel.gridx = 0;
        gbc_descPanel.gridy = 2;
        this.add(descPanel, gbc_descPanel);
        descPanel.setLayout(new BoxLayout(descPanel, BoxLayout.Y_AXIS));

        JLabel descLabel = new JLabel("Opis");
        descLabel.setForeground(Color.WHITE);
        descLabel.setBackground(new Color(188, 69, 69));
        descLabel.setFont(font);
        descLabel.setHorizontalAlignment(SwingConstants.LEFT);
        descPanel.add(descLabel);

        JTextArea descTextArea = new JTextArea("Wprowadź opis produktu");
        descTextArea.setForeground(Color.WHITE);
        descTextArea.setFont(font);
        descTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setBackground(Color.BLACK);
//                descTextArea.setEditable(isAdmin);
        descPanel.add(descTextArea);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == save) {
                    boolean toAdd = addProductToShop();
                    if (toAdd) {
                        Kategoria kategoria = (Kategoria) category.getSelectedItem();
                        Producent prod = (Producent) producent.getSelectedItem();
                        ProduktDao dao = new ProduktDao();
                        Produkt productToAdd = new Produkt(nameLabel.getText(), Float.parseFloat(price.getText()),
                                descTextArea.getText(), 0,
                                kategoria,
                                prod,
                                0, imageLabel.getText());
                        dao.create(productToAdd);
                        addProductToShopPopUp();
                        MainFrame frame = (MainFrame) (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent)e.getSource());
                        frame.addProduct(productToAdd);
                    }

                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.returnButton) {
            MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            mf.returnToShop();
        }

    }

    private boolean addProductToShop() {
        Object[] options = {"Tak",
            "Anuluj"};
        int n = JOptionPane.showOptionDialog(null,
                "Czy na pewno chcesz dodać produkt do sklepu?",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 0) {
            return true;
        }
        if (n == 1) {
            return false;
        }
        return false;
    }

//    private Kategoria addCategory() {
//        JPanel panel = new JPanel(new GridLayout(2, 2, 1, 1));
//        JTextField opis = new JTextField("Opis");
//        JTextField nazwa = new JTextField("Nazwa");
//        JButton add = new JButton("Dodaj");
//        JButton cancel = new JButton("Anuluj");
//        panel.add(nazwa);
//        panel.add(opis);
//        panel.add(cancel);
//        panel.add(add);
//        Object[] options = {"Tak",
//            "Anuluj"};
//        int n = JOptionPane.showOptionDialog(null,
//                "Dodaj kategorię",
//                "",
//                JOptionPane.YES_NO_CANCEL_OPTION,
//                JOptionPane.QUESTION_MESSAGE,
//                view.Image.LOGO.icon,
//                panel;
//        if (n == 0) {
//            addProductToShopPopUp();
////            zrób coś
//        }
//        if (n == 1) {
////            zrób coś
//        }
//        return null;
//    }
    private Producent addProducent() {
        Object[] options = {"Tak",
            "Anuluj"};
        int n = JOptionPane.showOptionDialog(null,
                "Czy na pewno chcesz dodać produkt do sklepu?",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 0) {
            addProductToShopPopUp();
//            zrób coś
        }
        if (n == 1) {
//            zrób coś
        }
        return null;
    }

    private void addProductToShopPopUp() {
        JOptionPane.showMessageDialog(null, "Dodano produkt do sklepu", "", JOptionPane.INFORMATION_MESSAGE);
    }

    private void categoryNeeded() {
        JOptionPane.showMessageDialog(null, "Podana kategoria nie istnieje!", "", JOptionPane.WARNING_MESSAGE);
    }

    private void producentNeeded() {
        JOptionPane.showMessageDialog(null, "Podany producent nie istnieje!", "", JOptionPane.WARNING_MESSAGE);
    }
}
