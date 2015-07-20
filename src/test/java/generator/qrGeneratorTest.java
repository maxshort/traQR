package generator;

import com.cerner.intern.traqr.generator.pdfGenerator;
import com.cerner.intern.traqr.generator.qrGenerator;

public class qrGeneratorTest {
	public static void main(String args[]) {
		int locationID = 0;
		try {
			qrGenerator.createQR(locationID);
			pdfGenerator.createPDF("New Location", locationID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
