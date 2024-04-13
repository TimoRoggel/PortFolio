package org.freezer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;

public class QRcode {

    /*
    Deze methode genereert een QR-code met behulp van de zxing-bibliotheek.

    Parameters:
        - data: De gegevens die in de QR-code moeten worden opgenomen.
        - path: Het pad waar de QR-code moet worden opgeslagen.

    Throws:
        - WriterException: Als er een fout optreedt tijdens het schrijven van de QR-code.
        - IOException: Als er een fout optreedt tijdens het schrijven van de QR-code naar het bestand.
     */
    public static void generateQRCode(String data, String path) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}
