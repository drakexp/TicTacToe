
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
   
   public boolean[] updateGame(int row, int col, int c_player) {
      boolean[] win_draw = new boolean[2];
      board_index[row][col] = c_player;
      win_draw[0] = checkVictory(c_player);
      win_draw[1] = checkDraw();
      if(win_draw[0]) {
         current_player = c_player;
      }
      else
         current_player = c_player == 1 ? 2 : 1;
      gbh.ListenerUpdate(current_player);
      return win_draw;
   }
   
   private boolean checkVictory(int p) {
      int row = 0;
      int col = 0;
      int ldiag = 0;
      int rdiag = 0;
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
         if(row == 3 || col == 3) {
            return true;
         }
         else {
            col = 0;
            row = 0;
         }
         rdiag_int--;
      }
      if(ldiag == 3 || rdiag == 3)
         return true;
      return false;
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