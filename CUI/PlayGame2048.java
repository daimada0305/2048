import java.util.Random;
import java.util.Scanner;

// CUIの課題では, このクラスを実行する
public class PlayGame2048 {

    // --- メソッド
    public static void main(String[] args) {

	Random random = new Random(); // ランダムな値を作るためのクラス
	                              // 例: random.nextInt(5);
	                              //     で0--4のランダムな整数が得られる
	Game2048 game = new Game2048();

	// ゲームの難易度選択
	// Hardにするとセル値2つがランダムで隠れる
	while(true) {
	    System.out.println();
	    System.out.println("Normal -> 1 / Hard -> 2");
	    System.out.print("Please select a mode. :");
	    Scanner scan = new Scanner(System.in);
	    char mode = scan.next().charAt(0);
	    if (mode == '1') {
		game.mode = 1;
		game.board.hideRandom(random, 1);
		break;
	    }else if (mode == '2') {
		game.mode = 2;
		game.board.hideRandom(random, 2);
		break;
	    }else {
		System.out.println();
		System.out.println("Please push an correct command.");
		System.out.println();
	    }		
	}



	game.startGame(random); // (課題5)ゲームの初期盤面を作る

	while(game.movable()) {
	    game.show(); // (課題1)毎ターン盤面を表示する
	    game.turn(random); // (課題2以降)毎ターンの処理
	}
    }
}
