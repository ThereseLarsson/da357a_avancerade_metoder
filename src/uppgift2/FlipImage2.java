package uppgift2;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;

import java.io.*;
import javax.imageio.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FlipImage2 {
	private double contrast;
	private double brightness;
	private static JFrame frame;
	private BufferedImage image;

	public BufferedImage getImage() {
		return this.image;
	}

	public FlipImage2(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		contrast = 20;
		brightness = 20;

		WritableRaster inraster = img.getRaster();

		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int colors = 3;
		try {
			int dummy = inraster.getSample(0, 0, 2);
		} catch (Exception e) { // Try accessing blue color value!
			this.image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			colors = 1;
		}

		WritableRaster outraster = this.image.getRaster();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				for (int c = 0; c < colors; c++) {
					int value = inraster.getSample(i, j, c);
					double newValue = value * contrast + brightness;
					outraster.setSample(i, j, c, 255 - newValue);
				}
			}
		}
	}
	
	/**
	 * displays the image in a window
	 * @throws IOException 
	 */
	public void displayImage() throws IOException {
		setSystemLookAndFeel();
		frame = new JFrame();
		frame.setSize(950, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Picture");
		initializeGUI();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * sets up the GUI with components
	 * @throws IOException 
	 */
	private static void initializeGUI() throws IOException {
		File fileOriginal = new File("files/flower.png");
		File fileAltered = new File("files/flippedImage.png");
		
		BufferedImage buffImgOriginal = ImageIO.read(fileOriginal);
		BufferedImage buffImgAltered = ImageIO.read(fileAltered);
		
		ImageIcon  imgOriginal = new ImageIcon(buffImgOriginal.getScaledInstance(400, 300, Image.SCALE_DEFAULT));
		ImageIcon  imgAltered = new ImageIcon(buffImgAltered.getScaledInstance(400, 300, Image.SCALE_DEFAULT));
		
		JLabel lblImgOriginal = new JLabel(imgOriginal);
		JLabel lblOriginal = new JLabel("Original image", SwingConstants.CENTER);
		JLabel lblImgConverted = new JLabel(imgAltered);
		JLabel lblConverted = new JLabel("Altered image", SwingConstants.CENTER);
		
		JPanel panelOriginal = new JPanel(new BorderLayout());
		panelOriginal.add(lblOriginal, BorderLayout.NORTH);
		panelOriginal.add(lblImgOriginal, BorderLayout.CENTER);
		
		JPanel panelConverted = new JPanel(new BorderLayout());
		panelConverted.add(lblConverted, BorderLayout.NORTH);
		panelConverted.add(lblImgConverted, BorderLayout.CENTER);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(panelOriginal, BorderLayout.WEST);
		panel.add(panelConverted, BorderLayout.EAST);
		
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); 
		frame.add(panel);
	}
	
	/**
	 * makes the interface´s appearance adjust to the running operating system
	 */
	private static void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String filename = "files/flower.png";
			BufferedImage img = ImageIO.read(new File(filename));
			FlipImage2 flip = new FlipImage2(img);
			//ImageIO.write(flip.getImage(), "PNG", new File("files/flippedImage.png"));
			
			flip.displayImage();
			
		} catch (Exception e) {
			System.out.println("Failed processing!\nUsage: java FlipImage 'image_file'\n\n" + e.toString());
		}
	}
}
