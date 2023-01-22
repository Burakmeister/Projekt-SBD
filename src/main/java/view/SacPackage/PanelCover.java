package view.SacPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JLabel;
// Brane z maven
import net.miginfocom.swing.MigLayout;
import view.Letter;
// Klasy rozszerzające
import view.SacPackage.ButtonOutLine;

/**
 *
 * @author gs
 * 
 * Odpowiada za panel z boku z gradientem
 */
public class PanelCover extends javax.swing.JPanel {

    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private ActionListener event;
    private MigLayout layout;
    private JLabel title;
    private JLabel description;
    private JLabel description1;
    private ButtonOutLine button;
    private boolean isLogin;
    
    public PanelCover() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");
        setLayout(layout);
        init();
    }

    private void init() {
        title = new JLabel("Witaj u"+Letter.ZY.getLetter()+"ytkowniku!");
        title.setFont(new Font("sansserif", 1, 30));
        title.setForeground(new Color(245, 245, 245));
        add(title);
        
        description = new JLabel("Aby kontynuowa"+Letter.CI.getLetter()+" dalej,");
        description.setForeground(new Color(245, 245, 245));
        add(description);
        
        description1 = new JLabel("zaloguj si"+Letter.EHH.getLetter()+" na swoje konto");
        description1.setForeground(new Color(245, 245, 245));
        add(description1);
        
        button = new ButtonOutLine();
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(255, 255, 255));
        button.setText("Zaloguj się");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                event.actionPerformed(ae);
            }
        });
        add(button, "w 60%, h 40");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g2d = (Graphics2D) gr;
        GradientPaint grad = new GradientPaint(0, 0, new Color(124, 56, 56 ), 0, getHeight(), new Color(149, 43, 43 ));    // 244, 56, 56 - czerwony, 149, 43, 43 - ciemny czerwony
        g2d.setPaint(grad);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(gr);
    }
    
    public void addEvent(ActionListener event) {
        this.event = event;
    }

    public void registerLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0 ");
        layout.setComponentConstraints(description, "pad 0 -" + v + "% 0 0 ");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0 "); 
    }
    
    public void registerRight(double v) {
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0 ");
        layout.setComponentConstraints(description, "pad 0 -" + v + "% 0 0 ");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0 "); 
    }
    
    public void loginLeft(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
    }
    
    public void loginRight(double v) {
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
    }
    
    private void login(boolean login) {
        if(this.isLogin != login) {
            if(login) {
                title.setText("Witaj u"+Letter.ZY.getLetter()+"ytkowniku!");
                description.setText("Aby rozpocz"+Letter.OHH.getLetter()+Letter.CI.getLetter()+",");
                description1.setText("podaj swoje dane i za"+Letter.ELL.getLetter()+Letter.UU.getLetter()+Letter.ZY.getLetter()+" konto");
                button.setText("Za"+Letter.ELL.getLetter()+Letter.UU.getLetter()+Letter.ZY.getLetter()+" konto");
            }
            else {
                title.setText("Witaj u"+Letter.ZY.getLetter()+"ytkowniku!");
                description.setText("Aby kontynuowa"+Letter.CI.getLetter()+" dalej,");
                description1.setText("zaloguj si"+Letter.EHH.getLetter()+" na swoje konto"); 
                button.setText("Zaloguj si"+Letter.EHH.getLetter());
            }
            this.isLogin = login;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
