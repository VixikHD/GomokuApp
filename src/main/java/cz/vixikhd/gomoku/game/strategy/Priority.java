package cz.vixikhd.gomoku.game.strategy;

/**
 * Priority is related to next move, which is player going to take.
 * That means win has higher priority, than lose
 */
public class Priority {
    final public static Priority DEFENSIVE_PRIORITY = new Priority(Type.LOSE_PRIORITY, 1000);
    final public static Priority OFFENSIVE_PRIORITY = new Priority(Type.WIN_PRIORITY, 1001);

    final private Type type;
    final private int priority;

    public Priority(Priority.Type type, int priority) {
        this.type = type;
        this.priority = priority;
    }

    public int compareWith(Priority priority) {
        if(this.priority == priority.priority) {
            if(this.type.equals(priority.type))
                return 0;
            return this.type.compareWith(priority.type);
        }

        if(this.priority < priority.priority) {
            return 1;
        }

        return -1;
    }

    public enum Type {
        WIN_PRIORITY,
        LOSE_PRIORITY;

        public int compareWith(Type type) {
            if(this.equals(type)) {
                return 0;
            }
            if(this.equals(WIN_PRIORITY)) {
                return 1;
            }
            return -1;
        }
    }
}
