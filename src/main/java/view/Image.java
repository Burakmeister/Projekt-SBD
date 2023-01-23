package view;

import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Image {
    
	CART("cart.png"),
	CART_ADD("add_to_cart.png"),
	USER("user.png"),
	DETAILS("details.png"),
	LOG_OUT("log_out.png"),
	LOGO("LOGO.png"),
        RETURN("return.png"),
        REMOVE("REMOVE.png"),
        SAVE("save.png"),
        EDIT_SAVE("edit_save.png"),
        WAREHOUSE("warehouse.png"),
        EMPTY("empty_image.png");
	
	public ImageIcon icon;
	private Image(String fileName) {
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            String source;
            
            System.out.println("screenWidth= "+screenWidth);
            
            if(screenWidth >= 2400){
                source = "src/main/images-2k-4k/" + fileName; 
            }else if(screenWidth<2400 && screenWidth>1800){
                source = "src/main/images-fullHD/" + fileName; 
            }else{
                source = "src/main/images-HD/" + fileName; 
            }
            
            this.icon = new ImageIcon(source);
	}
}