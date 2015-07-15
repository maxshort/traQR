package generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;

public class pdfGenerator {
	private final static String OUTPUT_FOLDER = System.getProperty("user.home");

	private pdfGenerator() {
	}

	public static void createPDF(String locationName, int locationID) throws IOException, COSVisitorException {
		String fileLocationString = OUTPUT_FOLDER + File.separator + Integer.toString(locationID);

		PDDocument document = new PDDocument();
		PDPage page = new PDPage(PDPage.PAGE_SIZE_LETTER);

		PDFont font1 = PDType1Font.HELVETICA_BOLD_OBLIQUE;
		PDFont font2 = PDType1Font.HELVETICA_BOLD;
		PDFont font3 = PDType1Font.HELVETICA_OBLIQUE;

		int font1Size = 24;
		int font2Size = 20;
		int font3Size = 22;

		document.addPage(page);

		// Text:
		String line1 = "Need some directions?";
		float line1X = centerTextOnPageX(page, font1, font1Size, line1);
		float line1Y = centerTextOnPageYFractional(page, font1, font1Size, line1, 1, 6);

		String line2 = "Just ask the new interns!";
		float line2X = centerTextOnPageX(page, font2, font2Size, line2);
		float line2Y = centerTextOnPageYFractional(page, font2, font2Size, line2, 2, 6);

		String line3 = "TraQR (A project of #CernerHackfest2015)";
		float line3X = centerTextOnPageX(page, font3, font3Size, line3);
		float line3Y = centerTextOnPageYFractional(page, font3, font3Size, line3, 5, 6);

		// QR code:
		InputStream in = new FileInputStream(new File(fileLocationString + ".jpg"));
		PDJpeg img = new PDJpeg(document, in);
		float imgX = centerOnPageX(page, img.getWidth());
		float imgY = centerOnPageYFractional(page, img.getHeight(), 1, 2);

		// Render the page:
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		contentStream.beginText();

		// Line1
		contentStream.setFont(font1, font1Size);
		contentStream.moveTextPositionByAmount(line1X, line1Y);
		contentStream.drawString(line1);

		// Line2
		contentStream.setFont(font2, font2Size);
		contentStream.moveTextPositionByAmount(line2X, line2Y);
		contentStream.drawString(line2);

		// Line3
		contentStream.setFont(font3, font3Size);
		contentStream.moveTextPositionByAmount(line3X, line3Y);
		contentStream.drawString(line3);
		contentStream.endText();

		// contentStream.drawImage(img, imgX, imgY);
		contentStream.close();

		document.save(fileLocationString + ".pdf");
	}

	static float centerTextOnPageX(PDPage page, PDFont font, int fontSize, String line) throws IOException {
		float lineWidth = font.getStringWidth(line) / 1000 * fontSize;
		return centerOnPageX(page, lineWidth) - (lineWidth / 2);
	}

	static float centerTextOnPageYFractional(PDPage page, PDFont font, int fontSize, String line, int numerator,
			int denominator) {
		float lineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
		return centerOnPageYFractional(page, lineHeight, numerator, denominator);
	}

	static float centerOnPageX(PDPage page, float objectWidth) {

		return (page.getMediaBox().getWidth() - objectWidth) / 2;
	}

	static float centerOnPageYFractional(PDPage page, float objectHeight, int numerator, int denominator) {
		return ((page.getMediaBox().getHeight() - objectHeight) * (numerator / (float) denominator));
	}
}
