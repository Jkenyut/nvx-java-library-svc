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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author this.naff
 */
public class Pengembalian extends javax.swing.JFrame {

    /**
     * Creates new form Pengembalian
     */
    String kembali;
    String pulang;
    
    public Statement st;
    public ResultSet rs;
    
    public DefaultTableModel tabModel;
    Connection cn = Koneksi.Koneksi();
    
    String tampilan = "yyyy-MM-dd";
    SimpleDateFormat date = new SimpleDateFormat(tampilan);
    
    
    public void peminjam() {
        Object[] peminjam = {
          "ID Peminjaman", "Kode Buku", "NIM", "ID Petugas", 
          "Tanggal Peminjaman", "Tanggal Pengembalian", "Status"
        };
        tabModel = new DefaultTableModel(null, peminjam);
        tblPeminjaman.setModel(tabModel);
    }

  public void tampilData() {
    try {
        st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        tabModel.getDataVector().removeAllElements();
        tabModel.fireTableDataChanged();
        
        String status = "Belum Kembali";
        rs = st.executeQuery("SELECT * FROM peminjaman WHERE status = '" + status + "'");

        while (rs.next()) {
            Object[] dataPeminjam = {
              rs.getString("idPeminjaman"),
              rs.getString("kodeBuku"),
              rs.getString("nim"),
              rs.getString("idAdmin"),
              rs.getString("tglPeminjaman"),
              rs.getString("tglPengembalian"),
              rs.getString("status"),
            };

          tabModel.addRow(dataPeminjam);
        }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
    
  private void tampilId() {
        try{
            Session ss = new Session();
            String user = ss.getUsername();
            
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT * FROM admin WHERE user = '"+ user +"'");
            while(rs.next()){
                txtId.setText(rs.getString("idAdmin"));
            }
       
        }   
        catch(Exception b){
            JOptionPane.showMessageDialog(null, b.getMessage());
        }
    }
  
    public void reset() {
        tampilId();
        Ai();
        btnHitung.setEnabled(false);
        
        txtIdPeminjaman.setText("");
        txtTglPeminjaman.setText("");
        txtTglKembali.setText("");
        txtCari.setText("");
        txtBayar.setText("");
        txtDenda.setText("");
        txtKembalian.setText("");
        txtTelat.setText("");
        btnSave.setEnabled(false);
        
        txtCari.requestFocus();
    }
    
    public void Ai(){
      try {
          Koneksi.Koneksi();
          rs = st.executeQuery("SELECT MAX(right(idPengembalian,3)) FROM pengembalian");
          while(rs.next()){
              if(rs.first()== false){
                  txtIdPengembalian.setText("PGM-001");
              }else{
                  rs.last();
                  int autoId = rs.getInt(1) + 1;
                  String idPengembalian = String.valueOf(autoId);
                  int noLong = idPengembalian.length();
                  for (int i = 0; i < 3 - noLong; i++) {
                      idPengembalian = "0" + idPengembalian;
                  }
                  txtIdPengembalian.setText("PGM-" + idPengembalian);
              }
          }
      } catch(Exception e) {
          JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                  "Kesalahan", JOptionPane.WARNING_MESSAGE);
      } 
    }
    
    private void cariData(String key){
        try{
            String status = "Belum Kembali";
            st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            rs = st.executeQuery("SELECT * FROM peminjaman WHERE nim LIKE '%" + key + "%' AND status ='" + status + "'");
            while (rs.next()) {
                Object[] data = {
                    rs.getString("idPeminjaman"),
                    rs.getString("kodeBuku"),
                    rs.getString("nim"),
                    rs.getString("idAdmin"),
                    rs.getString("tglPeminjaman"),
                    rs.getString("tglPengembalian"),
                    rs.getString("status"),
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
    
    public void transparan() {
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setContentAreaFilled(false);
        btnSave.setOpaque(false);
        
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setContentAreaFilled(false);
        btnCancel.setOpaque(false);
        
        btnHitung.setFocusPainted(false);
        btnHitung.setBorderPainted(false);
        btnHitung.setContentAreaFilled(false);
        btnHitung.setOpaque(false);
        
        btnCekData.setFocusPainted(false);
        btnCekData.setBorderPainted(false);
        btnCekData.setContentAreaFilled(false);
        btnCekData.setOpaque(false);
        
        txtBayar.setOpaque(false);
        txtCari.setOpaque(false);
//        txtJudulBuku.setOpaque(false);
//        txtPenerbit.setOpaque(false);
//        txtPengarang.setOpaque(false);
    }
    
    public void filterhuruf(KeyEvent a){
        if(Character.isAlphabetic(a.getKeyChar())){
            a.consume();
            JOptionPane.showMessageDialog(null,"Masukkan Hanya Angka!");
        }
    }
    
    public Pengembalian() {
        initComponents();
        reset();
        peminjam();
        tampilData();
        tampilId();
        transparan();
        
        txtBayar.setEditable(false);
        
        this.setTitle("Pengembalian Buku");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTglPeminjaman = new javax.swing.JLabel();
        txtTglKembali = new javax.swing.JLabel();
        txtIdAdmin = new javax.swing.JPanel();
        txtId = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        txtIdPeminjaman = new javax.swing.JLabel();
        tglDikembalikan = new com.toedter.calendar.JDateChooser();
        txtTelat = new javax.swing.JLabel();
        txtDenda = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        txtKembalian = new javax.swing.JLabel();
        btnHitung = new javax.swing.JButton();
        txtIdPengembalian = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeminjaman = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        btnCekData = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTglPeminjaman.setText("C");
        getContentPane().add(txtTglPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 430, 80, 20));

        txtTglKembali.setText("D");
        getContentPane().add(txtTglKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 470, 80, 20));

        txtId.setText("ID Admin");
        txtIdAdmin.add(txtId);

        getContentPane().add(txtIdAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 80, -1));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/btn_save.png"))); // NOI18N
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 360, 100, 60));

        txtIdPeminjaman.setText("B");
        getContentPane().add(txtIdPeminjaman, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, 80, 20));

        tglDikembalikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tglDikembalikanPropertyChange(evt);
            }
        });
        getContentPane().add(tglDikembalikan, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 500, 120, 30));

        txtTelat.setText("Telat");
        getContentPane().add(txtTelat, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 360, 30, 20));

        txtDenda.setText("Denda");
        getContentPane().add(txtDenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 70, 30));

        txtBayar.setText("Bayar");
        txtBayar.setBorder(null);
        txtBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBayarMouseClicked(evt);
            }
        });
        getContentPane().add(txtBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 430, 60, 20));

        txtKembalian.setText("Kembalian");
        getContentPane().add(txtKembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 470, 70, 20));

        btnHitung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/btn calcultor.png"))); // NOI18N
        btnHitung.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });
        getContentPane().add(btnHitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 500, 100, 60));

        txtIdPengembalian.setText("A");
        getContentPane().add(txtIdPengembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, 80, 20));

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/btn_cancel.png"))); // NOI18N
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 440, 100, 60));

        tblPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ));
        tblPeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPeminjamanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPeminjaman);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 700, 180));

        txtCari.setBorder(null);
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });
        getContentPane().add(txtCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 260, 20));

        btnCekData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/btn cek data.png"))); // NOI18N
        btnCekData.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCekData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCekDataMouseClicked(evt);
            }
        });
        getContentPane().add(btnCekData, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, 120, 60));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tubes/pbo/assets/Pengembalian.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(txtIdPeminjaman.getText().equals("") || txtTglPeminjaman.getText().equals("") || txtTglKembali.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong!");
        }else{
            try {
                String status = "Selesai";
                st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate("INSERT INTO pengembalian VALUES('" + txtIdPengembalian.getText() + "','" 
                    + txtIdPeminjaman.getText() + "','"
                    + txtTglPeminjaman.getText() + "','"
                    + txtTglKembali.getText() + "','"
                    + txtDenda.getText() + "')"
                );
                
                st.executeUpdate("UPDATE peminjaman set status ='" + status + "' WHERE "
                    + "idPeminjaman= '" + txtIdPeminjaman.getText() + "'"
                );
                tampilData();
                JOptionPane.showMessageDialog(null, "Simpan Berhasil");
                reset();
            } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "Isi data dengan benar");
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblPeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPeminjamanMouseClicked
        // TODO add your handling code here:
        txtIdPeminjaman.setText(tblPeminjaman.getValueAt(tblPeminjaman.getSelectedRow(), 0).toString());
        txtTglPeminjaman.setText(tblPeminjaman.getValueAt(tblPeminjaman.getSelectedRow(), 4).toString());
        txtTglKembali.setText(tblPeminjaman.getValueAt(tblPeminjaman.getSelectedRow(), 5).toString());
        kembali = tblPeminjaman.getValueAt(tblPeminjaman.getSelectedRow(), 5).toString();
    }//GEN-LAST:event_tblPeminjamanMouseClicked

    private void tglDikembalikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tglDikembalikanPropertyChange
        // TODO add your handling code here:
        if (tglDikembalikan.getDate() != null) {
            String tampilan = "yyyy-MM-dd";
            SimpleDateFormat date = new SimpleDateFormat(tampilan);
            
            pulang = date.format(tglDikembalikan.getDate());
        }
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date tanggalpinjam = format.parse(kembali);
            Date tanggalkembali = format.parse(pulang);
            long tglPinjam = tanggalpinjam.getTime();
            long tglKembali = tanggalkembali.getTime();
            long diff = tglKembali - tglPinjam;
            long lama = diff / (24 * 60 * 60 * 1000);
            txtTelat.setText(Long.toString(lama) + "");

            int denda;
            int telat = Integer.parseInt(txtTelat.getText());
            denda = telat * 5000;
            String dendaTelat;
            dendaTelat = String.valueOf(denda);   
            if(telat < 0){
                txtTelat.setText("0");
                txtDenda.setText("Tidak");
                txtBayar.setText("");
                txtKembalian.setText("");
                txtBayar.setEditable(false);
                btnHitung.setEnabled(false);
                btnSave.setEnabled(true);
                
            }
            else if(dendaTelat.equals("0")){
                txtBayar.setText("");
                txtDenda.setText("Tidak");
                txtKembalian.setText("");
                txtBayar.setEditable(false);
                btnHitung.setEnabled(false);
                btnSave.setEnabled(true);
               
            }else{
                txtBayar.setEditable(true);
                btnHitung.setEnabled(true);
                
                txtDenda.setText("");
                txtBayar.setText("");
                txtKembalian.setText("");
                
                txtDenda.setText(dendaTelat);
                
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }                                     

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         

    }//GEN-LAST:event_tglDikembalikanPropertyChange

    private void txtBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBayarMouseClicked
        // TODO add your handling code here:
        txtBayar.setText(null);
    }//GEN-LAST:event_txtBayarMouseClicked

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        // TODO add your handling code here:
        int bayar = Integer.parseInt(txtBayar.getText());
        int denda = Integer.parseInt(txtDenda.getText());

        if (txtBayar.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukan Pembayaran");
        }else if (bayar < denda){
           JOptionPane.showMessageDialog(null, "Pembayaran Kurang"); 
           btnSave.setEnabled(false);
        }else{
            int total = bayar - denda;
            String ttl;
            ttl = String.valueOf(total);
            txtKembalian.setText(ttl);
            btnSave.setEnabled(true);
            
        }
    }//GEN-LAST:event_btnHitungActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        String key = txtCari.getText();
        System.out.println(key);
        
        if (key != "") {
            cariData(key);
        }else{
            tampilData();
        }
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        // TODO add your handling code here:
        filterhuruf(evt);
    }//GEN-LAST:event_txtCariKeyTyped

    private void btnCekDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCekDataMouseClicked
        // TODO add your handling code here:
        new CekData().setVisible(true);
    }//GEN-LAST:event_btnCekDataMouseClicked

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
//            java.util.logging.Logger.getLogger(Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Pengembalian().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCekData;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPeminjaman;
    private com.toedter.calendar.JDateChooser tglDikembalikan;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCari;
    private javax.swing.JLabel txtDenda;
    private javax.swing.JLabel txtId;
    private javax.swing.JPanel txtIdAdmin;
    private javax.swing.JLabel txtIdPeminjaman;
    private javax.swing.JLabel txtIdPengembalian;
    private javax.swing.JLabel txtKembalian;
    private javax.swing.JLabel txtTelat;
    private javax.swing.JLabel txtTglKembali;
    private javax.swing.JLabel txtTglPeminjaman;
    // End of variables declaration//GEN-END:variables
}
