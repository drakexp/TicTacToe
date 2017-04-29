import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.UIManager;

public class GameButtonHandler implements ActionListener {
   private Board board;
   private int num_row;
   private int num_col;
   private JButton[][] buttonArray;
   private int current_player;
   private Game game;
   private String game_type;
   private String mode;
   private String player_turn;
   boolean winordraw = false;
   boolean playing_flag = false;
   
   public GameButtonHandler(Board board, JButton[][] buttonArray, int num_row, int num_col, String game_type) {
      this.board = board;
      this.buttonArray = buttonArray;
      this.num_row = num_row;
      this.num_col = num_col;
      this.game_type = game_type;
   }  

   public void actionPerformed(ActionEvent event) {
      if(!playing_flag) {
         playing_flag = true;
         JButton selectedBtn = (JButton) event.getSource();
         if(game_type == "2P" || (game_type == "AI" && ((current_player == 1 && player_turn == "First") 
               || (current_player == 2 && player_turn == "Second")))) {
            
            selectButton(selectedBtn);
            winordraw = checkWinorDraw(selectedBtn);
         }
         if(game_type == "AI" && !winordraw) {
            getAI();
         }
      }
      if(game_type == "2P")
         playing_flag = false;
      else {
      Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
               playing_flag = false;
            }
         });
         timer.setRepeats(false);
         timer.start();
      }
   }
   
   public void initPVP(Game g, int c_player) {
      game = g;
      current_player = c_player;
   }
   
   public void initAI(Game g, int c_player, String mode, String player_turn) {
      game = g;
      current_player = c_player;
      this.mode = mode;
      this.player_turn = player_turn;
      if(player_turn == "Second") {
         playing_flag = true;
         getAI();
         Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
               playing_flag = false;
            }
         });
         timer.setRepeats(false);
         timer.start();
      }
   }
   
   public void ListenerUpdate(int c_player) {
      current_player = c_player;
   }
   
   private void ListenerWinUpdate(int c_player, int cond, int index) {
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
      board.displayWin(c_player);
   }
   
   private void selectButton(JButton button) {
      if (current_player == 1) {
         button.setText("X");
         button.setForeground(Color.RED);
         UIManager.getDefaults().put("Button.disabledText",Color.RED);
         board.updateInfo(2);
      }
      else {
         button.setText("O");
         button.setForeground(Color.GREEN);
         UIManager.getDefaults().put("Button.disabledText",Color.GREEN);
         board.updateInfo(1);
      }
      button.setFont(new Font("Arial", Font.BOLD, 130));
      button.setBackground(Color.LIGHT_GRAY);
      button.setEnabled(false);
   }
   
   private boolean checkWinorDraw(JButton button) {
      for (int i = 0; i < num_row; i++) {
         for (int j = 0; j < num_col; j++) {
            if (buttonArray[i][j] == button) {
               Hashtable <String, Integer> win_draw = game.updateGame(i, j, current_player);
               if(win_draw.get("win") == 1) { // win
                  board.updateInfo(current_player);
                  ListenerWinUpdate(current_player, win_draw.get("win by"), win_draw.get("index"));
                  return true;
               }
               else { // no wins yet
                  if (win_draw.get("draw") == 1) { // draw
                     board.updateInfo(current_player);
                     board.displayDraw();
                     return true;
                  }
                  else
                     return false;
               }
            }
         }
      }
      return false;
   }
   
   private void getAI() {
      Timer timer = new Timer(500, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent arg0) {
            Hashtable <String, Integer> indices = new Hashtable <String, Integer>();
            if(mode == "Easy")
               indices = game.simEasy();
            else
               indices = game.simNormal(current_player);
            JButton aibutton = buttonArray[indices.get("row")][indices.get("col")];
            selectButton(aibutton);
            winordraw = checkWinorDraw(aibutton);
         }
      });
      timer.setRepeats(false);
      timer.start();
   }
}