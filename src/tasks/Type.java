package tasks;

public enum Type {
    WORK,
    PERSONAL;

    public final Type[] types() {
        return Type.values();
    }

    public final Type type(String nameOfType) {
        return Type.valueOf(nameOfType);
    }
}