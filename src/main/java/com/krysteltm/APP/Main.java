
package com.krysteltm.APP;

import com.krysteltm.UTIL.Theme;
import com.krysteltm.Views.Inicio;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        Theme.install();

        SwingUtilities.invokeLater(() -> {
            Inicio inicio = new Inicio();
            inicio.setVisible(true);
        });
    }
}
