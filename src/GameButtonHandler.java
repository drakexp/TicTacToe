import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

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
               Hashtable <String, Integer> win_draw = game.updateGame(i, j, current_player);
               if(win_draw.get("win") == 1) { // win
                  board.updateInfo(current_player);
                  ListenerWinUpdate(current_player, win_draw.get("win by"), win_draw.get("index"));
                  break;
               }
               else { // no wins yet
                  if (win_draw.get("draw") == 1) { // draw
                     board.updateInfo(current_player);
                     board.displayDraw();
                     break;
                  }
                  else
                     break;
               }
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
   
   private void ListenerWinUpdate(int x, int cond, int index) {
      for (int i = 0; i < num_row; i++) {
         for (int j = 0; j < num_col; j++) {
            buttonArray[i][j].setEnabled(false);
            buttonArray[i][j].setBackground(Color.LIGHT_GRAY);
         }
      }
      if(cond == 1)
         for (int col = 0; col < num_col; col++)
            buttonArray[index][col].setBackground(Color.CYAN);
      else if(cond == 2)
         for (int row = 0; row < num_col; row++)
            buttonArray[row][index].setBackground(Color.CYAN);
      else if(cond == 3) 
         for (int i = 0; i < num_row; i++)
            buttonArray[i][i].setBackground(Color.CYAN);
      else {
         int a = num_row-1;
         for (int col = 0; col < num_col; col++) {
            buttonArray[a][col].setBackground(Color.CYAN);
            a--;
         }
      }       
      board.displayWin(x);
   }
}