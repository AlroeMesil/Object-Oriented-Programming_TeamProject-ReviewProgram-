package swing;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private Image image;

	public ImagePanel(String image) {
		if("SignIn".equals(image)) {
			this.image = new ImageIcon("images/background_blur.jpg").getImage();	
		} else if("Title".equals(image)) {
			this.image = new ImageIcon("images/title.png").getImage();
		}
		else {
			this.image = new ImageIcon("images/background_blur.jpg").getImage();			
		}
	}
	
	public ImagePanel() {
		image = new ImageIcon("images/background_blur.jpg").getImage();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, 1024, 768, null);
	}
}