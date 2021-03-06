package org.mudit.tambola.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class PdfToImage {
	
	private static final String OUTPUT_DIR = "C:\\Users\\mudit\\Documents\\Housie\\Tambola-Tickets-Set-1\\";

    public static void main(String[] args) throws Exception{

        try (final PDDocument document = PDDocument.load(new File("C:\\Users\\mudit\\Documents\\Housie\\Tambola-Tickets-Set-1\\Ticket-1.pdf"))){
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page)
            {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                String fileName = OUTPUT_DIR + "image-" + page + ".jpg";
                ImageIOUtil.writeImage(bim, fileName, 300);
            }
            document.close();
        } catch (IOException e){
            System.err.println("Exception while trying to create pdf document - " + e);
        }
    }

}
