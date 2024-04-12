package org.freezer;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class StickerPrinter {

    // Method to print the QR code
    public void printQRCode(String qrImagePath) {
        try {
            // Resize the image to fit within the printable area
            File inputFile = new File(qrImagePath);
            BufferedImage resizedImage = resizeImage(inputFile, 80, 80); // Resize to 80x80 pixels

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "PNG", baos);
            byte[] imageData = baos.toByteArray();

            // Get the default print service
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

            // Create a Doc from the image data
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.PNG;
            Doc doc = new SimpleDoc(imageData, flavor, null);

            // Create a print job
            DocPrintJob printJob = printService.createPrintJob();

            // Adjust the margins and position as needed
            PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
            printAttributes.add(new MediaPrintableArea(10, 10, 120 / 2.83f, 120 / 2.83f, MediaPrintableArea.MM)); // Converted from pixels to mm
            printAttributes.add(MediaSizeName.ISO_A4);

            // Print the document
            printJob.print(doc, printAttributes);

            System.out.println("QR code printed successfully.");
        } catch (IOException | PrintException e) {
            System.err.println("Failed to print QR code: " + e.getMessage());
        }
    }

    // Method to resize the image
    public BufferedImage resizeImage(File inputFile, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(inputFile);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
