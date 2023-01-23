/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.layouts;

import dao.KategoriaDao;
import dao.ZamowienieDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import map.Kategoria;
import map.Uzytkownik;
import map.Zamowienie;
import view.Image;
import view.Letter;
import view.MainFrame;
import static view.layouts.ListPanel.scale;
import static view.layouts.ShopLayout.borderPx;
import static view.layouts.WarehouseLayout.borderPx;

/**
 *
 * @author grzec
 */
public class UserLayout extends JPanel implements ActionListener {

    public static final int borderPx = Toolkit.getDefaultToolkit().getScreenSize().width / 500;
    private Font font = new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (40 * scale));

//    private JPanel scroll;
    private JPanel mainPanel;

    private JPanel upPanel;

    private JPanel categoryPanel;
    private JButton showDetails;
    private JButton saveButton;
    private JList<String> list;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField nickField;
    private JTextField passField;
    private JTextField dateField;

    private Uzytkownik user;
    private boolean admin;

//    private JPanel upPanel;
//    private JPanel categoryPanel = new JPanel();
    private JButton goBackButton;

    public UserLayout(Uzytkownik user, boolean admin) {
        this.admin = admin;
        this.user = user;
        this.setLayout(null);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(Color.WHITE);
        this.upPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(Image.LOGO.icon.getImage(),
                        Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 7 * borderPx,
                        -borderPx,
                        null
                );
            }
        };
        this.categoryPanel = new JPanel();
        BorderLayout layout = new BorderLayout();

        categoryPanel.setLayout(layout);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridBagLayout());
        this.makeMainPanel();

        this.upPanel.setLayout(null);

        this.mainPanel.setVisible(true);
//        this.scroll.getVerticalScrollBar().setUnitIncrement(16);

        this.upPanel.setBounds(borderPx, borderPx, this.getPreferredSize().width - 2 * borderPx, this.getPreferredSize().height / 8);
        this.categoryPanel.setBounds(borderPx, this.getPreferredSize().height / 8 + 2 * ShopLayout.borderPx,
                this.getPreferredSize().width / 10, this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx);
        this.mainPanel.setBounds(this.getPreferredSize().width / 10 + 2 * borderPx, this.getPreferredSize().height / 8 + 2 * borderPx,
                getPreferredSize().width - 3 * borderPx - this.getPreferredSize().width / 10, this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx);

        makeCategoryPanel();
        makeUpPanel();

        this.add(upPanel);
        this.add(categoryPanel);
        this.add(mainPanel);
    }

    private void makeCategoryPanel() {

        if (!admin) {
           JLabel ordersLabel = new JLabel("Zam" + Letter.UU.getLetter() + "wienia");
        ordersLabel.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));
        ordersLabel.setForeground(Color.WHITE);

        this.categoryPanel.add(ordersLabel, BorderLayout.NORTH);

        this.showDetails = new JButton("<html>" + "  Sprawd" + Letter.ZIET.getLetter() + "<br>" + "szczeg" + Letter.UU.getLetter() + Letter.ELL.getLetter() + "y" + "</html>");
        showDetails.setPreferredSize(new Dimension(this.categoryPanel.getBounds().width, this.categoryPanel.getBounds().width / 2));
        this.showDetails.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));

        this.categoryPanel.add(showDetails, BorderLayout.SOUTH);
        this.showDetails.addActionListener(this);

        
        ZamowienieDao dao = new ZamowienieDao();
        List<Zamowienie> zamowienia = dao.getUserOrders(user);
        DefaultListModel<String> model = new DefaultListModel<>();
        this.list = new JList<>(model);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        list.setForeground(Color.white);
        list.setBackground(Color.black);
        list.setSelectionBackground(Color.gray);
        list.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));
        for (Zamowienie zamowienie : zamowienia) {
            String str = "nr." + String.valueOf(zamowienie.getIdZamowienia()) + " " + zamowienie.getDataGodzina();
            model.addElement(str);
        }
        this.categoryPanel.add(list, BorderLayout.CENTER);
        this.list.setSelectionBackground(new Color(188, 69, 69)); 
        }
        this.categoryPanel.setBackground(Color.black);
    }

    private void makeMainPanel() {
//        this.mainPanel = new ListPanel(new Dimension(getPreferredSize().width - 3 * borderPx - this.getPreferredSize().width / 10,
//                this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx), 20, this.admin);
//        this.mainPanel.setLayout(null);

           GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 5, 20, 5);
           c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 0;
        this.mainPanel.setBackground(new Color(188, 69, 69));
//        JPanel namePanel = new JPanel();
//        namePanel.setBackground(new Color(188, 69, 69));
        JLabel nameLabel = new JLabel("Imie:");
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.WHITE);
        this.mainPanel.add(nameLabel, c);
//        namePanel.add(nameLabel);
        this.nameField = new JTextField(user.getImie());
        nameField.setFont(font);
          c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 0;
     this.mainPanel.add(nameField,c);

//        JPanel surnamePanel = new JPanel();
//        surnamePanel.setBackground(new Color(188, 69, 69));
        JLabel surnameLabel = new JLabel("Nazwisko:");
        surnameLabel.setFont(font);
        surnameLabel.setForeground(Color.WHITE);
            c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 1;
        this.mainPanel.add(surnameLabel, c);
//        surnamePanel.add(surnameLabel);
        this.surnameField = new JTextField(user.getNazwisko());
        surnameField.setFont(font);
            c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 1;
         this.mainPanel.add(surnameField, c);
//
//        JPanel nickPanel = new JPanel();
//        nickPanel.setBackground(new Color(188, 69, 69));
        JLabel nickLabel = new JLabel("Nick:");
        nickLabel.setFont(font);
        nickLabel.setForeground(Color.WHITE);
           c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 2;
        this.mainPanel.add(nickLabel, c);
//        nickPanel.add(nickLabel);
        this.nickField = new JTextField(user.getNickname());
        nickField.setFont(font);
           c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 2;
       this.mainPanel.add(nickField, c);
//
//        JPanel passPanel = new JPanel();
//        passPanel.setBackground(new Color(188, 69, 69));
        JLabel passLabel = new JLabel("Has" + Letter.ELL.getLetter() + "o:");
        passLabel.setFont(font);
        passLabel.setForeground(Color.WHITE);
          c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 3;
        this.mainPanel.add(passLabel, c);
//        passPanel.add(passLabel);
        this.passField = new JTextField(user.getPassword());
        passField.setFont(font);
          c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 3;
        this.mainPanel.add(passField, c);
//
//        JPanel dataPanel = new JPanel();
//        dataPanel.setBackground(new Color(188, 69, 69));
        JLabel dataLabel = new JLabel("Data za" + Letter.ELL.getLetter() + "o" + Letter.ZY.getLetter() + "enia konta:");
        dataLabel.setFont(font);
        dataLabel.setForeground(Color.WHITE);
          c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 4;
        this.mainPanel.add(dataLabel, c);
//        dataPanel.add(dataLabel);
        this.dateField = new JTextField(String.valueOf(user.getDataZalozeniaKonta()));
        dateField.setFont(font);
          c.fill = GridBagConstraints.BOTH;
            c.weighty = 0.2;
            c.weightx = 0.25;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 4;
        this.mainPanel.add(dateField, c);

    }

    private void makeUpPanel() {
        this.upPanel.setLayout(new BorderLayout());
        this.goBackButton = new JButton(Image.RETURN.icon);
        this.goBackButton.setBackground(Color.black);
        this.goBackButton.setPreferredSize(new Dimension((int) this.upPanel.getBounds().getHeight(), (int) this.upPanel.getBounds().getHeight()));

        this.goBackButton.addActionListener(this);
        this.upPanel.setBackground(Color.BLACK);
        this.upPanel.add(this.goBackButton, BorderLayout.WEST);
        this.upPanel.add(
                Box.createRigidArea(
                        new Dimension((int) this.upPanel.getSize().getWidth() - 3 * (int) this.upPanel.getSize().getHeight(),
                                (int) this.upPanel.getSize().getHeight())
                )
        );

        this.saveButton = new JButton(Image.EDIT_SAVE.icon);
        this.saveButton.setBackground(Color.black);
        this.saveButton.setBounds(
                this.upPanel.getPreferredSize().width - this.upPanel.getPreferredSize().height + ShopLayout.borderPx,
                borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx);
        this.saveButton.addActionListener(this);

        this.upPanel.add(this.saveButton, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.goBackButton) {
            MainFrame mf = (MainFrame) SwingUtilities.getWindowAncestor(this);
            mf.returnToShopFromUser();
        }
        if (e.getSource() == this.showDetails) {

            ZamowienieDao dao = new ZamowienieDao();
//            Zamowienie zamowienie = dao.getZamowienie(());
//            String str = list.getSelectedIndex();
              int[] selectedIndices = list.getSelectedIndices();
//              
            List<Zamowienie> zamowienia = dao.getUserOrders(user);
            
            Zamowienie zamowienie = zamowienia.get(selectedIndices[0]);
//            
//              System.out.println(list.getSelectedIndex());
//              
//                System.out.println(zamowienia.size());
//                
//            System.out.println(zamowienia.get(selectedIndices[0]));
            MainFrame mf = (MainFrame) SwingUtilities.getWindowAncestor(this);
            mf.showOrderDetails(zamowienie);
            System.out.println(list.getSelectedIndex());
            System.out.println(zamowienia.get(list.getSelectedIndex()));
        }
    }

}
