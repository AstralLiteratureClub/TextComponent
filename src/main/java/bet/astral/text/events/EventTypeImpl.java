package bet.astral.text.events;

class EventTypeImpl implements EventType{
	private final String name;

	EventTypeImpl(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
