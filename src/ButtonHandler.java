import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ButtonHandler implements ActionListener {
   private JFrame window;
   private Board board;
   
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
      else if (button_name.equals("Play against AI")) {
         String[] options = new String[] {"First", "Second", "Cancel"};
         int response = JOptionPane.showOptionDialog(null, "Would you like to go first or second?", "Versus AI",
             JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
             null, options, options[0]);
         String turn = 
            response == 0 ? "first"    :
            response == 1 ? "second"   :
            null;
         if (turn != null)
            board = new Board(window, turn, this);
         else
            return;
      }
      else if (button_name.equals("Go to menu")) {
         new Menu(window);
      }
      else if (button_name.equals("Go again")) {
         board.goAgain();
      }
      else if (button_name.equals("Exit"))
         System.exit(0);
   }
}