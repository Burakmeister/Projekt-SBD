package view.layouts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.View;
import map.Produkt;
import view.Image;
import view.MainFrame;
import static view.layouts.ListPanel.scale;

public class CartLayout extends JPanel implements ActionListener {

    public static final int borderPx = Toolkit.getDefaultToolkit().getScreenSize().width / 500;

    private JScrollPane scroll;
    private Cart mainPanel;

    private JPanel upPanel;
    private JButton returnButton;
    private JLabel logo;
    private JButton complete;

    private JPanel categoryPanel = new JPanel();
    JLabel quantity;
    JLabel sum;
    JLabel mass;

    public CartLayout() {
        this.setLayout(null);

        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(Color.WHITE);

        this.upPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(Image.LOGO.icon.getImage(), Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 7 * borderPx, -borderPx, null);
            }
        };
        this.categoryPanel = new JPanel();

//		this.categoryPanel.repaint();
        this.makeMainPanel();

        this.upPanel.setLayout(null);
        this.categoryPanel.setLayout(new FlowLayout(View.Y_AXIS));

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

//    public void removeProduct(Produkt produkt) {
//        this.mainPanel.removeProdukt(produkt);
//    }
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
            sum.setText(String.valueOf(Math.round(sumFloat*100)/100.0) + "0zł");
        } else {
            sum.setText(String.valueOf(Math.round(sumFloat * 100) / 100.0) + "zł");
        }

        if (massFloat * 100 % 10 == 0) {
            mass.setText(String.valueOf(Math.round(massFloat*100)/100.0)+"0kg");
        }else {
            mass.setText(String.valueOf(Math.round(massFloat * 100) / 100.0) + "kg");
        }
    }
    

    public void makeCategoryPanel() {
        this.categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Font font = new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (40 * scale));
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

        JLabel tmp1 = new JLabel("Wartość:");
        tmp1.setFont(font);
        tmp1.setForeground(Color.white);

        JLabel tmp2 = new JLabel("Liczność:");
        tmp2.setFont(font);
        tmp2.setForeground(Color.white);

        JLabel tmp3 = new JLabel("Masa:");
        tmp3.setFont(font);
        tmp3.setForeground(Color.white);

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
                this.getPreferredSize().height - this.getPreferredSize().height / 8 - 8 * borderPx), 20, false);
        this.mainPanel.setLayout(null);
        this.mainPanel.setBackground(new Color(188, 69, 69));

        this.mainPanel.setLayout(null);
        this.mainPanel.setBackground(new Color(188, 69, 69));
    }

    private void makeUpPanel() {
        this.returnButton = new JButton(Image.RETURN.icon);
        this.returnButton.setBackground(Color.black);
        this.returnButton.setBounds(borderPx, borderPx, this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx, this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx);
        this.returnButton.addActionListener(this);

        this.complete = new JButton(Image.CART.icon);
        this.complete.setBounds(this.upPanel.getPreferredSize().width - this.upPanel.getPreferredSize().height + this.borderPx, borderPx,
                this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx, this.upPanel.getPreferredSize().height - 2 * ShopLayout.borderPx);
        this.complete.setBackground(Color.black);
        this.complete.addActionListener(this);
        this.upPanel.add(this.complete);

        this.upPanel.setBackground(Color.BLACK);
        this.upPanel.add(this.returnButton);
    }
    
    public List<Produkt> getProducts() {
        return this.mainPanel.getProducts();
    }

    public List<Integer> getNumOfProducts() {
        return this.mainPanel.getNumOfProducts();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.returnButton) {
            MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            mf.returnToShopFromCart();
        }
        if (e.getSource() == this.complete) {
            MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            mf.showCompleteOrder(getProducts());
        }
    }
}
