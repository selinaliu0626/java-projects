** Xiaonan Liu cs 622 HW1 **
-project description:
assignment 1  for 622
a program for the classic game : Donkey Kong, which includes inheritance, overriding and overloading

- approach
(1) For characters in the game

base                     child class                    interface
-------------------------------------------------------------------------
                      ----- Mario                     Attackable
 Character ------     ----- Gorilla                    Attackable
                      ----- Princess

 (2) for game environment
   Canvas : What we show when player begins the game
   some elements:
   * Oil : burn cask to fire
   * Weapon: Mario could get and attack cask

  (3) some helper class
  * MoveEnum: players could easily make movement by press 0-7
  * CharacterFactory: use singleton model to make sure only one character each in same game
  * Game: main function to begin the game




  (4) libraries
  lambok:
  @data : generate getter/setter
  @slf4j : for log
  @NoArgsConstructor: auto generate constructor with no arguments
  @AllArgsConstructor : auto generate constructor with all arguments