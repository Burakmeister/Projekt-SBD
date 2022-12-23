package view.layouts;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import map.Produkt;

import view.Image;
import view.MainFrame;

public class ListPanel extends JPanel implements ActionListener {

    private int imageWidth = 400;
    private int imageHeight = 300;
    public final Dimension defaultResolution = new Dimension(2560, 1440);
    private final Dimension curResolution;
    public float scale = 1;
    private final boolean admin;

    private final Font font;

    private final List<JButton> addToCartButton = new ArrayList<>();
    private final List<JButton> toDetailsButton = new ArrayList<>();
    private final List<JTextArea> shortTextLabel = new ArrayList<>();
    private final List<Produkt> list = new ArrayList<Produkt>();

    public ListPanel(Dimension dim, int cardinality, boolean admin) {
        this.admin = admin;
//		this.setSize(new Dimension(dim.width, dim.height*10));
        System.out.println(dim.width + "  " + dim.height);
        this.curResolution = Toolkit.getDefaultToolkit().getScreenSize();
        if (this.curResolution.width != this.defaultResolution.width || this.curResolution.height != this.defaultResolution.height) {
            float tmp = this.curResolution.height / (float) (this.defaultResolution.height);
            System.out.println(tmp);
            this.scale = this.curResolution.width / (float) (this.defaultResolution.width);
            if (scale > 1 && tmp > 1) {
                if (tmp > scale) {
                    scale = tmp;
                }
            } else if (scale > 1 && tmp < 1) {
                if (scale + tmp < 0) {
                    scale = tmp;
                }
            } else if (scale < 1 && tmp < 1) {
                if (tmp < scale) {
                    scale = tmp;
                }
            } else if (scale < 1 && tmp > 1) {
                if (scale + tmp > 0) {
                    scale = tmp;
                }
            }
        }
        System.out.println(scale);
        this.imageWidth = (int) (this.imageWidth * scale);
        this.imageHeight = (int) (this.imageHeight * scale);
        this.setPreferredSize(new Dimension(dim.width - ShopLayout.borderPx * 10, cardinality * imageHeight));
        font = new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 40));
        if (this.admin) {
            JButton add_product = new JButton("Dodaj nowy produkt");
            add_product.setBounds((int) (1 / scale * imageWidth / 4), (int) (1 / scale * imageHeight / 6), this.getPreferredSize().width - imageWidth + ShopLayout.borderPx + imageHeight / 2, imageHeight);
            add_product.setFont(font);
            add_product.setBackground(Color.black);
            add_product.setForeground(Color.WHITE);
            add_product.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == add_product) {
                        System.out.println("Przejscie do nowego produktu");
                        MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent) e.getSource());
                        mf.showNewProduct();
                    }
                }
            });
            this.add(add_product);
        }
    }

    public void addProdukt(Produkt produkt) {
        int listSize = this.list.size();
        if (admin) {
            listSize = this.list.size() + 1;
        }
        JButton addToCart;
        JButton toDetails;
        JTextArea shortText;

        addToCart = new JButton(Image.CART_ADD.icon);
        toDetails = new JButton(Image.DETAILS.icon);
        if (produkt.getOpis().length() > 100) {
            shortText = new JTextArea(produkt.getNazwaProduktu() + "\n\n" + produkt.getOpis().substring(0, 100) + "...");
        } else {
            shortText = new JTextArea(produkt.getNazwaProduktu() + "\n\n" + produkt.getOpis());
        }

        shortText.setFont(font);
        shortText.setLineWrap(true);
        shortText.setWrapStyleWord(true);
        shortText.setEditable(false);
//		shortText.setIgnoreRepaint(true);

        shortText.setBounds(imageWidth / 4 + imageWidth + ShopLayout.borderPx, listSize * imageHeight * 6 / 5 + imageHeight / 6,
                this.getPreferredSize().width - 2 * imageWidth, imageHeight);
        toDetails.setBounds(this.getPreferredSize().width - 3 * imageWidth / 4 + 2 * ShopLayout.borderPx, 3 / 2 * listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx,
                imageHeight / 2 - ShopLayout.borderPx, imageHeight / 2 - ShopLayout.borderPx);
        addToCart.setBounds(this.getPreferredSize().width - 3 * imageWidth / 4 + 2 * ShopLayout.borderPx, 3 / 2 * listSize * imageHeight * 6 / 5 + imageHeight / 6,
                imageHeight / 2 - ShopLayout.borderPx, imageHeight / 2 - ShopLayout.borderPx);

        addToCart.addActionListener(this);
        toDetails.addActionListener(this);

        shortText.setForeground(Color.WHITE);
        shortText.setBackground(Color.BLACK);
        toDetails.setBackground(Color.black);
        addToCart.setBackground(Color.black);

        this.addToCartButton.add(addToCart);
        this.shortTextLabel.add(shortText);
        this.toDetailsButton.add(toDetails);

        addToCart.setVisible(true);
        shortText.setVisible(true);
        toDetails.setVisible(true);

        this.add(shortText);
        this.add(addToCart);
        this.add(toDetails);

        list.add(produkt);
        this.repaint();
//		System.out.println("DODAWANE JEST, ileIchJest: " + listSize);
    }

    public void removeProdukt(Produkt produkt) {
        list.remove(produkt);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        this.paintProducts(g2d);
        g2d.scale(1 / scale, 1 / scale);
    }

    private void paintProducts(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        int i = 0;
        if (admin) {
            i = 1;
        }
        for (Produkt produkt : list) {
            int posX = (int) (1 / scale * imageWidth / 4), posY = (int) (i * 1 / scale * imageHeight * 6 / 5 + 1 / scale * imageHeight / 6);
            g2d.drawImage(new ImageIcon("src/products/" + this.list.get(this.list.size() - 1).getNazwaObrazka()).getImage(), posX, posY, null);
            i++;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i = 0;
        for (JButton but : this.addToCartButton) {
            if (e.getSource() == but) {
                this.addToCartPopUp();
                System.out.println("Dodano do koszyka: " + this.list.get(i).getNazwaProduktu());
                MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
                mf.addProductToCart(this.list.get(i));
            }
            i++;
        }
        i = 0;
        for (JButton but : this.toDetailsButton) {
            if (e.getSource() == but) {
                System.out.println("Przejscie do detali: " + this.list.get(i).getNazwaProduktu());
                MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
                mf.showProductPanel(this.list.get(i));
            }
            i++;
        }
    }

    private void addToCartPopUp() {
        JOptionPane.showMessageDialog(null, "Dodano produkt do koszyka", "", JOptionPane.INFORMATION_MESSAGE);
    }
}
