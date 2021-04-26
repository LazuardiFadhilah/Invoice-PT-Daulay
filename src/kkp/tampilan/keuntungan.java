/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkp.tampilan;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kkp.koneksi;
import kkp.session;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Lazuardi Fadhilah
 */
public class keuntungan extends javax.swing.JFrame {
    
    /**
     * Creates new form keuntungan
     */
    public keuntungan() {
        initComponents();
        autoNumber();
        disable();
        combo_id_inv();
        load_table_keuntungan();
        kosong();
    }
    
    String id_pjt;
    private void id_pjt(){
        try{
        String sql = "SELECT * FROM project WHERE id_project = '"+txt_id.getText()+"'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
        id_pjt = rs.getString(1);
        }
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    private void hapus(){
        id_pjt();
        String id = txt_id.getText();
        Object id_inv = combo_id_inv.getSelectedItem();
        try{
            String sql = "DELETE FROM project WHERE id_project='"+id+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);

            if(id.equals(id_pjt)){
             
            int hapus = JOptionPane.showOptionDialog(this, "Apakah anda yakin ingin menghapus data ini ?","Hapus",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            null, null);
            if (hapus == JOptionPane.YES_OPTION) {
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus");
         }    
         } else{
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    }
    
    private void ubah(){
        hitung();
        String id = txt_id.getText();
        Object id_inv = combo_id_inv.getSelectedItem();
        String harga = txt_total_harga.getText();
        String reimburse = txt_total_reimbursement.getText();
        
        try{
            String sql = "UPDATE project SET id_inv = '"+id_inv+"', total_harga= '"+harga+"', total_reimburse= '"+reimburse+"', total_untung= '"+C+"' WHERE id_project='"+id+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di ubah");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    }
    
    private void tambah(){
        hitung();
        String id = txt_id.getText();
        Object id_inv = combo_id_inv.getSelectedItem();
        String harga = txt_total_harga.getText();
        String reimburse = txt_total_reimbursement.getText();
        
        try{
            String sql = "INSERT INTO project VALUES ('"+id+"','"+id_inv+"','"+harga+"','"+reimburse+"','"+C+"')";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Tambah");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    }
    
    int A,B,C;
    private void hitung(){
         A =Integer.parseInt(txt_total_harga.getText().toString());
         B =Integer.parseInt(txt_total_reimbursement.getText().toString());
        C = A-B;
    }
    
    private void load_table_keuntungan(){
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Keuntungan");
    model.addColumn("ID Invoice");
    model.addColumn("Total Harga");
    model.addColumn("Total Reimburse");
    model.addColumn("Total Untung");
    try{
        String sql = "SELECT * FROM project WHERE id_project like '%"+txt_cari.getText()+"%' OR id_inv like '%"+txt_cari.getText()+"%'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
            model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
        }
        tabel_untung.setModel(model);
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    public void total_harga(){
        try{
            String sql ="SELECT total_harga FROM invoice WHERE id_inv='"+combo_id_inv.getSelectedItem()+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()){
            txt_total_harga.setText(res.getString("total_harga"));
            }
        } catch (SQLException e){
            System.out.println("Error :" +e);
        }
    }
    
    public void total_reimburse(){
       try{
            String sql ="select COALESCE(sum(biaya),0) FROM reimburse WHERE id_inv='"+combo_id_inv.getSelectedItem()+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()){
            txt_total_reimbursement.setText(res.getString("COALESCE(sum(biaya),0)"));
            }
        } catch (SQLException e){
            System.out.println("Error :" +e);
        }
    }
    
    public void nama_cs(){
        try{
            String sql ="select customer.nama_customer from invoice JOIN customer ON invoice.id_customer=customer.id_customer WHERE id_inv ='"+combo_id_inv.getSelectedItem()+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()){
            txt_nama_cs.setText(res.getString("nama_customer"));
            }
        } catch (SQLException e){
            System.out.println("Error :" +e);
        }
    }
    
    
    public void combo_id_inv(){
        try{
            String sql ="SELECT * FROM invoice";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            combo_id_inv.removeAllItems();
            while(res.next()){
            combo_id_inv.addItem(res.getString("id_inv"));
            }
        } catch (SQLException e){
            System.out.println("Error :" +e);
        }
    }
    
    public void disable(){
        txt_id.setEnabled(false);
        txt_nama_cs.setEnabled(false);
        txt_total_harga.setEnabled(false);
        txt_total_reimbursement.setEnabled(false);
    }
    
    public void kosong(){
        combo_id_inv.setSelectedItem(null);
        txt_nama_cs.setText("");
        txt_total_harga.setText("");
        txt_total_reimbursement.setText("");
        txt_cari.setText("");
    }
    
    public void autoNumber(){
        try{
            java.sql.Connection Con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = Con.createStatement();
            String sql = "SELECT MAX(RIGHT(id_project,4)) AS nomor FROM project";
            java.sql.ResultSet res = stm.executeQuery(sql);
            
            if(res.first() ==  false){
                txt_id.setText("PRJ0001");
            } else {
                res.last();
                int no = res.getInt(1) + 1;
                String nomor = String.valueOf(no);
                int oto = nomor.length();
                switch(oto){
                    case 1: nomor = "000" +nomor; break;
                    case 2: nomor = "00" +nomor; break;
                    case 3: nomor = "0" +nomor; break;
                    case 4: nomor = "" +nomor; break;
                }
                txt_id.setText("PRJ" +nomor);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        combo_id_inv = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_nama_cs = new javax.swing.JTextField();
        txt_total_harga = new javax.swing.JTextField();
        txt_total_reimbursement = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_cari = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_untung = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Keuntungan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat SemiBold", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel1.setText("ID Keuntungan :");

        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel2.setText("ID Invoice :");

        combo_id_inv.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        combo_id_inv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_id_inv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_id_invMouseClicked(evt);
            }
        });
        combo_id_inv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_id_invActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel3.setText("Total Harga Pesanan :");

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel4.setText("Rp.");

        jLabel6.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel6.setText("Total Reimbursement :");

        jLabel7.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel7.setText("Rp.");

        jLabel9.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel9.setText("Nama Customer :");

        jButton1.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jButton2.setText("Ubah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jButton4.setText("Kosongkan");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_id)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(combo_id_inv, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(txt_nama_cs)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_total_reimbursement))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_total_harga))))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_id_inv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama_cs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total_reimbursement, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Keuntungan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat SemiBold", 0, 14))); // NOI18N

        jButton5.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jButton5.setText("Cari");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jButton6.setText("Cetak");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        tabel_untung.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_untung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_untungMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_untung);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_cari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(combo_id_inv.getSelectedItem().equals(null)){
           JOptionPane.showMessageDialog(this, "Data Invoice tidak ditemukan");
           }else{
           ubah();
           autoNumber();
           load_table_keuntungan();
           kosong();
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
           hapus();
           autoNumber();
           load_table_keuntungan();
           kosong();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        autoNumber();
        load_table_keuntungan();
        kosong();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void combo_id_invActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_id_invActionPerformed
        // TODO add your handling code here:
        nama_cs();
        total_harga();
        total_reimburse();
    }//GEN-LAST:event_combo_id_invActionPerformed

    private void combo_id_invMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_id_invMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_combo_id_invMouseClicked

    private void tabel_untungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_untungMouseClicked
        // TODO add your handling code here:
        int baris = tabel_untung.rowAtPoint(evt.getPoint());
        String id = tabel_untung.getValueAt(baris, 0).toString();
        txt_id.setText(id);
        String id_inv = tabel_untung.getValueAt(baris, 1).toString();
        combo_id_inv.setSelectedItem(id_inv);
        String total_harga = tabel_untung.getValueAt(baris, 2).toString();
        txt_total_harga.setText(total_harga);
        String total_reimbursement = tabel_untung.getValueAt(baris, 3).toString();
        txt_total_reimbursement.setText(total_reimbursement);
    }//GEN-LAST:event_tabel_untungMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       if(combo_id_inv.getSelectedItem().equals(null)){
           JOptionPane.showMessageDialog(this, "Data Invoice tidak ditemukan");
       }else{
           tambah();
           autoNumber();
           load_table_keuntungan();
           kosong();
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        autoNumber();
           load_table_keuntungan();
           kosong();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {

            String path="src/kkp.report/keuntungan.jasper";      // letak penyimpanan report

            HashMap parameter = new HashMap();

            parameter.put("prj1",txt_id.getText());
            // "no_faktur" => nama parameter yang dibuat
            //jTextFieldnofaktur.getText() ==> disesuaikan dengan jTextField yang digunakan

            JasperPrint print = JasperFillManager.fillReport(path,parameter,koneksi.koneksidb());

            JasperViewer.viewReport(print, false);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(rootPane,"Error: "+ex);

        }
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(keuntungan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(keuntungan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(keuntungan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(keuntungan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new keuntungan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combo_id_inv;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_untung;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nama_cs;
    private javax.swing.JTextField txt_total_harga;
    private javax.swing.JTextField txt_total_reimbursement;
    // End of variables declaration//GEN-END:variables
}
