package uppgift2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;

import java.io.*;
import javax.imageio.*;

public class FlipImage {
	private double contrast;
	private double brightness;

   private BufferedImage image;

   public BufferedImage getImage() {
      return this.image;
   }

   public FlipImage(BufferedImage img) {
      int width  = img.getWidth();
      int height = img.getHeight();
      
      //meh
      contrast = 20;
      brightness= 50;

      WritableRaster inraster  = img.getRaster();

      this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);      // Assume image is color
      int colors = 3;
      try {
    	  int dummy = inraster.getSample(0, 0, 2);
      } catch (Exception e) {              // Try accessing blue color value!
          this.image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); // If exception, it is monochrome!
          colors = 1;
      }
      
      WritableRaster outraster = this.image.getRaster();

      for (int i = 0; i < width; i++) {
          for (int j = 0; j < height; j++) {
              for (int c = 0; c < colors; c++) {
                  int value = inraster.getSample(i, j, c);
                  double bla = value * contrast + brightness;
//                  outraster.setSample(i, j, c, 255 - value ); //original
                  outraster.setSample(i, j, c, 255 - bla);
              }
          }
      }
   }
   
   public static void main(String[] args) {
       try {
           String filename = "files/template_1.png";
           BufferedImage img = ImageIO.read(new File(filename));
           FlipImage flip = new FlipImage(img);
           ImageIO.write(flip.getImage(), "PNG", new File("MEH.png"));
       } catch (Exception e) {
           System.out.println("Failed processing!\nUsage: java FlipImage 'image_file'\n\n" + e.toString());
       } 
   }
}


