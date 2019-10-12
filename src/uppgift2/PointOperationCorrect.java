package uppgift2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PointOperationCorrect {
	private static double contrast;
	private static int brightness;
	private static BufferedImage imageOriginal;
	private static BufferedImage imageAltered;
	private static JFrame frame;
	private static Random rand;
	
	/**
	 * constructor
	 * @param filename
	 * @throws IOException
	 */
	public PointOperationCorrect(String filename) throws IOException {
		rand = new Random();
		File file = new File(filename);
		imageOriginal = ImageIO.read(file);
	}
	
	/**
	 * displays the image in a window
	 * @throws IOException 
	 */
	public static void displayImages() throws IOException {
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
		ImageIcon  imgOriginal = new ImageIcon(imageOriginal.getScaledInstance(imageOriginal.getWidth() / 2, imageOriginal.getHeight() / 2, Image.SCALE_DEFAULT));
		ImageIcon  imgAltered = new ImageIcon(imageAltered.getScaledInstance(imageAltered.getWidth() / 2, imageAltered.getHeight() / 2, Image.SCALE_DEFAULT));
		Font font = new Font("SansSerif", Font.BOLD, 14);
		
		JLabel lblImgOriginal = new JLabel(imgOriginal);
		JLabel lblOriginal = new JLabel("Original image", SwingConstants.CENTER);
		lblOriginal.setFont(font);
		JLabel lblImgAltered = new JLabel(imgAltered);
		JLabel lblAltered = new JLabel("Altered image", SwingConstants.CENTER);
		lblAltered.setFont(font);
		
		JPanel panelOriginal = new JPanel(new BorderLayout());
		panelOriginal.add(lblOriginal, BorderLayout.NORTH);
		panelOriginal.add(lblImgOriginal, BorderLayout.CENTER);
		
		JPanel panelAltered = new JPanel(new BorderLayout());
		panelAltered.add(lblAltered, BorderLayout.NORTH);
		panelAltered.add(lblImgAltered, BorderLayout.CENTER);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(panelOriginal, BorderLayout.WEST);
		panel.add(panelAltered, BorderLayout.EAST);
		
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
	
	/**
	 * alter the image´s contrast and brightness
	 * @throws IOException
	 */
	public void alterImage() throws IOException {
		setSystemLookAndFeel();
		imageAltered = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), BufferedImage.TYPE_INT_RGB);
		brightness = rand.nextInt(150 + 200 + 1) - 200; //values from 150 to 200
		contrast = 1.5 + (5.0 - 1.5) * rand.nextDouble(); //values from 1.5 to 5.0
				
		for(int i = 0; i < imageOriginal.getWidth(); i++) {
			for(int j = 0; j < imageOriginal.getHeight(); j++) {
				Color c = new Color(imageOriginal.getRGB(i, j));
				int red = (int) contrast * c.getRed() + brightness;
				int green = (int) contrast * c.getGreen() + brightness;
				int blue = (int) contrast * c.getBlue() + brightness;
				
				if(red > 255) {
					red = 255;
				} else if(red < 0) {
					red = 0;
				}
				if(green > 255) {
					green = 255;
				} else if(green < 0) {
					green = 0;
				}
				if(blue > 255) {
					blue = 255;
				} else if(blue < 0) {
					blue = 0;
				}
				imageAltered.setRGB(i, j, new Color(red, green, blue).getRGB());
			}
		}
	}
	
	//this method is irrelevant for the assignment
	/**
	 * turns an image to a grayscale version of the image
	 * @throws IOException
	 */
	public void alterImageGrayScale() throws IOException {
		imageAltered = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		for(int i = 0; i < imageOriginal.getWidth(); i++) {
			for(int j = 0; j < imageOriginal.getHeight(); j++) {
				Color c = new Color(imageOriginal.getRGB(i, j));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				int a = c.getAlpha();
				int gr = (r + g + b) / 3;
				
				Color grayColor = new Color(gr, gr, gr, a);
				imageAltered.setRGB(i, j, grayColor.getRGB());
			}
		}
	}
	
	/**
	 * main-method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		PointOperationCorrect po = new PointOperationCorrect("files/flower.png");
		po.alterImage();
		po.displayImages();
	}
}