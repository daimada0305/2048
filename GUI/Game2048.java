import java.util.Random;
import java.util.Scanner;


/*
 *中身はCUIと同じである
 */


public class Game2048 {
    // --- フィールド
    Board board = new Board();

    // --- コンストラクタ
    // Boardを初期化する
    public Game2048() {
    }

    // --- メソッド
    // 毎ターン呼ばれ, キーボードの入力を受け付ける
    public void turn(Random random, char dir) {
        // 1文字目を dir に格納する
	// !!! 処理を追加すること !!!
	int moving = this.move(dir);
	if(numberCount() == 0 && moving != 0) this.putRandom(random);	
    }

    private int numberCount() {
	for (int y = 0; y < 4; y++) {
	    for (int x = 0; x < 4; x++) {
		if (board.getCell(x, y) == 0) return 0;
	    }
	}
	return -1;
    }

    // (課題5)盤面をゲーム開始状態にする
    public void startGame(Random random) {
	this.putRandom(random); this.putRandom(random);
    }

    
    // (課題2, 4, 7) wasdのどれかの方向にタイルを動かす
    public int move(char dir) {
	if (dir == 'w') { // up
	    int count1 = this.moveUp(); 
	    int count2 = this.addUp(0); 
	    int count3 = this.moveUp();
	    return count1 + count2 + count3;
	}else if (dir == 'a') { // left
	    int count1 = this.moveLeft(); 
	    int count2 = this.addLeft(0); 
	    int count3 = this.moveLeft();
	    return count1 + count2 + count3;
	}else if (dir == 's') { // down
	    int count1 = this.moveDown(); 
	    int count2 = this.addDown(0); 
	    int count3 = this.moveDown();
	    return count1 + count2 + count3;
	}else if (dir == 'd') { //right
	    int count1 = this.moveRight(); 
	    int count2 = this.addRight(0); 
	    int count3 = this.moveRight();
	    return count1 + count2 + count3;
	}else {
	    return 0;
	}    
    }

    // (課題3) ランダムな空きマスに2(90%)または4(10%)のタイルを置く
    private void putRandom(Random random) {
	int rx = random.nextInt(4);
	int ry = random.nextInt(4);
	int r = random.nextInt(100);
	if (r < 90 && board.getCell(rx, ry) == 0) {
	    board.setCell(rx, ry, 2);
	}else if(r >= 90 && board.getCell(rx, ry) == 0) {
	    board.setCell(rx, ry, 4);
	}else{
	    this.putRandom(random);
	}    
    }
    
    // (課題6) タイルを動かすことが出来るか調べる
    public boolean movable() {
	if (this.numberCount() == 0) return true;
	int check = addUp(1) + addDown(1) + addLeft(1) + addRight(1);
	if (check > 0) return true;
		
	return false;
    }

    private int moveUp() {
    	int count = 0;
	for (int x = 0; x < 4; x++) {
	    int maker_y = 0;
	    int maker = board.getCell(x, maker_y); 
	    for (int y = 1; y < 4; y++) {
		if (maker == 0 && board.getCell(x, y) != 0) {
		    board.setCell(x, maker_y, board.getCell(x, y));
		    board.setCell(x, y, 0);
		    maker_y = y; maker = board.getCell(x, maker_y);
		    count++;
		} else if (maker != 0) {
		    maker_y = y; maker = board.getCell(x, maker_y);
		}
	    }
	}
	return count;
    }

    private int addUp(int check) {
	int count = 0;
	for (int x = 0; x < 4; x++) {
	    int remember_y = 0;
	    int remember = board.getCell(x, remember_y);
	    for (int y = 1; y < 4; y++) {
		if (check == 0 && remember != 0 && remember == board.getCell(x, y)) {
		    board.setCell(x, remember_y, remember*2);
		    board.setCell(x, y, 0);				    
		    remember_y = y; remember = board.getCell(x, y);
		    count++;
		}else if(check == 1 && remember != 0 && remember == board.getCell(x, y)) {
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

    private int moveDown() {
    	int count = 0;
	for (int x = 0; x < 4; x++) {
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

    private int addDown(int check) {
	int count = 0;
	for (int x = 0; x < 4; x++) {
	    int remember_y = 3;
	    int remember = board.getCell(x, remember_y);
	    for (int y = 2; y >= 0; y--) {
		if (check == 0 && remember != 0 && remember == board.getCell(x, y)) {
		    board.setCell(x, remember_y, remember*2);
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


    private int moveLeft() {
    	int count = 0;
	for (int y = 0; y < 4; y++) {
	    int maker_x = 0;
	    int maker = board.getCell(maker_x, y); 
	    for (int x = 1; x < 4; x++) {
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


    private int addLeft(int check) {
	int count =0;
	for (int y = 0; y < 4; y++) {
	    int remember_x = 0;
	    int remember = board.getCell(remember_x, y);
	    for (int x = 1; x < 4; x++) {
		if (check == 0 && remember != 0 && remember == board.getCell(x, y)) {
		    board.setCell(remember_x, y, remember*2);
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


    private int moveRight() {
    	int count = 0;
	for (int y = 0; y < 4; y++) {
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

    private int addRight(int check) {
	int count = 0;
	for (int y = 0; y < 4; y++) {
	    int remember_x = 3;
	    int remember = board.getCell(remember_x, y);
	    for (int x = 2; x >= 0; x--) {
		if (check == 0 && remember != 0 && remember == board.getCell(x, y)) {
		    board.setCell(remember_x, y, remember*2);
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
