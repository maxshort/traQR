package com.cerner.intern.traqr.generator;

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
	public final static String OUTPUT_FOLDER = System.getProperty("java.io.tmpdir");
	public final static String IMAGE_FORMAT = "JPG";

	private static final BarcodeFormat DEFAULT_BARCODE = BarcodeFormat.QR_CODE;
	private final static String BASE_URL = "https://traqr-docspock.c9.io/directions?fromLocation=";
	private final static int width = 300;
	private final static int height = 300;

	private qrGenerator() {

	}

	/**
	 * Static method to create a QR code. The default URL is
	 * {@code //servername/?="[imageId]"}. The file is saved to the user's home
	 * directory as "[imageId].png".
	 * 
	 * @param locationID
	 *            The ID number of the QR code to generate.
	 * @throws Exception
	 *             On any error.
	 */
	public static File createQR(int locationID) throws Exception {

		String idString = Integer.toString(locationID);

		String contents = BASE_URL + idString;
		String outFileString = OUTPUT_FOLDER + File.separator + idString + "." + IMAGE_FORMAT.toLowerCase();

		BitMatrix matrix = new MultiFormatWriter().encode(contents, DEFAULT_BARCODE, width, height);
		MatrixToImageWriter.writeToPath(matrix, IMAGE_FORMAT, Paths.get(outFileString));
		return new File(outFileString);
	}
}
