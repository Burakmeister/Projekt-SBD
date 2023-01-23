package view.layouts;

import dao.ProduktDao;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import map.Produkt;

import view.Image;
import view.Letter;
import view.MainFrame;

public class ListPanel extends JPanel implements ActionListener {

    private static int imageWidth = 400;
    private static int imageHeight = 300;
    public final Dimension defaultResolution = new Dimension(2560, 1440);
    private final Dimension curResolution;
    public static float scale = 1;
    private final boolean admin;

    private final Font font;

    private final List<JButton> addToCartButton = new ArrayList<>();
    private final List<JButton> toDetailsButton = new ArrayList<>();
    private final List<JTextArea> shortTextLabel = new ArrayList<>();
    private final List<JTextField> priceField = new ArrayList<>();
    private final List<JTextField> numOfProductsField = new ArrayList<>();
    private final List<Produkt> list = new ArrayList<>();

    public ListPanel(Dimension dim, int cardinality, boolean admin) {
        this.admin = admin;
        this.curResolution = Toolkit.getDefaultToolkit().getScreenSize();
        if (!MainFrame.itWasScalling()) {
            if (this.curResolution.width != this.defaultResolution.width || this.curResolution.height != this.defaultResolution.height) {
                float tmp = this.curResolution.height / (float) (this.defaultResolution.height);
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

            ListPanel.imageWidth = (int) (ListPanel.imageWidth * scale);
            ListPanel.imageHeight = (int) (ListPanel.imageHeight * scale);
            MainFrame.wasScalling();
        }

        this.setPreferredSize(new Dimension(dim.width - ShopLayout.borderPx * 10, cardinality / 2 * imageHeight));
        font = new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 36));
        if (this.admin) {
            JButton add_product = new JButton("Dodaj nowy produkt");
            add_product.setBounds((int) (imageWidth / 4),
                    (int) (imageHeight / 6),
                    (int) (6.84 * imageHeight),
                    imageHeight);
            add_product.setFont(font);
            add_product.setBackground(Color.black);
            add_product.setForeground(Color.WHITE);
            add_product.addActionListener((ActionEvent e) -> {
                if (e.getSource() == add_product) {
                    MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent) e.getSource());
                    mf.showNewProduct();
                }
            });
            this.add(add_product);
        }
    }

    public void clearProductsPanel() {
        this.removeAll();
        this.revalidate();
        this.repaint();
    }

    public void addProdukt(Produkt produkt) {
        int listSize = this.list.size();
        if (admin) {
            listSize = this.list.size() + 2;
        }
        JButton addToCart;
        JButton toDetails;
        JTextArea shortText;
        JTextField price;
        JTextField numOfProducts;

        if (!admin) {
            addToCart = new JButton(Image.CART_ADD.icon);
        } else {
            addToCart = new JButton(Image.REMOVE.icon);
        }
        toDetails = new JButton(Image.DETAILS.icon);

        String pr = String.valueOf(produkt.getCena());
        if (produkt.getCena() * 100 % 10 == 0) {
            pr += "0";
        }
        shortText = new JTextArea(produkt.getNazwaProduktu());
        price = new JTextField("Cena: " + pr);
        numOfProducts = new JTextField("Ilo"+Letter.SII.getLetter()+Letter.CI.getLetter()+": " + String.valueOf(produkt.getLiczbaSztuk()));
        price.setFont(font);
        numOfProducts.setFont(font);

        shortText.setFont(font);
        shortText.setLineWrap(true);
        shortText.setWrapStyleWord(true);
        shortText.setEditable(false);

        price.setEditable(false);
        numOfProducts.setEditable(false);

        if (listSize % 2 == 1) {
            listSize /= 2;
            shortText.setBounds((int) (imageWidth / 4 + imageWidth + ShopLayout.borderPx + 3.5 * imageHeight),
                    listSize * imageHeight * 6 / 5 + imageHeight / 6,
                    2 * imageHeight,
                    imageHeight / 2 - ShopLayout.borderPx / 2);
            toDetails.setBounds((int) (imageWidth / 4 + imageWidth + ShopLayout.borderPx + imageHeight / 2 - ShopLayout.borderPx / 2 + 3.5 * imageHeight),
                    listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx / 2,
                    imageHeight / 2 - ShopLayout.borderPx,
                    imageHeight / 2 - ShopLayout.borderPx);
            addToCart.setBounds((int) (imageWidth / 4 + imageWidth + ShopLayout.borderPx + 3.5 * imageHeight),
                    listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx / 2,
                    imageHeight / 2 - ShopLayout.borderPx,
                    imageHeight / 2 - ShopLayout.borderPx);
            price.setBounds((int) (imageWidth / 4 + imageWidth + imageHeight + ShopLayout.borderPx / 2 + 3.5 * imageHeight),
                    listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx / 2,
                    imageHeight,
                    (imageHeight / 2 - ShopLayout.borderPx) / 2);
            numOfProducts.setBounds((int) (imageWidth / 4 + imageWidth + imageHeight + ShopLayout.borderPx / 2 + 3.5 * imageHeight),
                    listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx / 2 + (imageHeight / 2 - ShopLayout.borderPx) / 2,
                    imageHeight,
                    (imageHeight / 2 - ShopLayout.borderPx) / 2);
        } else {
            listSize /= 2;
            shortText.setBounds(imageWidth / 4 + imageWidth + ShopLayout.borderPx,
                    listSize * imageHeight * 6 / 5 + imageHeight / 6,
                    2 * imageHeight,
                    imageHeight / 2 - ShopLayout.borderPx / 2);
            toDetails.setBounds(imageWidth / 4 + imageWidth + ShopLayout.borderPx + imageHeight / 2 - ShopLayout.borderPx / 2,
                    listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx / 2,
                    imageHeight / 2 - ShopLayout.borderPx,
                    imageHeight / 2 - ShopLayout.borderPx);
            addToCart.setBounds(imageWidth / 4 + imageWidth + ShopLayout.borderPx,
                    listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx / 2,
                    imageHeight / 2 - ShopLayout.borderPx,
                    imageHeight / 2 - ShopLayout.borderPx);
            price.setBounds(imageWidth / 4 + imageWidth + imageHeight + ShopLayout.borderPx / 2,
                    listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx / 2,
                    imageHeight,
                    (imageHeight / 2 - ShopLayout.borderPx) / 2);
            numOfProducts.setBounds(imageWidth / 4 + imageWidth + imageHeight + ShopLayout.borderPx / 2,
                    listSize * imageHeight * 6 / 5 + imageHeight / 6 + imageHeight / 2 + ShopLayout.borderPx / 2 + (imageHeight / 2 - ShopLayout.borderPx) / 2,
                    imageHeight,
                    (imageHeight / 2 - ShopLayout.borderPx) / 2);
        }

        addToCart.addActionListener(this);
        toDetails.addActionListener(this);

        shortText.setForeground(Color.WHITE);
        shortText.setBackground(Color.BLACK);
        toDetails.setBackground(Color.black);
        addToCart.setBackground(Color.black);

        this.addToCartButton.add(addToCart);
        this.shortTextLabel.add(shortText);
        this.toDetailsButton.add(toDetails);
        this.numOfProductsField.add(numOfProducts);
        this.priceField.add(price);
//        this.productsImages.add(productImage);

        this.add(shortText);
        this.add(addToCart);
        this.add(toDetails);
        this.add(price);
        this.add(numOfProducts);

        list.add(produkt);
        this.repaint();
//		System.out.println("DODAWANE JEST, ileIchJest: " + listSize);
    }

    public void removeProdukt(int index) {
        int max = this.list.size() - 1;
        this.remove(this.addToCartButton.get(max));
        this.addToCartButton.remove(max);
        this.remove(this.toDetailsButton.get(max));
        this.toDetailsButton.remove(max);
        this.remove(this.numOfProductsField.get(max));
        this.numOfProductsField.remove(max);
        this.remove(this.shortTextLabel.get(max));
        this.shortTextLabel.remove(max);
        this.remove(this.priceField.get(max));
        this.priceField.remove(max);
        this.list.remove(index);

        for (int i = index; i < this.shortTextLabel.size() - 1; i++) {
            this.shortTextLabel.get(i).setText(this.shortTextLabel.get(i + 1).getText());
            this.priceField.get(i).setText(this.shortTextLabel.get(i + 1).getText());
            this.numOfProductsField.get(i).setText(this.numOfProductsField.get(i + 1).getText());
        }
        this.revalidate();
        this.repaint();
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
            i = 2;
        }
        for (Produkt produkt : list) {
            int posX = (int) (1 / scale * imageWidth / 4), posY = (int) (i / 2 * 1 / scale * imageHeight * 6 / 5 + 1 / scale * imageHeight / 6);
            if (i % 2 == 0) {
                if (!g2d.drawImage(new ImageIcon("src/main/products/" + produkt.getNazwaObrazka()).getImage(), posX, posY, null)) {
                    g2d.drawImage(Image.EMPTY.icon.getImage(), posX, posY, null);
                }
            } else {
                posX += 3.5 * imageHeight / scale;
                if (!g2d.drawImage(new ImageIcon("src/main/products/" + produkt.getNazwaObrazka()).getImage(), posX, posY, null)) {
                    g2d.drawImage(Image.EMPTY.icon.getImage(), posX, posY, null);
                }
            }
            i++;
        }
    }

    public void setList(List<String> katList) {
        this.removeAll();
        while (!this.list.isEmpty()) {
            this.removeProdukt(0);
        }
        ProduktDao dao = new ProduktDao();
        ArrayList<Produkt> products = dao.getAll();
        for (Produkt produkt : products) {
            for (String cat : katList) {
                if (produkt.getKategoria().getNazwaKategorii().equals(cat)) {
                    this.addProdukt(produkt);
                }
            }
        }
        this.revalidate();
        this.repaint();
    }
    
    public void updateAvailability(){
        ProduktDao pDao = new ProduktDao();
        while (!this.list.isEmpty()) {
            this.list.remove(0);
        }
        
        for(Produkt p: pDao.getAll()){
            this.list.add(p);
        }
        
        for(int i=0; i<this.numOfProductsField.size(); i++){
            this.numOfProductsField.get(i).setText(
                    String.valueOf(
                            this.list.get(i).getLiczbaSztuk()
                    )
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i = 0;
        for (JButton but : this.addToCartButton) {
            if (e.getSource() == but) {
                if (admin) {
                    ProduktDao dao = new ProduktDao();
                    dao.delete(this.list.get(i));
                    this.removeProdukt(i);
                } else {
                    this.addToCartPopUp();
                    MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
                    mf.addProductToCart(this.list.get(i));
                }
            }
            i++;
        }
        i = 0;
        for (JButton but : this.toDetailsButton) {
            if (e.getSource() == but) {
                System.out.println("Przej"+Letter.SII.getLetter()+"cie do detali: " + this.list.get(i).getNazwaProduktu());
                MainFrame mf = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
                mf.showProductPanel(this.list.get(i));
            }
            i++;
        }
    }

    private void addToCartPopUp() {
        JOptionPane.showMessageDialog(null, "Dodano produkt do koszyka", "", JOptionPane.INFORMATION_MESSAGE);
    }

    void refreshProduct(Produkt produkt) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdProduktu() == produkt.getIdProduktu()) {
                this.numOfProductsField.get(i).setText("Ilo"+Letter.SII.getLetter()+Letter.CI.getLetter()+": " + String.valueOf(produkt.getLiczbaSztuk()));
                this.shortTextLabel.get(i).setText(produkt.getNazwaProduktu());
                String pr = String.valueOf(produkt.getCena());
                if (produkt.getCena() * 10 % 10 == 0) {
                    pr += "0";
                }
                this.priceField.get(i).setText("Cena: " + pr);
            }
        }
    }

    public static Dimension getImageDimension() {
        return new Dimension(imageWidth, imageHeight);
    }
}
