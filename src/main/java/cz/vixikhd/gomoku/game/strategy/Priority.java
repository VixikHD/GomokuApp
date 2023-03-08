package cz.vixikhd.gomoku.game.strategy;

/**
 * Priority is related to next move, which is player going to take.
 * That means win has higher priority, than lose
 */
public class Priority {
	final private Type type;
	final private int priority;

	public Priority(Priority.Type type, int priority) {
		this.type = type;
		this.priority = priority;
	}

	public int compareWith(Priority priority) {
		if (this.priority == priority.priority) {
			if (this.type.equals(priority.type))
				return 0;
			return this.type.compareWith(priority.type);
		}

		if (this.priority > priority.priority) {
			return 1;
		}

		return -1;
	}

	public String toString() {
		return "Priority(" + this.priority + ", " + (this.type.equals(Type.WIN) ? "W" : "L") + ")";
	}

	public enum Type {
		WIN,
		LOSE;

		public int compareWith(Type type) {
			if (this.equals(type)) {
				return 0;
			}
			if (this.equals(WIN)) {
				return -1;
			}
			return 1;
		}
	}
}
