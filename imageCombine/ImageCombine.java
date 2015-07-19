import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.image.WritableRaster;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class ImageCombine {
	
	//first file name is map
	//then pass in list of filenames that have base-64 strings.
	//last file is output image file.
	public static void main(String[] args) throws IOException{
		
		BufferedImage map = ImageIO.read(new FileInputStream(args[0]));
		
		List<BufferedImage> imageList = new ArrayList<>();
		imageList.add(map);
		
		for (int i = 1; i < args.length -1; i++) {
			imageList.add(fromBase64(streamToString(new FileInputStream(args[i]))));
		}
				
		BufferedImage combined = combineImages(imageList);
		
		ImageIO.write(combined, "PNG", new File(args[args.length-1]));
	}                  
	
	//ASSUMES ALL IMAGES ARE THE SAME SIZE...
	public static BufferedImage combineImages(List<BufferedImage> images) {
		//alternate the image colors
		//1 is map image
		for (int i = 1; i<images.size();i++) {
			System.out.println(i%2);
			colorPicture(images.get(i), (i%2==1)?Color.BLUE:Color.RED);
		}		
		BufferedImage combined = new BufferedImage(images.get(0).getWidth(), images.get(0).getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = combined.getGraphics();
		for (int i=0; i<images.size();i++) {
			g.drawImage(images.get(i), 0, 0, null);
		}
		g.dispose();
		return combined;
	}
	
	public static BufferedImage fromBase64(String s) throws IOException{
		s = s.replace("data:image/png;base64,","");
		s = s.replace("\uFEFF", ""); //extremely annoying no-break space
		byte[] bytes = Base64.getDecoder().decode(s);
		return ImageIO.read(new ByteArrayInputStream(bytes));
	}
	
	//not used right now...
	public static String streamToString(InputStream is) throws IOException {
		StringBuilder builder = new StringBuilder();
		byte[] buffer = new byte[4096];
		int bytesRead = 0;
		while (bytesRead >=0) {
			bytesRead = is.read(buffer, 0, 4096);
			if (bytesRead > 0)
			{
				builder.append(new String(buffer, 0, bytesRead, "UTF-8"));
			}
		}
		return builder.toString();
	}
	
	public static void colorPicture(BufferedImage image, Color color) {
		System.out.println("COLOR HERE: " + color);
		WritableRaster raster = image.getRaster();
		int width = image.getWidth();
		int height = image.getHeight();
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int[] pixels = raster.getPixel(x, y, (int[])null);
				pixels[0] = color.getRed();
				pixels[1] = color.getGreen();
				pixels[2] = color.getBlue();
				raster.setPixel(x, y, pixels);
			}
		}
	}
	
}