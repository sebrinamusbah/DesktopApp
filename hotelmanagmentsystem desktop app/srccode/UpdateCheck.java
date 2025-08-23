package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateCheck extends JFrame {
    Connection conn = null;
    private JPanel contentPane;
    private JTextField txt_Room;
    private JTextField txt_Name;
    private JTextField txt_CheckInTime;
    private JTextField txt_Deposit;
    private JTextField txt_Payment;

    Choice c1;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateCheck frame = new UpdateCheck();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateCheck() throws SQLException {
        conn = new conn().c; // Initialize connection
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 200, 950, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUpdateCheckStatus = new JLabel("Check-In Details");
        lblUpdateCheckStatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUpdateCheckStatus.setBounds(124, 11, 222, 25);
        contentPane.add(lblUpdateCheckStatus);

        JLabel lblNewLabel = new JLabel("Number:");
        lblNewLabel.setBounds(25, 88, 80, 14);
        contentPane.add(lblNewLabel);

        c1 = new Choice();
        loadCustomerNumbers();
        c1.setBounds(248, 85, 140, 20);
        contentPane.add(c1);

        JLabel lblRoom = new JLabel("Room Number:");
        lblRoom.setBounds(25, 129, 107, 14);
        contentPane.add(lblRoom);

        txt_Room = new JTextField();
        txt_Room.setBounds(248, 126, 140, 20);
        contentPane.add(txt_Room);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(25, 174, 97, 14);
        contentPane.add(lblName);

        txt_Name = new JTextField();
        txt_Name.setBounds(248, 171, 140, 20);
        contentPane.add(txt_Name);

        JLabel lblCheckInTime = new JLabel("Check-In Time:");
        lblCheckInTime.setBounds(25, 216, 107, 14);
        contentPane.add(lblCheckInTime);

        txt_CheckInTime = new JTextField();
        txt_CheckInTime.setBounds(248, 216, 140, 20);
        contentPane.add(txt_CheckInTime);

        JLabel lblDeposit = new JLabel("Deposit (Rs):");
        lblDeposit.setBounds(25, 261, 107, 14);
        contentPane.add(lblDeposit);

        txt_Deposit = new JTextField();
        txt_Deposit.setBounds(248, 258, 140, 20);
        contentPane.add(txt_Deposit);

        JLabel lblPayment = new JLabel("Pending Amount (Rs):");
        lblPayment.setBounds(25, 302, 150, 14);
        contentPane.add(lblPayment);

        txt_Payment = new JTextField();
        txt_Payment.setBounds(248, 299, 140, 20);
        contentPane.add(txt_Payment);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateCustomerDetails());
        btnUpdate.setBounds(168, 378, 89, 23);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        contentPane.add(btnUpdate);

        JButton btnExit = new JButton("Back");
        btnExit.addActionListener(e -> {
            new Reception().setVisible(true);
            setVisible(false);
        });
        btnExit.setBounds(281, 378, 89, 23);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        contentPane.add(btnExit);

        JButton btnCheck = new JButton("Check");
        btnCheck.addActionListener(e -> loadCustomerDetails());
        btnCheck.setBounds(56, 378, 89, 23);
        btnCheck.setBackground(Color.BLACK);
        btnCheck.setForeground(Color.WHITE);
        contentPane.add(btnCheck);

        getContentPane().setBackground(Color.WHITE);
    }

    private void loadCustomerNumbers() {
        try {
            String query = "SELECT number FROM customer";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                c1.add(rs.getString("number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerDetails() {
        try {
            String customerNumber = c1.getSelectedItem();
            String query = "SELECT * FROM customer WHERE number = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, customerNumber);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_Room.setText(rs.getString("room"));
                txt_Name.setText(rs.getString("name"));
                txt_CheckInTime.setText(rs.getString("checkintime"));
                txt_Deposit.setText(rs.getString("deposit"));
                calculatePendingAmount(txt_Room.getText(), txt_Deposit.getText());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void calculatePendingAmount(String roomNumber, String paidAmount) {
        try {
            String roomQuery = "SELECT price FROM room WHERE roomnumber = ?";
            PreparedStatement pst = conn.prepareStatement(roomQuery);
            pst.setString(1, roomNumber);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int totalAmount = rs.getInt("price");
                int paid = Integer.parseInt(paidAmount);
                int pending = totalAmount - paid;
                txt_Payment.setText(String.valueOf(pending));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomerDetails() {
        try {
            String customerNumber = c1.getSelectedItem();
            String roomNumber = txt_Room.getText();
            String name = txt_Name.getText();
            String checkInTime = txt_CheckInTime.getText();
            String deposit = txt_Deposit.getText();

            String updateQuery = "UPDATE customer SET room = ?, name = ?, checkintime = ?, deposit = ? WHERE number = ?";
            PreparedStatement pst = conn.prepareStatement(updateQuery);
            pst.setString(1, roomNumber);
            pst.setString(2, name);
            pst.setString(3, checkInTime);
            pst.setString(4, deposit);
            pst.setString(5, customerNumber);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Updated Successfully");
            new Reception().setVisible(true);
            setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}