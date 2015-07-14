package generator;

public class qrGeneratorTest {
	public static void main(String args[]) {
		int imageId = 0;
		try {
			qrGenerator.createQR(imageId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
