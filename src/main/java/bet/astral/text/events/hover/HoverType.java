package bet.astral.text.events.hover;

import bet.astral.text.component.Component;
import bet.astral.text.component.text.impl.TextComponentImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HoverType {
	public static final HoverType SHOW_TEXT = register(component("show_text", TextHoverEvent::new));
	static Map<String, HoverType> map;
	private final String name;
	private final boolean isComponent;
	private final Function<Object, HoverEvent> createFunction;

	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull HoverType of(String name, Function<String, HoverEvent> createFunction){
		return new HoverType(name, o->createFunction.apply((String) o), false);
	}
	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull HoverType component(String name, Function<Component, HoverEvent> createFunction){
		return new HoverType(name, o->createFunction.apply((Component) o), true);
	}

	@Contract(pure = true)
	public HoverType(String name, Function<Object, HoverEvent> createFunction, boolean isComponent) {
		this.name = name;
		this.createFunction = createFunction;
		this.isComponent = isComponent;
	}

	@Contract("_ -> param1")
	public static @NotNull HoverType register(HoverType type) {
		if (map == null){
			map = new HashMap<>();
		}
		map.put(type.getName(), type);
		return type;
	}

	public static HoverType get(String string) {
		return map.get(string);
	}

	public String getName() {
		return name;
	}

	public boolean isComponent() {
		return isComponent;
	}

	public HoverEvent create(Component component){
		if (!isComponent){
			return createFunction.apply(component.getPlain());
		}
		return createFunction.apply(component);
	}
	public HoverEvent create(String data){
		if (isComponent){
			return createFunction.apply(new TextComponentImpl(data, null));
		}
		return createFunction.apply(data);
	}
}
