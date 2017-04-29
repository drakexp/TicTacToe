import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

public class ButtonHandler implements ActionListener {
   private JFrame window;
   private Board board;
   private Box box;
   
   public ButtonHandler(JFrame window) {
      this.window = window;
   }
   
   public ButtonHandler(Menu menu, JFrame window) {
      this.window = window;
   }
   

   public void actionPerformed(ActionEvent event) {
      String button_name = event.getActionCommand();
      
      if (button_name.equals("Two Players"))
         board = new Board(window, this);
      else if (button_name.equals("Play against CPU")) {
         // Create CPU difficulty option items
         window.getContentPane().removeAll();
         window.revalidate();
         window.repaint();
         
         box = Box.createVerticalBox();
         box.setBorder(new EmptyBorder(30, 5, 30, 5));
         
         Dimension size = new Dimension(300, 60);
         box.add(createButton("Easy", size));
         box.add(Box.createVerticalStrut(20));
         box.add(createButton("Normal", size));
         box.add(Box.createVerticalStrut(20));
         box.add(createButton("Back", size));
         
         window.add(box);
         window.pack();
         window.setVisible(true);
         
      }
      else if (button_name.equals("Easy") || button_name.equals("Normal")) {
         String[] options = new String[] {"First", "Second", "Cancel"};
         int response = JOptionPane.showOptionDialog(null, "Would you like to go first or second?", "Versus CPU",
             JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
             null, options, options[0]);
         String turn = 
            response == 0 ? "First"    :
            response == 1 ? "Second"   :
            null;
         if (turn != null)
            board = new Board(window, this, turn, button_name);
         else
            return;
      }
      else if (button_name.equals("Go to menu") || button_name.equals("Back")) {
         new Menu(window);
      }
      else if (button_name.equals("Go again")) {
         board.goAgain();
      }
      else if (button_name.equals("Exit"))
         System.exit(0);
   }
   
   private JButton createButton(String name, Dimension dim) {
      JButton button = new JButton(name);
      button.setPreferredSize(dim);
      button.setMinimumSize(dim);
      button.setMaximumSize(dim);
      button.addActionListener(this);
      return button;
   }
}