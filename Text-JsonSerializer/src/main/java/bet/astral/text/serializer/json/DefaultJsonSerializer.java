package bet.astral.text.serializer.json;



import bet.astral.text.component.Component;
import bet.astral.text.component.translation.TranslatableComponent;
import bet.astral.text.events.click.ClickEvent;
import bet.astral.text.events.click.ClickType;
import bet.astral.text.events.hover.HoverComponentEvent;
import bet.astral.text.events.hover.HoverEvent;
import bet.astral.text.placeholder.Placeholder;
import bet.astral.tuples.Pair;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class DefaultJsonSerializer implements JsonSerializer<Component> {
	private final Gson gson = new Gson();
	private final boolean serializeEventComponents;

	public DefaultJsonSerializer(boolean serializeEventComponents) {
		this.serializeEventComponents = serializeEventComponents;
	}

	@Override
	public JsonElement serialize(Component component, Type type, JsonSerializationContext jsonSerializationContext) {
		return serializeBase(component);
	}

	private JsonArray serializeBase(Component component){
		if (component == null){
			return null;
		}
		Component first = component.getFirstComponent();
		ArrayList<JsonObject> objects = new ArrayList<>();
		objects.add(serialize(first, true));
		while (true){
			Component nullable = first.getNext();
			if (nullable != null){
				objects.add(serialize(nullable));
				first = nullable;
				continue;
			}
			break;
		}
		JsonArray array = new JsonArray();
		for (JsonObject obj : objects){
			array.add(obj);
		}
		return array;
	}

	private JsonObject serialize(Component component){
		return serialize(component, serializeEventComponents);
	}
	private JsonObject serialize(Component component, boolean serializeEvents){
		JsonObject object = new JsonObject();
		object.addProperty("value", component.getValue());
		object.addProperty("type", component.getType());
		if (component.getColor() != null) {
			if (component.getColor().isAlphaPresent()) {
				object.addProperty("color", component.getColor().toHexAlpha());
			} else {
				object.addProperty("color", component.getColor().toHex());
			}
		}
		if (component instanceof TranslatableComponent translationComponent){
			object.add("fallback", serializeBase(translationComponent.getFallback()));
		}


		if (component.getPlaceholders()!=null&&!component.getPlaceholders().isEmpty()){
			JsonArray array = new JsonArray();
			Map<Integer, Placeholder<?>> placeholderMap = component.getPlaceholders();
			for (int i = 0; i < 10; i ++){
				if (placeholderMap.get(i) == null){
					continue;
				}
				array.add(serialize(placeholderMap.get(i)));
			}

			object.add("placeholders", array);
		}

		if (serializeEvents) {
			if (component.getHoverEvent() != null) {
				object.add("hover_events", serialize(component.getHoverEvent()));
			}
			if (component.getClickEvents()!=null&& !component.getClickEvents().isEmpty()){
				JsonArray array = new JsonArray();
				for (Map.Entry<ClickType, ClickEvent> entry : component.getClickEvents().entrySet()){
					if (entry.getValue()==null){
						continue;
					}
					Pair<ClickType, ClickEvent> pair =Pair.of(entry);
					JsonObject event = serialize(pair);
					array.add(event);
				}
				object.add("click_events", array);
			}
		}
		return object;
	}
	private JsonObject serialize(HoverEvent event){
		JsonObject object = new JsonObject();
		object.addProperty("hover_type", event.getType().getName());
		if (event instanceof HoverComponentEvent componentEvent){
			object.add("value", serializeBase(componentEvent.getComponentValue()));
		} else {
			object.addProperty("value", event.getValue());
		}
		return object;
	}
	public JsonObject serialize(Pair<ClickType, ClickEvent> pair){
		final ClickType clickType = pair.getFirst();
		final ClickEvent event = pair.getSecond();
		final JsonObject object = new JsonObject();
		object.addProperty("click_type", clickType.name());
		object.addProperty("value", event.getValue());
		object.addProperty("click_event_type", event.getClickEventType().getName());
		return object;
	}


	private JsonObject serialize(Placeholder<?> placeholder){
		JsonObject object = new JsonObject();
		object.addProperty("id", placeholder.getId());
		object.addProperty("type", placeholder.getType().name());
		switch (placeholder.getType()){
			case COMPONENT -> {
				object.add("value", serializeBase((Component) placeholder.getValue()));
			}
			case LONG, INTEGER, FLOAT, DOUBLE -> {
				object.addProperty("value", (Number) placeholder.getValue());
			}
			case STRING -> {
				object.addProperty("value", (String) placeholder.getValue());
			}
			case CHARACTER -> {
				object.addProperty("value", (Character) placeholder.getValue());
			}
		}
		object.addProperty("extra", placeholder.getExtra());
		return object;
	}

	public Gson getGson(){
		return gson;
	}
}
