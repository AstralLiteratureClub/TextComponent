package bet.astral.test;

import bet.astral.text.color.TextColor;
import bet.astral.text.component.Component;
import bet.astral.text.events.click.ClickEvent;
import bet.astral.text.events.click.ClickType;
import bet.astral.text.events.hover.HoverEvent;
import bet.astral.text.serializer.json.JsonComponentSerializer;
import com.google.gson.JsonElement;

public class Main {
	public static void main(String[] args){
		Component component = Component.of("Hello!")
				.appendSpace()
				.append("Copyable").clickEvent(ClickType.ALL, ClickEvent.copyToClipboard("This should be copied to clipboard!"))
				.appendSpace()
				.append("Hover me for some cool action ig!").setColor(TextColor.WHITE).hoverEvent(HoverEvent.showText("Uh oh do do not leak!"))
				.appendNewline()
				.appendTranslatable("This is a translation hopefully")
				.appendSpace()
				.appendTranslatable("Hello").setFallback("Bye")
				.appendSpace()
				.append("{0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}").addPlaceholder("Test").addPlaceholder("Try").addPlaceholder("Third one !")
				;

		JsonComponentSerializer serializer = new JsonComponentSerializer(true);
		JsonElement serialized = serializer.serialize(component);
		System.out.println();
		System.out.println("Serializing components");
		System.out.println();
		System.out.println(serializer.serializeJson(component));
		System.out.println();
		System.out.println("Deserializing json");
		System.out.println();

		Component deserialized = serializer.deserialize(serialized);
		System.out.println(serializer.serializeJson(deserialized));

		System.out.println();
		System.out.println(component.getPlain());
		System.out.println();
		System.out.println(deserialized.getParsed());
		System.out.println();
	}
}
