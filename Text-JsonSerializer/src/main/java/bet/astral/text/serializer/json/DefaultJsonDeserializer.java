package bet.astral.text.serializer.json;


import bet.astral.text.color.TextColor;
import bet.astral.text.component.Component;
import bet.astral.text.component.translation.TranslatableComponent;
import bet.astral.text.events.click.ClickEvent;
import bet.astral.text.events.click.ClickEventType;
import bet.astral.text.events.click.ClickType;
import bet.astral.text.events.hover.HoverEvent;
import bet.astral.text.events.hover.HoverType;
import bet.astral.text.placeholder.Placeholder;
import bet.astral.tuples.Pair;
import com.google.gson.*;

import java.lang.reflect.Type;

public class DefaultJsonDeserializer implements JsonDeserializer<Component> {
	private final Gson gson = new Gson();
	private final boolean deserializeEventComponents;

	public DefaultJsonDeserializer(boolean deserializeEventComponents) {
		this.deserializeEventComponents = deserializeEventComponents;
	}

	@Override
	public Component deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		return deserializeBase(jsonElement);
	}
	public Component deserializeBase(JsonElement jsonElement) {
		if (jsonElement == null || jsonElement.isJsonNull()){
			return null;
		}
		if (jsonElement.isJsonObject()) {
			return deserialize(jsonElement.getAsJsonObject());
		}

		if (jsonElement.isJsonArray()) {
			JsonArray array = jsonElement.getAsJsonArray();
			Component component = null;
			for (JsonElement element : array) {
				if (element.isJsonNull()) {
					continue;
				}
				if (component != null) {
					component = component.append(deserialize(element.getAsJsonObject()));
				} else {
					component = deserialize(element.getAsJsonObject());
				}
			}
			return component;
		} else {
			return deserialize(jsonElement.getAsJsonObject());
		}
	}

	public Component deserialize(JsonObject object) {
		return deserialize(object, deserializeEventComponents);
	}

	public Component deserialize(JsonObject object, boolean events) {
		String value = object.get("value").getAsString();
		String type = object.get("type").getAsString();
		JsonElement colorElement = object.get("color");
		TextColor color = null;
		if (colorElement != null && !colorElement.isJsonNull()) {
			color = TextColor.of(colorElement.getAsString());
		}
		HoverEvent hover = null;
		if (events) {
			hover = deserializeHover(object.getAsJsonObject("hover_events"));
		}
		Component component;
		if (type.equalsIgnoreCase("translation")) {
			component = Component.translatable(value, color);
			TranslatableComponent transComp = (TranslatableComponent) component;
			transComp.setFallback(deserializeBase(object.getAsJsonArray("fallback")));
		} else {
			component = Component.of(value, color);
		}

		if (object.get("placeholders") != null && !object.get("placeholders").isJsonNull()){
			component.createPlaceholders();
			JsonArray array = object.getAsJsonArray("placeholders");
			for (JsonElement placeholderElement : array){
				Placeholder<?> placeholder = deserializePlaceholder(placeholderElement.getAsJsonObject());
				// Tries to throw null exception, but placeholders are created before
				//noinspection DataFlowIssue
				component.getPlaceholders().put(placeholder.getId(), placeholder);
			}
		}

		if (object.get("click_events")!=null&&!object.get("click_events").isJsonNull()){
			JsonArray array = object.getAsJsonArray("click_events");
			for (JsonElement element : array){
				if (element.isJsonObject()){
					Pair<ClickType, ClickEvent> pair = deserializeClick(element.getAsJsonObject());
					component.clickEvent(pair.getFirst(), pair.getSecond());
				}
			}
		}

		return component.hoverEvent(hover);
	}

	public HoverEvent deserializeHover(JsonObject object) {
		if (object == null || object.isJsonNull()) {
			return null;
		}
		String hoverType = object.get("hover_type").getAsString();
		HoverType type = HoverType.get(hoverType);
		if (type == null) {
			return null;
		}
		JsonElement valueObj = object.get("value");
		Component valueComp = null;
		String valueStr = null;
		if (valueObj.isJsonArray()) {
			valueComp = deserializeBase(valueObj.getAsJsonArray());
		} else {
			valueStr = valueObj.getAsString();
		}

		HoverEvent event;
		if (valueComp != null) {
			event = HoverEvent.of(type, valueComp);
		} else {
			event = HoverEvent.of(type, valueStr);
		}
		return event;
	}

	public Pair<ClickType, ClickEvent> deserializeClick(JsonObject object){
		final String clickTypeStr = object.get("click_type").getAsString();
		final ClickType clickType = ClickType.valueOf(clickTypeStr);
		final String value = object.get("value").getAsString();
		final String eventTypeStr = object.get("click_event_type").getAsString();
		final ClickEventType type = ClickEventType.get(eventTypeStr);
		ClickEvent clickEvent = type.create(value);
		return Pair.of(clickType, clickEvent);
	}

	private Placeholder<?> deserializePlaceholder(JsonObject object){
		int id = object.get("id").getAsInt();
		Placeholder.Type type = Placeholder.Type.valueOf(object.get("type").getAsString());
		Object value = null;
		switch (type){
			case COMPONENT -> {
				value = deserializeBase(object.get("value"));
			}
			case LONG -> {
				value = object.get("value").getAsLong();
			}
			case INTEGER -> {
				value = object.get("value").getAsInt();
			}
			case FLOAT -> {
				value = object.get("value").getAsFloat();
			}
			case DOUBLE -> {
				value = object.get("value").getAsDouble();
			}
			case STRING, CHARACTER -> {
				value = object.get("value").getAsString();
			}
		}
		String extra = object.get("extra") != null ? object.get("extra").isJsonNull() ? object.get("extra").getAsString() : null : null;

		return new Placeholder<>(id, value, extra, type);
	}

	public Gson getGson(){
		return gson;
	}
}