import org.martin.core.*;
import org.martin.scene.*;

public class Entry {
	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(1280, 720, "Game Engine", 120);
		engine.start(new Scene());
		//
	}
}
