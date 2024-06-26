/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.mavenproject2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class cust_track extends javax.swing.JFrame {
    Connection con;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    ResultSet rs1;
    Customer_jframe cjf;
    String phone;

    /**
     * Creates new form cust_track
     */
    public cust_track() {}
    public cust_track(Customer_jframe cjf,String phone) {
        this.cjf = cjf;
        this.phone = phone;
        initComponents();
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            //JOptionPane.showMessageDialog(this,"Driver Loaded!");
            try{
                con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","c##deepak","barca10118");
                //JOptionPane.showMessageDialog(this,"Connected to Oracle Database!");
            }
            catch(SQLException ex){
                Logger.getLogger(Restaurant_home.class.getName()).log(Level.SEVERE,null,ex);
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
            
            
        }
        catch(ClassNotFoundException ex){
            Logger.getLogger(Restaurant_home.class.getName()).log(Level.SEVERE,null,ex);
            JOptionPane.showMessageDialog(this,ex.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order No", "Rest Name", "Delv_part Name", "Order Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jButton1)
                .addGap(58, 58, 58)
                .addComponent(jButton2)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(48, 48, 48))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            try
            {
                DefaultTableModel tblModel2 = (DefaultTableModel)jTable1.getModel();
                tblModel2.setRowCount(0);
                String sql = "select emp_id,order_no from orders_list,users where orders_list.user_id = users.user_id and phone like ?";
                ps = con.prepareStatement(sql);
                ps.setString(1,phone);
                rs = ps.executeQuery();
                while(rs.next())
                {
                    if(rs.getString("emp_id")==null)
                    {
                        String sql1 = "select ol.order_no, r.rest_name, ol.ord_time from orders_list ol, restaurants1 r, users u where u.user_id = ol.user_id and ol.rest_id = r.rest_id and phone like ? and ord_time is not null and delv_time is null and ol.order_no = ?";
                        ps = con.prepareStatement(sql1);
                        ps.setString(1,phone);
                        ps.setString(2,rs.getString("order_no"));
                        rs1 = ps.executeQuery();
                        while(rs1.next())
                        {
                            String tb[] = {rs1.getString("order_no"),rs1.getString("rest_name"),null,rs1.getString("ord_time")};
                            tblModel2.addRow(tb);
                        }
                        
                    }
                    else
                    {
                        String sql2 = "select ol.order_no, emp_name, rest_name, ord_time from orders_list ol,del_partners1 dl, restaurants1 r, users u where ol.emp_id is not null and  ol.emp_id = dl.emp_id and ol.user_id = u.user_id and ol.rest_id = r.rest_id and u.phone like ? and delv_time is null and ord_time is not null and ol.order_no = ?";
                        ps = con.prepareStatement(sql2);
                        ps.setString(1,phone);
                        ps.setString(2,rs.getString("order_no"));
                        rs1 = ps.executeQuery();
                        while(rs1.next())
                        {
                            String tb[] = {rs1.getString("order_no"),rs1.getString("rest_name"),rs1.getString("emp_name"),rs1.getString("ord_time")};
                            tblModel2.addRow(tb);
                        }
                    }   
                }
                
    
                
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this,e.getMessage());
            }// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cjf.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(cust_track.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cust_track.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cust_track.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cust_track.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cust_track().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
