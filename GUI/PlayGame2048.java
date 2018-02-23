/*  GUI用のライブラリ */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.Color;


/* Random */
import java.util.Random;
/* 画面表示を綺麗に出すためのライブラリ  */
import javax.swing.plaf.metal.MetalLookAndFeel;

public class PlayGame2048 extends JFrame implements WindowListener, KeyListener{

    static JLabel[][] box; //二次元配列でLabel(マス)を設定する

    static int height = 4;
    static int width = 4; //縦4, 横4の大きさ, この値を変えるとFrameの大きさは分割するサイズが変えられる
    static int cotinue = 2;
    static DialogParts dialogParts;  //DialogParts(別ファイルにある)
    static Game2048 game; // Game2048を処理するクラス（CUIで使用）
    static Random rand;

    public PlayGame2048(){}

  public PlayGame2048(String title){
	super(title); //親クラスのコンストラクタを呼び出す
	this.box = new JLabel[height][width]; //Labelの定義をする
	this.rand = new Random(); // 2048制御用乱数
	this.game = new Game2048(); // Game2048の作成
	game.startGame(rand);
	this.init();
	this.setSize(100 * width, 100 * height); //Frameの大きさを指定
	this.setVisible(true); //画面に表示させる
	this.addKeyListener(this);
    }
    //メイン
    public static void main(String[] args){
	PlayGame2048 b = new PlayGame2048("2048");
    }

    private void init(){
	// 画面が綺麗に出るように調整
	try {
	    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
	} catch (Exception e) {
	    System.out.println("gue");
	}

	this.addWindowListener(this); //表示されるWindowの右上の×でWindowが消えるようにするため
	this.dialogParts = new DialogParts(this,"finish");
	this.setGUI();
    }

    private void setGUI(){ // 画面の初期設定
	this.setLayout(new GridLayout(height, width)); //Frameを指定した大きさで分割する（height x width）
	for(int y = 0; y < height; y++){
	    for(int x = 0; x < width; x++){
		String str =Integer.toString(game.board.getCell(x, y)); // board画面からの読み込み
		if (str.equals("0")) str = " ";
		this.box[y][x] = new JLabel(" " + str);
		// ラベルの表示設定
		this.box[y][x].setHorizontalAlignment(JLabel.CENTER);
		this.box[y][x].setFont(new Font("Arial", Font.PLAIN, 20));
		this.box[y][x].setBorder(new LineBorder(Color.BLACK));
		this.add(box[y][x]); //ラベルを追加する
	    }
	}
    }


    // ゲームを最初から行うメソッド
    public void restart(){
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		game.board.setCell(x, y, 0);
	    }
	}
	game.startGame(rand);
	this.GUIupdate();
    }

    // コンティニューを行うメソッド
    public void outRandom(Random random) {
	int rx = random.nextInt(4); // x座標を取る
	int ry = random.nextInt(4); // y座標を取る
	game.board.setCell(rx, ry, 0);
	this.GUIupdate();
    }

    /**
     * ダイアログを生成するメソッド
     * @param message 関数を呼び出した文字列を受け取る
     */
    static public void showDialog(String message){
	dialogParts.showDialog(message);
    }

    // これから下のwindowで始まるメソッドはimplements WindowsListenerをしたときに記述しておかなければならない
    public void windowOpened(WindowEvent e) {}

    //このメソッドでwindowが開いた時、右上の×でwindowを消せるようになる　
    public void windowClosing(WindowEvent e) {
	System.out.println("windowCloseing");
	System.exit(0);
    }

    public void windowClosed(WindowEvent e) {}

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {}

    public void windowActivated(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}


    // 画面の書き換えを行うメソッド
    public void GUIupdate() {
	for (int y = 0; y < 4; y++) {
	    for (int x = 0; x < 4; x++) {
	        String str = Integer.toString(game.board.getCell(x, y));
		if (str.equals("0")) str = " ";
		box[y][x].setText(str); // タイルをセットする
	    }
	}
    }

    // キーが押されたときの処理
    public void keyPressed(KeyEvent e) {
	char keycode = e.getKeyChar(); // 押されたキーをchar型に変換
	if(game.movable() == true) { // ゲームが続けれる場合
	    game.turn(rand, keycode);
	    this.GUIupdate();
	}else {
	    // ゲームオーバーになった場合
	    cotinue--;
	    showDialog("GAME OVER!!   Continue...the last " + cotinue + " time.");
	}

	// 画面を閉じる
	if (keycode == 'z') {
	    System.out.println("ゲームを終了します.");
	    System.exit(0);
	}
    }

    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){
    }


}
