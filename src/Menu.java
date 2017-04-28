import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;


public class Menu extends JFrame {
   private JFrame window = new JFrame("Tic-Tac-Toe");
   private Box box;
   private ButtonHandler bh;
   
   public Menu() {
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setLocation(500, 300);
      window.setResizable(false);

      box = Box.createVerticalBox();
      box.setBorder(new EmptyBorder(30, 5, 30, 5));
      
      bh = new ButtonHandler(this, window);
      
      Dimension size = new Dimension(300, 60);
      box.add(createButton("Two Players", size));
      box.add(Box.createVerticalStrut(20));
      box.add(createButton("Play against AI", size));
      box.add(Box.createVerticalStrut(20));
      box.add(createButton("Exit", size));
      
      window.add(box);
      window.pack();
      window.setVisible(true);
   }
   
   public Menu(JFrame window) {
      this.window = window;
      window.getContentPane().removeAll();
      window.revalidate();
      window.repaint();
      window.setTitle("Tic-Tac-Toe");
      
      box = Box.createVerticalBox();
      box.setBorder(new EmptyBorder(30, 5, 30, 5));
      
      bh = new ButtonHandler(this, window);
      
      Dimension size = new Dimension(300, 60);
      box.add(createButton("Two Players", size));
      box.add(Box.createVerticalStrut(20));
      box.add(createButton("Play against AI", size));
      box.add(Box.createVerticalStrut(20));
      box.add(createButton("Exit", size));
      
      window.add(box);
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
}