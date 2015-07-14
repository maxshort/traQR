package generator;

import java.io.File;
import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * A simple class for generating QR codes.
 *
 * @author Chris Bartz (CB042580)
 */
public class qrGenerator {
	private static final BarcodeFormat DEFAULT_BARCODE = BarcodeFormat.QR_CODE;
	private final static String BASE_URL = "//servername/?=";
	private final static String OUTPUT_FOLDER = System.getProperty("user.home"); //Some directory
	private final static String IMAGE_FORMAT = "PNG";
	private final static int width = 300;
	private final static int height = 300;
	
	private qrGenerator() {
		
	}
	
	/**
	 * Static method to create a QR code.
	 * The default URL is {@code //servername/?="[imageId]"}.
	 * The file is saved to the user's home directory as "[imageId].png".
	 * @param imageId The ID number of the QR code to generate.
	 * @throws Exception On any error.
	 */
	public static void createQR(int imageId) throws Exception {
		
		
		String idString = Integer.toString(imageId); 
		
		
		String contents = BASE_URL + "\"" + idString + "\"";
		String outFileString = OUTPUT_FOLDER + File.separator + idString + ".png";
		
				
		BitMatrix matrix = new MultiFormatWriter().encode(contents, DEFAULT_BARCODE, width, height);
	    MatrixToImageWriter.writeToPath(matrix, IMAGE_FORMAT, Paths.get(outFileString));
	    System.out.println("Image saved to " + outFileString + ".");
	}		
}
