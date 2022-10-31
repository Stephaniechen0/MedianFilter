/**
 * Program #4
 * Description: create an un-llama-fied picture from a group of llama-fied pictures.
 * CS108-1
 * Date: 3/13/21
 * @author Stephanie Chen
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.util.Collections;

public class MedianFilter {
	private BufferedImage filteredImage;
	private BufferedImage[] images;
	private File[] filer;
	
	public MedianFilter(String[] imageInputFilenames) { // constructor with array of names of the noisy images		
		filer = new File[imageInputFilenames.length];
		images = new BufferedImage[imageInputFilenames.length];
		
		//fill file with strings
		for (int i = 0; i < imageInputFilenames.length; i++) {
			filer[i] = new File(imageInputFilenames[i]);
			images[i] = readImage(filer[i]);
		}
	}
	
	public BufferedImage readImage(File imageFile) { // opens and reads in an image file
		BufferedImage locBuff = null;
		try {
			locBuff = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return locBuff;
	}
	
	public BufferedImage removeNoise() { // gets the median value for all pixels and returns the filtered noiseless image
		ArrayList<Integer> pxl = new ArrayList<Integer>();
		int median = 0;
		filteredImage = images[0];
		
		//start with pixel, goes through all images
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				pxl.clear();
				for (int i = 0; i < images.length; i++) {
					int rgb = images[i].getRGB(x, y); //color pixel value
					pxl.add(rgb);
				}
				Collections.sort(pxl);
				median = getMedianValue(pxl);
				filteredImage.setRGB(x, y, median);
			}
		}
		
		return filteredImage;
	}
	
	public int getMedianValue(ArrayList<Integer> pixels) { // returns the median value of the pixel(x,y) for all images				
		int medVal = pixels.get(4);
		return medVal;
	}
		
	public int writeImage(String outputFilename) { // writes filteredImage to the outputFilename jpg file. Returns 0 if successful, or -1 if an exception was thrown.
		File out = new File(outputFilename);
		try {
			ImageIO.write(filteredImage, "jpg", out);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public int getHeight() { // returns height (y-dimension) of filteredImage
		return images[0].getHeight();
	}
	
	public int getWidth() { // returns width (x-dimension) of filteredImage
		return images[0].getWidth();
	}
	
	public static void main(String[] args) {
		String[] imageInputNames = {"veg1.jpg", "veg2.jpg", "veg3.jpg", "veg4.jpg", "veg5.jpg", "veg6.jpg", "veg7.jpg", "veg8.jpg"};
		String output = "output.jpg";
		
		MedianFilter medFil = new MedianFilter(imageInputNames);
		medFil.removeNoise();
		medFil.writeImage(output);
	}
}
