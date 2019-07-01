# -*-coding:Utf-8 -*

"""Ce module contient la classe Joueur.

Un joueur est ici un objet faisant le lien entre le jeu de labyrinthe
et une connexion réseau (socket).

"""

from random import choice

from obstacle.mur import Mur
from obstacle.porte import Porte
from robot import Robot

class Joueur:

    """Classe représentant un joueur.

    Un joueur est un lien entre le Jeu et une connexion réseau.
    Il est principalement là pour exécuter les commandes
    entrées. Notez cependant que ce mécanisme est utilisé par le
    serveur (module 'serveur').

    Pour construire un joueur, vous devez préciser :
        jeu -- le jeu (voir le module 'jeu' et la classe 'Jeu')
        client -- le socket connecté

    Un joueur est normalement automatiquement créé par le serveur
    qui appelle la méthode 'ajouter_joueur' de la classe 'Jeu'.

    """

    def __init__(self, jeu, client):
        self.jeu = jeu
        self.client = client
        self.numero = None
        self.robot = None

    def __repr__(self):
        return "<Joueur connecté sur {}>".format(self.client)

    def envoyer(self, message):
        """Envoie le message au client (socket)."""
        if self.client:
            self.client.send(message.encode())

    def placer_robot(self):
        """Place le robot aléatoirement sur la grille."""
        labyrinthe = self.jeu.labyrinthe
        grille = labyrinthe.grille
        libres = []
        x = 0
        y = 0

        # D'abord, on cherche les limites x et y de la grille
        l_x = labyrinthe.limite_x
        while l_x > 0:
            if (l_x, 0) in grille:
                break
            l_x -= 1

        l_y = labyrinthe.limite_y
        while l_y > 0:
            if (0, l_y) in grille:
                break
            l_y -= 1

        while y < l_y:
            x = 0
            while x < l_x:
                if (x, y) not in grille:
                    libres.append((x, y))
                x += 1
            y += 1

        x, y = choice(libres)
        robot = Robot(x, y)
        labyrinthe.ajouter_robot(robot)
        self.robot = robot
