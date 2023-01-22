package view.layouts;

import dao.KategoriaDao;
import dao.MagazynDao;
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
import javax.swing.ImageIcon;
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
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import map.Kategoria;
import map.Magazyn;
import map.Producent;
import map.Produkt;
import view.MainFrame;
import static view.layouts.ListPanel.scale;

public class Details extends JPanel implements ActionListener {

    public static final int borderPx = Toolkit.getDefaultToolkit().getScreenSize().width / 500;
    private JButton returnButton;
    private JButton addToCartButton;
    private boolean isAdmin;
    private int imageWidth = 144;
    private int imageHeight = 144;
    private boolean fromCart;
    private Produkt produkt;
    private JList<String> list;
    private JScrollPane scrollPane;

    private Font font = new Font("Sans Serif", Font.BOLD, (int) (scale * 40));

    public Details() {

    }

    public Details(Produkt produkt, boolean admin, boolean fromCart) {
        this.isAdmin = admin;
        this.fromCart = fromCart;
        this.produkt = produkt;
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
        JLabel logo = new JLabel(view.Image.LOGO.icon);
        panel.add(logo);

        JTextArea nameLabel = new JTextArea(produkt.getNazwaProduktu());
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBackground(Color.black);
        nameLabel.setLineWrap(true);
        nameLabel.setWrapStyleWord(true);
//        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        nameLabel.setEditable(admin);
        panel.add(nameLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout());
        imagePanel.setBackground(new Color(128, 128, 128));
        GridBagConstraints gbc_imagePanel = new GridBagConstraints();
        gbc_imagePanel.insets = new Insets(0, 0, 5, 5);
        gbc_imagePanel.gridx = 0;
        gbc_imagePanel.gridy = 1;
        this.add(imagePanel, gbc_imagePanel);

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("src/main/products/" + produkt.getNazwaObrazka()));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(188, 69, 69));
        GridBagConstraints gbc_textPanel = new GridBagConstraints();
        gbc_textPanel.insets = new Insets(0, 0, 5, 0);
        gbc_textPanel.gridx = 1;
        gbc_textPanel.gridy = 1;
        this.add(textPanel, gbc_textPanel);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));

        JPanel textInputsPanel = new JPanel();
        textInputsPanel.setBackground(new Color(188, 69, 69));
        textInputsPanel.setLayout(new GridBagLayout());  //new GridLayout(4, 2, 0, 0)
        textPanel.add(textInputsPanel);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        if (isAdmin) {
            List<Magazyn> wybraneMagazyny = produkt.getMagazyn();
            int[] indices = new int[wybraneMagazyny.size()];
            int index = 0;
            for (Magazyn mag : wybraneMagazyny) {
                indices[index] = (mag.getIdMagazynu() - 1);
                index++;
            }

            MagazynDao dao = new MagazynDao();
            List<Magazyn> magazyny = dao.getAll();
            DefaultListModel<String> model = new DefaultListModel<>();
            this.list = new JList<>(model);
            list.setForeground(Color.white);
            list.setBackground(Color.black);
            for (Magazyn magazyn : magazyny) {
//                model.addElement("Magazyn" + String.valueOf(magazyn.getIdMagazynu()));
                model.addElement(String.valueOf(magazyn.getIdMagazynu()));
            }

            this.list.setSelectedIndices(indices);

            list.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 40)));
            JPanel magazins = new JPanel();
            magazins.setBackground(Color.black);
            magazins.add(list);

            c.fill = GridBagConstraints.BOTH;
            c.weighty = 1;
            c.weightx = 0.4;
            c.gridheight = 5;
            c.gridwidth = 1;
            c.gridx = 3;
            c.gridy = 0;
            textInputsPanel.add(magazins, c);
        } else {
            addToCartButton = new JButton(view.Image.CART_ADD.icon);
            addToCartButton.addActionListener(this);
            addToCartButton.setPreferredSize(new Dimension(imageWidth, imageHeight));
            addToCartButton.setBackground(Color.BLACK);
            textPanel.add(addToCartButton);
        }

        JLabel priceLabel = new JLabel("Cena: ");
        priceLabel.setFont(font);
        priceLabel.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        textInputsPanel.add(priceLabel, c);

        String pr = String.valueOf(produkt.getCena());
        if (produkt.getCena() * 100 % 10 == 0) {
            pr += "0";
        }
        JTextField price = new JTextField(pr);
        price.setFont(font);
        price.setEditable(isAdmin);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 0;
        textInputsPanel.add(price, c);

        JLabel producentLabel = new JLabel("Producent: ");
        producentLabel.setFont(font);
        producentLabel.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        textInputsPanel.add(producentLabel, c);

        ProducentDao pDao = new ProducentDao();
        ArrayList<Producent> prod = (ArrayList<Producent>) pDao.getAll();
        JComboBox producent = new JComboBox();
        for (Producent p : prod) {
            producent.addItem(p.toString());
        }
        producent.setFont(font);
        producent.setSelectedItem(produkt.getProducent().toString());
        producent.setEnabled(isAdmin);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        textInputsPanel.add(producent, c);

        JLabel categoryLabel = new JLabel("Kategoria: ");
        categoryLabel.setFont(font);
        categoryLabel.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        textInputsPanel.add(categoryLabel, c);

        KategoriaDao kDao = new KategoriaDao();
        ArrayList<Kategoria> kat = (ArrayList<Kategoria>) kDao.getAll();
        JComboBox category = new JComboBox();
        for (Kategoria k : kat) {
            category.addItem(k.toString());
        }
        category.setSelectedItem(produkt.getKategoria().toString());
        category.setFont(font);
        category.setEnabled(admin);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 2;
        textInputsPanel.add(category, c);

        JLabel MassLabel = new JLabel("Masa: ");
        MassLabel.setFont(font);
        MassLabel.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 3;
        textInputsPanel.add(MassLabel, c);

        JTextField mass = new JTextField(String.valueOf(produkt.getMasa()));
        mass.setFont(font);
        mass.setEditable(isAdmin);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 3;
        textInputsPanel.add(mass, c);

        JLabel pieces = new JLabel("Liczba sztuk: ");
        pieces.setFont(font);
        pieces.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 4;
        textInputsPanel.add(pieces, c);

        JTextField quantity = new JTextField(String.valueOf(produkt.getLiczbaSztuk()));
        quantity.setFont(font);
        quantity.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.2;
        c.weightx = 0.3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 4;
        textInputsPanel.add(quantity, c);

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

        JTextArea descTextArea = new JTextArea(produkt.getOpis());
        descTextArea.setForeground(Color.WHITE);
        descTextArea.setFont(font);
        descTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setBackground(Color.BLACK);
        descTextArea.setEditable(isAdmin);
        descPanel.add(descTextArea);
        if (admin) {
            JButton save = new JButton(view.Image.EDIT_SAVE.icon);
            save.setPreferredSize(new Dimension(imageWidth, imageHeight));
            save.setFont(font);
            save.setAlignmentX(Component.LEFT_ALIGNMENT);
            save.setBackground(Color.BLACK);
            returnPanel.add(save);

            JTextArea imageText = new JTextArea();
            imageText.setWrapStyleWord(true);
            imageText.setLineWrap(true);
            imageText.setPreferredSize(ListPanel.getImageDimension());
            imageText.setText(produkt.getNazwaObrazka());
            imageText.setFont(font);
            imagePanel.add(imageText);
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == save) {
                        Kategoria kategoria = produkt.getKategoria();
                        Producent prod = produkt.getProducent();
                        for (Kategoria k : kDao.getAll()) {
                            if (k.getNazwaKategorii().equals(category.getSelectedItem())) {
                                kategoria = k;
                                break;
                            }
                        }
                        for (Producent p : pDao.getAll()) {
                            if (p.getNazwaProducenta().equals(producent.getSelectedItem())) {
                                prod = p;
                                break;
                            }
                        }

                        // magazyny
                        int[] selectedIndices = list.getSelectedIndices();
                        MagazynDao daoM = new MagazynDao();
                        List<Magazyn> magazyny = daoM.getAll();
                        List<Magazyn> magazyny_produktu = new ArrayList<>();
                        int howMany = 0;
                        for (int i = 0; i < selectedIndices.length; i++) {
                            System.out.println(list.getModel().getElementAt(selectedIndices[i]));
                            magazyny_produktu.add(magazyny.get(selectedIndices[i]));
                            howMany++;
                        }

                        ProduktDao dao = new ProduktDao();

                        produkt.setCena(Float.parseFloat(price.getText()));
                        produkt.setKategoria(kategoria);
                        produkt.setNazwaObrazka(imageText.getText());
                        produkt.setNazwaProduktu(nameLabel.getText());
                        produkt.setOpis(descTextArea.getText());
                        produkt.setProducent(prod);
                        produkt.setMasa(Float.parseFloat(mass.getText()));
                        
                        List<Magazyn> magTemp = produkt.getMagazyn();
                        ArrayList<Magazyn> newMags = new ArrayList<>();
                        boolean isExist;
                        for(Magazyn magazyn: magazyny_produktu){
                            isExist=false;
                            for(Magazyn mCur: magTemp){
                                if(magazyn.equals(mCur)){
                                    newMags.add(magazyn);
                                    isExist = true;
                                }
                            }
                            if(!isExist){
                                newMags.add(magazyn);
                            }
                        }
                        
                        produkt.setMagazyn(newMags);
                        produkt.setLiczbaSztuk(produkt.getMagazyn().size());

                        MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(returnButton);
                        dao.update(produkt);
                        produktPopPup();
                        mf.refreshShop(produkt);
                        mf.updateAvailability();
                        mf.refreshWarehouse();
                    }
                }
            }
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.returnButton) {
            MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            if (!fromCart) {
                mf.returnToShop();
            } else {
                mf.returnToCart();
            }
        }
        if (e.getSource() == this.addToCartButton) {
            MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            mf.addProductToCart(produkt);
            this.addToCartPopUp();
        }

    }

    private void produktPopPup() {

        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(null,
                "Produkt zosta? zaktualizowany",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

    }

    private void addToCartPopUp() {
        JOptionPane.showMessageDialog(null, "Dodano produkt do koszyka", "", JOptionPane.INFORMATION_MESSAGE);
    }

}
