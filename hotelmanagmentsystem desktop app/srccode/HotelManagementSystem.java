package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HotelManagementSystem extends JFrame implements ActionListener {

    public HotelManagementSystem() {
        // Window setup
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main panel with layered display
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1366, 768));

        // 1. Background Image
        ImageIcon originalIcon = new ImageIcon(ClassLoader.getSystemResource("hotel/management/system/icons/first.jpg"));
        JLabel backgroundLabel = new JLabel(new ImageIcon(originalIcon.getImage()
            .getScaledInstance(1366, 768, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, 1366, 768);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // 2. Title - Reduced font size to 50 (from 60)
        JLabel titleLabel = new JLabel("HOTEL MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("serif", Font.BOLD, 50)); // Smaller font
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 10, 1366, 80); // Adjusted height to match new font
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        layeredPane.add(titleLabel, Integer.valueOf(1));

        // 3. Next Button - Maintained position
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(1100, 550, 150, 50); // Same position as before
        nextButton.setBackground(Color.WHITE);
        nextButton.setForeground(Color.BLACK);
        nextButton.addActionListener(this);
        layeredPane.add(nextButton, Integer.valueOf(2));

        // Blink effect for title
        new Thread(() -> {
            while (true) {
                titleLabel.setVisible(!titleLabel.isVisible());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        add(layeredPane);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Login().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        new HotelManagementSystem();
    }
}