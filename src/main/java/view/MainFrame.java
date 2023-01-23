package view;

import dao.ProduktDao;
import view.SacPackage.PanelCover;
import view.SacPackage.PanelLoginAndRegister;
import view.layouts.Details;
import view.layouts.ShopLayout;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.JPanel;
// Brane z maven
import net.miginfocom.swing.MigLayout;  // aby Ĺ‚atwiej robiÄ‡ layouty
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.Animator;
// Brane z zewnÄ™trznych klas
import map.Produkt;
import map.Uzytkownik;
import map.Zamowienie;
import view.SacPackage.OrderPanel;
import view.layouts.CartLayout;
import view.layouts.NewProduct;
import view.layouts.OrderDetails;
import view.layouts.UserLayout;
import view.layouts.WarehouseLayout;

public class MainFrame extends javax.swing.JFrame {     // gĹ‚Ăłwny main na dole

    public static org.hibernate.Session session;

    private Uzytkownik user;
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));  // patrz fractionCover = Double... , po prostu inaczej nie dziaĹ‚aĹ‚o

    // Z ShopFrame
    private JPanel temp;

    private JPanel[] panels; //0-shopLayout  1-tymczasowe  2-koszyk   3-userConfig
    //

    private static boolean wasScalling = false;

    public MainFrame() {
        this.setTitle("Hardware Shop");
        this.setResizable(false);
        this.panels = new JPanel[4];
        initComponents();
        init();
    }

// Z ShopFrame
    public void refreshCategoryPanel(boolean fromWarehouse) {
        if (!fromWarehouse) {
            CartLayout tmp = (CartLayout) this.panels[2];
            tmp.refreshCategoryPanel();
        } else {
            WarehouseLayout tmp = (WarehouseLayout) this.panels[2];
            tmp.refreshCategoryPanel();
        }

    }

    public void refreshShop(Produkt produkt) {
        ShopLayout sl = (ShopLayout) this.panels[0];
        sl.refreshProduct(produkt);
    }

    public void updateAvailability() {
        ShopLayout sl = (ShopLayout) this.panels[0];
        sl.updateAvailability();
    }

    public void refreshWarehouse() {
        WarehouseLayout tmp = (WarehouseLayout) this.panels[2];
        tmp.refreshWarehouses();
    }

    public void showProductPanel(Produkt produkt) {
        this.panels[1] = new Details(produkt, user.isUprawnieniaAdministratora(), false);
        this.panels[0].setVisible(false);
        this.add(panels[1]);
    }

    public void showProductPanelCart(Produkt produkt) {
        if(this.panels[1] instanceof OrderDetails)
            this.temp = this.panels[1];
        if(this.panels[1]!=null)
            this.remove(this.panels[1]);
        this.panels[1] = new Details(produkt, user.isUprawnieniaAdministratora(), true);
        this.panels[2].setVisible(false);
        this.panels[3].setVisible(false);
        this.add(panels[1]);
    }

    public void showCompleteOrder() {
        this.panels[2].setVisible(false);
        this.panels[1] = new OrderPanel();
        this.add(panels[1]);
    }

    public void showUserSettings() {
        this.panels[0].setVisible(false);
        this.panels[3].setVisible(true);
        this.add(this.panels[3]);
        this.pack();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    public void returnToCart() {
        if (this.temp != null) {
            this.remove(this.panels[1]);
            this.panels[1] = temp;
            this.add(panels[1]);
            panels[1].repaint();
            temp=null;
        } else {
            this.remove(this.panels[1]);
            this.panels[2].setVisible(true);
        }
    }

    public void returnToShop() {
        this.remove(this.panels[1]);
        this.panels[0].setVisible(true);
    }

    public void returnToShopFromCart() {
        this.panels[2].setVisible(false);
        this.panels[0].setVisible(true);
    }

    public void returnToShopFromUser() {
        this.remove(this.panels[3]);
        this.panels[0].setVisible(true);
    }

    public void showLogInPanel() {
        this.getContentPane().removeAll();
        this.isLogin = false;
        initComponents();
        init();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);   // Fullscreen
    }

    public void showNewProduct() {
        this.panels[0].setVisible(false);
        this.panels[1] = new NewProduct();
        this.add(panels[1]);
    }

    public void showCart() {
        this.add(this.panels[2]);
        this.panels[0].setVisible(false);
        this.panels[2].setVisible(true);
    }

    public void showWarehouse() {
        this.add(this.panels[2]);
        this.panels[0].setVisible(false);
        this.panels[2].setVisible(true);
    }

    public void addProductToCart(Produkt produkt) {
        CartLayout panel = (CartLayout) this.panels[2];
        panel.addProduct(produkt);
    }

    public void addWarehouseProduct(Produkt produkt) {
        WarehouseLayout tmp = (WarehouseLayout) this.panels[2];
        tmp.addWarehouseProduct(produkt);
    }

    public void showOrderDetails(Zamowienie zamowienie) {
        HashMap<Produkt, Integer> products = new HashMap<>();
        List<Produkt> allProducts = zamowienie.getProdukt();
        allProducts.sort((Object o1, Object o2) -> {
            Produkt p1 = (Produkt) o1;
            Produkt p2 = (Produkt) o2;
            if (p1.getIdProduktu() < p2.getIdProduktu()) {
                return 1;
            } else if (p1.getIdProduktu() > p2.getIdProduktu()) {
                return -1;
            }
            return 0;
        });
        products.put(allProducts.get(0), 1);
        for (int i = 1; i < allProducts.size(); i++) {
            if (products.containsKey(allProducts.get(i))) {
                products.put(allProducts.get(i), products.get(allProducts.get(i)) + 1);
            }
        }
        this.panels[3].setVisible(false);
        this.panels[1] = new OrderDetails(zamowienie);
        add(this.panels[1]);
        OrderDetails cl = (OrderDetails) this.panels[1];
        this.panels[1].setVisible(true);
        for (Produkt p : products.keySet()) {
            for (Integer i : products.values()) {
                for (int j = 0; j < i; j++) {
                    cl.addProduct(p);
                }
            }
        }
    }

    public List<Produkt> getProductsFromCart() {
        List<Produkt> ret = new ArrayList<>();
        CartLayout cl = (CartLayout) this.panels[2];
        List<Produkt> products = cl.getProducts();
        List<Integer> ints = cl.getNumOfProducts();
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < ints.get(i); j++) {
                ret.add(products.get(i));
            }
        }
        return ret;
    }

    public void loadPanels(Uzytkownik user) {
        this.user = user;
        this.panels[0] = new ShopLayout(user.isUprawnieniaAdministratora());
        loadProducts();
        if (user.isUprawnieniaAdministratora()) {
            this.panels[2] = new WarehouseLayout();
        } else {
            this.panels[2] = new CartLayout();
        }
        this.panels[3] = new UserLayout(user, this.user.isUprawnieniaAdministratora());

        this.add(this.panels[2]);
        this.add(this.panels[3]);
        this.add(this.panels[0]);

        this.panels[2].setVisible(false);
        this.panels[3].setVisible(false);
        this.panels[0].setVisible(true);
    }

    private void loadProducts() {
        ProduktDao dao = new ProduktDao();
        ArrayList<Produkt> produkty = (ArrayList<Produkt>) dao.getAll();
        for (Produkt produkt : produkty) {
            if (user.isUprawnieniaAdministratora()) {
                this.addProduct(produkt);
            } else if (produkt.getLiczbaSztuk() > 0) {
                this.addProduct(produkt);
            }
        }
    }

    public void addProduct(Produkt produkt) {
        ShopLayout sl = (ShopLayout) this.panels[0];
        sl.addProduct(produkt);
    }

    private void init() {
        layout = new MigLayout("fill, insets 0");    // insets 0 aby nie byĹ‚o dziwnych odstÄ™pĂłw np. od panel covera, moĹĽna teĹĽ po insets dopisaÄ‡ ",debug" aby zobaczyÄ‡ krawÄ™dzie layoutu
        cover = new PanelCover();
        loginAndRegister = new PanelLoginAndRegister();
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = coverSize;

                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }

                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }

                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }

                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                Background.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
            }
        };

        final Animator animator = new Animator(800, target);   // java kazaĹ‚o mi tu zrobiÄ‡ final bo jest gĹ‚upia - ale moĹĽe tak nie powinno byÄ‡??
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);  // aby byĹ‚a pĹ‚ynna animacja i nie dostaÄ‡ oczoplÄ…su
        Background.setLayout(layout);
        Background.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        Background.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%"); // 1al - 100%
        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {     // jeĹ›li nie trwa aktualnie ĹĽadna animacja, rozpocznij nowÄ…
                    animator.start();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Background.setBackground(new java.awt.Color(255, 255, 255));
        Background.setOpaque(true);

        javax.swing.GroupLayout BackgroundLayout = new javax.swing.GroupLayout(Background);
        Background.setLayout(BackgroundLayout);
        BackgroundLayout.setHorizontalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        BackgroundLayout.setVerticalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        try {
//	    org.hibernate.Transaction chuj = session.beginTransaction();
        //chuj.begin();
//	    Kategoria kategoria = new Kategoria("Pawlak1", "karz2el");
//            session.save(kategoria);
//            Kategoria kategoria2 = new Kategoria("dorosz", "karz2e213l");
//            session.save(kategoria2);
//            Kategoria kategoria3 = new Kategoria("denert", "kaczka");
//            session.save(kategoria3);
//            Producent producent = new Producent("TAK", " Kraj", "ptok");
//            session.save(producent);
//            Produkt produkt = new Produkt("dupa", 55,"pomocy",5, kategoria, producent, 15, "kwiat1");
//            session.save(produkt);
//            Produkt produkt2 = new Produkt("pupa", 42,"pomocy",5, kategoria2, producent, 15, "kwiat1");
//            session.save(produkt2);
//            Produkt produkt3 = new Produkt("grzes", 15,"pomocy",5, kategoria2, producent, 15, "kwiat1");
//            session.save(produkt3);
//            System.out.print("UDALO SIE HIHIHI ");
//          System.out.print(" " + kategoria.getIdKategoria());
//          chuj.commit();

//	} catch (Exception e) {
//	    System.out.print("NIE WIEM CO SIE DZIEJE ALE BUJA ");
//	    e.printStackTrace();
//	}
        try {

            // HIBERNATE
//            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//	    Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
//	    org.hibernate.SessionFactory factory = meta.getSessionFactoryBuilder().build();
//	    MainFrame.session = factory.openSession();
            // HIBERNATE
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame mainframe = new MainFrame();
                mainframe.setVisible(true);
                mainframe.setExtendedState(mainframe.getExtendedState() | JFrame.MAXIMIZED_BOTH);   // Fullscreen
                mainframe.setIconImage(Image.LOGO.icon.getImage());
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane Background;
    // End of variables declaration//GEN-END:variables

    public static void wasScalling() {
        MainFrame.wasScalling = true;
    }

    public static boolean itWasScalling() {
        return MainFrame.wasScalling;
    }

    public void returnToUserFromOrderDetails() {
        this.remove(this.panels[1]);
        this.panels[3].setVisible(true);
    }
}
