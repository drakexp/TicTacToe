import java.util.Hashtable;


public class Game {
   private GameButtonHandler gbh;
   private int current_player;
   private int num_row;
   private int num_col;
   private int[][] board_index;
   
   public Game(GameButtonHandler gbh, int num_row, int num_col) {
      this.gbh = gbh;
      this.num_row = num_row;
      this.num_col = num_col;
      board_index = new int[num_row][num_col];
      initGame();
   }
   
   private void initGame() {
      current_player = 1;
      gbh.initListenerUpdate(this, current_player);
   }
   
   public Hashtable<String, Integer> updateGame(int row, int col, int c_player) {
      board_index[row][col] = c_player;
      Hashtable<String, Integer> results = new Hashtable<String, Integer>();
      Hashtable<String, Integer> win = checkVictory(c_player);
      if(win.get("win") != 0) {
         results.put("win", 1);
         if(win.get("win") == 1) {
            results.put("win by", 1);
            results.put("index", win.get("row_num"));
         }
         else if(win.get("win") == 2) {
            results.put("win by", 2);
            results.put("index", win.get("col_num"));
         }
         else if(win.get("win") == 3) {
            results.put("win by", 3);
            results.put("index", 0);
         }
         else {
            results.put("win by", 4);
            results.put("index", 0);
         }
         return results;
      }
      else {
         results.put("win", 0);
         current_player = c_player == 1 ? 2 : 1;
      }
      
      boolean draw = checkDraw();
      if(draw) {
         results.put("draw", 1);
         return results;
      }
      else {
         results.put("draw", 0);
         current_player = c_player == 1 ? 2 : 1;
      }
      
      gbh.ListenerUpdate(current_player);
      return results;
   }
   
   private Hashtable<String, Integer> checkVictory(int p) {
      // for reference: the hashtable win key with 0 value = no win
      Hashtable<String, Integer> data = new Hashtable<String, Integer>();
      int row = 0; // 1 (Hashtable win key)
      int col = 0; // 2
      int ldiag = 0; // 3
      int rdiag = 0; // 4
      int rdiag_int = num_row-1;
      
      for (int i = 0; i < num_row; i++) {
         // right diagonal win
         if(board_index[rdiag_int][i] == p)
            rdiag++;
         for(int j = 0; j < num_col; j++) {
            // row win
            if(board_index[i][j] == p)
               row++;
            // column win
            if(board_index[j][i] == p)
               col++;
            // left diagonal win
            if(i == j && board_index[i][j] == p)
               ldiag++;
         }
         if(row == 3) {
            data.put("win", 1);
            data.put("row_num", i);
            return data;
         }
         else if (col == 3) {
            data.put("win", 2);
            data.put("col_num", i);
            return data;
         }
         else {
            col = 0;
            row = 0;
         }
         rdiag_int--;
      }
      if(ldiag == 3) {
         data.put("win", 3);
         return data;
      }
      else if(rdiag == 3) {
         data.put("win", 4);
         return data;
      }
      data.put("win", 0);
      return data;
   }
   
   private boolean checkDraw() {
      for (int i = 0; i < num_row; i++) {
         for (int j = 0; j < num_col; j++) {
            if(board_index[i][j] == 0)
               return false;
         }
      }
      return true;
   }
}