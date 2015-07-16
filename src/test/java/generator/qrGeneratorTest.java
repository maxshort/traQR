package generator;

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
