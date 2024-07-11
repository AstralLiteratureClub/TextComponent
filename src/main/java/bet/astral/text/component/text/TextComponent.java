package bet.astral.text.component.text;

import bet.astral.text.component.Component;
import org.jetbrains.annotations.NotNull;

public interface TextComponent extends Component {
	@Override
	default @NotNull String getType() {
		return "text";
	}
}
