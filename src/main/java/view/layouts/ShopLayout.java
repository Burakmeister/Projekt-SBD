package view.layouts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import map.Kategoria;
import map.Produkt;
import dao.KategoriaDao;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import view.Image;
import view.Letter;
import view.MainFrame;
import static view.layouts.ListPanel.scale;

public class ShopLayout extends JPanel implements ActionListener {

    public static final int borderPx = Toolkit.getDefaultToolkit().getScreenSize().width / 500;

    private final JScrollPane scroll;
    private ListPanel mainPanel;

    private final JPanel upPanel;
    private JButton logOut;
    private JButton cart;
    private JButton user;

    private final JPanel categoryPanel;
    private JButton addCategory;
    private JList<String> list;

    private JTextField nameField;
    private JTextArea descField;

    private boolean admin = false;

    public ShopLayout(boolean admin) {
        this.admin = admin;
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
        this.makeMainPanel();

        this.upPanel.setLayout(null);

        this.scroll = new JScrollPane(mainPanel);
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
        this.mainPanel.addProdukt(produkt);
    }

    private void makeCategoryPanel() {
        if (admin) {
            this.addCategory = new JButton("<html>" + "  Dodaj" + "<br>" + "kategori"+Letter.EHH.getLetter() + "</html>");
            addCategory.setPreferredSize(new Dimension(this.categoryPanel.getBounds().width, this.categoryPanel.getBounds().width/2));
            this.addCategory.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));
            this.categoryPanel.add(addCategory, BorderLayout.SOUTH);
            this.addCategory.addActionListener(this);
        } else {
            this.addCategory = new JButton("<html>" + "  Zastosuj" + "<br>" + "filtry" + "</html>");
            addCategory.setPreferredSize(new Dimension(this.categoryPanel.getBounds().width, this.categoryPanel.getBounds().width/2));
            this.addCategory.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));
            this.categoryPanel.add(addCategory, BorderLayout.SOUTH);
            this.addCategory.addActionListener(this);
        }
        this.categoryPanel.setBackground(Color.black);
        KategoriaDao dao = new KategoriaDao();
        List<Kategoria> kategorie = dao.getAll();
        DefaultListModel<String> model = new DefaultListModel<>();
        this.list = new JList<>(model);
        list.setForeground(Color.white);
        list.setBackground(Color.black);
        list.setSelectionBackground(Color.gray);
        list.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));
        for (Kategoria kategoria : kategorie) {
            model.addElement(kategoria.getNazwaKategorii());
        }
        this.categoryPanel.add(list, BorderLayout.CENTER);
        this.list.setSelectionBackground(new Color(188, 69, 69));
    }

    private void makeMainPanel() {
        this.mainPanel = new ListPanel(new Dimension(getPreferredSize().width - 3 * borderPx - this.getPreferredSize().width / 10,
                this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx), 20, this.admin);
        this.mainPanel.setLayout(null);
        this.mainPanel.setBackground(new Color(188, 69, 69));
    }

    private void makeUpPanel() {
        this.upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.LINE_AXIS));
        this.logOut = new JButton(Image.LOG_OUT.icon);
        this.logOut.setBackground(Color.black);
        this.logOut.setPreferredSize(new Dimension((int)this.upPanel.getBounds().getHeight(), (int)this.upPanel.getBounds().getHeight()));

        this.logOut.addActionListener(this);

        this.user = new JButton(Image.USER.icon);
        this.user.setBackground(Color.black);
        this.user.setPreferredSize(new Dimension((int)this.upPanel.getBounds().getHeight(), (int)this.upPanel.getBounds().getHeight()));
        this.user.addActionListener(this);

        this.cart = new JButton();
        if (admin) {
            this.cart.setIcon(Image.WAREHOUSE.icon);
        } else {
            this.cart.setIcon(Image.CART.icon);
        }
        this.cart.setPreferredSize(new Dimension((int)this.upPanel.getBounds().getHeight(), (int)this.upPanel.getBounds().getHeight()));
        this.cart.setBackground(Color.black);
        this.cart.addActionListener(this);

        this.upPanel.setBackground(Color.BLACK);
        this.upPanel.add(this.logOut);
        this.upPanel.add(
                Box.createRigidArea(
                        new Dimension((int)this.upPanel.getSize().getWidth()-3*(int)this.upPanel.getSize().getHeight(), 
                                (int)this.upPanel.getSize().getHeight())
                )
        );
        this.upPanel.add(this.cart);
        this.upPanel.add(this.user);
    }

    public void refreshProduct(Produkt produkt) {
        this.mainPanel.refreshProduct(produkt);
    }
    
    public void updateAvailability(){
        this.mainPanel.updateAvailability();
    }

    public void updateCategoryList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        this.list.setModel(model);
        KategoriaDao dao = new KategoriaDao();
        List<Kategoria> kategorie = dao.getAll();
        this.list = new JList<>(model);
        for (Kategoria kategoria : kategorie) {
            model.addElement(kategoria.getNazwaKategorii());
            System.out.println(kategoria.getNazwaKategorii());
        }
    }
        private void kategoriaPopPup(Kategoria junk){
        if (junk != null)
            {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,
                "Utworzono Kategori"+Letter.EHH.getLetter(),
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            }
            else
            {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null,
                "Nie utworzono kategorii",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.logOut) {
            this.logOutPopUp();
        }
        if (e.getSource() == this.cart) {
            if (!admin) {
                MainFrame mf = (MainFrame) SwingUtilities.getWindowAncestor(this);
                mf.showCart();
                mf.refreshCategoryPanel(false);
            } else {
                MainFrame mf = (MainFrame) SwingUtilities.getWindowAncestor(this);
                mf.showWarehouse();
                mf.refreshCategoryPanel(true);
            }
        }

        if (e.getSource() == this.user) {
            MainFrame mf = (MainFrame) SwingUtilities.getWindowAncestor(this);
            mf.showUserSettings();
        }
        if (e.getSource() == this.addCategory && admin) {
            addedCategoryPopUp();
        }
        if (e.getSource() == this.addCategory && admin == false){
            mainPanel.clearProductsPanel();
            int[] selectedIndices = list.getSelectedIndices();
            ArrayList<String> categories = new ArrayList<>();
            for (int i = 0; i < selectedIndices.length; i++) {
                categories.add(list.getModel().getElementAt(selectedIndices[i]));
            }
            mainPanel.setList(categories);
        }
    }

    private void logOutPopUp() {
        Object[] options = {"Tak",
            "Nie, wróć do sklepu"};
        int n = JOptionPane.showOptionDialog(null,
                "Czy na pewno chcesz się wylogowa"+Letter.CI.getLetter()+"?",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 0) {
            logOutMessagePopUp();
            System.out.println("Wylogowujesz si"+Letter.EHH.getLetter());
            MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            this.setVisible(false);
            mf.showLogInPanel();
        }
        if (n == 1) {
        }
    }

    private void logOutMessagePopUp() {
        JOptionPane.showMessageDialog(null, "Nast"+Letter.OHH.getLetter()+"pi"+Letter.ELL.getLetter()+"o wylogowanie", "", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addedCategoryPopUp() {
        this.nameField = new JTextField(15);
        this.descField = new JTextArea("Wpisz opis kategorii", 5, 5);
        this.descField.setWrapStyleWord(true);
        this.descField.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(descField);
        scrollPane.setPreferredSize(new Dimension(300, 125));
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Nazwa:"));
        myPanel.add(nameField);
        myPanel.add(Box.createVerticalStrut(15)); // a spacer
        myPanel.add(new JLabel("Opis:"));
        myPanel.add(scrollPane);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Podaj nazw"+Letter.OHH.getLetter()+"pi"+Letter.EHH.getLetter()+" i opis kategorii", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            KategoriaDao dao = new KategoriaDao();
            dao.addKategoria(nameField.getText(), descField.getText());
            Kategoria junk = dao.getKategoria(nameField.getText(), descField.getText());
            kategoriaPopPup(junk);
            updateCategoryList();
            this.list.repaint();
        }
    }
}
