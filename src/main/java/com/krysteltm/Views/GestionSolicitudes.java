
package com.krysteltm.Views;

import com.krysteltm.MODEL.Estudiante;
import com.krysteltm.SERVICE.EstudianteService;
import com.krysteltm.UTIL.Theme;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;

public class GestionSolicitudes extends javax.swing.JFrame {
    private DefaultTableModel modelo;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GestionSolicitudes.class.getName());
    EstudianteService estudianteService;
    List<Estudiante> estudiantes;
    //funciona como mapeo de una matriz ´llave, valor´ : 099999999, rechazado
    private Map<String, String> estadosOriginales = new HashMap<>();

    /**
     * Creates new form GestionSolicitudes
     */

    public GestionSolicitudes() {
        initComponents();
        this.estudianteService = new EstudianteService();
        this.estudiantes = estudianteService.obtenerEstudiantes();
        configurarTabla();

        activarFiltroEnTiempoReal();
        aplicarEstilo();
    }

    private void aplicarEstilo() {
        setTitle("RESI-ESPOCH · Gestión de solicitudes");
        setLocationRelativeTo(null);
        Theme.applyAppIcon(this);

        jPanel1.setBackground(Theme.BACKGROUND);
        Theme.installGradient(jPanel2, Theme.PRIMARY, Theme.PRIMARY_DARK);
        jLabel1.setFont(jLabel1.getFont().deriveFont(java.awt.Font.BOLD, 24f));
        jLabel1.setForeground(java.awt.Color.WHITE);

        for (javax.swing.JLabel l : new javax.swing.JLabel[]{jLabel2, jLabel3}) {
            l.setFont(l.getFont().deriveFont(java.awt.Font.BOLD, 13f));
            l.setForeground(Theme.TEXT_SECONDARY);
        }

        cmbEstado.setFont(cmbEstado.getFont().deriveFont(java.awt.Font.PLAIN, 14f));
        txtNombre.putClientProperty("JTextField.placeholderText", "Buscar por nombre…");
        txtNombre.putClientProperty("JTextField.leadingIcon", Theme.icon("search", 16, Theme.TEXT_SECONDARY));
        txtNombre.setFont(txtNombre.getFont().deriveFont(java.awt.Font.PLAIN, 14f));

        btnFiltrar.setBackground(Theme.ACCENT);
        btnFiltrar.setForeground(java.awt.Color.WHITE);
        btnFiltrar.setIcon(Theme.icon("search", 14, java.awt.Color.WHITE));
        btnFiltrar.setFont(btnFiltrar.getFont().deriveFont(java.awt.Font.BOLD, 13f));
        btnFiltrar.setBorderPainted(false);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.putClientProperty("JButton.buttonType", "roundRect");

        btnGuardar.setBackground(Theme.SUCCESS);
        btnGuardar.setForeground(java.awt.Color.WHITE);
        btnGuardar.setFont(btnGuardar.getFont().deriveFont(java.awt.Font.BOLD, 16f));
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.putClientProperty("JButton.buttonType", "roundRect");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(Theme.BORDER, 1, true));
        jTable2.setRowHeight(36);
        jTable2.setShowGrid(false);
        jTable2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.getTableHeader().setFont(jTable2.getTableHeader().getFont().deriveFont(java.awt.Font.BOLD, 13f));
        jTable2.getColumnModel().getColumn(3).setCellRenderer(Theme.statusBadgeRenderer());
    }
   
    
    private void activarFiltroEnTiempoReal() {
    txtNombre.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

        private void filtrar() {
            String estado = cmbEstado.getSelectedItem().toString();
            String nombre = txtNombre.getText().trim();
            cargarTablaFiltrada(estado, nombre);
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            filtrar();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            filtrar();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            filtrar();
        }
    });
}

    private void cargarTablaFiltrada(String estado , String nombre) {

        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.setRowCount(0);
        estadosOriginales.clear();

        // OBTENER TEXTO DEL CAMPO
        String textoNombre = txtNombre.getText();
        if (textoNombre == null) {
            textoNombre = "";
        }
        textoNombre = textoNombre.toLowerCase();

        for (Estudiante e : estudiantes) {

            boolean cumpleEstado =
                    estado.equals("TODOS") ||
                    e.getEstadoSolicitud().equalsIgnoreCase(estado);

            boolean cumpleNombre =
                textoNombre.isEmpty() ||
                (e.getNombreCompleto().toLowerCase().contains(textoNombre));


            if (cumpleEstado && cumpleNombre) {

                modelo.addRow(new Object[]{
                    e.getCedula(),
                    e.getNombreCompleto(),
                    e.getCarrera(),
                    e.getEstadoSolicitud()
                });

                estadosOriginales.put(e.getCedula(), e.getEstadoSolicitud());
            }
        }
    }



    private void configurarTabla() {

        modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"CEDULA", "NOMBRE", "CARRERA", "ESTADO"}
        ) {
            @Override
            //editamos un ComboBox en la 4 columna
            public boolean isCellEditable(int row, int column) {
                return column == 3; // SOLO Estado editable
            }
        };
        
        jTable2.setModel(modelo);

        // ComboBox para la columna Estado
        JComboBox<String> comboEstado = new JComboBox<>();
        comboEstado.addItem("APROBADA");
        comboEstado.addItem("RECHAZADA");
        comboEstado.addItem("REVISION");

        TableColumn columnaEstado = jTable2.getColumnModel().getColumn(3);
        columnaEstado.setCellEditor(new DefaultCellEditor(comboEstado));
        
        for (Estudiante e : estudiantes) {
            String nombreCompleto = 
            (e.getNombre() != null ? e.getNombre() : "") + " " +
            (e.getApellido() != null ? e.getApellido() : "");

                modelo.addRow(new Object[]{
                    e.getCedula(),
                    nombreCompleto.trim(),
                    e.getCarrera(),
                    e.getEstadoSolicitud()
                });
            // Guardamos el estado original
            estadosOriginales.put(e.getCedula(), e.getEstadoSolicitud());
        }
        
        jTable2.setModel(modelo);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTIÓN DE SOLICITUDES");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(22, 22, 22))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "CEDULA", "NOMBRE", "CARRERA", "ESTADO"
            }
        ));
        jTable2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jTable2ComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        btnGuardar.setBackground(new java.awt.Color(153, 0, 0));
        btnGuardar.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel2.setText("ESTADO:");

        cmbEstado.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "APROBADA", "RECHAZADA", "PENDIENTE", "REVISION", "NO POSTULADO" }));
        cmbEstado.addActionListener(this::cmbEstadoActionPerformed);

        btnFiltrar.setBackground(new java.awt.Color(153, 0, 0));
        btnFiltrar.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnFiltrar.setForeground(new java.awt.Color(255, 255, 255));
        btnFiltrar.setText("FILTRAR");
        btnFiltrar.addActionListener(this::btnFiltrarActionPerformed);

        txtNombre.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombre.addActionListener(this::txtNombreActionPerformed);

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel3.setText("NOMBRE:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(223, 223, 223))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel3)
                        .addGap(37, 37, 37)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(btnFiltrar)))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable2ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jTable2ComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2ComponentAdded

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea guardar los cambios?",
            "Confirmar guardado",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // Si el usuario presiona NO, se cancela todo
        if (opcion != JOptionPane.YES_OPTION) {
        JOptionPane.showMessageDialog(
            this,
            "Operación cancelada",
            "Información",
            JOptionPane.INFORMATION_MESSAGE
        );
        return;
        }

        // --- SI CONFIRMA, SE CONTINÚA ---
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        int filas = modelo.getRowCount();
        int cambios = 0;

        for (int i = 0; i < filas; i++) {

            String cedula = modelo.getValueAt(i, 0).toString();
            String estadoNuevo = modelo.getValueAt(i, 3).toString();
            String estadoOriginal = estadosOriginales.get(cedula);

            if (!estadoNuevo.equals(estadoOriginal)) {

                boolean actualizado = estudianteService
                    .actualizarEstadoSolicitud(cedula, estadoNuevo);

                if (actualizado) {
                    cambios++;
                    estadosOriginales.put(cedula, estadoNuevo);
                }
            }
        }

    JOptionPane.showMessageDialog(
        this,
        "Cambios guardados correctamente.\nTotal de cambios: " + cambios,
        "Guardado exitoso",
        JOptionPane.INFORMATION_MESSAGE
    );
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        String estado = cmbEstado.getSelectedItem().toString();
        String nombre = txtNombre.getText().trim();

    cargarTablaFiltrada(estado, nombre);
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void cmbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEstadoActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GestionSolicitudes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
