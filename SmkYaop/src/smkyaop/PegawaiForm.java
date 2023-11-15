/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smkyaop;
import helper.Helper;
import java.awt.Color;
import java.sql.*;
import database.Database;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PegawaiForm extends javax.swing.JFrame {
    public static boolean isFromPegawaiJabat = false;
    public static PegawaiJabatForm pj = null;
    public static boolean isFromDataAbsen = false;
    public static DataAbsenForm absen = null;
    public static boolean isFromPotonganPegawai = false;
    public static PegawaiPotonganForm pegawaiPotong = null;
    
    public PegawaiForm() {
        initComponents();
        taAlamat.setLineWrap(true);
        taAlamat.setWrapStyleWord(true);
        labelCari.setBackground(new Color(20,165,255));
        active(true);
        loadData("");
        this.setLocale(new Locale("id","ID"));
        tfTgl.setLocale(new Locale("id","ID"));
        tfTgl.setDateFormatString("dd MMMM yyyy");   
        java.util.Date date9 = null,date10 = null;
        try {
            date9 = new SimpleDateFormat("dd MMMM yyyy hh:mm").parse("26 aug 2022 15:00");
            date10 = new SimpleDateFormat("dd MMMM yyyy hh:mm").parse("26 aug 2022 15:10");
        } catch (ParseException ex) {
            Logger.getLogger(PegawaiForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void setDataFromPegawai(String idJabatan){
        tfJabatan.setText(idJabatan);
    }
    
    private void firetable(){
        DefaultTableModel dtm = (DefaultTableModel)table.getModel();
        dtm.fireTableDataChanged();
    }
    
    private void active(boolean b){
        btnSave.setEnabled(b);
        btnDelete.setEnabled(!b);
        btnEdit.setEnabled(!b);
    }    
   
    
    protected void clear() {
        tfId.setText("");
        tfJabatan.setText("");
        tfTempat.setText("");        
        tfNama.setText("");
        tfTelp.setText("");
        tfTgl.setDate(null);
        taAlamat.setText("");
        tfCari.setText("");
        rjk.clearSelection();
        rstatus.clearSelection();
        tfNip.setText("");
        cbWalas.setSelected(false);
        cbGty.setSelected(false);    
        combo.setSelectedIndex(0);
    }
    
    private void loadData(String keys) {
      Object[] Baris ={"ID Pegawai","ID Jabatan","NIP", "Nama Pegawai", "Jenis kelamin","Tempat,Tanggal Lahir","Alamat","Telp","Status","Wali Kelas","Guru Tetap","Pendidikan"};
      DefaultTableModel tabmode = new DefaultTableModel (null, Baris);
      table.setModel(tabmode);
      table.setRowHeight(40);
      String cari = "";
      String sql = "";
      if(keys != ""){
          cari = " WHERE pegawai.nip LIKE '%"+keys+"%' OR pegawai.namapegawai LIKE '%"+keys+"%' ";
      }
      if(PegawaiForm.isFromPegawaiJabat){
          sql = "SELECT pegawai.* FROM pegawaijabat RIGHT JOIN pegawai ON pegawaijabat.idPegawai = pegawai.idpegawai WHERE pegawaijabat.idPegawai IS NULL ";
          if(keys != "")cari = " AND pegawai.nip LIKE '%"+keys+"%' OR pegawai.namapegawai LIKE '%"+keys+"%' ";
      }      else sql = "SELECT * FROM pegawai";
        System.out.println(sql+cari);
          DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
          renderer.setHorizontalAlignment(0);          
                  table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
          table.getColumnModel().getColumn(0).setPreferredWidth(10);
          table.getColumnModel().getColumn(1).setPreferredWidth(10);
          table.getColumnModel().getColumn(2).setPreferredWidth(10);          
          table.getColumnModel().getColumn(4).setPreferredWidth(10);
          table.getColumnModel().getColumn(9).setPreferredWidth(10);
          table.getColumnModel().getColumn(8).setPreferredWidth(15);
          table.getColumnModel().getColumn(7).setPreferredWidth(15);          
          DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
          cellRenderer.setHorizontalAlignment(JLabel.CENTER);
          table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer); 
          table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);  
          table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer); 
          table.getColumnModel().getColumn(4).setCellRenderer(cellRenderer); 
          table.getColumnModel().getColumn(6).setCellRenderer(cellRenderer);  
          table.getColumnModel().getColumn(7).setCellRenderer(cellRenderer);  
          table.getColumnModel().getColumn(8).setCellRenderer(cellRenderer);  
          table.getColumnModel().getColumn(9).setCellRenderer(cellRenderer);          
      
      try {
          java.sql.Connection conn = database.Database.getConn();
          java.sql.Statement stat = conn.createStatement();
          ResultSet hasil = stat.executeQuery(sql+cari);
          int index = 0;
          while(hasil.next()){
//      Object[] Baris ={"ID Pegawai","ID Jabatan","NIP", "Nama Pegawai", "Jenis kelamin","Tempat,Tanggal Lahir","Alamat","Telp","Status","Wali Kelas"};
              SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id","ID"));
                java.util.Date date = new java.util.Date(hasil.getDate(7).getTime());
                String tgl = sdf.format(date);
              String a = hasil.getString(1);
              String b = hasil.getString(2);
              String c = hasil.getString(3);
              String d = hasil.getString(4);
              String e = hasil.getString(5);
              String f = hasil.getString(6)+","+tgl;
              String g = hasil.getString(8);
              String h = hasil.getString(9);
              String i = hasil.getString(10);
              boolean j = hasil.getBoolean(11);
              boolean k = hasil.getBoolean("gty");
              String l = hasil.getString("pendidikan") != "" ? hasil.getString("pendidikan") : "-" ;
              Object[] data={a,b,c,d,e,f,g,h,i,j,k,l};
              tabmode.addRow(data);
          }
      }catch(SQLException e){   
          e.printStackTrace();
      }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rjk = new javax.swing.ButtonGroup();
        rstatus = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        tfNama = new javax.swing.JTextField();
        tfTelp = new javax.swing.JTextField();
        rlaki = new javax.swing.JRadioButton();
        rcewe = new javax.swing.JRadioButton();
        tfTgl = new com.toedter.calendar.JDateChooser();
        tfCari = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        tfJabatan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        rmenikah = new javax.swing.JRadioButton();
        rlajang = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        tfTempat = new javax.swing.JTextField();
        tfNip = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbWalas = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taAlamat = new javax.swing.JTextArea();
        labelCari = new javax.swing.JLabel();
        btnPrin = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        cbGty = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        combo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FORM PEGAWAI");
        setResizable(false);
        setSize(new java.awt.Dimension(1100, 650));

        jPanel1.setBackground(new java.awt.Color(37, 38, 40));

        jPanel3.setBackground(new java.awt.Color(20, 165, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table);

        jLabel2.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Kode Pegawai ");

        jLabel3.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Jenis Kelamin");

        jLabel4.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nama Pegawai");

        jLabel5.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Alamat    ");

        jLabel6.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Tanggal Lahir");

        jLabel7.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("No Telepon");

        tfId.setEnabled(false);

        tfNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaActionPerformed(evt);
            }
        });

        tfTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfTelpKeyReleased(evt);
            }
        });

        rlaki.setBackground(new java.awt.Color(20, 165, 255));
        rjk.add(rlaki);
        rlaki.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        rlaki.setForeground(new java.awt.Color(255, 255, 255));
        rlaki.setText("Laki - Laki");

        rcewe.setBackground(new java.awt.Color(20, 165, 255));
        rjk.add(rcewe);
        rcewe.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        rcewe.setForeground(new java.awt.Color(255, 255, 255));
        rcewe.setText("Perempuan");

        tfCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCariActionPerformed(evt);
            }
        });
        tfCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariKeyReleased(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(255, 255, 255));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/kembali.png"))); // NOI18N
        btnBack.setText("KEMBALI");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.png"))); // NOI18N
        btnCancel.setText("BATAL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        btnEdit.setText("UBAH");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnDelete.setText("HAPUS");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnSave.setText("SIMPAN");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Jabatan ");

        tfJabatan.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Status");

        rmenikah.setBackground(new java.awt.Color(20, 165, 255));
        rstatus.add(rmenikah);
        rmenikah.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        rmenikah.setForeground(new java.awt.Color(255, 255, 255));
        rmenikah.setText("Menikah");

        rlajang.setBackground(new java.awt.Color(20, 165, 255));
        rstatus.add(rlajang);
        rlajang.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        rlajang.setForeground(new java.awt.Color(255, 255, 255));
        rlajang.setText("Lajang");

        jLabel11.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Tempat Lahir");

        tfNip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNipActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("NIP");

        jLabel13.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Wali Kelas");

        cbWalas.setBackground(new java.awt.Color(20, 165, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(25, 59, 104));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/caricari.png"))); // NOI18N

        taAlamat.setColumns(20);
        taAlamat.setLineWrap(true);
        taAlamat.setRows(5);
        jScrollPane1.setViewportView(taAlamat);

        labelCari.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        labelCari.setForeground(new java.awt.Color(25, 59, 104));
        labelCari.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/caricari.png"))); // NOI18N
        labelCari.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        labelCari.setOpaque(true);
        labelCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCariMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelCariMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelCariMouseEntered(evt);
            }
        });

        btnPrin.setBackground(new java.awt.Color(255, 255, 255));
        btnPrin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cetak.png"))); // NOI18N
        btnPrin.setText("CETAK");
        btnPrin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrinActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("GTY");

        cbGty.setBackground(new java.awt.Color(20, 165, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Masukkan Nama atau NIP");

        jLabel16.setFont(new java.awt.Font("Agency FB", 0, 22)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Pendidikan");

        combo.setMaximumRowCount(4);
        combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Pilih ---", "SMA/K", "D3", "S1", "S2" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(20, 20, 20)
                                        .addComponent(rmenikah, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rlajang, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(20, 20, 20)
                                        .addComponent(rlaki)
                                        .addGap(18, 18, 18)
                                        .addComponent(rcewe)))
                                .addGap(77, 77, 77))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbWalas)
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbGty))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfJabatan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                                            .addComponent(tfId))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(20, 20, 20))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNip)
                            .addComponent(tfNama, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(labelCari, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(210, 210, 210)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfTempat, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfTgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfTelp, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(29, 29, 29)
                .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel8)
                .addGap(25, 25, 25))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addComponent(btnSave)
                .addGap(45, 45, 45)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnEdit)
                .addGap(38, 38, 38)
                .addComponent(btnCancel)
                .addGap(41, 41, 41)
                .addComponent(btnBack)
                .addGap(24, 24, 24)
                .addComponent(btnPrin, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(tfJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(rlaki)
                                    .addComponent(rcewe))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(rmenikah)
                                    .addComponent(rlajang))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfNip, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(cbWalas)
                                    .addComponent(jLabel14)
                                    .addComponent(cbGty)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(labelCari))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfTempat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tfTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(tfTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrin, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(20, 165, 255));

        jLabel1.setFont(new java.awt.Font("Agency FB", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Pegawai");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        try {
            active(false);
             int bar = table.getSelectedRow();
             String idpegawai = table.getValueAt(bar, 0).toString();
             String idjabatan = table.getValueAt(bar, 1).toString();
             String nip = table.getValueAt(bar, 2).toString();
             String nama = table.getValueAt(bar, 3).toString();
             String jk = table.getValueAt(bar, 4).toString();
             String ttl = table.getValueAt(bar, 5).toString();
             String tempat = ttl.split(",")[0];
             String tgl = ttl.split(",")[1];
             java.util.Date now = new SimpleDateFormat("dd MMMM yyyy",new Locale("id","ID")).parse(tgl);
             String alamat = table.getValueAt(bar, 6).toString();
             String telp = table.getValueAt(bar, 7).toString(); 
             String status = table.getValueAt(bar, 8).toString();
             boolean walas = Boolean.parseBoolean(table.getValueAt(bar, 9).toString());             
             boolean gty = Boolean.parseBoolean(table.getValueAt(bar, 10).toString());             
             String pendidikan = table.getValueAt(bar,11).toString();
//      Object[] Baris ={"ID Pegawai","ID Jabatan","NIP", "Nama Pegawai", "Jenis kelamin","Tempat,Tanggal Lahir","Alamat","Telp","Status","Wali Kelas"};
            //  java.util.Date sdf = new SimpleDateFormat("dd MMMMM yyyy", new Locale("id","ID"));
             
            tfId.setText(idpegawai);
            tfJabatan.setText(idjabatan);
            tfTempat.setText(tempat);        
            tfNama.setText(nama);
            tfTelp.setText(telp);
            tfTgl.setDate(now);
            taAlamat.setText(alamat);
            if(jk.contains("La")) rlaki.setSelected(true); else rcewe.setSelected(true);
            rstatus.clearSelection();
            tfNip.setText(nip);
            if(status.contains("La")) rlajang.setSelected(true); else rmenikah.setSelected(true);            
            cbWalas.setSelected(walas);
            cbGty.setSelected(gty);
            combo.setSelectedItem(pendidikan);
            if(pendidikan.contains("-")) combo.setSelectedIndex(0);            
            if(isFromPegawaiJabat){
                pj.setFromPegawai(idpegawai);
                isFromPegawaiJabat = false;
                pj = null;
                dispose();
                
            }
            
            if(PegawaiForm.isFromDataAbsen){
                               absen.setFromPegawai(idpegawai); 
                isFromDataAbsen = false;
                absen = null;
                dispose();
            }
            
            if(PegawaiForm.isFromPotonganPegawai){
                PegawaiForm.pegawaiPotong.setFromPegawai(idpegawai); 
                isFromPotonganPegawai = false;
                pegawaiPotong = null;
                dispose();
            }            
            
           
            
         } catch (ParseException ex) {
             Logger.getLogger(PegawaiForm.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_tableMouseClicked

    private void tfCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCariActionPerformed
        String keys = tfCari.getText();
        if(keys.isEmpty()){
            loadData("");
            return;
        }
        loadData(keys);
    }//GEN-LAST:event_tfCariActionPerformed

    private void tfCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKeyReleased
        String keys = tfCari.getText();
        if(keys.isEmpty()){
            loadData("");
            return;
        }
        loadData(keys);
    }//GEN-LAST:event_tfCariKeyReleased

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        if(isFromPegawaiJabat){
            isFromPegawaiJabat = false;
             pj = null;        
        }
            if(PegawaiForm.isFromDataAbsen){
                isFromDataAbsen = false;
                absen = null;
                dispose();
            }        
        dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        clear();
        loadData("");
        firetable();
        active(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        if(row < 0){
            Helper.alert("PIlih data dahulu di tabel!");
            return;
        }
        String id = tfId.getText();

        int tanya = Helper.tanya("Apakah anda ingin Mengupdate data ini ?");
        if(tanya == JOptionPane.YES_OPTION && !id.isEmpty() ){
            String idjabatan = tfJabatan.getText();
            String nama = tfNama.getText();
            String nip = tfNip.getText();
            String kelamin = "";
            String tempat = tfTempat.getText();
            java.util.Date tgl = tfTgl.getDate();
            String alamat = taAlamat.getText();
            String telp = tfTelp.getText();
            String status = "";
            if(idjabatan.isEmpty()){
                Helper.alert("Isi jabatan dulu!");
                return;
            }        

            if(nip.isEmpty()){
                Helper.alert("Isi nip dulu!");
                return;
            }           

            if(rjk.getSelection() == null){
                Helper.alert("PIlih Jenis Kelamin!");
                return;
            }
            kelamin = (rlaki.isSelected()) ? rlaki.getText() : rcewe.getText();


            if(tempat.isEmpty()){
                Helper.alert("Isi Tempat Lahir dulu!");
                return;
            }
            if(tgl == null){
                Helper.alert("Isi Tanggal Lahir dulu!");
                return;
            }        
            if(alamat.isEmpty()){
                Helper.alert("Isi Alamat dulu!");
                return;
            }    
            if(telp.isEmpty()){
                Helper.alert("Isi Telepon dulu!");
                return;
            }            

            if(rstatus.getSelection() == null){
                Helper.alert("PIlih Status!");
                return;
            }        
            status = (rmenikah.isSelected()) ? rmenikah.getText() : rlajang.getText();
            boolean walas = (cbWalas.isSelected());
            boolean gty = cbGty.isSelected();
            String pendidikan = combo.getSelectedItem().toString();
            if(pendidikan.contains("---")){
                Helper.alert("PIlih Pendidikkan!");
                return;
            }            
            try{
                String query = "UPDATE pegawai SET idjabatan = ?,nip = ?,namapegawai = ?,jeniskelamin = ?"
                + ",tempatlahir=?,tgllahir=?,alamat=?,telp=?,pernikahan=?,walas=? , gty = ?, pendidikan = ? WHERE idpegawai = ?";
                PreparedStatement ps = Database.getConn().prepareStatement(query);
                ps.setInt(1, Integer.parseInt(idjabatan));
                ps.setString(2, nip);
                ps.setString(3, nama);
                ps.setString(4, kelamin);
                ps.setString(5, tempat);
                ps.setDate(6, new java.sql.Date(tgl.getTime()));
                ps.setString(7, alamat);
                ps.setString(8, telp);
                ps.setString(9,status);
                ps.setBoolean(10, walas);
                ps.setBoolean(11, gty);
                ps.setString(12,pendidikan);
                ps.setInt(13, Integer.parseInt(id));
                 int hasil = ps.executeUpdate();
                if(hasil > 0 ){
                    Helper.alert("Data Berhasil diUpdate!!");
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        loadData("");
        clear();
        firetable();
        active(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        if(row < 0){
            Helper.alert("PIlih data dahulu di tabel!");
            return;
        }
        String id = tfId.getText();

        int tanya = Helper.tanya("Apakah anda ingin menghapus data ini ?");
        if(tanya == JOptionPane.YES_OPTION && !id.isEmpty() ){
            try{
                PreparedStatement ps = Database.getConn().prepareStatement("DELETE fROM pegawai WHERE idpegawai = ?");
                ps.setInt(1, Integer.parseInt(id));
                int hasil = ps.executeUpdate();
                if(hasil > 0 ){
                    Helper.alert("Data Berhasil dihapus!");
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        loadData("");
        clear();
        firetable();
        active(true);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String idjabatan = tfJabatan.getText();
        String nama = tfNama.getText();
        String nip = tfNip.getText();
        String kelamin = "";
        String tempat = tfTempat.getText();
        java.util.Date tgl = tfTgl.getDate();
        String alamat = taAlamat.getText();
        String telp = tfTelp.getText();
        String status = "";
        String pendidikan = combo.getSelectedItem().toString();
        if(idjabatan.isEmpty()){
            Helper.alert("Isi jabatan dulu!");
            return;
        }        
        
        if(nip.isEmpty()){
            Helper.alert("Isi nip dulu!");
            return;
        }           
        
        if(rjk.getSelection() == null){
            Helper.alert("PIlih Jenis Kelamin!");
            return;
        }
        kelamin = (rlaki.isSelected()) ? rlaki.getText() : rcewe.getText();
        
        
        if(tempat.isEmpty()){
            Helper.alert("Isi Tempat Lahir dulu!");
            return;
        }
        if(tgl == null){
            Helper.alert("Isi Tanggal Lahir dulu!");
            return;
        }        
        if(alamat.isEmpty()){
            Helper.alert("Isi Alamat dulu!");
            return;
        }    
        if(telp.isEmpty()){
            Helper.alert("Isi Telepon dulu!");
            return;
        }            
        
        if(rstatus.getSelection() == null){
            Helper.alert("PIlih Status!");
            return;
        }        
        status = (rmenikah.isSelected()) ? rmenikah.getText() : rlajang.getText();
        boolean walas = (cbWalas.isSelected());
        boolean gty = (cbGty.isSelected());
        if(pendidikan.contains("---")){
            Helper.alert("PIlih Pendidikkan!");
            return;
        }
        
        String query = "INSERT INTO pegawai(idjabatan,nip,namapegawai,jeniskelamin"
                + ",tempatlahir,tgllahir,alamat,telp,pernikahan,walas,gty,pendidikan) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            Connection conn = database.Database.getConn();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idjabatan));
            ps.setString(2, nip);
            ps.setString(3, nama);
            ps.setString(4, kelamin);
            ps.setString(5, tempat);
            ps.setDate(6, new java.sql.Date(tgl.getTime()));
            ps.setString(7, alamat);
            ps.setString(8, telp);
            ps.setString(9,status);
            ps.setBoolean(10, walas);
            ps.setBoolean(11, gty);
            ps.setString(12,pendidikan);
            int result = ps.executeUpdate();
            if(result > 0 ){
                Helper.alert("Data Berhasil dimasukkan!");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        loadData("");
        clear();
        firetable();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tfNipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNipActionPerformed

    private void labelCariMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCariMouseEntered
        // TODO add your handling code here:
        labelCari.setBackground(new Color(20, 121, 255));
        labelCari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));        
    }//GEN-LAST:event_labelCariMouseEntered

    private void labelCariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCariMouseExited
        // TODO add your handling code here:
                labelCari.setBackground(new Color(20,165,255));
            labelCari.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));                    
    }//GEN-LAST:event_labelCariMouseExited

    private void labelCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCariMouseClicked
        // TODO add your handling code here:
        JabatanForm.isFromPegawai = true;
        JabatanForm.pegForm = this;
        JabatanForm form = new JabatanForm();
        form.setVisible(true);
    }//GEN-LAST:event_labelCariMouseClicked

    private void tfTelpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTelpKeyReleased
        // TODO add your handling code here:
        if(!Helper.onlyNumber(evt)){
            evt.consume();
            Helper.alert("Isi Dengan Angka!");
            tfTelp.setText("");
        }
        
    }//GEN-LAST:event_tfTelpKeyReleased

    private void btnPrinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrinActionPerformed
        // TODO add your handling code here:
        Helper helpMe = new Helper();
        String path = "/report/laporangajioke.jasper";
        java.util.HashMap param = new HashMap();
        helpMe.generateReportLogo(path, param, "Laporan Pegawai");
//        Helper.generateReportTable(path, param, table.getModel());
    }//GEN-LAST:event_btnPrinActionPerformed

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
            java.util.logging.Logger.getLogger(PegawaiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PegawaiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PegawaiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PegawaiForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PegawaiForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrin;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox cbGty;
    private javax.swing.JCheckBox cbWalas;
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelCari;
    private javax.swing.JRadioButton rcewe;
    private javax.swing.ButtonGroup rjk;
    private javax.swing.JRadioButton rlajang;
    private javax.swing.JRadioButton rlaki;
    private javax.swing.JRadioButton rmenikah;
    private javax.swing.ButtonGroup rstatus;
    private javax.swing.JTextArea taAlamat;
    private javax.swing.JTable table;
    private javax.swing.JTextField tfCari;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfJabatan;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfNip;
    private javax.swing.JTextField tfTelp;
    private javax.swing.JTextField tfTempat;
    private com.toedter.calendar.JDateChooser tfTgl;
    // End of variables declaration//GEN-END:variables
}
