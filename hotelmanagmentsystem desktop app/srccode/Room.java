package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.sql.*;

public class Room extends JFrame {
    private Connection conn = null;
    private JPanel contentPane;
    private JTable table;
    private JLabel lblAvailability;
    private JLabel lblCleanStatus;
    private JLabel lblPrice;
    private JLabel lblBedType;
    private JLabel lblRoomNumber;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Room frame = new Room();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Room() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 200, 1100, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Load background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("hotel/management/system/icons/eight.jpg"));
        Image i3 = i1.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(i3));
        l1.setBounds(500, 0, 600, 600);
        add(l1);

        table = new JTable();
        table.setBounds(0, 40, 500, 400);
        contentPane.add(table);

        JButton btnLoadData = new JButton("Load Data");
        btnLoadData.addActionListener(e -> loadRoomData());
        btnLoadData.setBounds(100, 470, 120, 30);
        btnLoadData.setBackground(Color.BLACK);
        btnLoadData.setForeground(Color.WHITE);
        contentPane.add(btnLoadData);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            new Reception().setVisible(true);
            setVisible(false);
        });
        btnBack.setBounds(290, 470, 120, 30);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        contentPane.add(btnBack);

        lblAvailability = new JLabel("Availability");
        lblAvailability.setBounds(119, 15, 69, 14);
        contentPane.add(lblAvailability);

        lblCleanStatus = new JLabel("Clean Status");
        lblCleanStatus.setBounds(216, 15, 76, 14);
        contentPane.add(lblCleanStatus);

        lblPrice = new JLabel("Price");
        lblPrice.setBounds(330, 15, 46, 14);
        contentPane.add(lblPrice);

        lblBedType = new JLabel("Bed Type");
        lblBedType.setBounds(417, 15, 76, 14);
        contentPane.add(lblBedType);

        lblRoomNumber = new JLabel("Room Number");
        lblRoomNumber.setBounds(12, 15, 90, 14);
        contentPane.add(lblRoomNumber);

        getContentPane().setBackground(Color.WHITE);
    }

    private void loadRoomData() {
        try {
            conn = new conn().c; // Ensure connection is established
            String displayRoomSql = "SELECT * FROM Room"; // Check table name
            System.out.println("Executing SQL: " + displayRoomSql);
            ResultSet rs = conn.createStatement().executeQuery(displayRoomSql);
            table.setModel(DbUtils.resultSetToTableModel(rs)); // Ensure DbUtils is implemented
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}