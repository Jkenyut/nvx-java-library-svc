/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes.pbo;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author this.naff
 */
public class Peminjaman extends javax.swing.JFrame {

    /**
     * Creates new form Peminjaman
     */
    public Statement st;
    public ResultSet rs;
    
    public DefaultTableModel tabModel, tabModel2;
    Connection cn = Koneksi.Koneksi();

    String tampilan = "yyyy-MM-dd";
    SimpleDateFormat date = new SimpleDateFormat(tampilan);
    
    public void buku() {
        Object[] buku = {
          "Kode Buku", "Judul", "Pengarang", "Penerbit"
        };
        tabModel = new DefaultTableModel(null, buku);
        tblDataBuku.setModel(tabModel);
    }

    public void tampilDataBuku(String where) {
        try {
          st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
          tabModel.getDataVector().removeAllElements();
          tabModel.fireTableDataChanged();
          rs = st.executeQuery("SELECT * FROM buku " + where);

          while (rs.next()) {
            Object[] data = {
              rs.getString("kodeBuku"),
              rs.getString("judulBuku"),
              rs.getString("pengarang"),
              rs.getString("penerbit"),
            };

              tabModel.addRow(data);
          }
        } catch(Exception e) {
          JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void peminjaman() {
        Object[] pinjam = {
          "Id Peminjaman", "Kode Buku", "NIM", "ID Admin", "Tanggal Peminjaman", "Tanggal Pengembalian", "Status"
        };
        tabModel2 = new DefaultTableModel(null, pinjam);
        tblDataPeminjaman.setModel(tabModel2);
    }
    
    public void tampilDataPeminjaman(String where) {
    try {
      st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
      tabModel2.getDataVector().removeAllElements();
      tabModel2.fireTableDataChanged();
      rs = st.executeQuery("SELECT * FROM peminjaman " + where);

      while (rs.next()) {
        Object[] dataPinjam = {
          rs.getString("idPeminjaman"),
          rs.getString("kodeBuku"),
          rs.getString("nim"),
          rs.getString("idAdmin"),
          rs.getString("tglPeminjaman"),
          rs.getString("tglPengembalian"),
          rs.getString("status")
        };

          tabModel2.addRow(dataPinjam);
      }
    } catch(Exception e) {
      e.printStackTrace();
        }   
    }
    
    private void tampilId() {
        try{
            Session ss = new Session();
            String user = ss.getUsername();
            
            System.out.println(user);
            
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT * FROM admin WHERE user = '"+ user +"'");
            
            //txtIdAdmin.setText(user);
            
            while(rs.next()){
                txtIdAdmin.setText(rs.getString("idAdmin"));
                
            }
       
        }   
        catch(Exception b){
            JOptionPane.showMessageDialog(null, b.getMessage());
        }
    }
    
    public void transparan(){
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setContentAreaFilled(false);
        btnSave.setOpaque(false);

        
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setOpaque(false);
        
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setContentAreaFilled(false);
        btnCancel.setOpaque(false);
        
        txtIdPeminjaman.setOpaque(false);
        txtKodeBuku.setOpaque(false);
        txtNim.setOpaque(false);
        txtSearch.setOpaque(false);

        
    }   
    
    public void Ai(){
      try {
          Koneksi.Koneksi();
          rs = st.executeQuery("SELECT MAX(right(idPeminjaman,3)) FROM peminjaman");
          while(rs.next()){
              if(rs.first()== false){
                  txtIdPeminjaman.setText("PJM-001");
              }else{
                  rs.last();
                  int autoId = rs.getInt(1) + 1;
                  String idPeminjaman = String.valueOf(autoId);
                  int noLong = idPeminjaman.length();
                  for (int i = 0; i < 3 - noLong; i++) {
                      idPeminjaman = "0" + idPeminjaman;
                  }
                  txtIdPeminjaman.setText("PJM-" + idPeminjaman);
              }
          }
      } catch(Exception e) {
          System.out.println("SKIP " + e);
      } 
    }
    
    private void cariData(String key){
        try{
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            rs = st.executeQuery("SELECT * FROM buku WHERE judulBuku LIKE '%" + key + 
                    "%' OR pengarang LIKE '%" + key + 
                    "%' OR penerbit LIKE '%" + key + "%'");
            while (rs.next()) {
                Object[] data = {
                  rs.getString("kodeBuku"),
                  rs.getString("judulBuku"),
                  rs.getString("pengarang"),
                  rs.getString("penerbit"),
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void filterhuruf(KeyEvent a){
        if(Character.isAlphabetic(a.getKeyChar())){
            a.consume();
            JOptionPane.showMessageDialog(null,"Masukkan Hanya Angka!");
        }
    }
    

    
    public Peminjaman() {
        initComponents();
        reset();
        buku();
        peminjaman();
        tampilDataPeminjaman("");
        tampilDataBuku("");
        
        transparan();
        tampilId();
        Ai();
        
        this.setTitle("Peminjaman Buku");
    }
    
    public void reset() {
        Ai();
        txtKodeBuku.setText("Pilih Buku ->");
        txtNim.setText("");
        txtSearch.setText("");
        
        
        txtNim.requestFocus();
       
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txtIdAdmin = new javax.swing.JLabel();
        txtNim = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDataPeminjaman = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataBuku = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        txtIdPeminjaman = new javax.swing.JLabel();
        txtKodeBuku = new javax.swing.JLabel();
        txtTglPeminjaman = new com.toedter.calendar.JDateChooser();
        txtTglPengembalian = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 204, 255)));

        txtIdAdmin.setText("ID Petugas");
        jPanel2.add(txtIdAdmin);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 90, -1));

        txtNim.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNim.setBorder(null);
        txtNim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNimActionPerformed(evt);
            }
        });
        txtNim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNimKeyTyped(evt);
            }
        });
        getContentPane().add(txtNim, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 300, -1));

        tblDataPeminjaman.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tblDataPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Peminjaman", "Kode Buku", "NIM", "ID Admin", "Tanggal Peminjaman", "Durasi"
            }
        ));
        tblDataPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataPeminjamanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDataPeminjaman);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 730, 160));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/btn_save.png"))); // NOI18N
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 100, 60));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/btn_delete.png"))); // NOI18N
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 360, 100, 60));

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/btn_cancel.png"))); // NOI18N
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 360, 100, 60));

        tblDataBuku.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tblDataBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode Buku", "Judul Buku", "Pengarang", "Penerbit"
            }
        ));
        tblDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDataBuku);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 310, 190));

        txtSearch.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtSearch.setBorder(null);
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });
        getContentPane().add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 260, -1));

        txtIdPeminjaman.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtIdPeminjaman.setText("Kode Otomatis");
        getContentPane().add(txtIdPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 94, -1, 20));

        txtKodeBuku.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtKodeBuku.setText("Pilih Buku");
        getContentPane().add(txtKodeBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 300, 20));
        getContentPane().add(txtTglPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 150, 30));
        getContentPane().add(txtTglPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 150, 30));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/Peminjaman.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNimActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String tanggalPinjam = String.valueOf(date.format(txtTglPeminjaman.getDate()));
        String tanggalKembali = String.valueOf(date.format(txtTglPengembalian.getDate()));
        
        if(txtNim.getText().equals("") || txtKodeBuku.getText().equals("Pilih Buku ->")){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong!");
        }else{
            try {
                st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate("INSERT INTO peminjaman VALUES('" + txtIdPeminjaman.getText() + "','" + txtKodeBuku.getText() + "','"
                    + txtNim.getText() + "','"
                    + txtIdAdmin.getText() + "','"
                    + tanggalPinjam + "','"
                    + tanggalKembali + "','"
                    + "Belum Kembali" + "')");
                tampilDataPeminjaman("");
                JOptionPane.showMessageDialog(null, "Simpan Berhasil");
                reset();
            } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "Isi data dengan benar! EROR PADA :" + e);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblDataBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataBukuMouseClicked
        // TODO add your handling code here:
        txtKodeBuku.setText(tblDataBuku.getValueAt(tblDataBuku.getSelectedRow(), 0).toString());

    }//GEN-LAST:event_tblDataBukuMouseClicked

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String key = txtSearch.getText();
        System.out.println(key);
        
        if (key != "") {
            cariData(key);
        }else{
            tampilDataBuku("");
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtNimKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNimKeyTyped
        // TODO add your handling code here:
        filterhuruf(evt);
    }//GEN-LAST:event_txtNimKeyTyped

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        try {
            int jawab;

            if ((jawab = JOptionPane.showConfirmDialog(null, "Ingin menghapus data?", "konfirmasi", JOptionPane.YES_NO_OPTION)) == 0) {
                st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate("DELETE FROM peminjaman WHERE idPeminjaman='"
                    + tabModel2.getValueAt(tblDataPeminjaman.getSelectedRow(), 0) + "'");
                tampilDataPeminjaman("");
                reset();
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblDataPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataPeminjamanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDataPeminjamanMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Peminjaman().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDataBuku;
    private javax.swing.JTable tblDataPeminjaman;
    private javax.swing.JLabel txtIdAdmin;
    private javax.swing.JLabel txtIdPeminjaman;
    private javax.swing.JLabel txtKodeBuku;
    private javax.swing.JTextField txtNim;
    private javax.swing.JTextField txtSearch;
    private com.toedter.calendar.JDateChooser txtTglPeminjaman;
    private com.toedter.calendar.JDateChooser txtTglPengembalian;
    // End of variables declaration//GEN-END:variables
}
