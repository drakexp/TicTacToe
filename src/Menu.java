import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;


public class Menu extends JFrame {
   private JFrame window = new JFrame("Tic-Tac-Toe");
   private Box menubox;
   private ButtonHandler bh;
   
   /**
    * Called from TicTacToe.java
    */
   public Menu() {
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setLocation(500, 300);
      window.setResizable(false);     
      window.add(createMenuBox());
      window.pack();
      window.setVisible(true);
   }
   
   /**
    * Called to reinitialize Menu
    * @param window JFrame window
    */
   public Menu(JFrame window) {
      this.window = window;
      window.getContentPane().removeAll();
      window.revalidate();
      window.repaint();
      window.setTitle("Tic-Tac-Toe");
      
      window.add(createMenuBox());
      window.pack();
      window.setVisible(true);
   }
   
   private JButton createButton(String name, Dimension dim) {
      JButton button = new JButton(name);
      button.setPreferredSize(dim);
      button.setMinimumSize(dim);
      button.setMaximumSize(dim);
      button.addActionListener(bh);
      return button;
   }
   
   private Box createMenuBox() {
      menubox = Box.createVerticalBox();
      menubox.setBorder(new EmptyBorder(30, 5, 30, 5));
      
      bh = new ButtonHandler(this, window);
      
      Dimension size = new Dimension(300, 60);
      menubox.add(createButton("Two Players", size));
      menubox.add(Box.createVerticalStrut(20));
      menubox.add(createButton("Play against CPU", size));
      menubox.add(Box.createVerticalStrut(20));
      menubox.add(createButton("Exit", size));
      return menubox;
   }
}