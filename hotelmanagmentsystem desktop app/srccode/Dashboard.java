package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame {

    public static void main(String[] args) {
        new Dashboard().setVisible(true);
    }
    
    public Dashboard() {
        super("HOTEL MANAGEMENT SYSTEM");
        
        // Set window properties
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen mode
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        // Create main panel with layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        add(layeredPane);

        // Load and display background image
        try {
            ImageIcon originalIcon = new ImageIcon(ClassLoader.getSystemResource("hotel/management/system/icons/third.jpg"));
            
            // Scale image to fit screen width while maintaining aspect ratio
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double aspectRatio = (double)originalIcon.getIconHeight() / originalIcon.getIconWidth();
            int scaledHeight = (int)(screenSize.width * aspectRatio);
            
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                screenSize.width, 
                scaledHeight, 
                Image.SCALE_SMOOTH);
            
            JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
            backgroundLabel.setBounds(0, 0, screenSize.width, scaledHeight);
            layeredPane.add(backgroundLabel, Integer.valueOf(0));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading background image: " + e.getMessage());
        }

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        // Hotel Management Menu
        JMenu hotelMenu = new JMenu("HOTEL MANAGEMENT");
        hotelMenu.setForeground(new Color(0, 0, 128)); // Dark blue
        hotelMenu.setFont(new Font("SansSerif", Font.BOLD, 14));
        menuBar.add(hotelMenu);
        
        JMenuItem receptionItem = new JMenuItem("RECEPTION");
        receptionItem.addActionListener(e -> new Reception());
        hotelMenu.add(receptionItem);

        // Admin Menu
        JMenu adminMenu = new JMenu("ADMIN");
        adminMenu.setForeground(Color.RED);
        adminMenu.setFont(new Font("SansSerif", Font.BOLD, 14));
        menuBar.add(adminMenu);
        
        // Admin Menu Items
        JMenuItem addEmployeeItem = new JMenuItem("ADD EMPLOYEE");
        addEmployeeItem.addActionListener(e -> new AddEmployee().setVisible(true));
        adminMenu.add(addEmployeeItem);

        JMenuItem addRoomsItem = new JMenuItem("ADD ROOMS");
        addRoomsItem.addActionListener(e -> new AddRoom().setVisible(true));
        adminMenu.add(addRoomsItem);

        JMenuItem addDriversItem = new JMenuItem("ADD DRIVERS");
        addDriversItem.addActionListener(e -> new AddDrivers().setVisible(true));
        adminMenu.add(addDriversItem);

        // Add subtle shadow effect to menu bar
        menuBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Make sure window is properly displayed
        setVisible(true);
    }
}