import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board {
   private String game_type;
   private String turn;
   private String mode;
   
   private String p1_name;
   private String p2_name;
   
   private int p1_score = 0;
   private int p2_score = 0;
   
   private JFrame window;
   private JLabel p1_label;
   private JLabel p1_icon;
   private JLabel p1_indicator;
   private JLabel p2_label;
   private JLabel p2_icon;
   private JLabel p2_indicator;
   private JPanel info_panel;
   private JButton back_button;
   private JButton go_again;
   private Box player_box;
   private Box score_box;
   private JLabel status_label;
   private JPanel board_panel;
   private JPanel bottom_panel;
   private JLabel p1_scoreLabel;
   private JLabel p2_scoreLabel;
   
   private int num_row = 3;
   private int num_col = 3;
   private JButton[][] buttonArray = new JButton[num_row][num_col];
   private ButtonHandler bh;
   private GameButtonHandler gbh;
   
   /**
    * Board constructor for 2 Player mode
    * @param window JFrame window
    */
   public Board(JFrame window, ButtonHandler bh) {
      game_type = "2P";
      this.window = window;
      this.bh = bh;
      createBoard();
   }
   
   /**
    * Board constructor for versus CPU mode
    * @param window JFrame window
    * @param turn player's turn
    */
   public Board(JFrame window, ButtonHandler bh, String turn, String mode) {
      game_type = "CPU";
      this.turn = turn;
      this.mode = mode;
      this.window = window;
      this.bh = bh;
      createBoard();
   }
   
   /**
    * Paint board
    */
   private void createBoard() {
      window.getContentPane().removeAll();
      window.revalidate();
      window.repaint();
      
      if(game_type == "2P") {
         p1_name = "Player 1";
         p2_name = "Player 2";
      }
      else {
         if(turn == "First") {
            p1_name = "Player";
            p2_name = "Computer";
         }
         else {
            p1_name = "Computer";
            p2_name = "Player";
         }
      }
      
      window.setTitle(p1_name + " vs " + p2_name);
      
      // bottom panel with buttons
      back_button = new JButton("Go to menu");
      back_button.addActionListener(bh);     
      
      go_again = new JButton("Go again");
      go_again.setVisible(false);
      go_again.addActionListener(bh);
      
      bottom_panel = new JPanel();
      bottom_panel.add(go_again);
      bottom_panel.add(back_button);
      
      // top info panel
      p1_label = new JLabel(p1_name + ":");
      p1_label.setFont(new Font("Arial", Font.BOLD, 14));
      
      p1_icon = new JLabel("X");
      p1_icon.setForeground(Color.RED);
      p1_icon.setFont(new Font("Arial", Font.BOLD, 16));
      
      p1_indicator = new JLabel("\u2190");
      p1_indicator.setFont(new Font("Arial", Font.BOLD, 18));
      
      p2_label = new JLabel(p2_name + ":");
      p2_label.setFont(new Font("Arial", Font.BOLD, 14));
      
      p2_icon = new JLabel("O");
      p2_icon.setForeground(Color.GREEN);
      p2_icon.setFont(new Font("Arial", Font.BOLD, 16));
      
      p2_indicator = new JLabel();
      p2_indicator.setFont(new Font("Arial", Font.BOLD, 18));
      
      player_box = Box.createHorizontalBox();
      player_box.add(Box.createHorizontalStrut(10));
      player_box.add(p1_label);
      player_box.add(Box.createHorizontalStrut(10));
      player_box.add(p1_icon);
      player_box.add(Box.createHorizontalStrut(10));
      player_box.add(p1_indicator);
      player_box.add(Box.createHorizontalStrut(225));
      player_box.add(p2_indicator);
      player_box.add(Box.createHorizontalStrut(10));
      player_box.add(p2_label);
      player_box.add(Box.createHorizontalStrut(10));
      player_box.add(p2_icon);
      player_box.setPreferredSize(new Dimension(450, 30));
      
      p1_scoreLabel = new JLabel("Score: " + p1_score);
      p2_scoreLabel = new JLabel("Score: " + p2_score);
      
      score_box = Box.createHorizontalBox();
      score_box.add(Box.createHorizontalStrut(10));
      score_box.add(p1_scoreLabel);
      score_box.add(Box.createHorizontalStrut(300));
      score_box.add(p2_scoreLabel);    
      
      status_label = new JLabel(p1_name + "'s turn",JLabel.CENTER);
      status_label.setFont(new Font("Times New Roman", Font.BOLD, 18));
      status_label.setPreferredSize(new Dimension(450, 30));
      
      info_panel = new JPanel(); 
      info_panel.add(player_box);
      info_panel.add(score_box);
      info_panel.add(status_label);
      info_panel.setLayout(new GridLayout(3,1));

      // create board
      board_panel = new JPanel();
      Dimension size = new Dimension(150, 150);
      gbh = new GameButtonHandler(this, buttonArray, num_row, num_col, game_type);
      for(int i = 0; i < num_row; i++) {
         for (int j = 0; j < num_col; j++) {
            board_panel.add(createButton(size, buttonArray, i, j));
         }
      }      

      board_panel.setLayout(new GridLayout(3,3));  
      window.add(board_panel,BorderLayout.CENTER);
      window.add(info_panel,BorderLayout.NORTH);
      window.add(bottom_panel,BorderLayout.SOUTH);
      window.pack();

      Game game = new Game(gbh, num_row, num_col);
      if(game_type == "2P")
         game.playPVP();
      else 
         game.playCPU(mode, turn);
      
      window.setVisible(true);
   }
   
   // update info panel
   public void updateInfo(int player_now) {
      if(player_now == 1) {
         p2_indicator.setText("");
         p1_indicator.setText("\u2190");
         status_label.setText(p1_name + "'s turn");
      }
      else {
         p1_indicator.setText("");
         p2_indicator.setText("\u2192");
         status_label.setText(p2_name + "'s turn");
      }
   }
   
   // display winner on info panel
   public void displayWin(int player_now) {
      if(player_now == 1) {
         p1_score++;
         status_label.setText(p1_name + " wins!");
      }
      else {
         p2_score++;
         status_label.setText(p2_name + " wins!");
      }
      p1_scoreLabel.setText("Score: " + p1_score);
      p2_scoreLabel.setText("Score: " + p2_score);
      status_label.setFont(new Font("Times New Roman", Font.BOLD, 20));
      status_label.setForeground(Color.BLUE);
      go_again.setVisible(true);
   }
   
   // display draw on info panel
   public void displayDraw() {
      status_label.setText("Draw!");
      status_label.setFont(new Font("Times New Roman", Font.BOLD, 20));
      status_label.setForeground(Color.BLUE);
      go_again.setVisible(true);
   }
   
   // go again option after game ends
   public void goAgain() {
      go_again.setVisible(false);
      status_label.setFont(new Font("Times New Roman", Font.BOLD, 18));
      updateInfo(1);
      board_panel.removeAll();
      board_panel.revalidate();
      board_panel.repaint();
      Dimension size = new Dimension(150, 150);
      gbh = new GameButtonHandler(this, buttonArray, num_row, num_col, game_type);
      for(int i = 0; i < num_row; i++) {
         for (int j = 0; j < num_col; j++) {
            board_panel.add(createButton(size, buttonArray, i, j));
         }
      }
      window.add(board_panel,BorderLayout.CENTER);
      Game game = new Game(gbh, num_row, num_col);
      if(game_type == "2P")
         game.playPVP();
      else 
         game.playCPU(mode, turn);
   }
   
   private JButton createButton(Dimension dim, JButton[][] btnArray, int row, int col) {
      btnArray[row][col] = new JButton();
      btnArray[row][col].setPreferredSize(dim);
      btnArray[row][col].setMinimumSize(dim);
      btnArray[row][col].setMaximumSize(dim);
      btnArray[row][col].addActionListener(gbh);
      btnArray[row][col].setBackground(Color.WHITE);
      btnArray[row][col].setFocusPainted(false);
      return btnArray[row][col];
   }
   
}