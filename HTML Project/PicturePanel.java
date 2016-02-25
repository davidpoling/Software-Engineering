/* Subclass of JPanel used to draw the images supplied from the
 * list of image source tags.
 */
import javax.swing.*;
import java.awt.*;

public class PicturePanel extends JPanel {
    Image image;

    public PicturePanel() {
        super();
        setPreferredSize(new Dimension(400,400));
    } // end constructor PicturePanel().
//=========================================================================
	// Overriden paintComponent() method to scale the passed image appropriately with the panel.
    public void paintComponent(Graphics g) {
        double k;
        double panelWidth;
        double panelHeight;
        double imageWidth;
        double imageHeight;
        
        super.paintComponent(g);
        
        if (image != null) {
			panelWidth = getWidth();
			panelHeight = getHeight();
			imageWidth = (double)image.getWidth(null);
			imageHeight = (double)image.getHeight(null);
			
			if ((imageWidth / imageHeight) > (panelWidth / panelHeight)) {
				  k = panelWidth / imageWidth;
				  g.drawImage(image, 0, (int)((panelHeight - (k*imageHeight)))/2, (int)(k*imageWidth), (int)(k*imageHeight), null);
			} else {
				  k = panelHeight / imageHeight;
				  g.drawImage(image, (int)((panelWidth - (k*imageWidth)))/2, 0, (int)(k*imageWidth), (int)(k*imageHeight), null);;
			}
		}
    } // end method paintComponent().
//=========================================================================
    public void setImage(Image image) {
        this.image = image;
        repaint();
    } // end method setImage().
} // end class PicturePanel.
