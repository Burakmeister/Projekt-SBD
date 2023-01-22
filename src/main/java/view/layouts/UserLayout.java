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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

/**
 *
 * @author grzec
 */
public class UserLayout extends JPanel implements ActionListener {
    
       public static final int borderPx = Toolkit.getDefaultToolkit().getScreenSize().width / 500;

//    private JPanel scroll;
    private JPanel mainPanel;

    private JPanel upPanel;

    private JPanel categoryPanel;
    private JButton showDetails;
    private JList<String> list;

    private JTextField nameField;
    private JTextArea descField;

    private boolean admin = true;
    
//    private JPanel upPanel;
//    private JPanel categoryPanel = new JPanel();
    private JButton goBackButton;
     
    public UserLayout(Uzytkownik user) {
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
        
        
        this.mainPanel = new JPanel();
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

        JLabel ordersLabel = new JLabel("Zam" + Letter.UU.getLetter() + "wienia");
        ordersLabel.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));

        this.categoryPanel.add(ordersLabel, BorderLayout.NORTH);

        this.showDetails = new JButton("<html>" + "  Sprawd" + Letter.ZIET.getLetter() + "<br>" + "szczeg" + Letter.UU.getLetter() + Letter.ELL.getLetter() + "y" + "</html>");
        showDetails.setPreferredSize(new Dimension(this.categoryPanel.getBounds().width, this.categoryPanel.getBounds().width / 2));
        this.showDetails.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));
        this.categoryPanel.add(showDetails, BorderLayout.SOUTH);
        this.showDetails.addActionListener(this);

        this.categoryPanel.setBackground(Color.black);
        ZamowienieDao dao = new ZamowienieDao();
        List<Zamowienie> zamowienia = dao.getAll();
        DefaultListModel<String> model = new DefaultListModel<>();
        this.list = new JList<>(model);
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        list.setForeground(Color.white);
        list.setBackground(Color.black);
        list.setSelectionBackground(Color.gray);
        list.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 30)));
        for (Zamowienie zamowienie : zamowienia) {
            model.addElement(String.valueOf(zamowienie.getIdZamowienia()));
        }
        this.categoryPanel.add(list, BorderLayout.CENTER);
        this.list.setSelectionBackground(new Color(188, 69, 69));
    }

    private void makeMainPanel() {
//        this.mainPanel = new ListPanel(new Dimension(getPreferredSize().width - 3 * borderPx - this.getPreferredSize().width / 10,
//                this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx), 20, this.admin);
//        this.mainPanel.setLayout(null);
        this.mainPanel.setBackground(new Color(188, 69, 69));
    }

    private void makeUpPanel() {
        this.upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.LINE_AXIS));
        this.goBackButton = new JButton(Image.LOG_OUT.icon);
        this.goBackButton.setBackground(Color.black);
        this.goBackButton.setPreferredSize(new Dimension((int)this.upPanel.getBounds().getHeight(), (int)this.upPanel.getBounds().getHeight()));

        this.goBackButton.addActionListener(this);
        this.upPanel.setBackground(Color.BLACK);
        this.upPanel.add(this.goBackButton);
        this.upPanel.add(
                Box.createRigidArea(
                        new Dimension((int)this.upPanel.getSize().getWidth()-3*(int)this.upPanel.getSize().getHeight(), 
                                (int)this.upPanel.getSize().getHeight())
                )
        );
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.goBackButton) {
            MainFrame mf = (MainFrame) SwingUtilities.getWindowAncestor(this);
            mf.returnToShopFromUser();
        }
        if (e.getSource() == this.showDetails) {

            ZamowienieDao dao = new ZamowienieDao();
            List<Zamowienie> zamowienia = dao.getAll();
            MainFrame mf = (MainFrame) SwingUtilities.getWindowAncestor(this);
            mf.showOrderDetails(zamowienia.get(list.getSelectedIndex()));
            System.out.println(list.getSelectedIndex());
             System.out.println(zamowienia.get(list.getSelectedIndex()));
        }
    }

}
