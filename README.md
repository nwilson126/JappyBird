# JappyBird
A FlappyBird port in Java, made to run on anything! The goal of this project is to bring a very fun mobile game to PCs. Besides that, I want this game to be 100% dynmically scalable and eventualy feature alternate gamemodes/levels.

<img width="798" alt="capture3" src="https://cloud.githubusercontent.com/assets/865352/16344347/be5cd6ea-3a01-11e6-9a1e-80e7b24a636d.PNG">

# Getting Started
Once you have cloned this project on your pc, open eclipse and set the repository folder as your workspace folder. It should look something like this: yourDocuments/Github/JappyBird Once it is imported, compile/run the Game.java file to start the game.

# Where are we so far?

<img width="278" alt="capture" src="https://cloud.githubusercontent.com/assets/865352/16344346/be5a78d2-3a01-11e6-8fa2-d26844fb9db7.PNG">

The entire game is powered by a tick Thread that is located in Game.java. The tick thread runs the game at a constant tick while upscaling and downscaling the fps to prevent the game from slowing. Currently the fps cap is set at 60 fps.

Each time the window is resized, all imported sprites are also resized to be proportional to the height of the window. Almost all movement variables are scaled by a static ratio Game.heightRatio(). So when the window is resized, the physics are scaled. 

<img width="278" alt="capture2" src="https://cloud.githubusercontent.com/assets/865352/16344345/be5975c2-3a01-11e6-9548-f79c6aef6f97.PNG">

In the imported workspace, you'll find a todo.txt. If you are looking for inspiration, perhaps you may want to tackle one of those challenges. Just make sure you commit the changes to an appropiately named branch. Example: collision_detection_fix

#Copyright Notice
Take note that I am not affiliated with .GEARS Studios. I have only created the code for this project. I have used the assets without explicit permission from their creator. I am using this tweet: https://twitter.com/dongatory/status/431060041009856512 / http://i.imgur.com/AcyWyqf.png as an open invitation to use the games assets in making this port. If the original copyright owner wishes to have them removed from this project, please contact me. The final version of this project will include links to www.dotgears.com and will never be sold.
