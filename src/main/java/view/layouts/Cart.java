/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.layouts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import map.Produkt;
import view.Image;
import view.MainFrame;

public class Cart extends JPanel {

    private int imageWidth = 400;
    private int imageHeight = 300;
    public final Dimension defaultResolution = new Dimension(2560, 1440);
    private Dimension curResolution;
    private boolean warehouseCart;

    public float scale = 1;

    private Font font;

    private List<JButton> removeButtons = new ArrayList<JButton>();
    private List<JButton> toDetailsButton = new ArrayList<JButton>();
    private List<JTextArea> shortTextLabel = new ArrayList<JTextArea>();
    private List<JSpinner> spiners = new ArrayList<>();

    private List<Produkt> list = new ArrayList<Produkt>();

    public Cart(Dimension dim, int cardinality, boolean warehouseCart) {
        this.warehouseCart = warehouseCart;
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
        scale /= 2;
        this.imageWidth = (int) (this.imageWidth * scale);
        this.imageHeight = (int) (this.imageHeight * scale);
        this.setPreferredSize(new Dimension(dim.width - CartLayout.borderPx * 10, cardinality * imageHeight));
        font = new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (scale * 40));
    }
    public void addProdukt(Produkt produkt, boolean isRepaint) {
        if (!isRepaint) {
            MainFrame frame = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
            if (!this.warehouseCart) {
                frame.refreshCategoryPanel(false);
            }

            for (int i = 0; i < this.list.size(); i++) {
                if (this.list.get(i).getIdProduktu() == produkt.getIdProduktu()) {
                    int integ = (Integer) this.spiners.get(i).getValue();
                    integ++;
                    this.spiners.get(i).setValue(integ);
                    return;
                }
            }
        }
        JButton removeButton;
        JButton toDetails;
        JTextArea shortText;
        JSpinner spinner;

        SpinnerModel model = null;
        if (this.warehouseCart) {
            model = new SpinnerNumberModel(1, 1, 100000, 1);
        } else {
            model = new SpinnerNumberModel(1, 1, produkt.getLiczbaSztuk(), 1);
        }

        spinner = new JSpinner(model);
        spinner.addChangeListener((ChangeEvent e) -> {
            MainFrame frame = (MainFrame) (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent) e.getSource());  // zdobÄ…dĹş rodzica (czyli JFrame)
            if (!warehouseCart) {
                frame.refreshCategoryPanel(false);
            }
        });
        spinner.setFont(font);
        removeButton = new JButton(Image.RETURN.icon);
        toDetails = new JButton(Image.DETAILS.icon);
        shortText = new JTextArea(produkt.getNazwaProduktu());

        shortText.setFont(font);
        shortText.setLineWrap(true);
        shortText.setWrapStyleWord(true);
        shortText.setEditable(false);
//		shortText.setIgnoreRepaint(true);
        int tmp = this.list.size();

        switch (tmp % 3) {
            case 0:
                shortText.setBounds(imageWidth + 2 * ShopLayout.borderPx,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx,
                        imageWidth * 2,
                        imageHeight / 2);
                toDetails.setBounds(imageWidth + 2 * ShopLayout.borderPx,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                removeButton.setBounds(imageWidth + 2 * ShopLayout.borderPx + 2 * imageWidth / 3,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                spinner.setBounds(imageWidth + 2 * ShopLayout.borderPx + 4 * imageWidth / 3,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                break;
            case 1:
                shortText.setBounds(imageWidth + 2 * ShopLayout.borderPx + 4 * imageWidth,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx,
                        imageWidth * 2,
                        imageHeight / 2);
                toDetails.setBounds(imageWidth + 2 * ShopLayout.borderPx + 4 * imageWidth,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                removeButton.setBounds(imageWidth + 2 * ShopLayout.borderPx + 2 * imageWidth / 3 + 4 * imageWidth,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                spinner.setBounds(imageWidth + 2 * ShopLayout.borderPx + 4 * imageWidth / 3 + 4 * imageWidth,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                break;
            default:
                shortText.setBounds(imageWidth + 2 * ShopLayout.borderPx + 8 * imageWidth,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx,
                        imageWidth * 2,
                        imageHeight / 2);
                toDetails.setBounds(imageWidth + 2 * ShopLayout.borderPx + 8 * imageWidth,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                removeButton.setBounds(imageWidth + 2 * ShopLayout.borderPx + 2 * imageWidth / 3 + 8 * imageWidth,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                spinner.setBounds(imageWidth + 2 * ShopLayout.borderPx + 4 * imageWidth / 3 + 8 * imageWidth,
                        (tmp / 3) * 4 * imageHeight / 3 + ShopLayout.borderPx + imageHeight / 2,
                        2 * imageWidth / 3,
                        imageHeight / 2);
                break;
        }

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == removeButton) {
                    removeProdukt(tmp);
                }
            }

        });
        toDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame frame = (MainFrame) (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (JComponent) e.getSource());  // zdobÄ…dĹş rodzica (czyli JFrame)
                frame.showProductPanelCart(produkt);
            }

        });
        shortText.setForeground(Color.WHITE);
        shortText.setBackground(Color.BLACK);
        toDetails.setBackground(Color.black);
        removeButton.setBackground(Color.black);

        this.removeButtons.add(removeButton);
        this.toDetailsButton.add(toDetails);
        this.spiners.add(spinner);
        this.shortTextLabel.add(shortText);

        this.add(shortText);
        this.add(removeButton);
        this.add(toDetails);
        this.add(spinner);
        if (!isRepaint) {
            list.add(produkt);
        }
        this.repaint();
        MainFrame frame = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
        if (!this.warehouseCart) {
            frame.refreshCategoryPanel(false);
        }
    }

    public void removeProdukt(int index) {
        int max = this.list.size() - 1;
        this.remove(this.removeButtons.get(max));
        this.removeButtons.remove(max);

        this.remove(this.toDetailsButton.get(max));
        this.toDetailsButton.remove(max);
        this.remove(this.shortTextLabel.get(max));
        this.shortTextLabel.remove(max);
        for (int i = index; i < this.spiners.size() - 1; i++) {
            this.spiners.get(i).setValue(this.spiners.get(i + 1).getValue());
        }
        this.remove(this.spiners.get(max));
        this.spiners.remove(max);
        this.list.remove(index);
        this.repaint();
        MainFrame frame = (MainFrame) (JFrame) SwingUtilities.getWindowAncestor(this);
        if (this.warehouseCart) {
            frame.refreshCategoryPanel(true);
        } else {
            frame.refreshCategoryPanel(false);
        }
    }

    public void removeAll() {
        while (!this.list.isEmpty()) {
            this.removeProdukt(0);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale / 1, scale / 1);
        this.paintProducts(g2d);
        g2d.scale(1 / scale, 1 / scale);
    }

    private void paintProducts(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        for (int i = 0; i < this.list.size(); i++) {
            switch (i % 3) {
                case 0: {
                    int posX = ShopLayout.borderPx, posY = (int) (i / 3 * 1 / scale * 4 * imageHeight / 3 + ShopLayout.borderPx);
                    if (!g2d.drawImage(new ImageIcon("src/main/products/" + this.list.get(i).getNazwaObrazka()).getImage(), posX, posY, null)) {
                        g2d.drawImage(Image.EMPTY.icon.getImage(), posX, posY, null);
                    }
                    break;
                }
                case 1: {
                    int posX = (int) (ShopLayout.borderPx + 4 * imageWidth / scale), posY = (int) (i / 3 * 1 / scale * 4 * imageHeight / 3 + ShopLayout.borderPx);
                    if (!g2d.drawImage(new ImageIcon("src/main/products/" + this.list.get(i).getNazwaObrazka()).getImage(), posX, posY, null)) {
                        g2d.drawImage(Image.EMPTY.icon.getImage(), posX, posY, null);
                    }

                    break;
                }
                case 2: {
                    int posX = (int) (ShopLayout.borderPx + 8 * imageWidth / scale), posY = (int) (i / 3 * 1 / scale * 4 * imageHeight / 3 + ShopLayout.borderPx);
                    if (!g2d.drawImage(new ImageIcon("src/main/products/" + this.list.get(i).getNazwaObrazka()).getImage(), posX, posY, null)) {
                        g2d.drawImage(Image.EMPTY.icon.getImage(), posX, posY, null);
                    }

                    break;
                }
            }
        }

    }

    public List<Produkt> getProducts() {
        return this.list;
    }

    public List<Integer> getNumOfProducts() {
        List<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < this.list.size(); i++) {
            ret.add((Integer) this.spiners.get(i).getValue());
        }
        return ret;
    }
}
