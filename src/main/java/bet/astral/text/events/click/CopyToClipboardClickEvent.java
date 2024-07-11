package bet.astral.text.events.click;

public class CopyToClipboardClickEvent implements ClickEvent{
	private final String value;

	public CopyToClipboardClickEvent(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public ClickEventType getClickEventType() {
		return ClickEventType.COPY_TO_CLIPBOARD;
	}
}
