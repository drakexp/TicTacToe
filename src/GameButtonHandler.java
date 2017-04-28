import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.UIManager;

public class GameButtonHandler implements ActionListener {
   private Board board;
   private int num_row;
   private int num_col;
   private JButton[][] buttonArray;
   private int current_player;
   private Game game;
   
   public GameButtonHandler(Board board, JButton[][] buttonArray, int num_row, int num_col) {
      this.board = board;
      this.buttonArray = buttonArray;
      this.num_row = num_row;
      this.num_col = num_col;
   }  

   public void actionPerformed(ActionEvent event) {
      JButton selectedBtn = (JButton) event.getSource();
      if (current_player == 1) {
         selectedBtn.setText("X");
         selectedBtn.setForeground(Color.RED);
         UIManager.getDefaults().put("Button.disabledText",Color.RED);
         board.updateInfo(2);
      }
      else {
         selectedBtn.setText("O");
         selectedBtn.setForeground(Color.GREEN);
         UIManager.getDefaults().put("Button.disabledText",Color.GREEN);
         board.updateInfo(1);
      }
      selectedBtn.setFont(new Font("Arial", Font.BOLD, 130));
      selectedBtn.setBackground(Color.LIGHT_GRAY);
      selectedBtn.setEnabled(false);
      for (int i = 0; i < num_row; i++) {
         for (int j = 0; j < num_col; j++) {
            if (buttonArray[i][j] == selectedBtn) {
               boolean[] win_draw = game.updateGame(i, j, current_player);
               if(win_draw[0] == true) {
                  ListenerWinUpdate(current_player);
                  break;
               }
               else if (win_draw[1] == true) {
                  board.displayDraw();
                  break;
               }
               else 
                  break;
            }
         }
      }
   }
   
   public void initListenerUpdate(Game g, int x) {
      game = g;
      current_player = x;
   }
   
   public void ListenerUpdate(int x) {
      current_player = x;
   }
   
   private void ListenerWinUpdate(int x) {
      for (int i = 0; i < num_row; i++) {
         for (int j = 0; j < num_col; j++) {
            buttonArray[i][j].setEnabled(false);
            buttonArray[i][j].setBackground(Color.LIGHT_GRAY);
         }
      }
      board.displayWin(x);
   }
}