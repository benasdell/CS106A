/* 
 * File: ImageShopAlgorithms.java
 * --------------------------------------
 * Program given by Stanford University CS106A
 * Methods developed by Benjamin Asdell, 2019
 * Section: Wednesday, 5pm
 * TA: Trey Connelly
 * ImageShop Algorithms
 * 
 * This file contains all of the algorithms documented in ImageShopAlgorithmsInterface.java and includes
 * clockwise rotation, counterclockwise rotation, horizontal reflection, greenscreen background removal, blurring, 
 * grayscale contrast equalization, and crop functionality for ImageShop. The methods in this file rely
 * heavily on the use of 2D pixel arrays and other array manipulation. The GImage class is also used to provide
 * much of the internal functionality of each method. These methods can only be used on images loaded into ImageShop
 * by running ImageShop.java, another file in this project folder.
 * 
 */


import acm.graphics.*;

public class ImageShopAlgorithms implements ImageShopAlgorithmsInterface {

	/*
	 * (non-Javadoc)
	 * @see ImageShopAlgorithmsInterface#rotateLeft(acm.graphics.GImage)
	 * Method: Rotate Left
	 * This method implements a 90 degree counterclockwise rotation of the loaded image. It uses a transposition
	 * of the pixel array to achieve this. The updated image will have as many rows as the original image has 
	 * columns, and as many columns as the original has rows. It does have any impact on RGB values of the 
	 * transposed pixels.
	 */
	public GImage rotateLeft(GImage source) {
		int[][] img = source.getPixelArray(); //line repeated throughout each method, allows image to be iterated over in a 2D array
		int numRows = img.length; //this line is also repeated, returns vertical size of pixel array
		int numCols = img[0].length; //this line is once again repeated, returns horizontal size of pixel array
		int[][] newImg = new int[numCols][numRows];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				newImg[col][row] = img[row][numCols - (col +1)];
				}
		}
		GImage display = new GImage(newImg);
		return display;
	}

	/*
	 * (non-Javadoc)
	 * @see ImageShopAlgorithmsInterface#rotateRight(acm.graphics.GImage)
	 * Method: Rotate Right
	 * This method implements a 90 degree clockwise rotation of the loaded image. It uses a transposition
	 * of the pixel array to achieve this. The updated image will have as many rows as the original image has 
	 * columns, and as many columns as the original has rows. It does have any impact on RGB values of the 
	 * transposed pixels.
	 */
	public GImage rotateRight(GImage source) {
		int[][] img = source.getPixelArray();
		int numRows = img.length;
		int numCols = img[0].length;
		int[][] newImg = new int[numCols][numRows];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				newImg[col][row] = img[numRows -(row+1)][col];
				}
		}
		GImage display = new GImage(newImg);
		return display;
	}

	/*
	 * (non-Javadoc)
	 * @see ImageShopAlgorithmsInterface#flipHorizontal(acm.graphics.GImage)
	 * Method: Flip Horizontal
	 * This method implements a reflection of the loaded image across an imaginary y-axis. Based on the
	 * pixel array of the loaded image, it builds an new pixel array containing the same pixels in each
	 * row, but in reverse order with respect to the columns in each row. Once again, it does not impact
	 * the visual appearance of individual pixels, only the entire image due to the transposition of pixels.
	 */
	public GImage flipHorizontal(GImage source) {
		int[][] img = source.getPixelArray();
		int numRows = img.length;
		int numCols = img[0].length;
		int[][] newImg = new int[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col ++) {
				newImg[row][col] = img[row][numCols - (col+1)];
			}
		}
		GImage display = new GImage(newImg);
		return display;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ImageShopAlgorithmsInterface#negative(acm.graphics.GImage)
	 * Method: Negative
	 * This method implements a "negative" filter, which inverts the RGB values of any given pixel on the
	 * screen. Iterating over each pixel in the loaded image's pixel array, the method finds each pixel's
	 * RGB values and inverts it individuals. Calling this method twice will produce the original loaded image.
	 */
	public GImage negative(GImage source) {
		int[][] img = source.getPixelArray();
		int numRows = img.length;
		int numCols = img[0].length;
		int[][] newImg = new int[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				int pixel = img[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				newImg[row][col] = GImage.createRGBPixel(255 - red, 255 - green, 255 - blue);
			}
		}
		GImage display = new GImage(newImg);
		return display;
	}

	/* 
	 * (non-Javadoc)
	 * @see ImageShopAlgorithmsInterface#greenScreen(acm.graphics.GImage)
	 * Method: Green Screen
	 * This method facilitates the use of the pre-built "Overlay Image" function of ImageShop. By making any
	 * overtly green pixels transparent, any images with bright green backgrounds can easily be turned into
	 * workable overlays by this method. The method will return the loaded image with any green (with Green
	 * value greater than twice the highest other color value) pixels made entirely transparent.
	 */
	public GImage greenScreen(GImage source) {
		int[][] img = source.getPixelArray();
		int numRows = img.length;
		int numCols = img[0].length;
		int[][] newImg = new int[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				int pixel = img[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int alpha = GImage.getAlpha(pixel);
				if (green > 2* Math.max(red, blue)) {
					alpha = 0;
				}
				newImg[row][col] = GImage.createRGBPixel(red,green,blue,alpha);
			}
		}
		GImage display = new GImage(newImg);
		return display;
	}

	/*
	 * (non-Javadoc)
	 * @see ImageShopAlgorithmsInterface#blur(acm.graphics.GImage)
	 * Method: Blur
	 * This method softens the image visually by using surrounding pixels to bleed into and "dull" each
	 * individual pixel. This method implements a relatively abstract concept that I remember from my Python
	 * class last year, parallel arrays, in order to complete this task. Each pixel has its surrounding pixels
	 * checked for validity, then the valid surrounding pixels contribute their RGB values to the average value,
	 * which is then used to build the new pixel in the new pixel array. 
	 */
	public GImage blur(GImage source) {
		int[][] img = source.getPixelArray();
		int numRows = img.length;
		int numCols = img[0].length;
		int[][] newImg = new int[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				int avgRed = 0;
				int avgGreen = 0;
				int avgBlue = 0;
				int dx[] = {-1, -1, -1, 0, 0, 1, 1, 1}; //parallel arrays representing offset coordinates from selected pixel
				int dy[] = {1, 0, -1, 1, -1, 1, 0, -1};
				int count = 1;
				for (int i = 0; i < dx.length; i++) {
					if (pixelInBounds(img, row + dx[i], col + dy[i])) {
						int pixel = img[row + dx[i]][col + dy[i]];
						avgRed += GImage.getRed(pixel);
						avgGreen += GImage.getGreen(pixel);
						avgBlue += GImage.getBlue(pixel);
						count++;
					}
				}
				avgRed += GImage.getRed(img[row][col]);
				avgGreen += GImage.getGreen(img[row][col]);
				avgBlue += GImage.getBlue(img[row][col]);
				avgRed /= count; 
				avgGreen /=count;
				avgBlue /= count;
				newImg[row][col] = GImage.createRGBPixel(avgRed,avgGreen,avgBlue);
			}
		}
		GImage display = new GImage(newImg);
		return display;
	}

	/*
	 * (non-Javadoc)
	 * @see ImageShopAlgorithmsInterface#crop(acm.graphics.GImage, int, int, int, int)
	 * Method: Crop
	 * This method implements a simple crop, or cutout, of the original loaded image to the area
	 * selected and highlighted in ImageShop by a red rectangle. The size of the cropped image is exactly
	 * the same size as the selection rectangle.
	 */
	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
		int[][] img = source.getPixelArray();
		int[][] newImg = new int[cropHeight][cropWidth];
		for (int row = cropY; row < cropY + cropHeight; row++) {
			for (int col = cropX; col < cropX + cropWidth; col++) {
				newImg[row-cropY][col-cropX] = img[row][col];
			}
		}
		GImage display = new GImage(newImg);
		return display;
	}

	/*
	 * (non-Javadoc)
	 * @see ImageShopAlgorithmsInterface#equalize(acm.graphics.GImage)
	 * Method: Equalize
	 * This method implements a pair of luminosity histograms to increase the contrast on a given image.
	 * The first luminosity histogram is built by iterating over the entire pixel array and incrementing
	 * the value in a size-256 integer array whose index corresponds to that specific luminosity value. The 
	 * second histogram is cumulative, meaning the value at each index denotes the number of pixels with that
	 * luminosity value or lower. The final process of this method is reassigning pixels RGB values equal to a
	 * new luminosity that is calculated by using its original luminosity value relative to the entire range
	 * of luminosities (0-255). This method works best on grayscale images, as it will always return a 
	 * grayscale image.
	 */
	public GImage equalize(GImage source) {
		int[][] img = source.getPixelArray();
		int numRows = img.length;
		int numCols = img[0].length;
		int[][] newImg = new int[numRows][numCols];
		int numPixels = numRows * numCols;
		int[] lumHist = new int[256];
		for (int row = 0; row < numRows; row++) { //these for loops build the first luminosity histogram
			for (int col = 0; col < numCols; col++) {
				int pixel = img[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int luminosity = computeLuminosity(red,green,blue);
				lumHist[luminosity] ++;
			}
		}
		int[] cumulativeLumHist = new int[256]; //cumulative histogram is built from the first luminosity histogram
		cumulativeLumHist[0] = lumHist[0];
		for (int i = 1; i < lumHist.length; i++) {
			cumulativeLumHist[i] = lumHist[i] + cumulativeLumHist[i-1];
		}
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				int pixel = img[row][col];
				int red = GImage.getRed(pixel);
				int green = GImage.getGreen(pixel);
				int blue = GImage.getBlue(pixel);
				int luminosity = computeLuminosity(red,green,blue);
				int newLum = 255 * cumulativeLumHist[luminosity] / numPixels; //increases contrast by spreading pixel luminosity over the entire possible range of luminosities
				newImg[row][col] = GImage.createRGBPixel(newLum, newLum, newLum);
			}
		}
		GImage display = new GImage(newImg);
		return display;
	}
	
	/*
	 * Method: Pixel in Bounds
	 * This submethod helps the Blue method above achieve its purpose by running to check on any given pixel
	 * to see if it returns null (out of bounds). This method actually does so by checking if the pixel's 
	 * row and column coordinate fall within acceptable bounds given a certain pixel array to check. If the pixel
	 * is out of bounds of the given pixel array, the method returns false, which means the Blur method does not
	 * attempt to get its RGB values. If it is within bounds, the Blur method will continue to use that specific
	 * pixel to calculate the average RGB value.
	 */
	private boolean pixelInBounds(int[][] pixelArray, int row, int col) {
		if ((row >= 0) && (col >= 0) && (row < pixelArray.length) && (col < pixelArray[0].length)) {
			return true;
		}
		return false;
	}
}
