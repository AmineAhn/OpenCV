/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devoiropencv;

import org.opencv.core.Core;

/**
 *
 * @author Amine
 */
public class DevoirOpenCV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // TODO code application logic here
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        TestForm form = new TestForm();
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }
    
}
