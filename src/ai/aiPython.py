from py4j.java_gateway import JavaGateway

import numpy

import time

def goToLocation(x_pos, rotation):
    while tetris_java.getRotation() is not rotation:
        if tetris_java.getRotation() - rotation < 0:
            actions_obj.rotateClockwise()
        else:
            actions_obj.rotateCounterClockwise()
        time.sleep(0.1)
    
    while tetris_java.getX() != x_pos:
        if tetris_java.getX() > x_pos:
            actions_obj.moveLeft()
        else:
            actions_obj.moveRight()
        time.sleep(0.1)

'''
Connects to java script
'''
gateway = JavaGateway()
tetris_game = gateway.jvm.tetris.TetrisDriver()
actions_obj = tetris_game.getActionsObject()
tetris_java = tetris_game.getGameUI()
terminal = gateway.jvm.System.out

goToLocation(7,1)


# numpy.zeros((state_size, 5))

while True:
 #  actions_obj.moveLeft()
    time.sleep(0.1)

