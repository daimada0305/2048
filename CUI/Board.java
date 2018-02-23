import java.util.Random;

public class Board {

    // --- フィールド
    private int cell = 4; // マス目の数
    public int rx, rx2, ry, ry2;
    private int[][] board; // 2次元配列で作るとき
    private int[][] board2;

    // --- コンストラクタ
    // boardを作る
    public Board() {
	this.board = new int[cell][cell];
	this.board2 = new int[cell][cell];
    }

    Random random = new Random();
    // --- メソッド
    // あるマスのタイルの値を返す
    public int getCell(int x, int y) {
	return this.board[y][x];
    }

    // あるマスのタイルの値を設定する
    public void setCell(int x, int y, int num) {
	this.board[y][x] = num;
    }

    // (課題1)盤面を表示する関数
    public void print() {
	for (int y = 0; y < cell; y++) {
	    for (int x = 0; x < cell; x++) {
                if ((x == rx && y == ry) || (x == rx2 && y == ry2)) {
                    // Hardのときは設定したセル値を隠す
                    System.out.printf("    ?");
                }else {
                    System.out.printf("%5d",this.board[y][x]);
                }
	    }
	    System.out.println();
	}
    }

    // Boordを初期画面に戻すメソッド
    public void clearBoard() {
	for (int y = 0; y < cell; y++) {
	    for (int x = 0; x < cell; x++) {
		this.setCell(x, y, 0);
	    }
	}
    }

    // Hardの場合における隠すセル値を決定するメソッド
    public void hideRandom(Random random, int mode) {
        if (mode == 1) {
            this.rx = 100;
            this.ry = 100;
            this.rx2 = 100;
            this.ry2 = 100;
        }else {
            this.rx = random.nextInt(cell); // x座標を取る
            this.ry = random.nextInt(cell); // y座標を取る
            this.rx2 = random.nextInt(cell); // x座標を取る
            this.ry2 = random.nextInt(cell); // y座標を取る
            if(rx == rx2 && ry == ry2) this.hideRandom(random, 2);
        }
    }

    // セル値の配置をシャッフルするときに使用するメソッド
    public void shuffleRandom(Random random) {
        for (int y = 0; y < cell; y++) {
            for (int x = 0; x < cell; x++) {
                this.board2[y][x] = this.board[y][x];
                this.board[y][x] = 0;
            }
        }
        for (int y = 0; y < cell; y++) {
            for (int x = 0; x < cell; x++) {
                while(true) {
                    int ranX = random.nextInt(cell); // x座標を取る
                    int ranY = random.nextInt(cell); // y座標を取る
                    if (this.board[ranY][ranX] == 0) {
                        this.board[ranY][ranX] = this.board2[y][x];
                        break;
                    }
                }
            }
        }   
    }

}
