import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * TicTacToe vs bot, PLAYER jalan pertama
 */
@SuppressWarnings("serial")
public class playerFirst extends JFrame {
   // Konstanta Baris Kolom
   String seri = "Hasil Seri! States yang dilewati \nq0  0000O0000- \n ↓ 1 \nq1  X000O000O- \n ↓ 3 \nq10  XOX0O000O- \n ↓ 8 \nq44  XOXOO00XO- \n ↓ 6 \nq65  XOXOOXOXOd";
   String menang = "Bot Menang! States yang dilewati \nq0  0000O0000- \n ↓ 6 \nq5  0000OX00O- \n  ↓ 1 \nq12  X000OXO0O- \n  ↓ 2 \nq49  XX00OXOOOw";

   public static final int BAR = 3;  // Baris
   public static final int KOL = 3;	 // Kolom
 
   // Konstanta untuk dimensi GUI
   public static final int UKURAN_KOTAK = 100; // Ukuran Kotak
   public static final int LEBAR = UKURAN_KOTAK * KOL;
   public static final int TINGGI = UKURAN_KOTAK * BAR;
   public static final int TEBAL_GARIS = 8;                   // Tebal garis grid
   public static final int TEBAL_GARIS_SET = TEBAL_GARIS / 2; // Tebal garis grid setengah
   
   // Simbol yg diinput pada kotak
   public static final int PADDING = UKURAN_KOTAK / 6;
   public static final int UKURAN_SIMBOL = UKURAN_KOTAK - PADDING * 2;
   public static final int TEBAL_SIMBOL = 8; // tebal simbol
 
   public enum GameState {
      BERMAIN, SERI, X_MENANG, O_MENANG
   }
   private GameState stateSekarang;  // state sekarang
 
   public enum Seed {
      KOSONG, SILANG, BULAT		//isi kotak
   }
   private Seed pemainSekarang;  // pemain sekarang
 
   private Seed[][] board   ; // Papan BARxKOL kotak
   private DrawCanvas canvas; 
   private JLabel statusBar;  
 
   /** Konstruktor */
   public playerFirst() {
      canvas = new DrawCanvas(); 
      canvas.setPreferredSize(new Dimension(LEBAR, TINGGI+150));
 
      canvas.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  
            int mouseX = e.getX();
            int mouseY = e.getY();
 
            int barisDipilih = mouseY / UKURAN_KOTAK;
            int kolomDipilih = mouseX / UKURAN_KOTAK;
 
            if (stateSekarang == GameState.BERMAIN) {
               if (barisDipilih >= 0 && barisDipilih < BAR && kolomDipilih >= 0
                     && kolomDipilih < KOL && board[barisDipilih][kolomDipilih] == Seed.KOSONG) {
                  board[barisDipilih][kolomDipilih] = pemainSekarang; // Make a move
                  updateGame(pemainSekarang, barisDipilih, kolomDipilih); // update state

                  pemainSekarang = (pemainSekarang == Seed.SILANG) ? Seed.BULAT : Seed.SILANG;
               }
            } else {       // game over
               initGame(); // restart
            }
            // Refresh
            repaint();
         }
      });

      statusBar = new JLabel("  ");
      statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
      statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
 
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(canvas, BorderLayout.CENTER);
      cp.add(statusBar, BorderLayout.PAGE_END);
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack(); 
      setTitle("vs Bot");
      setVisible(true);
 
      board = new Seed[BAR][KOL];
      initGame(); 
   }
 
   /** Initialize the game-board contents and the status */
   public void initGame() {
      for (int row = 0; row < BAR; ++row) {
         for (int col = 0; col < KOL; ++col) {
            board[row][col] = Seed.KOSONG; 
         }
      }
      board[1][1] = Seed.SILANG;
      board[0][0] = Seed.BULAT;

      stateSekarang = GameState.BERMAIN; 
      pemainSekarang = Seed.SILANG;       
   }
 

   public void updateGame(Seed theSeed, int barisDipilih, int kolomDipilih) {
      if (hasWon(theSeed, barisDipilih, kolomDipilih)) {  
         stateSekarang = (theSeed == Seed.SILANG) ? GameState.X_MENANG : GameState.O_MENANG;
      } else if (isDraw()) {
         stateSekarang = GameState.SERI;
      }
   }
 
   public boolean isDraw() {
      for (int row = 0; row < BAR; ++row) {
         for (int col = 0; col < KOL; ++col) {
            if (board[row][col] == Seed.KOSONG) {
               return false;
            }
         }
      }
      return true; 
   }
 

   public boolean hasWon(Seed theSeed, int barisDipilih, int kolomDipilih) {
      return (board[barisDipilih][0] == theSeed 
            && board[barisDipilih][1] == theSeed
            && board[barisDipilih][2] == theSeed
       || board[0][kolomDipilih] == theSeed 
            && board[1][kolomDipilih] == theSeed
            && board[2][kolomDipilih] == theSeed
       || barisDipilih == kolomDipilih           
            && board[0][0] == theSeed
            && board[1][1] == theSeed
            && board[2][2] == theSeed
       || barisDipilih + kolomDipilih == 2 
            && board[0][2] == theSeed
            && board[1][1] == theSeed
            && board[2][0] == theSeed);
   }

   class DrawCanvas extends JPanel {
      @Override
      public void paintComponent(Graphics g) { 
         super.paintComponent(g);
         setBackground(Color.WHITE);
 

         g.setColor(Color.LIGHT_GRAY);
         for (int row = 1; row < BAR; ++row) {
            g.fillRoundRect(0, UKURAN_KOTAK * row - TEBAL_GARIS_SET,
                  LEBAR-1, TEBAL_GARIS, TEBAL_GARIS, TEBAL_GARIS);
         }
         for (int col = 1; col < KOL; ++col) {
            g.fillRoundRect(UKURAN_KOTAK * col - TEBAL_GARIS_SET, 0,
                  TEBAL_GARIS, TINGGI-1, TEBAL_GARIS, TEBAL_GARIS);
         }
 

         Graphics2D g2d = (Graphics2D)g;
         g2d.setStroke(new BasicStroke(TEBAL_SIMBOL, BasicStroke.CAP_ROUND,
               BasicStroke.JOIN_ROUND)); 
         for (int row = 0; row < BAR; ++row) {
            for (int col = 0; col < KOL; ++col) {
               int x1 = col * UKURAN_KOTAK + PADDING;
               int y1 = row * UKURAN_KOTAK + PADDING;
               if (board[row][col] == Seed.SILANG) {
                  g2d.setColor(Color.RED);
                  int x2 = (col + 1) * UKURAN_KOTAK - PADDING;
                  int y2 = (row + 1) * UKURAN_KOTAK - PADDING;
                  g2d.drawLine(x1, y1, x2, y2);
                  g2d.drawLine(x2, y1, x1, y2);
               } else if (board[row][col] == Seed.BULAT) {
                  g2d.setColor(Color.BLUE);
                  g2d.drawOval(x1, y1, UKURAN_SIMBOL, UKURAN_SIMBOL);
               }
            }
         }
 
         // Cetak status
         if (stateSekarang == GameState.BERMAIN) {
            statusBar.setForeground(Color.BLACK);
            if (pemainSekarang == Seed.BULAT) {
               statusBar.setText("Bot Jalan");
            } else {
               statusBar.setText("Player Jalan");
            }
         } else if (stateSekarang == GameState.SERI) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("<html>" + seri.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
         } else if (stateSekarang == GameState.X_MENANG) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Menang! Klik Untuk Main Lagi.");
         } else if (stateSekarang == GameState.O_MENANG) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("<html>" + menang.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
         }
      }
   }
 
}
