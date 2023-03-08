package cz.vixikhd.gomoku.math;

public class Vector2i {
	private final int x, y;

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i addVector(Vector2i vector) {
		return this.add(vector.getX(), vector.getY());
	}

	public Vector2i add(int x, int y) {
		return new Vector2i(this.x + x, this.y + y);
	}

	public Vector2i subtractVector(Vector2i vector) {
		return this.subtract(vector.getX(), vector.getY());
	}

	public Vector2i subtract(int x, int y) {
		return new Vector2i(this.x - x, this.y - y);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public String toString() {
		return "Vector2i(" + this.x + ":" + this.y + ")";
	}
}
