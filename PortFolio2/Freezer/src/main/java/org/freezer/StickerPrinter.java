package org.freezer;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class StickerPrinter {

    // Methode om de QR-code af te drukken
    /*
    Deze methode drukt de QR-code af op een sticker.

    Parameters:
        - qrImagePath: Het pad naar de afbeelding van de QR-code.

    Throws:
        - IOException: Als er een fout optreedt tijdens het lezen van de afbeelding.
        - PrintException: Als er een fout optreedt tijdens het afdrukken van de QR-code.
     */
    public void printQRCode(String qrImagePath) {
        try {
            // Formaat van de afbeelding aanpassen om binnen het afdrukgebied te passen
            File inputFile = new File(qrImagePath);
            BufferedImage resizedImage = resizeImage(inputFile, 80, 80); // Formaat wijzigen naar 80x80 pixels

            // BufferedImage converteren naar een byte-array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "PNG", baos);
            byte[] imageData = baos.toByteArray();

            // De standaard afdrukservice verkrijgen
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

            // Een Doc maken van de afbeeldingsgegevens
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.PNG;
            Doc doc = new SimpleDoc(imageData, flavor, null);

            // Een afdruktaak maken
            DocPrintJob printJob = printService.createPrintJob();

            // De marges en positie aanpassen indien nodig
            PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
            printAttributes.add(new MediaPrintableArea(10, 10, 120 / 2.83f, 120 / 2.83f, MediaPrintableArea.MM)); // Geconverteerd van pixels naar mm
            printAttributes.add(MediaSizeName.ISO_A4);

            // Het document afdrukken
            printJob.print(doc, printAttributes);

            System.out.println("QR-code succesvol afgedrukt.");
        } catch (IOException | PrintException e) {
            System.err.println("Het afdrukken van de QR-code is mislukt: " + e.getMessage());
        }
    }

    // Methode om de afbeelding te vergroten
    /*
    Deze methode vergroot de opgegeven afbeelding naar de gewenste grootte.

    Parameters:
        - inputFile: Het bestand dat de originele afbeelding bevat.
        - width: De breedte van de vergrote afbeelding.
        - height: De hoogte van de vergrote afbeelding.

    Returns:
        - BufferedImage: De vergrote afbeelding.

    Throws:
        - IOException: Als er een fout optreedt tijdens het lezen van de afbeelding.
     */
    public BufferedImage resizeImage(File inputFile, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(inputFile);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
