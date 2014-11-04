******************************
Project progress documentation
******************************

Like in my other projects so far I will document the progress. This time I will try to document the code better
then before and create a "me, myself and I" less documentation, when it is suitable.
To take a break from web frameworks I will go to another topic I have partially dealt with years ago. This time I want
to keep my results in a repository. It also should help getting back deeper into Java.

The topic this time is Java OpenGL (JOGL2), which is used to create a game like program. With OpenGL a 3D game suits
most. I had something like a very simplified Super Mario 64 in my mind.
There are probably many engines out there that make things easier, however
I want to get a better understanding for the steps involved from low level graphic access to a game.



A cube - first version
======================

I set up the OpenGL following an example. The next step is to create a cube. To keep things simple
It starts with an single color. I will use a point to start at and the vector describing the diagonal.
With the need for collision detection later on I will provide a method the check if a point is inside the cube.

Object
------

- Properties

    - start
    - diagonal
    - color

- Methods

    - pointInCube
    - cubeInCube
    - move
    - setPosition

It is likely that it will be renamed later, but currently I do not have a good name for it. It would be WorldObject
or something like that. But first I will get the cube running.

I had a draw method in there at first, but that would be outside of the model. A helper class could implement how
to draw the object. The model may still contain references to textures or color names,
but that is only a reference and not the real data.

Point in cube
-------------

Due to some nice properties of cubes a point is in it if for each dimension the point is between start
and start + vector. If it hits the border it will count as in cube as well. This will be used to avoid
overlapping of colliding objects.

Cube in cube
------------

One cube is in another cube if at least one of its points is in the other cube.
As a first step the significant points start and start + vector are used.
It might happen that these two points are not in the other cube. There are still infinitely many points
that could be in the other cube in the case that this cube contains the other cube completely. For this
case the check can swap the two parts and only check a single point.

This takes up to three checks:

- Cube1.start in Cube2
- Cube1.start+Cube1.vector in Cube2
- Cube2.start in Cube1


Move and setPosition
--------------------

move will change the start position relative to its previous value.
setPostion will set to the given position.
Move is more for the animation and setPosition for resetting after a change of area.

Vector3
-------

I decided to add a data type Vector3, which has x, y and z as keys to access its components.
It could be a mathematical vector, a position, a velocity or what ever.

Change size
-----------

There might be a need to change the size of an object and therefor setter and getter for size are added.
With this addition I will take the chance to rename vector to size and start to position.

Final steps
-----------

I added tests for the Cube and Vector3 classes.


View
====

Next a DrawHelper class is added. It will have a method to draw a Cube object.
The method currently contains some extra lines such that each plane has its own color and the cube rotates on its own.
After adding the depth test the cube looks just as I had it in my mind.
I added the documentation directly into the DrawHelper class. It contains some ascii art and a table.

Control
=======

For the first steps I had to add the Object of the model to the controller::

     window.addKeyListener(new KeyboardInputController(cube));

The controller then directly operates on the Cube Object directly. A Manager for objects will follow that then will
handle the control of every object.

Before that I need to add another Vector3 to the Cube to allow rotating it. OpenGL uses degrees for rotation.
Somehow it seems I did not correctly compile the code after adding the changes for the rotation. Without changing anything
the cube suddenly rotated around its center instead of a point anywhere else.

The issue currently is that only one key per time is accepted. I hope I can change this with using an additional controller
class that receives the key pressed and release events and then handles the action according to the time that has passed.

With the changes to act on time difference and state of the keys everything is working as intended up to the limit of the
hardware. An example for the limit of the hardware (keyboard):
If two of the arrow keys are pressed one of the remaining arrow keys is still working, while the other is not.

The one thing that is currently a bit strange is the z-axis movement. The item should get larger or smaller, but instead
the visible parts get smaller. It seems I will need a camera control next.

I will have to go through an OpenGL tutorial to get how the "camera" is set up correctly.
The "camera" means I have no idea what to call it. The symptoms are
the square not having equal length, the cube is not moving.

The "camera" is set in two steps first the GL_PROJECTION matrix sets
up the perspective. In the GL_MODELVIEW Matrix the camera is position
with gluLookAt. I am not fully satisfied with the camera as it seems to look as if was on a sphere, but
For getting things running it is enough.

Adding a floor and gravity
==========================

As next step a floor will be added that the cube will be able to move on. This will need the intersection method already
defined. For this the velocity will be added to the cube instead of having it in the WorldManager.

There is an issue with the angle I am looking from. It prevents from telling if the intersection is correct or not.
Now is the moment to correctly set up the camera. There are some issues I will have to check some things first.
OpenGL favors the right handed coordinate system. In it x goes to the right, y goes up and z comes out of the monitor.
In the DrawHelper class the drawing has rotated these axis. Considering this the one plane that should be parallel to
the monitor plane should be the one numbered 6. While in the test phase it should be colored magenta.

For the cube representing the player it is magenta. For the level object it did not. It was due to the
negative sizes. There is one issue with my intersection algorithm. It only intersects if the position is in the other
cube. It seems I got things mixed upped.

A surefire cubes intersection check is check for containment of all 8 vertices of this cube other cube and any one
vertex of the other cube in this cube. The check I implemented was one usable for lines.
As a first step I will add a test that has to fail with the current implementation. Already for 0,0,1 the test fails,
which is as expected for now.

I will check for each point to be in the cube and leave early if it is found. If it can not be proven that the cubes
intersect this way the last rule applies that if any arbitrary point of this cube is in the other cube they intersect.

After the fix the tests are running fine and in the running program it works as expected for the surface of the
worldCube. To fully test the functionality the camera angle has to be altered. This is for the next session.

Setting up the camera
=====================

The coordinate system is right handed. x goes right y goes up z comes out of the the screen. Generally I want to look at
the player, currently represented by the movable cube. For a first try the camera will be twice as far away from the
openGL world origin as the box. This means the camera is at 2*cube.getPosition().

It is working somehow but it looks quite strange. In the next step
the camera is set to be in an absolute distance to the player cube. I changed the PROJECTION to orthographic, hoping
it is what I want.

The cube is change to have 2.5 time the height (y) taken from guessing the proportions of Mario on a twitch stream.
The camera I want for now is the behind view.

So what does from behind mean in coordinates? Considering world coordinates it always changes when moving. It is a
vector in the opposite direction to the movement vector. As a first step the movement direction is assumed to be
only in x direction.

Using only a different x than the players position leads to a 2d projection eliminating x. The height is in the y
component it has to be set correctly to get a good angle. From a snapshot the screen height is 6 times the size of Mario
(even though that changes depending on the zoom).

The angle is okay for now, but will be changed later when the movement is improved.

While testing around I found another error in my collision detection. When the edges are in the cube it is working just
fine. But when moving to the border and non of the edges intersecting the other cube it fails. Due to the gravity one
of the edges will eventually hit in the other cube and reset the positioning. Lets see if there is any other way
to find out if two cubes intersect.

Testing is quite hard for the 3D room if one is not used to it. The manual testing showed me that I had forgotten
the above case where none of the edges are in the other cube, but still parts overlap.

Fixing the collision detection
==============================

Now I remember how I came to the number of 3 checks for the collision. It uses 3 one dimensional lines - one for each
dimension. If for each dimension these lines of the two cubes overlap then the cubes overlap. The alternative would be
using 16 3D points checked for being in the cube the points are not from.

A real line for a 3D application would have 3 dimensions, however I am only interested in the components, which is
why there is no reason to add another class. For each 1d line the lines either overlap partially or one contains the other
if they intersect.

For each individual line it is always true:
end >= start

This leads to 4 orders for the points of lines a and b::

    a.start <= b.start <= b.end <= a.end
    a.start <= b.start <= a.end <= b.end
    b.start <= a.start <= a.end <= b.end
    b.start <= a.start <= b.end <= a.end


In all orders there is::

   a.start <= b.end
   b.start <= a.end

Swapping a and b does not change anything in the condition. The test now succeeds for all test cases. Leaving
my pointInCube method useless for now.

According to the rules::

   a.end >= a.start
   b.end >= b.start

Now with the violation of the above rules (one line per case)::

   a.start > b.end
   b.start > a.end

It follows::

   a.end >= a.start > b.end >= b.start
   b.end >= b.start > a.end >= a.start

For both cases there are no overlapping points.

Adjusting controls
==================

The goal is to turn arrow up in running forward down running backward and left and right are turning the player.
The direction the player is running is based on its x and z rotation. The y component is only of interest for jumps.
The camera should still stay behind the player, resulting in everything else turning. I will leave out jumping for
now as it might need a different camera handling.

The starting angle in each dimension is (0, 0, 0) As we walk on the xz plane the rotation axis is parallel to the y
dimension. To keep the length of the velocity vector and with it the movement speed constant it means points with
z and x are moving on a circle. The rotation of y in degrees is ry.::

   x/speed = sin(ry)
   z/speed = cos(ry)

To get things clear in my head::

    Expected from Pythagoras theorem: speed = sqrt(x^2 + z^2)
    => speed = sqrt(speed^2 * sin^2(ry) + speed^2 * cos^2(ry)
    => speed = sqrt(speed^2 * (sin^2(ry) + cos^2(ry))
    => speed = sqrt(speed^2)
    => speed = speed


On a first manual test the box probably moved as it should, but the rotation was in the wrong direction, which
is fixed by swapping the sign for rotate without further proofing things. The probably comes from the fact that the
camera was not set up yet. The camera should stay behind the box. It will have to rotate in the same angle as the
player.

Extending collision handling
============================

For now the only collision handling is putting the player on top of the intersected object. The next intersection
added is running against a wall. For this the first step is obviously adding that wall to the world.
Due to the current handling of intersections the player is put on top of the wall.

In the simplest case on an intersection with an object the velocity can be used to determine how the unit has
to be placed.
Assuming only moving in x direction leading to an intersection with a wall. With the size always being positive
if the movement direction is positive then the player runs to the right into the left handed side of the wall.
Then player.position.x+player.size.x must be smaller than wall.position.x. For negative movement direction
it is player.position.x must be larger than wall.position.x+wall.size.x.
This way does not work as I would like it to. This means the collision must be handled one step before it
actually happens.

Lets assume moving consists of the cube with diagonal from pos to pos+size changes to from pos to pos+size+velocity*dt
and afterwards shrinking again but anchored to pos+size+velocity*dt. If during its expansion it intersects with a wall
it will stop growing in that direction.
Alternatively formulated: The cube take any possible way to get to its goal and will prefer the one where it collides
(inside the room describe above).

Instead of checking the cube for intersecting the cubes potential position space is checked for intersection.

Removing code duplication
-------------------------

Much of the code is equivalent with exception to the dimension used. Therefore I will refactor the classes to
use maps instead of 3 variables.

Depending on the situation the use of 0, 1 and 2 or the use of x, y and z might be best. An array will be used
as the base structure. The get method will be changed to return the complete array, while for each dimension
the String or the index Integer can be used as first parameter. Set keeps this parameter and allows to use
another Vector3 object to set this Vector3.

While cleaning up I noticed another issue. I will now use the term velocity as it is really meant to be.
Then the previously named velocityCube must be correctly named moveCube, as it describes all possible paths
the cube may take. In addition I will move the handling of the collision with one other cube to the Cube class.
One of the reasons is that it makes it easier to test without depending on too many classes. As the moveCube
depends on the time since the last draw I will also add a method in the Cube class to generate it there.

With the restructuring I added some shortcut methods. The cubes getPosition can have an parameter which directly returns
the provided dimension. So instead of writing getPostion().get(i) it can be written as getPostion(i).

Getting the collision handling correct
--------------------------------------

First the one dimensional case:

The different cases with x as the start, ---> as the movement vector and | as the wall.

Cases that have no collision::

    x--->|
    x-->  |
    |<---x
    |  <-x

With collision::

    x---|---->
    <---|----x
    a   b    c

In case no collision happens the object is moved like the movement vector suggests.
For the collsion the vector is too long and has to be truncated such, that it is the
length of the 1. and 3. non colliding case. In the first collision case 6 character widths would
have to be removed from the movement vector and in the second case it is 5, but in the other direction.

With the names above in the integer range we have the speed vector vx and the to truncate part dx. For the first
collision case::

    vx = c-a
    dx = c-b - 1
    vxnew = vx - dx = c - a - (c - b - 1) = b - a -1

For real numbers the -1 can be drop as an infinitely small number would prevent the collision with the wall.
So it is either taking the speed vector and subtract the dx or just setting the vector as the difference between wall
and player (b-a).

So what did I do in my code? ::

    Vector3 startMoveCube = moveCube.getPosition();
    Vector3 endMoveCube = moveCube.getEnd();
    for (int i = 0; i < 3; i++) {
        double vi = velocity.get(i);
        if (vi >= MATH_ACCURACY) {
            double di = moveCube.getEnd(i) - otherCube.getPosition(i);
                if (di > 0) {
                    velocity.set(i, vi - di);
                }

vi >= MATH_ACCURACY means the speed vector is in positive direction. The MATH_ACCURACY is due to sin and cos
being not exactly 0 due to computational limitations. It is for each dimension the speed vector component vi.
I will use right and left as suitable for the x dimension in standard representations.
The difference di is the furthest right point of the player cube minus the furthest left point of the wall.
If di is negative there is no collision in this dimension. If it is positive it has to be removed from the vector
component as derived above. From vi being greater than 0 and the player being left of the wall it must hold
that vi >= di. The vector may not invert its direction.

I added some output code at one moment hitting the wall the output was::

    vi=
    4.090749190086851
    vi-di=
    -11.923634241522546

I think the issue is with the generation of the moveCube. It seems I have missed adding the time difference since
last update. vi is the velocity, which is time independent, while the di is the distance. So I mixed up units.
So the velocity to subtract is di/timediff.

Now I see were another problem lies. My test for the moveCube was using 1 second for testing, which did not reveal
the issue that I am not using timeDiff in there even though it is a parameter... (My home environment seems to heavily
affect my concentration.)

The method now uses di instead of vi with di being a distance calculated from speed x time (vi x dt). With a time of
0.5 second another moveCube is generated and tested to be as expected.

Next a test case for handleIntersection. Using just a movement in x direction with vx=2 dx=1. The cubes are of
size 1x1x1. The first cube is at (0, 0, 0) and the second at (2, 0, 0)::

    |cube1|<--dx-->|cube2| //initial position
    |----------------------------------------> vx = 2 //original speed vector
    |------> vxnew = 1 //truncate the speed vector
            |cube1||cube2| //final position

The first cube should be positioned at (1, 0, 0) after the move.

While adding a test for a case were the vector should not change I noticed something was wrong::

    Both are 1x1x1 cubes. Cube1 is at (1,0,0), Cube2 is at (2,0,0). Cube2 should be moved for 0.5 seconds with a speed
    of 1 in the x direction. Now calling the handleIntersection method should result in no change in the vector. But
    inserting the values:

        if (vi >= MATH_ACCURACY) {
        double di = 2 + 0.5*1 - 1 = 1.5
        if (di > 0) true
            velocity.set(i, 1 - 1.5 / 0.5);

This results in a negative velocity of -2 even though it should be positive. This leads to the position of Cube2 being
0 after an update. This is also the error message when running the code.

As a quick test I decided to stop the movement in the direction completely if the intersection in that direction occurs.
Due to the slow movement speed it is approximately okay. The issue becomes clear when looking at the behaviour depending
on which edge of the cube hits the wall. With an angle so that it has to move left along the wall it continues moving
without moving through the wall. When it would have to continue move right it stops.

The major issue is the angle of the object.

Pen and paper
-------------

One of the issues found in the last session is that the angle of the cube has a major influence on its behaviour.
As drawing on the screen takes more time than on paper, I will take a break from the screen and use a block to
calculate the intersection with angles and find a correct handling for the intersection. The cube should
move along the wall as long as the movement vector allows for it. I will start with the two dimensional
(x and z) collision with angles and then continue with adding the y dimension allowing to handle slopes.

After some doodling I though about testing the effects of changing the moveCube to only use the dimension of the
speed that is currently processed, as I handle dimension independently.

After all not being exactly what I wanted, I decided to use the edges of the cube again including the rotation.
For now I have a method to calculate the intersection for two lines in two dimensions. The goal of that approach
is to find the shortest of the vectors to the intersection points from all edges of the cube. (I will explain it
better later when all is ready.)