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
	private final static String OUTPUT_FOLDER = System.getProperty("java.io.tmpdir");

	private pdfGenerator() {
	}

	public static File createPDF(String locationName, int locationID) throws Exception {
		pdfGenerator generator = new pdfGenerator();
		return generator.PDFgenerator(locationName, locationID);
	}

	private File PDFgenerator(String locationName, int locationID) throws Exception {
		String fileLocationString = qrGenerator.createQR(locationID).getAbsolutePath(); //OUTPUT_FOLDER + File.separator + Integer.toString(locationID);

		PDDocument document = new PDDocument();
		PDPage page = new PDPage(PDPage.PAGE_SIZE_LETTER);

		document.addPage(page);

		// Text:
		TextObject line1 = new TextObject("Need some directions?", page, PDType1Font.HELVETICA_BOLD_OBLIQUE, 42);
		TextObject line2 = new TextObject("Just ask the new interns!", page, PDType1Font.HELVETICA_BOLD, 50);

		TextObject line3 = new TextObject("You are at: " + locationName, page, PDType1Font.HELVETICA_BOLD_OBLIQUE, 22);

		TextObject line4 = new TextObject("TraQR (A project of #CernerHackfest2015)", page,
				PDType1Font.HELVETICA_BOLD_OBLIQUE, 22);

		// QR code:
		InputStream in = new FileInputStream(new File(fileLocationString));
		PDJpeg img = new PDJpeg(document, in);
		float imgX = centerOnPageX(page, img.getWidth());
		float imgY = centerOnPageY(page, img.getHeight());

		// Render the page:
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// Write lines.
		writeLine(contentStream, line1, 1, 8);
		writeLine(contentStream, line2, 1, 4);
		writeLine(contentStream, line3, 25, 32);
		writeLine(contentStream, line4, 7, 8);

		// Close text stream

		contentStream.drawImage(img, imgX, imgY);
		contentStream.close();

		File toSave = new File(fileLocationString.substring(0, fileLocationString.length()-4) + ".pdf");
		document.save(toSave);
		return toSave;
	}

	static void writeLine(PDPageContentStream contentStream, TextObject t) throws IOException {
		writeLine(contentStream, t, 1, 2);
	}

	static void writeLine(PDPageContentStream contentStream, TextObject t, int numerator, int denominator)
			throws IOException {
		contentStream.beginText();
		contentStream.setFont(t.getFont(), t.getFontSize());
		contentStream.moveTextPositionByAmount(t.getCenteredXCoordinate(),
				t.getFractionalYCoordinate(numerator, denominator));
		contentStream.drawString(t.getText());
		contentStream.endText();

	}

	static float centerOnPageX(PDPage page, float objectWidth) {

		return (page.getMediaBox().getWidth() - objectWidth) / 2;
	}

	static float centerOnPageY(PDPage page, float objectHeight) {
		return (page.getMediaBox().getHeight() - objectHeight) / 2;
	}

	class TextObject {
		String line;
		PDPage page;
		PDFont font;
		float width;
		float height;
		int fontSize;

		// An immutable class
		TextObject(String line, PDPage page, PDFont font, int fontSize) throws IOException {
			this.line = line;
			this.font = font;
			this.page = page;
			this.width = font.getStringWidth(line) / 1000 * fontSize;
			this.height = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
			this.fontSize = fontSize;
		}

		PDFont getFont() {
			return font;
		}

		int getFontSize() {
			return fontSize;
		}

		String getText() {
			return line;
		}

		float getCenteredXCoordinate() {
			float pageWidth = page.getMediaBox().getWidth();
			return (pageWidth - width) / 2;
		}

		float getFractionalYCoordinate(int numerator, int denominator) {
			float pageHeight = page.getMediaBox().getHeight();
			return pageHeight - (((pageHeight - height) * numerator) / denominator);
		}
	}
}
