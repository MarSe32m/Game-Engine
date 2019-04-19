import org.martin.core.*;
import org.martin.engineTesting.*;

public class Entry {
	public static void main(String[] args) {
		new CoreEngine(1280, 720, "Game Engine", 300).start(new GameScene());
	}
}
