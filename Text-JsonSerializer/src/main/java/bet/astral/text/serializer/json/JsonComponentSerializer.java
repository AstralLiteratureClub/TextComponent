package bet.astral.text.serializer.json;

import bet.astral.text.serializer.TextDeserializer;
import bet.astral.text.serializer.TextSerializer;
import bet.astral.text.component.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class JsonComponentSerializer implements TextSerializer<JsonElement>, TextDeserializer<JsonElement> {
	public static JsonComponentSerializer JSON = new JsonComponentSerializer();
	private final Gson gson;
	public JsonComponentSerializer(boolean serializeEventComponentEvents){
		gson = new GsonBuilder().registerTypeAdapter(Component.class, new DefaultJsonSerializer(serializeEventComponentEvents))
				.registerTypeAdapter(Component.class, new DefaultJsonDeserializer(serializeEventComponentEvents))
//				.setPrettyPrinting()
				.create();
	}
	public JsonComponentSerializer(){
		this(false);
	}

	public Component deserializeJson(String serialized){
		return gson.fromJson(serialized, Component.class);
	}
	public String serializeJson(Component component){
		return gson.toJson(component, Component.class);
	}
	@Override
	public Component deserialize(JsonElement serialized) {
		return gson.fromJson(serialized, Component.class);
	}

	@Override
	public JsonElement serialize(Component component) {
		return gson.toJsonTree(component, Component.class);
	}
}
