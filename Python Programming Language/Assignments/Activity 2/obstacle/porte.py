# -*-coding:Utf-8 -*

"""Fichier contenant la classe Porte, un obstacle passable."""

from obstacle.obstacle import Obstacle

class Porte(Obstacle):

    """Classe représentant une porte, un obstacle passable."""

    peut_traverser = True
    nom = "porte"
    symbole = "."
