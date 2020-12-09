/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import clases.Articulo;
import clases.Categoria;
import clases.Estado;
import clases.Render;
import clases.Usuario;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import conn.Conn;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import jdk.internal.org.jline.terminal.MouseEvent;
import org.bson.Document;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author micha
 */
public class Interfaz extends javax.swing.JFrame implements ActionListener {

    public static ArrayList<Articulo> listado_articulos = new ArrayList<Articulo>();
    ;
  public static ArrayList<Categoria> listado_categoria = new ArrayList<Categoria>();

    ;

    public Interfaz() {

        initComponents();
        setLocationRelativeTo(null);
        actualizar_categorias();
        ArrayList<Categoria> categorias = Conn.listar_categorias();
        DefaultComboBoxModel model_cats = new DefaultComboBoxModel();
        model_cats.addElement(new Categoria("Todos"));
        categorias.forEach(categoria -> {
            model_cats.addElement(categoria);
        });
        cb_cat.setModel(model_cats);

        ArrayList<Estado> estados = Conn.listar_estados();
        DefaultComboBoxModel model_estados = new DefaultComboBoxModel();
        model_estados.addElement(new Estado("Todos", ""));
        estados.forEach(estado -> {
            model_estados.addElement(estado);
        });
        cb_estados.setModel(model_estados);

        actualizar_articulos();

    }

    public static void actualizar_categorias() {
        listado_categoria = Conn.listar_categorias();
        DefaultTableModel model_cat = (DefaultTableModel) tbl_categorias.getModel();
        model_cat.setRowCount(0);
        System.out.println();
        Interfaz.listado_categoria.forEach(a -> {
            model_cat.addRow(new Object[]{
                a.getId(),
                a.getNombre(),
                a.getCreado_el(),
                a.getCreado_por()
            });
        });
    }

    public static void actualizar_articulos() {
        tabla_listado.setDefaultRenderer(Object.class, new Render());
        JButton btn_editar = new JButton("Editar");
        JButton btn_eliminar = new JButton("Eliminar");

        Categoria cat = (Categoria) cb_cat.getSelectedItem();
        cat = cat.getId() == null ? null : cat;
        Estado st = (Estado) cb_estados.getSelectedItem();
        st = st.getId() == null ? null : st;
        listado_articulos = Conn.listar_articulos(cat, st);
        DefaultTableModel model = (DefaultTableModel) tabla_listado.getModel();
        model.setRowCount(0);

        tabla_listado.setDefaultEditor(Object.class, null);

        Interfaz.listado_articulos.forEach(a -> {
            model.addRow(new Object[]{
                a.getId(),
                a.getDescripcion(),
                a.getMarca(),
                a.getModelo(),
                a.getNum_serie(),
                a.getEstado().getId(),
                a.getCreado_el().toString(),
                a.getF_modiciacion().toString(),
                a.getObservaciones(),
                btn_editar,});
        });

        tabla_listado.setRowHeight(30);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        btnagregar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_categorias = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cb_cat = new javax.swing.JComboBox<>();
        cb_estados = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_listado = new javax.swing.JTable();
        btn_open_add = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnclose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnagregar.setText("agregar categoria");
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });

        tbl_categorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Fecha", "Usuario"
            }
        ));
        jScrollPane3.setViewportView(tbl_categorias);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(137, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Categorías", jPanel4);

        jLabel8.setText("Listar por Categorias");

        jLabel9.setText("Listar por Estado");

        cb_cat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Cat 1", "Cat 2", "Cat 3", "Cat 4", "Cat 5", " " }));
        cb_cat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_catItemStateChanged(evt);
            }
        });
        cb_cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_catActionPerformed(evt);
            }
        });

        cb_estados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Cat 1", "Cat 2", "Cat 3", "Cat 4", "Cat 5", " " }));
        cb_estados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_estadosActionPerformed(evt);
            }
        });

        tabla_listado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descripción", "Marca", "Modelo", "# de serie", "Estado", "F.Registro", "F.Mod", "Observaciones", "Opciones"
            }
        ));
        tabla_listado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_listadoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabla_listado);

        btn_open_add.setText("Agregar artículo");
        btn_open_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_open_addActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_open_add, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(401, 401, 401))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cb_estados, 0, 140, Short.MAX_VALUE)
                            .addComponent(cb_cat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(137, 675, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_cat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_open_add)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Listado", jPanel2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Registrados", "Extraviados", "Malogrados", "Total", "Opciones"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setText(" # Registrados ");

        jLabel5.setText(" # Extraviados ");

        jLabel6.setText(" # Malogrados ");

        jLabel7.setText(" # Suma total");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(348, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(299, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Resumen general", jPanel1);

        btnclose.setBackground(new java.awt.Color(217, 47, 61));
        btnclose.setFont(new java.awt.Font("Wide Latin", 0, 36)); // NOI18N
        btnclose.setText("Cerrar Sesion");
        btnclose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncloseActionPerformed(evt);
            }
        });
        jTabbedPane1.addTab("Cerrar Sesion", btnclose);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_open_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_open_addActionPerformed
        AddArticulo ag = new AddArticulo();
        ag.setVisible(true);
        // dispose();
    }//GEN-LAST:event_btn_open_addActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed

        AddCategoria categori = new AddCategoria();
        categori.setVisible(true);
        // dispose();
        //  Categoria cat = new Categoria();
        //  cat.AddCat();

    }//GEN-LAST:event_btnagregarActionPerformed

    private void btncloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseActionPerformed

        Login login = new Login();
        login.setVisible(true);
        dispose();


    }//GEN-LAST:event_btncloseActionPerformed

    private void cb_catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_catActionPerformed

        actualizar_articulos();

    }//GEN-LAST:event_cb_catActionPerformed

    private void cb_estadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_estadosActionPerformed

        actualizar_articulos();

    }//GEN-LAST:event_cb_estadosActionPerformed

    private void cb_catItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_catItemStateChanged


    }//GEN-LAST:event_cb_catItemStateChanged

    private void tabla_listadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_listadoMouseClicked
        int column = tabla_listado.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / tabla_listado.getRowHeight();

        String descripcion = tabla_listado.getValueAt(row, 1).toString();
        String marca = tabla_listado.getValueAt(row, 2).toString();
        String modelo = tabla_listado.getValueAt(row, 3).toString();
        String num_ser = tabla_listado.getValueAt(row, 4).toString();
        String observaciones = tabla_listado.getValueAt(row, 8).toString();

        if (row < tabla_listado.getRowCount() && row >= 0 && column < tabla_listado.getColumnCount() && column >= 0) {
            Object value = tabla_listado.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                EditArticulo ea = new EditArticulo();
                ea.setVisible(true);
                ea.llenar(descripcion, marca, modelo, num_ser, observaciones);
            }
        }
    }//GEN-LAST:event_tabla_listadoMouseClicked

    public static String editdatos() {
        return "hola";
    }

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_open_add;
    private javax.swing.JButton btnagregar;
    private javax.swing.JButton btnclose;
    private static javax.swing.JComboBox<String> cb_cat;
    private static javax.swing.JComboBox<String> cb_estados;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable tabla_listado;
    private static javax.swing.JTable tbl_categorias;
    // End of variables declaration//GEN-END:variables
}
