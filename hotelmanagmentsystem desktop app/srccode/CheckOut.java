package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CheckOut extends JFrame {
    Connection conn = null;
    PreparedStatement pst = null;
    private JPanel contentPane;
    private JTextField t1;
    Choice c1;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CheckOut frame = new CheckOut();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CheckOut() throws SQLException {
        conn = new conn().c; // Initialize connection properly
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(530, 200, 800, 294);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("hotel/management/system/icons/sixth.jpg"));
        Image i3 = i1.getImage().getScaledInstance(400, 225, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(i3));
        l1.setBounds(300, 0, 500, 225);
        add(l1);

        JLabel lblCheckOut = new JLabel("Check Out ");
        lblCheckOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCheckOut.setBounds(70, 11, 140, 35);
        contentPane.add(lblCheckOut);

        JLabel lblNumber = new JLabel("Number :");
        lblNumber.setBounds(20, 85, 80, 14);
        contentPane.add(lblNumber);

        c1 = new Choice();
        loadCustomerNumbers();
        c1.setBounds(130, 82, 150, 20);
        contentPane.add(c1);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("hotel/management/system/icons/tick.png"));
        Image i5 = i4.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        JButton l2 = new JButton(new ImageIcon(i5));
        l2.setBounds(290, 82, 20, 20);
        add(l2);

        l2.addActionListener(e -> {
            try {
                String number = c1.getSelectedItem();
                String query = "SELECT * FROM customer WHERE number = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, number);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    t1.setText(rs.getString("roomnumber"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JLabel lblRoomNumber = new JLabel("Room Number:");
        lblRoomNumber.setBounds(20, 132, 86, 20);
        contentPane.add(lblRoomNumber);

        t1 = new JTextField();
        t1.setBounds(130, 132, 150, 20);
        contentPane.add(t1);

        JButton btnCheckOut = new JButton("Check Out");
        btnCheckOut.addActionListener(e -> checkOut());
        btnCheckOut.setBounds(50, 200, 100, 25);
        btnCheckOut.setBackground(Color.BLACK);
        btnCheckOut.setForeground(Color.WHITE);
        contentPane.add(btnCheckOut);

        JButton btnExit = new JButton("Back");
        btnExit.addActionListener(e -> {
            new Reception().setVisible(true);
            setVisible(false);
        });
        btnExit.setBounds(160, 200, 100, 25);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        contentPane.add(btnExit);

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

    private void checkOut() {
        String id = c1.getSelectedItem();
        String roomNumber = t1.getText();
        String deleteSQL = "DELETE FROM customer WHERE number = ?";
        String updateSQL = "UPDATE room SET availability = 'Available' WHERE roomnumber = ?";

        try {
            PreparedStatement pstDelete = conn.prepareStatement(deleteSQL);
            pstDelete.setString(1, id);
            PreparedStatement pstUpdate = conn.prepareStatement(updateSQL);
            pstUpdate.setString(1, roomNumber);

            pstDelete.executeUpdate();
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(null, "Check Out Successful");
            new Reception().setVisible(true);
            setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}