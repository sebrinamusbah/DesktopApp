package hotel.management.system;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateRoom extends JFrame {
    Connection conn = null;
    private JPanel contentPane;
    private JTextField txt_Room;
    private JTextField txt_Ava;
    private JTextField txt_Status;
    Choice c1;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateRoom frame = new UpdateRoom();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateRoom() throws SQLException {
        conn = new conn().c; // Initialize connection
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(530, 200, 1000, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUpdateRoomStatus = new JLabel("Update Room Status");
        lblUpdateRoomStatus.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUpdateRoomStatus.setBounds(85, 11, 206, 34);
        contentPane.add(lblUpdateRoomStatus);

        JLabel lblNewLabel = new JLabel("Guest ID:");
        lblNewLabel.setBounds(27, 87, 90, 14);
        contentPane.add(lblNewLabel);

        c1 = new Choice();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT number FROM customer");
            while (rs.next()) {
                c1.add(rs.getString("number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        c1.setBounds(160, 84, 140, 20);
        contentPane.add(c1);

        JLabel lblRoomId = new JLabel("Room Number:");
        lblRoomId.setBounds(27, 133, 100, 14);
        contentPane.add(lblRoomId);

        txt_Room = new JTextField();
        txt_Room.setBounds(160, 130, 140, 20);
        contentPane.add(txt_Room);
        txt_Room.setColumns(10);

        JLabel lblAvailability = new JLabel("Availability:");
        lblAvailability.setBounds(27, 187, 90, 14);
        contentPane.add(lblAvailability);

        JLabel lblCleanStatus = new JLabel("Clean Status:");
        lblCleanStatus.setBounds(27, 240, 90, 14);
        contentPane.add(lblCleanStatus);

        txt_Ava = new JTextField();
        txt_Ava.setBounds(160, 184, 140, 20);
        contentPane.add(txt_Ava);
        txt_Ava.setColumns(10);

        txt_Status = new JTextField();
        txt_Status.setBounds(160, 237, 140, 20);
        contentPane.add(txt_Status);
        txt_Status.setColumns(10);

        JButton b1 = new JButton("Check");
        b1.addActionListener(e -> {
            try {
                String guestId = c1.getSelectedItem();
                PreparedStatement pst = conn.prepareStatement("SELECT room FROM customer WHERE number = ?");
                pst.setString(1, guestId);
                ResultSet rs1 = pst.executeQuery();

                if (rs1.next()) {
                    txt_Room.setText(rs1.getString("room"));
                }

                PreparedStatement pst2 = conn.prepareStatement("SELECT availability, cleaning_status FROM room WHERE roomnumber = ?");
                pst2.setString(1, txt_Room.getText());
                ResultSet rs2 = pst2.executeQuery();

                if (rs2.next()) {
                    txt_Ava.setText(rs2.getString("availability"));
                    txt_Status.setText(rs2.getString("cleaning_status"));
                }
            } catch (SQLException ee) {
                ee.printStackTrace();
            }
        });
        b1.setBounds(120, 315, 89, 23);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        contentPane.add(b1);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            try {
                String updateQuery = "UPDATE room SET cleaning_status = ? WHERE roomnumber = ?";
                PreparedStatement pst = conn.prepareStatement(updateQuery);
                pst.setString(1, txt_Status.getText());
                pst.setString(2, txt_Room.getText());
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Update Successful");
                new Reception().setVisible(true);
                setVisible(false);
            } catch (SQLException ee) {
                ee.printStackTrace();
            }
        });
        btnUpdate.setBounds(60, 355, 89, 23);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        contentPane.add(btnUpdate);

        JButton btnExit = new JButton("Back");
        btnExit.addActionListener(e -> {
            new Reception().setVisible(true);
            setVisible(false);
        });
        btnExit.setBounds(180, 355, 89, 23);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        contentPane.add(btnExit);

        getContentPane().setBackground(Color.WHITE);
    }
}