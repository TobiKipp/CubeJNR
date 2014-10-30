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

