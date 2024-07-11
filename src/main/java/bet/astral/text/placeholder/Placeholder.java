package bet.astral.text.placeholder;

public class Placeholder<V> {
	private final int id;
	private final V value;
	private final String extra;
	private final Type type;

	public <D extends V> Placeholder(int id, V v, String extra, Type type) {
		this.id = id;
		value = v;
		this.extra = extra;
		this.type = type;
	}
	public <D extends V> Placeholder(int id, V v, Type type) {
		this.id = id;
		value = v;
		extra = null;
		this.type = type;
	}

	public V getValue(){
		return value;
	}

	public int getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	public String getExtra() {
		return extra;
	}

	public enum Type {
		COMPONENT,
		STRING,
		INTEGER,
		LONG,
		FLOAT,
		DOUBLE,
		CHARACTER
	}
}
