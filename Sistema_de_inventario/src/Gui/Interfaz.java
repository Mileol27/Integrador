/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import clases.Articulo;
import clases.Categoria;
import clases.Estado;
import Tools.Render;
import clases.EvActualizacion;
import clases.EvCreacion;
import clases.EvEliminacion;
import clases.Evento;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author micha
 */
public class Interfaz extends javax.swing.JFrame implements ActionListener {

    public static ArrayList<Articulo> listado_articulos = new ArrayList<Articulo>();

    public static ArrayList<Categoria> listado_categoria = new ArrayList<Categoria>();

    public static ArrayList<Estado> listado_estados = new ArrayList<Estado>();

    public static ArrayList<Usuario> listado_users = new ArrayList<Usuario>();

    public static ArrayList<Evento> listado_logs = new ArrayList<Evento>();

    public Interfaz() {

        initComponents();
        setLocationRelativeTo(null);
        lbl_nombre.setText(Conn.user_logged.getNombre());
        actualizar_cmbo_estados();
        actualizar_estados();
        actualizar_categorias();
        actualizar_users();
        actualizar_cmbo_categorias();
        actualizar_logs();

        ArrayList<Estado> estados = Conn.listar_estados();
        DefaultComboBoxModel model_estados = new DefaultComboBoxModel();
        model_estados.addElement(new Estado("Todos", ""));
        estados.forEach(estado -> {
            model_estados.addElement(estado);
        });
        cb_estados.setModel(model_estados);

        actualizar_articulos();

        // Ocultar los tabs de amdin y botones eliminar a usuarios no admin
        if (!Conn.user_logged.isAdmin()) {
            pan_usuario.remove(4);
            pan_usuario.remove(2);
            btn_eliminar_cat.setVisible(false);
            btn_eliminar_user.setVisible(false);
            btn_eliminar_articulo.setVisible(false);
        }

    }

    public static void actualizar_cmbo_categorias() {
        ArrayList<Categoria> categorias = Conn.listar_categorias();
        DefaultComboBoxModel model_cats = new DefaultComboBoxModel();
        model_cats.addElement(new Categoria("Todos"));
        categorias.forEach(categoria -> {
            model_cats.addElement(categoria);
        });
        cb_cat.setModel(model_cats);
    }

    public static void actualizar_cmbo_estados() {
        ArrayList<Estado> estados = Conn.listar_estados();
        DefaultComboBoxModel model_estado = new DefaultComboBoxModel();
        model_estado.addElement(new Estado("Todos"));
        estados.forEach(categoria -> {
            model_estado.addElement(categoria);
        });
        cb_estados.setModel(model_estado);
    }

    public static void actualizar_categorias() {
        tabla_categorias.setDefaultRenderer(Object.class, new Render());
        JButton btn_editar = new JButton("Editar");
        listado_categoria = Conn.listar_categorias();
        DefaultTableModel model_cat = (DefaultTableModel) tabla_categorias.getModel();
        model_cat.setRowCount(0);
        System.out.println();
        Interfaz.listado_categoria.forEach(a -> {
            model_cat.addRow(new Object[]{
                a.getId(),
                a.getNombre(),
                a.getCreado_el(),
                a.getCreado_por().getUsername(),
                btn_editar
            });
        });
        tabla_categorias.setRowHeight(30);
    }

    public static void actualizar_estados() {
        tabla_estados.setDefaultRenderer(Object.class, new Render());
        JButton btn_editar = new JButton("Editar");
        listado_estados = Conn.listar_estados();
        DefaultTableModel model_estado = (DefaultTableModel) tabla_estados.getModel();
        model_estado.setRowCount(0);
        System.out.println();
        Interfaz.listado_estados.forEach(a -> {
            model_estado.addRow(new Object[]{
                a.getId(),
                a.getNombre(),
                a.getDescripcion(),
                btn_editar
            });
        });
        tabla_estados.setRowHeight(30);
    }

    public static void actualizar_users() {
        tbl_users.setDefaultRenderer(Object.class, new Render());
        JButton btn_editar = new JButton("Editar");
        listado_users = Conn.listar_users();
        DefaultTableModel model_user = (DefaultTableModel) tbl_users.getModel();
        model_user.setRowCount(0);
        tbl_users.setDefaultEditor(Object.class, null);
        System.out.println();
        Interfaz.listado_users.forEach(a -> {
            model_user.addRow(new Object[]{
                a.getId(),
                a.getNombre(),
                a.getApellido(),
                a.getUsername(),
                a.getPassword(),
                a.isActivo() == true ? "SI" : "NO",
                a.isAdmin() == true ? "SI" : "NO",
                btn_editar
            });
        });
        tbl_users.setRowHeight(30);
    }

    public static void actualizar_articulos() {
        tabla_listado.setDefaultRenderer(Object.class, new Render());
        JButton btn_editar = new JButton("Editar");

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
                a.getEstado().getNombre(),
                a.getCategoria().getNombre(),
                a.getCreado_el().toString(),
                a.getModificado_el().toString(),
                a.getObservaciones(),
                btn_editar
            });
        });

        tabla_listado.setRowHeight(30);
    }

    public static void actualizar_logs() {
        Date f = new Date();
        tabla_logs.setDefaultEditor(Object.class, null);
        int m = combo_mes.getSelectedIndex();
        String mes = "";
        switch (m) {
            case 0:
                Month mon = LocalDate.now().getMonth();
                mes = mon.getDisplayName(TextStyle.FULL, new Locale("en", "En"));
                break;
            case 1:
                mes = "January";
                break;
            case 2:
                mes = "February";
                break;
            case 3:
                mes = "March";
                break;
            case 4:
                mes = "April";
                break;
            case 5:
                mes = "May";
                break;
            case 6:
                mes = "June";
                break;
            case 7:
                mes = "July";
                break;
            case 8:
                mes = "August";
                break;
            case 9:
                mes = "September";
                break;
            case 10:
                mes = "October";
                break;
            case 11:
                mes = "November";
                break;
            case 12:
                mes = "December";
                break;

        }

        String str_date = "11-" + mes + "-2020";
        DateFormat formatter;
        Date fecha = null;
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            fecha = formatter.parse(str_date);
        } catch (ParseException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("MES :" + mes);

        listado_logs = Conn.get_log(fecha);
        DefaultTableModel model = (DefaultTableModel) tabla_logs.getModel();
        model.setRowCount(0);

        Interfaz.listado_logs.forEach(a -> {
            String tipo;
            String info;
            if (a instanceof EvCreacion) {
                tipo = "Creación";
                info = "---";
            } else if (a instanceof EvActualizacion) {
                EvActualizacion actu = (EvActualizacion) a;
                tipo = "Actualización";
                info = actu.getDetalle();
            } else {
                EvEliminacion elim = (EvEliminacion) a;
                tipo = "Eliminación";
                info = elim.getMotivo();
            }
            model.addRow(new Object[]{
                a.getId(),
                a.getArticulo().getId(),
                tipo,
                a.getCreado_el(),
                info
            });
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        pan_usuario = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        btnagregar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_categorias = new javax.swing.JTable();
        btn_editar_cat = new javax.swing.JToggleButton();
        jButton3 = new javax.swing.JButton();
        btn_eliminar_cat = new javax.swing.JButton();
        jpanelestados = new javax.swing.JPanel();
        btnagregar1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla_estados = new javax.swing.JTable();
        btn_editar_cat1 = new javax.swing.JToggleButton();
        jButton4 = new javax.swing.JButton();
        btn_eliminar_cat1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cb_cat = new javax.swing.JComboBox<>();
        cb_estados = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_listado = new javax.swing.JTable();
        btn_open_add = new javax.swing.JButton();
        btn_eliminar_articulo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_resumen = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_users = new javax.swing.JTable();
        btn_add = new javax.swing.JButton();
        lbl_nombre = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_eliminar_user = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabla_logs = new javax.swing.JTable();
        combo_mes = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        btn_cerrar_sesion = new javax.swing.JMenuItem();
        btn_salir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        pan_usuario.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnagregar.setBackground(new java.awt.Color(0, 204, 102));
        btnagregar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnagregar.setText("agregar categoria");
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });

        tabla_categorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Fecha de creación", "Usuario", "opciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_categorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_categoriasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabla_categorias);
        if (tabla_categorias.getColumnModel().getColumnCount() > 0) {
            tabla_categorias.getColumnModel().getColumn(2).setResizable(false);
            tabla_categorias.getColumnModel().getColumn(4).setResizable(false);
        }

        btn_editar_cat.setBackground(new java.awt.Color(255, 255, 255));
        btn_editar_cat.setForeground(new java.awt.Color(255, 255, 255));
        btn_editar_cat.setBorder(null);
        btn_editar_cat.setContentAreaFilled(false);
        btn_editar_cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_catActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(165, 19, 19));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Cerrar Session");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btn_eliminar_cat.setBackground(new java.awt.Color(165, 19, 19));
        btn_eliminar_cat.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminar_cat.setText("Eliminar");
        btn_eliminar_cat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_eliminar_cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_catActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_editar_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_eliminar_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(449, 449, 449)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(450, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_editar_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_eliminar_cat, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(246, 246, 246)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(273, Short.MAX_VALUE)))
        );

        pan_usuario.addTab("Categorías", jPanel4);

        jpanelestados.setBackground(new java.awt.Color(255, 255, 255));

        btnagregar1.setBackground(new java.awt.Color(0, 204, 102));
        btnagregar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnagregar1.setText("agregar estado");
        btnagregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar1ActionPerformed(evt);
            }
        });

        tabla_estados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Descripción", "opciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_estados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_estadosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabla_estados);
        if (tabla_estados.getColumnModel().getColumnCount() > 0) {
            tabla_estados.getColumnModel().getColumn(2).setResizable(false);
            tabla_estados.getColumnModel().getColumn(3).setResizable(false);
        }

        btn_editar_cat1.setBackground(new java.awt.Color(255, 255, 255));
        btn_editar_cat1.setForeground(new java.awt.Color(255, 255, 255));
        btn_editar_cat1.setBorder(null);
        btn_editar_cat1.setContentAreaFilled(false);
        btn_editar_cat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_cat1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(165, 19, 19));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Cerrar Session");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btn_eliminar_cat1.setBackground(new java.awt.Color(165, 19, 19));
        btn_eliminar_cat1.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminar_cat1.setText("Eliminar");
        btn_eliminar_cat1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_eliminar_cat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_cat1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanelestadosLayout = new javax.swing.GroupLayout(jpanelestados);
        jpanelestados.setLayout(jpanelestadosLayout);
        jpanelestadosLayout.setHorizontalGroup(
            jpanelestadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelestadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelestadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelestadosLayout.createSequentialGroup()
                        .addComponent(btnagregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_editar_cat1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_eliminar_cat1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jpanelestadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpanelestadosLayout.createSequentialGroup()
                    .addGap(449, 449, 449)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(472, Short.MAX_VALUE)))
        );
        jpanelestadosLayout.setVerticalGroup(
            jpanelestadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelestadosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jpanelestadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_editar_cat1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpanelestadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnagregar1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_eliminar_cat1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(jpanelestadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpanelestadosLayout.createSequentialGroup()
                    .addGap(246, 246, 246)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(273, Short.MAX_VALUE)))
        );

        pan_usuario.addTab("Estados", jpanelestados);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

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
                "ID", "Descripción", "Marca", "Modelo", "# de serie", "Estado", "Categorias", "F.Registro", "F.Mod", "Observaciones", "Opciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        btn_eliminar_articulo.setBackground(new java.awt.Color(165, 19, 19));
        btn_eliminar_articulo.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminar_articulo.setText("Eliminar");
        btn_eliminar_articulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_articuloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(506, Short.MAX_VALUE)
                .addComponent(btn_open_add, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(401, 401, 401))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cb_estados, 0, 140, Short.MAX_VALUE)
                            .addComponent(cb_cat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 598, Short.MAX_VALUE)
                        .addComponent(btn_eliminar_articulo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_cat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_estados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_eliminar_articulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btn_open_add)
                .addContainerGap())
        );

        pan_usuario.addTab("Listado", jPanel2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabla_resumen.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabla_resumen);

        jLabel4.setText(" # Registrados ");

        jLabel5.setText(" # Extraviados ");

        jLabel6.setText(" # Malogrados ");

        jLabel7.setText(" # Suma total");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 329, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(44, 44, 44)
                .addComponent(jLabel5)
                .addGap(48, 48, 48)
                .addComponent(jLabel6)
                .addGap(56, 56, 56)
                .addComponent(jLabel7)
                .addGap(281, 281, 281))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        pan_usuario.addTab("Resumen general", jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tbl_users.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Apellido", "Nombre", "Usuario", "Contraseña", "Activo", "Administrador", "Opciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_usersMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_users);

        btn_add.setText("Agregar Usuario");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        lbl_nombre.setBackground(new java.awt.Color(153, 51, 255));
        lbl_nombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_nombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Persona Logueada : ");

        btn_eliminar_user.setBackground(new java.awt.Color(165, 19, 19));
        btn_eliminar_user.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminar_user.setText("Eliminar");
        btn_eliminar_user.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_eliminar_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_userActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(413, 413, 413)
                        .addComponent(btn_add)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_eliminar_user, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                    .addComponent(btn_eliminar_user, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(btn_add)
                .addGap(38, 38, 38))
        );

        pan_usuario.addTab("Users", jPanel5);

        tabla_logs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID logs", "Artículo ID", "Tipo de evento", "Fecha", "Más información"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tabla_logs);

        combo_mes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona ", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        combo_mes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_mesActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Seleccione Mes");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(combo_mes, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(781, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(jScrollPane5)
                    .addGap(15, 15, 15)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_mes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(507, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );

        pan_usuario.addTab("Log", jPanel6);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pan_usuario)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pan_usuario)
        );

        jMenu1.setText("Archivo");

        btn_cerrar_sesion.setText("Cerrar Sesión");
        btn_cerrar_sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrar_sesionActionPerformed(evt);
            }
        });
        jMenu1.add(btn_cerrar_sesion);

        btn_salir.setText("Salir");
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        jMenu1.add(btn_salir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (Conn.user_logged.isAdmin() == true) {
            NuevoUsuario nu = new NuevoUsuario();
            nu.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no es un administrador");
        }

        //dispose();

    }//GEN-LAST:event_btn_addActionPerformed

    private void tbl_usersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_usersMouseClicked

        int column = tbl_users.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / tbl_users.getRowHeight();

        ObjectId id = (ObjectId) tbl_users.getValueAt(row, 0);
        String nombre = tbl_users.getValueAt(row, 1).toString();
        String apellido = tbl_users.getValueAt(row, 2).toString();
        String user = tbl_users.getValueAt(row, 3).toString();
        String password = tbl_users.getValueAt(row, 4).toString();
        String activo = tbl_users.getValueAt(row, 5).toString();
        String admin = tbl_users.getValueAt(row, 6).toString();

        if (row < tbl_users.getRowCount() && row >= 0 && column < tbl_users.getColumnCount() && column >= 0) {
            Object value = tbl_users.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                NuevoUsuario view_edit = new NuevoUsuario();
                view_edit.lbl_usuario.setText("Editar Usuario");
                view_edit.setVisible(true);
                view_edit.llenar(id, nombre, apellido, user, password, admin, activo);
            }
        }

    }//GEN-LAST:event_tbl_usersMouseClicked

    private void btn_open_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_open_addActionPerformed

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("inventario");
        MongoCollection<Document> col = database.getCollection("categorias");
        if (col.countDocuments() == 0) {
            JOptionPane.showMessageDialog(null, "Primero agregue categorias");
        } else {
            AddArticulo ag = new AddArticulo();
            ag.setVisible(true);
        }

        // dispose();
    }//GEN-LAST:event_btn_open_addActionPerformed

    private void tabla_listadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_listadoMouseClicked
        int column = tabla_listado.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / tabla_listado.getRowHeight();

        ObjectId id = (ObjectId) tabla_listado.getValueAt(row, 0);
        String descripcion = tabla_listado.getValueAt(row, 1).toString();
        String marca = tabla_listado.getValueAt(row, 2).toString();
        String modelo = tabla_listado.getValueAt(row, 3).toString();
        String num_ser = tabla_listado.getValueAt(row, 4).toString();
        String estado = tabla_listado.getValueAt(row, 5).toString();
        String categoria = tabla_listado.getValueAt(row, 6).toString();
        String observaciones = tabla_listado.getValueAt(row, 9).toString();

        if (row < tabla_listado.getRowCount() && row >= 0 && column < tabla_listado.getColumnCount() && column >= 0) {
            Object value = tabla_listado.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                AddArticulo view_edit = new AddArticulo();
                view_edit.lbl_titulo.setText("Editar Artículo");
                view_edit.setVisible(true);
                view_edit.llenar(id, descripcion, marca, modelo, num_ser, observaciones, estado, categoria);
            }
        }
    }//GEN-LAST:event_tabla_listadoMouseClicked

    private void cb_estadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_estadosActionPerformed

        actualizar_articulos();
    }//GEN-LAST:event_cb_estadosActionPerformed

    private void cb_catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_catActionPerformed

        actualizar_articulos();
    }//GEN-LAST:event_cb_catActionPerformed

    private void cb_catItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_catItemStateChanged

    }//GEN-LAST:event_cb_catItemStateChanged

    private void btn_editar_catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_catActionPerformed

        int row = tabla_categorias.getSelectedRow();
        if (row != -1) {
            String nombre = (String) tabla_categorias.getValueAt(row, 1);
            ObjectId id = (ObjectId) tabla_categorias.getValueAt(row, 0);
            AddCategoria categoria = new AddCategoria();
            categoria.txtcat.setText(nombre);
            categoria.lbl_titulo.setText("MODIFICAR CATEGORIA");
            categoria.id = id;
            categoria.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una categoria para editar");
        }
    }//GEN-LAST:event_btn_editar_catActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        if (Conn.user_logged.isAdmin() == true) {
            AddCategoria categori = new AddCategoria();
            categori.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no es administrador");
        }

        // dispose();

    }//GEN-LAST:event_btnagregarActionPerformed

    private void btn_eliminar_articuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_articuloActionPerformed
        // Agregar confirmacion de eliminación
        int row = tabla_listado.getSelectedRow();
        if (row != -1) {
            ObjectId id = (ObjectId) tabla_listado.getValueAt(row, 0);
            Articulo art = new Articulo(id);
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "¿Esta Seguro de eliminar el Articulo?", "Alerta", dialogButton);
            if (dialogResult == 0) {
                art.eliminar();
                actualizar_articulos();
                actualizar_logs();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un artículo para eliminar");
        }
    }//GEN-LAST:event_btn_eliminar_articuloActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_eliminar_catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_catActionPerformed
        int row = tabla_categorias.getSelectedRow();

        if (row != -1) {
            ObjectId id = (ObjectId) tabla_categorias.getValueAt(row, 0);
            Categoria cat = new Categoria(id);
            int articulos_total = Conn.listar_articulos(cat, null).size();
            if (articulos_total > 0) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar, articulos asociados");
            } else {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(this, "¿Esta Seguro de eliminar la categoria?", "Alerta", dialogButton);
                if (dialogResult == 0) {
                    cat.eliminar();
                    actualizar_categorias();
                    actualizar_cmbo_categorias();
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una categoria para eliminar");
        }
    }//GEN-LAST:event_btn_eliminar_catActionPerformed

    private void btn_eliminar_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_userActionPerformed
        int row = tbl_users.getSelectedRow();

        if (row != -1) {
            ObjectId id = (ObjectId) tbl_users.getValueAt(row, 0);
            Usuario usuario = new Usuario(id);
            int articulos_total = Conn.cantidad_articulos_user(usuario).size();
            if (articulos_total > 0) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar, articulos asociados");
            } else {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(this, "¿Esta Seguro de eliminar el Usuario?", "Alerta", dialogButton);
                if (dialogResult == 0) {
                    usuario.eliminar();
                    actualizar_users();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para eliminar");
        }
    }//GEN-LAST:event_btn_eliminar_userActionPerformed

    private void btn_cerrar_sesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrar_sesionActionPerformed
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_cerrar_sesionActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void tabla_categoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_categoriasMouseClicked
        int column = tabla_categorias.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / tabla_categorias.getRowHeight();

        ObjectId id = (ObjectId) tabla_categorias.getValueAt(row, 0);
        String nombre = tabla_categorias.getValueAt(row, 1).toString();

        if (row < tabla_categorias.getRowCount() && row >= 0 && column < tabla_categorias.getColumnCount() && column >= 0) {
            Object value = tabla_categorias.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                AddCategoria categoria = new AddCategoria();
                categoria.txtcat.setText(nombre);
                categoria.lbl_titulo.setText("MODIFICAR CATEGORIA");
                categoria.id = id;
                categoria.setVisible(true);
                //view_edit.llenar(id, nombre, apellido, user, password);
            }
        }
    }//GEN-LAST:event_tabla_categoriasMouseClicked

    private void btnagregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar1ActionPerformed
        if (Conn.user_logged.isAdmin() == true) {
            AddEstado estado = new AddEstado();
            estado.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no es administrador");
        }
    }//GEN-LAST:event_btnagregar1ActionPerformed

    private void tabla_estadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_estadosMouseClicked
        int column = tabla_estados.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / tabla_estados.getRowHeight();
        ObjectId id = (ObjectId) tabla_estados.getValueAt(row, 0);
        String nombre = tabla_estados.getValueAt(row, 1).toString();
        String descripcion = tabla_estados.getValueAt(row, 2).toString();
        if (row < tabla_estados.getRowCount() && row >= 0 && column < tabla_estados.getColumnCount() && column >= 0) {
            Object value = tabla_estados.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                AddEstado estado = new AddEstado();
                estado.txtestado.setText(nombre);
                estado.txtdescripcion.setText(descripcion);
                estado.lbl_titulo.setText("MODIFICAR ESTADO");
                estado.id = id;
                estado.setVisible(true);
            }
        }
    }//GEN-LAST:event_tabla_estadosMouseClicked

    private void btn_editar_cat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_cat1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_editar_cat1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btn_eliminar_cat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_cat1ActionPerformed
        int row = tabla_estados.getSelectedRow();

        if (row != -1) {
            ObjectId id = (ObjectId) tabla_estados.getValueAt(row, 0);
            Estado estado = new Estado(id);
            int articulos_total = Conn.listar_articulos(null, estado).size();
            if (articulos_total > 0) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar, articulos asociados");
            } else {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(this, "¿Esta Seguro de eliminar el Estado?", "Alerta", dialogButton);
                if (dialogResult == 0) {
                    estado.eliminar();
                    actualizar_estados();
                    actualizar_cmbo_estados();
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un estado para eliminar");
        }
    }//GEN-LAST:event_btn_eliminar_cat1ActionPerformed

    private void combo_mesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_mesActionPerformed
        actualizar_logs();
    }//GEN-LAST:event_combo_mesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

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
    public static javax.swing.JButton btn_add;
    private javax.swing.JMenuItem btn_cerrar_sesion;
    private javax.swing.JToggleButton btn_editar_cat;
    private javax.swing.JToggleButton btn_editar_cat1;
    private javax.swing.JButton btn_eliminar_articulo;
    private javax.swing.JButton btn_eliminar_cat;
    private javax.swing.JButton btn_eliminar_cat1;
    private javax.swing.JButton btn_eliminar_user;
    private javax.swing.JButton btn_open_add;
    private javax.swing.JMenuItem btn_salir;
    private javax.swing.JButton btnagregar;
    private javax.swing.JButton btnagregar1;
    private static javax.swing.JComboBox<String> cb_cat;
    private static javax.swing.JComboBox<String> cb_estados;
    private static javax.swing.JComboBox<String> combo_mes;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    public static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel jpanelestados;
    private javax.swing.JLabel lbl_nombre;
    public static javax.swing.JTabbedPane pan_usuario;
    private static javax.swing.JTable tabla_categorias;
    private static javax.swing.JTable tabla_estados;
    private static javax.swing.JTable tabla_listado;
    private static javax.swing.JTable tabla_logs;
    private static javax.swing.JTable tabla_resumen;
    public static javax.swing.JTable tbl_users;
    // End of variables declaration//GEN-END:variables
}
