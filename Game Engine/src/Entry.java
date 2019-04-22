import java.util.*;

import org.martin.applicationTesting.*;
import org.martin.core.*;

public class Entry {
	public static void main(String[] args) {
		new CoreEngine(1280, 720, "Game Engine", 60).start(new GameScene());
	}
}
