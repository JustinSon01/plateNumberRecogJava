/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaanpr;

import com.github.sarxos.webcam.Webcam;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author EMAXX-A55-FM2
 */
public class CapturePlateNumberServiceTimerTask extends TimerTask {

    private Webcam webcam;
    private JLabel lbl;

    public void setWebCam(Webcam webcm) {
        this.webcam = webcm;
    }

    public void setLabelPlateNumber(JLabel l) {
        this.lbl = l;
    }

    @Override
    public void run() {

        //TODO upload the log  , image to the server , http://localhost:8000/carLog/upload
        if (this.webcam.isOpen()) {
            CarSnapshot carSnap = new CarSnapshot(this.webcam.getImage());
            String numberPlate = null;
            Intelligence intel = null;
            try {
                intel = new Intelligence(false);
            } catch (Exception ex) {
                Logger.getLogger(CamCap.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                //ask anpr if it can recognize
                numberPlate = intel.recognize(carSnap);
                //print result to output
            } catch (Exception ex) {
                Logger.getLogger(CamCap.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (numberPlate != null) {
                try {
                    //save the image to jpg
                    if (!this.lbl.getText().equals(numberPlate)) {
                        File fileCaptured = new File(String.format("capture-%d.jpg", System.currentTimeMillis()));
                        ImageIO.write(this.webcam.getImage(), "JPG", fileCaptured);
                    }
                    this.lbl.setText(numberPlate);
                } catch (IOException ex) {
                    Logger.getLogger(CapturePlateNumberServiceTimerTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.lbl.setText("Unknown");
            }

            System.out.println(numberPlate);
        } else {
            System.out.println("Service skipping");
        }

    }

}
