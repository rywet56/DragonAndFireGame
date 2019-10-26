# DragonAndFireGame
This very minimalistic 2D tile game was created to practice to manage "complexity", learn to apply the basic knowledge
of the java syntax as a novice Java programmer.
<p align="center">
  <img src="https://user-images.githubusercontent.com/43107602/67616927-1d6d1800-f7de-11e9-83b9-52f4ac079f44.png"        height="540" width="960">
 </p>
# Getting Started
Download all files in the repository, compile Game.java and run Game from the command line.


# Game Description
## Goal:
    equip yourself with armor, swords and ham (food) to withstand the fire and fights with dragons that you
    will encounter. Try to fight your way through to the exit door, which you can only open if you have one key
    and one red diamond equipped in your inventory. After exiting opening the door, you won the game.
    The diamond and key needed can be obtained by fighting dragons and going through fire.
    If you die, you loose the game.
## Movement:
    Use W,S,E and D to move
## Collecting items:
    Collect swords, armor and ham by standing directly in front of these items and using Left Mouse klick.
    You can only select items that are to your right, left, up and down.
    Collected items will appear in your inventory.
## equip/use items:
    Every item in the inventory can be equipped by pressing E and at the same time klicking on the respective
    item in the inventory that you want to equip.
    - Ham: When you equip ham, ham will be eaten and you obtain one life point (heart)
    - Sword: When you equip a sword, your damage will be increased by one. You can equip a maximum of two swords at a time.
    - Armor: When you equip an armor, you will be protected by five shields as shown in the top menu bar. When you
           are attacked, first these shields will be removed until the armor is used up - destroyed. When the armor is used
           destroyed, it will disappear form the inventory. You can equip a maximum of one armor at a time.
    - Red Diamond: When you equip a red diamond, nothing will happen, unless you are at the exit gate of the game.
    - Key: When you equip a key, nothing will happen, unless you are at the exit gate of the game.
## Remove items:
    If you want to remove item from your inventory, press R and Left Mouse klick on the item in your inventory that
    you want to remove. The removed item will be dropped into the world where you are currently standing and can be
    picked up again at a later point.
## Attack dragons:
    Use Left Mouse to attack dragons that sometimes drop keys. You have to be equipped with at least one sword in
    order to fight dragons. If you do not have a sword equipped, the dragon will damage you, but you won't hurt
    the dragon at all.
## Walking on fire:
    Walk on fire to extinguish it and sometimes drops a red diamond. Walking over fire  causes you damage.
