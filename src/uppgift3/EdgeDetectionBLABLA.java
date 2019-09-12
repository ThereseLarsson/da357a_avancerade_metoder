package uppgift3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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

public class EdgeDetectionBLABLA {
	private static JFrame frame;
	private static BufferedImage imageOriginal;
	private static BufferedImage imageGrayScale;
	private static BufferedImage imageBlackAndWhite;
	private static BufferedImage imageEdgeDetection;
	
	/**
	 * contructor
	 * @param filename, name of the file of the input image
	 * @throws IOException
	 */
	public EdgeDetectionBLABLA(String filename) throws IOException {
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
		frame.setSize(1000, 800);
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
		int factor = 2;
		ImageIcon  imgOriginal = new ImageIcon(imageOriginal.getScaledInstance(imageOriginal.getWidth() / factor, imageOriginal.getHeight() / factor, Image.SCALE_DEFAULT));
		ImageIcon  imgGrayScale = new ImageIcon(imageGrayScale.getScaledInstance(imageGrayScale.getWidth() / factor, imageGrayScale.getHeight() / factor, Image.SCALE_DEFAULT));
		ImageIcon  imgEdgeDetection = new ImageIcon(imageEdgeDetection.getScaledInstance(imageEdgeDetection.getWidth() / factor, imageEdgeDetection.getHeight() / factor, Image.SCALE_DEFAULT));
		ImageIcon  imgBlackAndWhite = new ImageIcon(imageBlackAndWhite.getScaledInstance(imageBlackAndWhite.getWidth() / factor, imageBlackAndWhite.getHeight() / factor, Image.SCALE_DEFAULT));
		Font font = new Font("SansSerif", Font.BOLD, 14);
		
		JLabel lblImgOriginal = new JLabel(imgOriginal);
		JLabel lblOriginal = new JLabel("Original image", SwingConstants.CENTER);
		lblOriginal.setFont(font);
		
		JLabel lblImgGrayScale = new JLabel(imgGrayScale);
		JLabel lblGrayScale = new JLabel("Grayscale image", SwingConstants.CENTER);
		lblGrayScale.setFont(font);
		
		JLabel lblImgEdgeDetection = new JLabel(imgEdgeDetection);
		JLabel lblEdgeDetection = new JLabel("Edge detection (sobel)", SwingConstants.CENTER);
		lblEdgeDetection.setFont(font);
		
		JLabel lblImgBlackAndWhite = new JLabel(imgBlackAndWhite);
		JLabel lblBlackAndWhite = new JLabel("Edge detection with threshold", SwingConstants.CENTER);
		lblBlackAndWhite.setFont(font);
		
		JPanel panelOriginal = new JPanel(new BorderLayout());
		panelOriginal.add(lblOriginal, BorderLayout.NORTH);
		panelOriginal.add(lblImgOriginal, BorderLayout.CENTER);
		
		JPanel panelGrayScale = new JPanel(new BorderLayout());
		panelGrayScale.add(lblGrayScale, BorderLayout.NORTH);
		panelGrayScale.add(lblImgGrayScale, BorderLayout.CENTER);
		
		JPanel panelEdgeDetection = new JPanel(new BorderLayout());
		panelEdgeDetection.add(lblEdgeDetection, BorderLayout.NORTH);
		panelEdgeDetection.add(lblImgEdgeDetection, BorderLayout.CENTER);
		
		JPanel panelBlackAndWhite = new JPanel(new BorderLayout());
		panelBlackAndWhite.add(lblBlackAndWhite, BorderLayout.NORTH);
		panelBlackAndWhite.add(lblImgBlackAndWhite, BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel(new GridLayout(1, 2));
		JPanel panel2 = new JPanel(new GridLayout(1, 2));
		panel1.add(panelOriginal);
		panel1.add(panelGrayScale);
		panel2.add(panelEdgeDetection);
		panel2.add(panelBlackAndWhite);
		
		JPanel mainPanel = new JPanel(new GridLayout(2, 1));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); 
		frame.add(mainPanel);
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
	 * turns an image to a grayscale version of the image
	 */
	public void alterImageGrayScale() throws IOException {
		imageGrayScale = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		for(int i = 0; i < imageOriginal.getWidth(); i++) {
			for(int j = 0; j < imageOriginal.getHeight(); j++) {
				Color c = new Color(imageOriginal.getRGB(i, j));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();
				int gray = (int) (0.2126*red + 0.7152*green + 0.0722*blue);
				imageGrayScale.setRGB(i, j, new Color(gray, gray, gray).getRGB());
			}
		}
	}      
	
//	/**
//	 * edge detection
//	 * @throws IOException
//	 */
//	public void alterEdgeDetection() throws IOException {
//		imageBlackAndWhite = new BufferedImage(imageGrayScale.getWidth(), imageGrayScale.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
//		int x = imageGrayScale.getWidth();
//		int y = imageGrayScale.getHeight();
//		int threshold = 10;
//
//		for (int i = 1; i < x - 1; i++) {
//			for (int j = 1; j < y - 1; j++) {
//
//				//sobel convolution
//				int val00 = imageGrayScale.getRGB(i - 1, j - 1);
//				int val01 = imageGrayScale.getRGB(i - 1, j);
//				int val02 = imageGrayScale.getRGB(i - 1, j + 1);
//				int val10 = imageGrayScale.getRGB(i, j - 1);
//				int val11 = imageGrayScale.getRGB(i, j); //current pixel, surrounding are the pixels neighbours
//				int val12 = imageGrayScale.getRGB(i, j + 1);
//				int val20 = imageGrayScale.getRGB(i + 1, j - 1);
//				int val21 = imageGrayScale.getRGB(i + 1, j);
//				int val22 = imageGrayScale.getRGB(i + 1, j + 1);
//
//				System.out.println(val11);
//				
//				//calculation of the magnitude gradient
//				int gradientX = ((-1 * val00) + (0 * val01) + (1 * val02)) + ((-2 * val10) + (0 * val11) + (2 * val12))
//						+ ((-1 * val20) + (0 * val21) + (1 * val22));
//				int gradientY = ((-1 * val00) + (-2 * val01) + (-1 * val02)) + ((0 * val10) + (0 * val11) + (0 * val12))
//						+ ((1 * val20) + (2 * val21) + (1 * val22));
//				int gradientValue = (int) Math.sqrt(Math.pow(gradientX, 2) + Math.pow(gradientY, 2));
//				
//				//???? feel like something should be done here, but dont know what
//				
//				if(threshold > gradientValue) {
//					imageBlackAndWhite.setRGB(i, j, new Color(0, 0, 0).getRGB());
//				} else {
//					imageBlackAndWhite.setRGB(i, j, new Color(255, 255, 255).getRGB());
//				}
//				
//			}
//		}
//	}
	
	public void alterEdgeDetection() throws IOException {
		int width = imageOriginal.getWidth();
		int height = imageOriginal.getHeight();
		imageEdgeDetection = new BufferedImage(imageOriginal.getWidth(), imageOriginal.getHeight(), BufferedImage.TYPE_INT_RGB);

		int maxGval = 0;
		int[][] edgeColors = new int[width][height];
		int maxGradient = -1;

		for (int i = 1; i < width - 1; i++) {
			for (int j = 1; j < height - 1; j++) {

				int val00 = getGrayScale(imageOriginal.getRGB(i - 1, j - 1));
				int val01 = getGrayScale(imageOriginal.getRGB(i - 1, j));
				int val02 = getGrayScale(imageOriginal.getRGB(i - 1, j + 1));

				int val10 = getGrayScale(imageOriginal.getRGB(i, j - 1));
				int val11 = getGrayScale(imageOriginal.getRGB(i, j));
				int val12 = getGrayScale(imageOriginal.getRGB(i, j + 1));

				int val20 = getGrayScale(imageOriginal.getRGB(i + 1, j - 1));
				int val21 = getGrayScale(imageOriginal.getRGB(i + 1, j));
				int val22 = getGrayScale(imageOriginal.getRGB(i + 1, j + 1));

				int gradientX = ((-1 * val00) + (0 * val01) + (1 * val02)) + ((-2 * val10) + (0 * val11) + (2 * val12))
						+ ((-1 * val20) + (0 * val21) + (1 * val22));

				int gradientY = ((-1 * val00) + (-2 * val01) + (-1 * val02)) + ((0 * val10) + (0 * val11) + (0 * val12))
						+ ((1 * val20) + (2 * val21) + (1 * val22));

				double gval = Math.sqrt(Math.pow(gradientX, 2) + Math.pow(gradientY, 2));
				int gradient = (int) gval;

				if (maxGradient < gradient) {
					maxGradient = gradient;
				}

				edgeColors[i][j] = gradient;
			}
		}

		double scale = 255.0 / maxGradient;

		for (int i = 1; i < width - 1; i++) {
			for (int j = 1; j < height - 1; j++) {
				int edgeColor = edgeColors[i][j];
				edgeColor = (int) (edgeColor * scale);
				edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;
				imageEdgeDetection.setRGB(i, j, edgeColor);
			}
		}	
	}
	
	public static int  getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;
        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
        return gray;
    }
	
	/**
	 * converts a bufferedimage to binary form
	 * @param input
	 * @param threshold
	 * @return
	 */
	private static void convertToBinary() {
		imageBlackAndWhite = new BufferedImage(imageEdgeDetection.getWidth(), imageEdgeDetection.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		int threshold = 35;

		for (int i = 0; i < imageEdgeDetection.getWidth(); i++) {
			for (int j = 0; j < imageEdgeDetection.getHeight(); j++) {
				int rgb = imageEdgeDetection.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb) & 0xFF;
				int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
				if (gray >= threshold) {
					imageBlackAndWhite.setRGB(i, j, new Color(255, 255, 255).getRGB()); //white
				} else {
					imageBlackAndWhite.setRGB(i, j, new Color(0, 0, 0).getRGB()); //black
				}
			}
		}
	} 
	
	/**
	 * main-method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		EdgeDetectionBLABLA e = new EdgeDetectionBLABLA("files/flower.png");
		e.alterImageGrayScale();
		e.alterEdgeDetection();
		e.convertToBinary();
		e.displayImages();
	}
	
	//Time complexity: O(w*h) where w=width of the image and h=height of the image
}
