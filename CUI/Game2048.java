import java.util.Random;
import java.util.Scanner;

public class Game2048 {
    // --- フィールド
    Board board = new Board();
    Random random = new Random();
    private int cell = 4;
    private int jude = 0; // ゲームの推移を取る変数
    private int score = 0; // ゲームスコアを記録する変数
    private int shuffle = 3; // シャッフル回数を取る変数
    public int mode; // ゲームの難易度を記憶する変数

    // --- コンストラクタ
    // Boardを初期化する
    public Game2048() {
    }

    // --- メソッド
    // 毎ターン呼ばれ, キーボードの入力を受け付ける
    public void turn(Random random) {
	if (jude != 1 && jude != 2 && jude != 4 && jude != 5) {
	    // ゲーム中の場合のみ表示するスコア値とコマンド説明
	    System.out.println("SCORE :" + score +"pt");
	    System.out.print("Up -> w / Left -> a / Down -> s / Right -> d / Shuffle -> m / Stop -> z :");
	}
	Scanner scan = new Scanner(System.in);
	char dir = scan.next().charAt(0); // 1文字目を dir に格納する
	int moving = this.move(dir); // board内の動きを取る
	// 動きがあった場合はランダム値をboardに入れる
	if(numberCount(0) == 0 && moving != 0) this.putRandom(random);
    }
	
    // (課題5)盤面をゲーム開始状態にする
    public void startGame(Random random) {
	this.putRandom(random); this.putRandom(random);
    }

    // board内に0 or 2048があるか確認するメソッド
    private int numberCount(int number) {
	for (int y = 0; y < cell; y++) {
	    for( int x = 0; x < cell; x++) {
		// board内に0があるか(ランダム値を入れれるかの判断)
		if (number == 0 && board.getCell(x, y) == 0) return 0;
		// board内に2048があるか(クリア条件の判断)
		if (number == 2048 && jude != 6 && board.getCell(x, y) == 2048) {
		    return 2048;
		}
	    }
	}
	return -1;
    }

    // ランダム値を入れる判断をするために返り値を取る
    // add(0)の場合は全セルに対して移動を行った後，返り値を返す
    private int move(char dir) {
	if ( (jude == 0||jude == 3 || jude == 6)  && dir == 'w') { // up
	    int count1 = this.moveUp(); 
	    int count2 = this.addUp(0); 
	    int count3 = this.moveUp();
	    return count1 + count2 + count3;
	}else if ((jude == 0||jude == 3 || jude == 6)&& dir == 'a') { // left
	    int count1 = this.moveLeft(); 
	    int count2 = this.addLeft(0); 
	    int count3 = this.moveLeft();
	    return count1 + count2 + count3;
	}else if ((jude == 0||jude == 3 || jude == 6) && dir == 's') { // down
	    int count1 = this.moveDown(); 
	    int count2 = this.addDown(0); 
	    int count3 = this.moveDown();
	    return count1 + count2 + count3;
	}else if ((jude == 0||jude == 3 || jude == 6) && dir == 'd') { //right
	    int count1 = this.moveRight(); 
	    int count2 = this.addRight(0); 
	    int count3 = this.moveRight();
	    return count1 + count2 + count3;
	}else if ((jude == 0||jude == 3 || jude == 6) && dir == 'm' && shuffle > 0) { //shuffle
	    shuffle--;
	    System.out.println();	
	    System.out.println("Shuffle... the last " + shuffle + " times.");
	    System.out.println();
	    board.shuffleRandom(random);
	    return 0;
	}else if ((jude == 0||jude == 3 || jude == 6) && dir == 'm' && shuffle == 0) { //no shuffle
	    System.out.println();	
	    System.out.println("Can't shuffle.");
	    System.out.println();
	    return 0;
	}else if (dir == 'z') { // zを入力した場合は終了する
	    System.out.println();	
	    System.out.println("End...");
	    System.exit(0);
	    return 0;
	}else if (jude == 1 && dir == 'c') { //clear
	    this.outRandom(random);
	    jude = 3;
	    return 0;
	}else if ((jude == 1 || jude == 4 || jude == 5) && dir == 'n') { //newgame
	    board.clearBoard();
	    this.putRandom(random);
	    board.hideRandom(random, mode);
	    jude = 0;
	    score = 0;
	    shuffle = 3;
	    return 1;
	}else if (jude == 1 && dir != 'c' && dir != 'n' && dir != 'z') { //違うコマンドが押された場合
	    jude = 2;
	    System.out.println();
	    System.out.println("Please push an correct command.");
	    System.out.println();	
	    return 0;
	}else if ((jude == 4 || jude == 5) && dir == 'y') { //クリア後の続行
	    jude = 6;
	    return 1;
	}else if ((jude == 4 || jude == 5) && dir != 'y' && dir != 'z' && dir != 'n') { 
	    jude = 5;
	    System.out.println();
	    System.out.println("Please push an correct command.");
	    System.out.println();	
	    return 0;
	}else {
	    System.out.println();
	    System.out.println("Please push an correct command.");
	    System.out.println();	
	    return 0;
	}    
    }

    // (課題3) ランダムな空きマスに2(90%)または4(10%)のタイルを置く
    private void putRandom(Random random) {
	int rx = random.nextInt(cell); // x座標を取る
	int ry = random.nextInt(cell); // y座標を取る
	int r = random.nextInt(100); // 2 or 4 の確率
	if (r < 90 && board.getCell(rx, ry) == 0) { // 90% - 2
	    board.setCell(rx, ry, 2);
	}else if(r >= 90 && board.getCell(rx, ry) == 0) { // 10% - 4
	    board.setCell(rx, ry, 4);
	}else{
	    // セルに数字があった場合
	    this.putRandom(random);
	}
    }

    // 救済処置として1つのセル値をランダムで取り除くメソッド
    private void outRandom(Random random) {
	int rx = random.nextInt(cell); // x座標を取る
	int ry = random.nextInt(cell); // y座標を取る
	board.setCell(rx, ry, 0);	
    }

    // (課題6) タイルを動かすことが出来るか調べる
    public boolean movable() {
	// セルに2048がある場合(クリア判定)
	if (jude == 4) {
	    this.show(); // クリアboardの表示
	    System.out.println("SCORE :" + score +"pt");
	    this.gameLevel();
	    System.out.println("GAME CLEAR!! Congratulations!!");
	    System.out.println();
	}
	if (jude == 4 || jude == 5) {
	    System.out.println("Do you want to continue?");
	    System.out.print("Yes -> y / No -> z / NewGame -> n :" );
	    return true;
	}

	// セルに0がある場合(続行)
	if (numberCount(0) == 0) return true;

	// boardを動かせるかの判定
	// add(1)の場合は動かせたと同時に値を返す
	int check = addUp(1) + addDown(1) + addLeft(1) + addRight(1);
	if (check > 0) return true;
	
	// どの続行操作も受け付けなかった場合
	this.show();
	if (jude == 0 || jude == 3 || jude == 6) {
	    System.out.print("SCORE :" + score +"pt");
	    this.gameLevel();
	    System.out.println("GAME OVER!!!");
	    System.out.println();
	    if (jude == 6) return false;
	}
	if (jude == 0 || jude == 2) { 
	    jude = 1;
	    System.out.println("Continue, Reload or End?");
	    System.out.print("Continue -> c  / NewGame -> n / End -> z :" );
	    return true;
	}   

	return false;
    }
	
    // (課題1) 条件にあった場合(毎ターン)呼び出して盤面を表示する
    public void show() {
	if (jude != 1 && jude != 2 && jude != 4 && jude != 5) {
	    board.print();
	    System.out.println();
	}
    }

    // 作れた数字の大きさによる称号を付与するメソッド
    private void gameLevel() {
	int level = 0;
	for (int y = 0; y < cell; y++) {
	    for (int x = 0; x < cell; x++) {
		if (board.getCell(x, y) > level) level = board.getCell(x, y);
	    }
	}

	if (level >= 4096) { System.out.println("     You are Teacher Level!!!!"); 
	} else if (level >= 2048) { System.out.println("     You are TA Level!!!"); 
	} else if (level >= 1024) { System.out.println("     You are AA student!!"); 
	} else if (level >= 512) { System.out.println("     You are A student!"); 
	} else if (level >= 256) { System.out.println("     You are B student."); 
	} else if (level >= 128) { System.out.println("     You are C student.."); 
	} else { System.out.println("     You are F student...");
	}
	System.out.println();
    }

    // 上(w)で移動を行うメソッド
    private int moveUp() {
	int count = 0; // 移動があったかを調べる変数
	for (int x = 0; x < cell; x++) { 
	    // 指標となるmakerセルを設定する
	    int maker_y = 0;
	    int maker = board.getCell(x, maker_y); 
	    for (int y = 1; y < cell; y++) {
		if (maker == 0 && board.getCell(x, y) != 0) {
		    // 上のmakerセル値が0で，検索をかけているboardセル値に0以外の数字が入っていた場合
		    // 数の入れ替えを行い，makerの値を入れ替えて0になった方へ移す
		    board.setCell(x, maker_y, board.getCell(x, y));
		    board.setCell(x, y, 0);
		    maker_y = y; maker = board.getCell(x, maker_y);
		    count++; // 入れ替えが行われたカウント
		} else if (maker != 0) {
		    // maker値に0以外の数字が入っていた場合はmakerを次に動かす
		    maker_y = y; maker = board.getCell(x, maker_y);
		}
	    }
	}
	return count; // 入れ替えの有無を返すカウント
    }


    // 上での加算を行うメソッド
    private int addUp(int check) {
	int count = 0; // 移動があったかを調べる変数
	for (int x = 0; x < cell; x++) {
	    // 指標となるrememberセルを設定する
	    int remember_y = 0; 
	    int remember = board.getCell(x, remember_y);
	    for (int y = 1; y < cell; y++) {
		// 移動の場合
		if (check == 0 && remember != 0 && remember == board.getCell(x, y)) {
		    // 上のrememberセル値が0以外で，検索をかけているboardセル値と同値だった場合
		    // rememberセル値の数を2倍して，検索セル値を0にする。
		    // その後．rememberセル値を検索セル値に動かす
		    board.setCell(x, remember_y, remember*2);
		    score += remember*2;
		    board.setCell(x, y, 0);				    
		    remember_y = y; remember = board.getCell(x, y);
		    count++; // 加算があった場合のカウント
		    // 移動が行えるかの調査		
		}else if(check == 1 && remember != 0 && remember == board.getCell(x, y)) {
		    count++;
		    return count; // 実際に移動は行わす移動ができることを返してやる
		}else if (remember == 0) {
		    // rememberが0で2倍する必要がない場合
		    remember_y = y; remember = board.getCell(x, y);
		}else if (board.getCell(x, y) != 0 && board.getCell(x, y) != remember) {
		    // 連続している数字が同値ではない場合
		    remember_y = y; remember = board.getCell(x, y);
		}
	    }
	}
	return count; //入れ替えの有無を返すカウント
    }

    // 下(s)方向への移動を行うメソッド
    private int moveDown() {
	int count = 0;
	for (int x = 0; x < cell; x++) {
	    int maker_y = 3;
	    int maker = board.getCell(x, maker_y); 
	    for (int y = 2; y >= 0; y--) {
		if (maker == 0 && board.getCell(x, y) != 0) {
		    board.setCell(x, maker_y, board.getCell(x, y));
		    board.setCell(x, y, 0);
		    maker_y = y; maker = board.getCell(x, maker_y);
		    count++;
		}else if (maker != 0) {
		    maker_y = y;
		    maker = board.getCell(x, maker_y);	
		}
	    }
	}
	return count;
    }

    // 下方向への加算を行うメソッド
    private int addDown(int check) {
	int count = 0;
	for (int x = 0; x < cell; x++) {
	    int remember_y = 3;
	    int remember = board.getCell(x, remember_y);
	    for (int y = 2; y >= 0; y--) {
		if (check == 0 && remember != 0 && remember == board.getCell(x, y)) {
		    board.setCell(x, remember_y, remember*2);
		    score += remember*2;
		    board.setCell(x, y, 0);
		    remember_y = y; remember = board.getCell(x, y);
		    count++;
		}else if (check == 1 && remember != 0 && remember == board.getCell(x, y)) {
		    count++;
		    return count;
		}else if (remember == 0) {
		    remember_y = y; remember = board.getCell(x, y);
		}else if (board.getCell(x, y) != 0 && board.getCell(x, y) != remember) {
		    remember_y = y; remember = board.getCell(x, y);
		}
	    }
	}
	return count;
    }

    // 左(a)方向への移動を行うメソッド
    private int moveLeft() {
	int count = 0;
	for (int y = 0; y < cell; y++) {
	    int maker_x = 0;
	    int maker = board.getCell(maker_x, y); 
	    for (int x = 1; x < cell; x++) {
		if (maker == 0 && board.getCell(x, y) != 0) {
		    board.setCell(maker_x, y, board.getCell(x, y));
		    board.setCell(x, y, 0);
		    maker_x = x; maker = board.getCell(maker_x, y);
		    count++;
		}else if (maker != 0) {
		    maker_x = x;
		    maker = board.getCell(maker_x, y);		     
		}		    
	    }
	}
	return count;
    }

    // 左方向への加算を行うメソッド
    private int addLeft(int check) {
	int count =0;
	for (int y = 0; y < cell; y++) {
	    int remember_x = 0;
	    int remember = board.getCell(remember_x, y);
	    for (int x = 1; x < cell; x++) {
		if (check == 0 && remember != 0 && remember == board.getCell(x, y)) {
		    board.setCell(remember_x, y, remember*2);
		    score += remember*2;
		    board.setCell(x, y, 0);
		    remember_x = x; remember = board.getCell(x, y);
		    count++;
		}else if (check == 1 && remember != 0 && remember == board.getCell(x, y)) {
		    count++;
		    return count;
		}else if (remember == 0) {
		    remember_x = x; remember = board.getCell(x, y);
		}else if (board.getCell(x, y) != 0 && board.getCell(x, y) != remember) {
		    remember_x = x; remember = board.getCell(x, y);
		}
	    }
	}
	return count;
    }

    // 右(d)方向への移動を行うメソッド
    private int moveRight() {
	int count = 0;
	for (int y = 0; y < cell; y++) {
	    int maker_x = 3;
	    int maker = board.getCell(maker_x, y); 
	    for (int x = 2; x >= 0; x--) {
		if (maker == 0 && board.getCell(x, y) != 0) {
		    board.setCell(maker_x, y, board.getCell(x, y));
		    board.setCell(x, y, 0);
		    maker_x = x; maker = board.getCell(maker_x, y);
		    count++;
		}else if (maker != 0) {
		    maker_x = x;
		    maker = board.getCell(maker_x, y);	
		}
	    }
	}
	return count;
    }

    // 右方向への加算を行うメソッド
    private int addRight(int check) {
	int count = 0;
	for (int y = 0; y < cell; y++) {
	    int remember_x = 3;
	    int remember = board.getCell(remember_x, y);
	    for (int x = 2; x >= 0; x--) {
		if (check == 0 && remember != 0 && remember == board.getCell(x, y)) {
		    board.setCell(remember_x, y, remember*2);
		    score += remember*2;
		    board.setCell(x, y, 0);
		    remember_x = x; remember = board.getCell(x, y);
		    count++;
		}else if (check == 1 && remember != 0 && remember == board.getCell(x, y)) {
		    count++;
		    return count;
		}else if (remember == 0) {
		    remember_x = x; remember = board.getCell(x, y);
		}else if (board.getCell(x, y) != 0 && board.getCell(x, y) != remember) {
		    remember_x = x; remember = board.getCell(x, y);
		}
	    }
	}
	return count;
    }

	
}
