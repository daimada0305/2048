public class Board {
    // --- フィールド
    private int[][] board; // 2次元配列で作るとき
    // private int[] board; // ただの配列で作るとき

    // --- コンストラクタ
    // boardを作る
    public Board() {
	this.board = new int[4][4];
	for (int y = 0; y < 4; y++) {
	    for (int x = 0; x < 4; x++) {
		this.board[y][x] = 0;
	    }
	}
    }

    // --- メソッド
    // あるマスのタイルの値を返す
    public int getCell(int x, int y) {
	return this.board[y][x];
    }

    // あるマスのタイルの値を設定する
    public void setCell(int x, int y, int num) {
	this.board[y][x] = num;
    }
    
}
