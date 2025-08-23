package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewCustomer extends JFrame {
    private Connection conn = null;
    private JPanel contentPane;
    private JTextField t1, t2, t3, t5;
    private JComboBox<String> comboBox;
    private JRadioButton r1, r2;
    private Choice c1;
    private ButtonGroup genderGroup;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NewCustomer frame = new NewCustomer();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NewCustomer() {
        // Initialize JFrame
        setBounds(530, 200, 850, 550);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setTitle("New Customer Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("hotel/management/system/icons/fifth.png"));
        Image i3 = i1.getImage().getScaledInstance(300, 400, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(i3));
        l1.setBounds(480, 10, 300, 500);
        add(l1);

        // Form labels and fields
        JLabel lblName = new JLabel("NEW CUSTOMER FORM");
        lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
        lblName.setBounds(118, 11, 260, 53);
        contentPane.add(lblName);

        // ID label and combo box for document type
        JLabel lblId = new JLabel("ID Type:");
        lblId.setBounds(35, 76, 200, 14);
        contentPane.add(lblId);

        comboBox = new JComboBox<>(new String[]{"Passport", "Fin Number", "Driving License"});
        comboBox.setBounds(271, 73, 150, 20);
        contentPane.add(comboBox);

        // Number label and text field
        JLabel l2 = new JLabel("ID Number:");
        l2.setBounds(35, 111, 200, 14);
        contentPane.add(l2);

        t1 = new JTextField();
        t1.setBounds(271, 111, 150, 20);
        contentPane.add(t1);
        t1.setColumns(10);

        // Name label and text field
        JLabel lblName_1 = new JLabel("Name:");
        lblName_1.setBounds(35, 151, 200, 14);
        contentPane.add(lblName_1);

        t2 = new JTextField();
        t2.setBounds(271, 151, 150, 20);
        contentPane.add(t2);
        t2.setColumns(10);

        // Gender selection
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(35, 191, 200, 14);
        contentPane.add(lblGender);

        genderGroup = new ButtonGroup();
        r1 = new JRadioButton("Male");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBackground(Color.WHITE);
        r1.setBounds(271, 191, 80, 20);
        contentPane.add(r1);

        r2 = new JRadioButton("Female");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBackground(Color.WHITE);
        r2.setBounds(350, 191, 100, 20);
        contentPane.add(r2);

        genderGroup.add(r1);
        genderGroup.add(r2);

        // Country label and text field
        JLabel lblCountry = new JLabel("Country:");
        lblCountry.setBounds(35, 231, 200, 14);
        contentPane.add(lblCountry);

        t3 = new JTextField();
        t3.setBounds(271, 231, 150, 20);
        contentPane.add(t3);
        t3.setColumns(10);

        // Allocated Room Number
        JLabel lblReserveRoomNumber = new JLabel("Allocated Room Number:");
        lblReserveRoomNumber.setBounds(35, 274, 200, 14);
        contentPane.add(lblReserveRoomNumber);

        c1 = new Choice();
        try {
            conn = new conn().c;
            ResultSet rs = conn.createStatement().executeQuery("SELECT roomnumber FROM room WHERE availability = 'Available'");
            while (rs.next()) {
                c1.add(rs.getString("roomnumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading rooms: " + e.getMessage());
        }
        c1.setBounds(271, 274, 150, 20);
        contentPane.add(c1);

        // Deposit label and text field (moved up to remove gap)
        JLabel lblDeposit = new JLabel("Deposit:");
        lblDeposit.setBounds(35, 315, 200, 14);
        contentPane.add(lblDeposit);

        t5 = new JTextField();
        t5.setBounds(271, 315, 150, 20);
        contentPane.add(t5);
        t5.setColumns(10);

        // Add button
        JButton btnNewButton = new JButton("Add");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!validateInputs()) {
                    return;
                }

                String radio = r1.isSelected() ? "Male" : "Female";
                String s6 = c1.getSelectedItem();

                try {
                    if (conn == null || conn.isClosed()) {
                        conn = new conn().c;
                    }

                    String s1 = (String) comboBox.getSelectedItem();
                    String s2 = t1.getText();
                    String s3 = t2.getText();
                    String s4 = radio;
                    String s5 = t3.getText();
                    String s7 = t5.getText();

                    if (!isRoomAvailable(s6)) {
                        JOptionPane.showMessageDialog(null, "Selected room is no longer available");
                        return;
                    }

                    String q1 = "INSERT INTO customer (document, number, name, gender, country, room, checkintime, deposit) " +
                               "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";
                    try (PreparedStatement pst = conn.prepareStatement(q1)) {
                        pst.setString(1, s1);
                        pst.setString(2, s2);
                        pst.setString(3, s3);
                        pst.setString(4, s4);
                        pst.setString(5, s5);
                        pst.setString(6, s6);
                        pst.setString(7, s7);
                        pst.executeUpdate();
                    }

                    String q2 = "UPDATE room SET availability = 'Occupied' WHERE roomnumber = ?";
                    try (PreparedStatement pstUpdate = conn.prepareStatement(q2)) {
                        pstUpdate.setString(1, s6);
                        pstUpdate.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Customer added successfully!");
                    new Reception().setVisible(true);
                    dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database Error: " + e1.getMessage());
                }
            }
        });
        btnNewButton.setBounds(100, 430, 120, 30);
        btnNewButton.setBackground(Color.BLACK);
        btnNewButton.setForeground(Color.WHITE);
        contentPane.add(btnNewButton);

        // Back button
        JButton btnExit = new JButton("Back");
        btnExit.addActionListener(e -> {
            new Reception().setVisible(true);
            dispose();
        });
        btnExit.setBounds(260, 430, 120, 30);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        contentPane.add(btnExit);

        getContentPane().setBackground(Color.WHITE);
    }

    private boolean validateInputs() {
        if (t1.getText().trim().isEmpty() || 
            t2.getText().trim().isEmpty() || 
            t3.getText().trim().isEmpty() || 
            t5.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!r1.isSelected() && !r2.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select gender", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Double.parseDouble(t5.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Deposit must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean isRoomAvailable(String roomNumber) throws SQLException {
        String query = "SELECT availability FROM room WHERE roomnumber = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, roomNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Available".equals(rs.getString("availability"));
            }
        }
        return false;
    }
}
