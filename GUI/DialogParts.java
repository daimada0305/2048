/* GUI用のライブラリ */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/* Random */ 
import java.util.Random; 

/* ダイアログ表示用のパーツ  */
class DialogParts extends JDialog implements ActionListener{

    private JFrame owner;
    private JLabel text;
    JButton b1, b2, b3; //ボタンの種類
    static PlayGame2048 playgame; 
    static Random rand;
    static int cotinue = 1;

    private DialogParts(){}

    //コンストラクタ
    public DialogParts(JFrame owner, String title){
	super(owner,title);
	this.rand = new Random(); // 乱数の生成
	this.playgame = new PlayGame2048(); // PlayGameの呼び出し
	this.setGUIParts(); //GUI盤面の初期設定
	this.owner = owner;
    }

    private void setGUIParts(){
	this.setLayout(new GridLayout(3,1)); // 2 × 1の大きさで設定
	JPanel p1 = new JPanel(); //パネルを作成
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	p1.setBackground(Color.WHITE); //パネルの色を指定
	p2.setBackground(Color.WHITE);
	p3.setBackground(Color.WHITE);
	this.text = new JLabel();
	b1 = new JButton("Continue"); //パネルの表示
	b2 = new JButton("Restart");
	b3 = new JButton("End");
	b1.addActionListener(this); // ボタンの読み込み
	b2.addActionListener(this);
	b3.addActionListener(this);
	p1.add(text); // ボタンのセット
	p2.add(b1);
	p2.add(b2);
	p2.add(b3);
	this.add(p1);
	this.add(p2);
	this.add(p3);
    }

    // ダイアログの表示を行うメソッド
    public void showDialog(String message){
	this.text.setHorizontalAlignment(JLabel.CENTER); //　文字の位置
	this.text.setFont(new Font("Arial", Font.PLAIN, 15)); // 文字のフォントとサイズ
	this.text.setText(message); //textにmessageをセットする
	this.setSize(400,200); //Frameの大きさを400,200にする
	this.setVisible(true); //画面を開く
    }
	
    // 追加課題例：
    // restartボタンをクリックしたとき、盤面を初期化し、再挑戦できるようにする。
    // その関数の呼び出しはここで行う。関数はPlayGame2048.javaで実装する。
    public void actionPerformed(ActionEvent a){
	// コンティニューを行うボタン
	if (a.getSource() == b1) {
	    if (cotinue > 0) {
		cotinue--;
		this.setVisible(false); // ウインドウを閉じる
		playgame.outRandom(rand); // 1つのタイルを取り除く
	    }
	}
	// Restartを行うボタン
	else if(a.getSource() == b2) {
	    this.cotinue = 1; // コンテ回数の初期化
	    playgame.cotinue = 2;
	    this.setVisible(false); 
	    playgame.restart(); // restartの実行
	}
	//"End"システムを終わらせる
	else if(a.getSource() == b3) {
	    System.exit(0);
	}
    }

}
