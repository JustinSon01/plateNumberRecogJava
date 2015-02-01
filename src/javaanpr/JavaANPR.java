/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaanpr;

import com.github.sarxos.webcam.Webcam;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;
import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author EMAXX-A55-FM2
 */
public class JavaANPR {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Intelligence intel = null;
        try {
            intel = new Intelligence(false);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(JavaANPR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(JavaANPR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JavaANPR.class.getName()).log(Level.SEVERE, null, ex);
        }
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        CarSnapshot carSnap = new CarSnapshot(webcam.getImage());
        String numberPlate = null;
        try {
            numberPlate = intel.recognize(carSnap);
        } catch (Exception ex) {
            Logger.getLogger(JavaANPR.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(numberPlate);
        //save the image
        File fileCaptured = new File(String.format("capture-%d.jpg", System.currentTimeMillis()));
        ImageIO.write(webcam.getImage(), "JPG", fileCaptured);
        

    }

}
