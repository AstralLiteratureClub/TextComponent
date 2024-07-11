package bet.astral.text.events.click;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ClickEventType {
	public static final ClickEventType COPY_TO_CLIPBOARD = register(of("copy_to_clipboard", CopyToClipboardClickEvent::new));
	static Map<String, ClickEventType> map;
	private final String name;
	private final Function<String, ClickEvent> createFunction;

	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull ClickEventType of(String name, Function<String, ClickEvent> createFunction){
		return new ClickEventType(name, createFunction);
	}
	@Contract(pure = true)
	public ClickEventType(String name, Function<String, ClickEvent> createFunction) {
		this.name = name;
		this.createFunction = createFunction;
	}

	@Contract("_ -> param1")
	public static @NotNull ClickEventType register(ClickEventType type) {
		if (map == null){
			map = new HashMap<>();
		}
		map.put(type.getName(), type);
		return type;
	}

	public static ClickEventType get(String string) {
		return map.get(string);
	}

	public String getName() {
		return name;
	}

	public ClickEvent create(String data){
		return createFunction.apply(data);
	}
}
