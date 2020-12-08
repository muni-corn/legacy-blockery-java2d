package musicaflight.blockery;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Images extends JPanel {
	private static final long serialVersionUID = 1L;

	public Image blockeryLogo;

	public Image envbackG;
	public Image endbackG;

	public Image paused;

	public Image cottageimg;
	public Image factoryimg;
	public Image powerhimg;
	public Image megapowerhimg;
	public Image treeimg;
	
	public Image x;

	public Image GOimg;

	public Images() {

		ImageIcon iimenuLogo = new ImageIcon(this.getClass().getResource("/mainmenu.png"));
		blockeryLogo = iimenuLogo.getImage();

		ImageIcon ii = new ImageIcon(this.getClass().getResource("/GameBackground.png"));
		envbackG = ii.getImage();
		ImageIcon iiend = new ImageIcon(this.getClass().getResource("/EndlessBackground.png"));
		endbackG = iiend.getImage();

		ImageIcon iipause = new ImageIcon(this.getClass().getResource("/paused.png"));
		paused = iipause.getImage();

		ImageIcon iicott = new ImageIcon(this.getClass().getResource("/cottage.png"));
		cottageimg = iicott.getImage();
		ImageIcon iifact = new ImageIcon(this.getClass().getResource("/factory.png"));
		factoryimg = iifact.getImage();
		ImageIcon iipowerh = new ImageIcon(this.getClass().getResource("/powerh.png"));
		powerhimg = iipowerh.getImage();
		ImageIcon iimPH = new ImageIcon(this.getClass().getResource("/megapowerh.png"));
		megapowerhimg = iimPH.getImage();
		ImageIcon iitree = new ImageIcon(this.getClass().getResource("/tree.png"));
		treeimg = iitree.getImage();
		
		ImageIcon xii = new ImageIcon(this.getClass().getResource("/x.png"));
		x = xii.getImage();

		ImageIcon iiGO = new ImageIcon(this.getClass().getResource("/gameover.png"));
		GOimg = iiGO.getImage();
	}
}
