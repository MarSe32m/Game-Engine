# Game-Engine
Java Game Engine developed by Sebastian Toivonen. This engine will be an ongoing project for many years to come and might at some point be good and later be nonexistant because of some new programming language for game development that everyone will migrate to but it will still be a good learning process.

While I agree that Java is not and never will be the best language for game development, it's still a popular language and many novice programmers start with Java. So for people that have started out with programming and are keen on game development, this engine will introduce a good starting point with out being another Scratch (which is total garbage in my opinion) and has some capabilities. I mean who uses LibGDX anyways?

The first task is to make the engine easy to use but still have some reasonably good functionality and cross platform (Windows, Linux and Mac). Firstly a comprehensive graphical framework upon which people can build their own game logic. Then we would add some helpers to the game logic side such as Actions (see SpriteKit documentation) and physics. I'm planning on making these decoupled in such a way that they aren't needed to be used if the user doesn't want to. That way the user can create their own logic without having to suffer from these systems' (Action, Physics etc.) performance hits. 

Next step would be to create some kind of editor for it so that game creation would be easier right out of the box. First this editor will simply be a level editor for placing objects in the game world. After that I might attempt on doing a "Scripting" solution so that people can attach their "scripts" on their game objects. BUT, there is never going to be a plan to introduce a scripting language or integrate any currently used scripting language (lua, C# etc) to the engine, every possible script is going to be in Java.

Finally somekind of networking framework so that synchronizing game states etc. would effectivily be just one liners. In addition I would create some kind of demo game using this engine.

Now note that:
- This engine will not be a competitor to Unity or Unreal.
- This engine is there to allow programmers with Java knowledge to build games with a single jar file dependency
- This is a learning experience, after sometime I might not have time or motivation to continue this project so it's laid out as open source for people to pick on my novice mistakes. 

The 2D graphical side of the engine architecture is inspired heavily by SpriteKit, such that the visual content is separated into Scenes and object are Nodes with a Scene graph. 3D side will be made as similar to the 2D side but some adjustments has to be made. 

The architecture will be:
- Graphics (rendering)
- Physics 
- Actions
- Constraints
