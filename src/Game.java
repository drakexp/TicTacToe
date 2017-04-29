import java.util.Hashtable;
import java.util.Random;

public class Game {
   private GameButtonHandler gbh;
   private int current_player;
   private int num_row;
   private int num_col;
   private int[][] board_index;
   private Random rand = new Random();
   
   public Game(GameButtonHandler gbh, int num_row, int num_col) {
      this.gbh = gbh;
      this.num_row = num_row;
      this.num_col = num_col;
      board_index = new int[num_row][num_col];
   }
   
   public void playPVP() {
      initPVPGame();
   }
   
   public void playAI(String mode, String turn) {
      initAIGame(mode, turn);
   }
   
   private void initPVPGame() {
      current_player = 1;
      gbh.initPVP(this, current_player);
   }
   
   private void initAIGame(String mode, String turn) {
      current_player = 1;
      gbh.initAI(this, current_player, mode, turn);
   }
   
   public Hashtable<String, Integer> simEasy() {
      Hashtable<String, Integer> results = new Hashtable<String, Integer>();
      results = getRandomIndices();
      return results;
   }
   
   public Hashtable<String, Integer> simNormal(int c_player) {
      int opposing_player = c_player == 1 ? 2 : 1;
      Hashtable<String, Integer> results = new Hashtable<String, Integer>();
      
      // CPU Win check
      results = checkOneMoveWin(opposing_player, c_player);
      
      // Block opponent winning condition
      if (results.get("row") == -1 && results.get("col") == -1)
         results = checkOneMoveWin(c_player, opposing_player);
      
      // random
      if(results.get("row") == -1 && results.get("col") == -1)
         results = getRandomIndices();
      
      return results;
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
   
   private Hashtable<String, Integer> checkVictory(int cp) {
      // for reference: the hashtable win key with 0 value = no win
      Hashtable<String, Integer> data = new Hashtable<String, Integer>();
      int row = 0; // 1 (Hashtable win key)
      int col = 0; // 2
      int ldiag = 0; // 3
      int rdiag = 0; // 4
      int rdiag_int = num_row-1;
      
      for (int i = 0; i < num_row; i++) {
         // right diagonal win
         if(board_index[rdiag_int][i] == cp)
            rdiag++;
         for(int j = 0; j < num_col; j++) {
            // row win
            if(board_index[i][j] == cp)
               row++;
            // column win
            if(board_index[j][i] == cp)
               col++;
            // left diagonal win
            if(i == j && board_index[i][j] == cp)
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
   
   private Hashtable<String, Integer> getRandomIndices() {
      Hashtable<String, Integer> results = new Hashtable<String, Integer>();
      int board_element = -1;
      int row = 0;
      int col = 0;
      while(board_element != 0) {
         row = rand.nextInt(num_row);
         col = rand.nextInt(num_col);
         board_element = board_index[row][col];
      }
      results.put("row", row);
      results.put("col", col);
      return results;
   }
   
   private Hashtable<String, Integer> checkOneMoveWin(int first, int second) {
      Hashtable<String, Integer> results = new Hashtable<String, Integer>();
      results.put("row", -1);
      results.put("col", -1);
      int rowchk = 0;
      int colchk = 0;
      int ldiag = 0;
      int rdiag = 0;
      int rdiag_int = num_row-1;
      
      for(int i = 0; i < num_row; i++) {
         rowchk = 0;
         colchk = 0;
         for(int j = 0; j < num_col; j++) {
            if(board_index[i][j] == second)
               rowchk++;
            if(board_index[j][i] == second)
               colchk++;
         }
         if(board_index[i][i] == second)
            ldiag++;
         if(board_index[rdiag_int][i] == second)
            rdiag++;
         rdiag_int--;
         
         if(rowchk == 2) {
            for(int k = 0; k < num_col; k++) {
               if(board_index[i][k] != second && board_index[i][k] != first) {
                  results.put("row", i);
                  results.put("col", k);
                  return results;
               }
            }
         }
         if(colchk == 2) {
            for(int k = 0; k < num_row; k++) {
               if(board_index[k][i] != second && board_index[k][i] != first) {
                  results.put("row", k);
                  results.put("col", i);
                  return results;
               }
            }
         }
      }
      if(ldiag == 2) {
         for (int i = 0; i < num_row; i++) {
            if(board_index[i][i] != second && board_index[i][i] != first) {
               results.put("row", i);
               results.put("col", i);
               return results;
            }
         }
      }
      if(rdiag == 2) {
         int a = num_row-1;
         for (int i = 0; i < num_col; i++) {
            if(board_index[a][i] != second && board_index[a][i] != first) {
               results.put("row", a);
               results.put("col", i);
               return results;
            }
            a--;
         }
      }
      return results;
   }
   
}