package bet.astral.text.events;

public interface EventType {
	EventType HOVER = new EventTypeImpl("hover");
	EventType CLICK = new EventTypeImpl("click");

	static EventType of(String name){
		return new EventTypeImpl(name);
	}
	String getName();
}
