package uppgift2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
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

public class PointOperation {
	private static int contrast;
	private static int brightness;
	private static Random random;
	private static BufferedImage imageOriginal;
	private static BufferedImage imageAltered;
	private static int[][] pixels;
	private static JFrame frame;
	private static SampleModel sampleModel;
	private static String filename;
	
	/**
	 * constructor
	 * @param contrast
	 * @param brightness
	 * @throws IOException 
	 */
	public PointOperation(String filename, int contrast, int brightness) throws IOException {
		this.contrast = contrast;
		this.brightness = brightness;
		random = new Random();
		this.filename = filename;
	}
	
	/**
	 * displays the image in a window
	 * @throws IOException 
	 */
	public static void displayImage() throws IOException {
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
		ImageIcon  imgOriginal = new ImageIcon(imageOriginal.getScaledInstance(400, 300, Image.SCALE_DEFAULT));
		ImageIcon  imgAltered = new ImageIcon(imageAltered.getScaledInstance(400, 300, Image.SCALE_DEFAULT));
		
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
	
	/**
	 * converts an image to a 2 dimensional pixel-array
	 * @return pixelarray, 2 dimensional array of pixels
	 * @throws IOException 
	 */
	public static int[][] imageToPixelArray() throws IOException {
		File file = new File(filename);
		imageOriginal = ImageIO.read(file);
		Raster raster = imageOriginal.getData();
		int width = raster.getWidth();
		int height = raster.getHeight();
		pixels = new int[width][height];
		
		sampleModel = raster.getSampleModel();
		int color = imageOriginal.getColorModel().getNumColorComponents();
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				for(int c = 0; c < color; c++) {
					pixels[i][j] = raster.getSample(i, j, c);
				}
			}
		}
		return pixels;
	}
	
	/**
	 * alters the picture by changing the contrast and brightness
	 * @param pixels, 2 dimensional pixel-array
	 * @return the altered picture
	 */
	public static int[][] compute(int[][] pixels) {
		contrast = 20;
		brightness = 20;
		for(int i = 0;  i < pixels.length; i++) {
			for(int j = 0; j < pixels[i].length; j++) {
				pixels[i][j] = pixels[i][j] * contrast + brightness;
			}
		}
		return pixels;
	}
	
	/**
	 * converts a 2 dimensional pixel-array to an image
	 * @return image
	 */
	public static BufferedImage pixelArrayToImage(int[][] pixels) {
		int width = pixels.length;
		int height = pixels[0].length;
		WritableRaster raster = Raster.createWritableRaster(sampleModel, new Point(0, 0));
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				raster.setSample(i, j, 0, pixels[i][j]);
			}
		}
		
		imageAltered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imageAltered.setData(raster);
		return imageAltered;
	}
	
	/**
	 * main-method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		PointOperation po = new PointOperation("files/flower.png", 20, 20);
		pixels = imageToPixelArray();
//		pixels = compute(pixels);
		imageAltered = pixelArrayToImage(pixels);
		displayImage();
	}
}
