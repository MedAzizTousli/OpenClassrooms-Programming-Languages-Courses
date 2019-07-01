# -*-coding:Utf-8 -*

"""Ce module contient la classe Labyrinthe."""

import os

from obstacle.mur import Mur
from obstacle.porte import Porte
from obstacle.sortie import Sortie
from robot import Robot

class Labyrinthe:

    """Classe représentant un labyrinthe.

    Un labyrinthe est une grille comprenant des murs placés à endroits fixes
    ainsi que plusieurs robots. D'autres types d'obstacles pourraient
    également s'y rencontrer.

    Paramètres à préciser à la construction :
        obstacles -- une liste des obstacles déjà positionnés

    Pour créer un labyrinthe à partir d'une chaîne (par exemple à partir
    d'un fichier), considérez la fonction 'creer_labyrinthe_depuis_chaine'
    définie au-dessous de la classe.

    """

    limite_x = 20
    limite_y = 20
    def __init__(self, obstacles):
        self.robots = []
        self.grille = {}
        self.invisibles = []
        self.gagnee = False
        for obstacle in obstacles:
            if (obstacle.x, obstacle.y) in self.grille:
                raise ValueError("les coordonnées x={} y={} sont déjà " \
                        "utilisées dans cette grille".format(obstacle.x,
                        obstacle.y))

            if obstacle.x > self.limite_x or obstacle.y > self.limite_y:
                raise ValueError("l'obstacle {} a des coordonnées trop " \
                        "grandes".format(obstacle))

            self.grille[obstacle.x, obstacle.y] = obstacle

    def ajouter_robot(self, robot):
        """Ajoute un robot dans le labyrinthe."""
        self.grille[robot.x, robot.y] = robot
        self.robots.append(robot)

    def afficher(self, robot=None):
        """Retourne la chaîne représentant le labyrinthe.

        On prend les limites pour afficher la grille. Les obstacles et
        les robots sont affichés en utilisant leur attribut de classe
        'symbole'.

        On peut également préciser en paramètre un robot qui sera
        celui du joueur consultant la grille. Son robot sera marqué
        en majuscule pour le repérer plus facilement.

        """
        y = 0
        grille = ""

        while y < self.limite_y:
            x = 0
            while x < self.limite_x:
                case = self.grille.get((x, y))
                if case:
                    symbole = case.symbole
                    if case is robot:
                        symbole = symbole.upper()
                    grille += symbole
                else:
                    grille += " "

                x += 1

            grille += "\n"
            y += 1

        return grille

    def actualiser_invisibles(self):
        """Cette méthode actualise les obstacles invisibles.

        Si un robot passe sur un obstacle passable (disons une porte),
        l'obstacle ne s'affiche pas. En fait, il est supprimé de la grille,
        mais placé dans les obstacles invisibles et sera de nouveau
        afficher quand le robot se sera de nouveau déplacé.

        """
        for obstacle in list(self.invisibles):
            if (obstacle.x, obstacle.y) not in self.grille:
                self.grille[obstacle.x, obstacle.y] = obstacle
                self.invisibles.remove(obstacle)

    def deplacer_robot(self, robot, direction):
        """Déplace le robot.

        La direction est à préciser sous la forme de chaîne, "nord",
        "est", "sud", ou "ouest".

        Si le robot rencontre un obstacle impassable, il s'arrête.

        """
        coords = [robot.x, robot.y]
        if direction == "nord":
            coords[1] -= 1
        elif direction == "est":
            coords[0] += 1
        elif direction == "sud":
            coords[1] += 1
        elif direction == "ouest":
            coords[0] -= 1
        else:
            raise ValueError("direction {} inconnue".format(direction))

        x, y = coords
        if x >= 0 and x < self.limite_x and y >= 0 and y < self.limite_y:
            # On essaye de déplacer le robot
            # On vérifie qu'il n'y a pas d'obstacle à cet endroit
            obstacle = self.grille.get((x, y))
            if obstacle is None or obstacle.peut_traverser:
                if obstacle:
                    self.invisibles.append(obstacle)

                # On supprime l'ancienne position du robot
                del self.grille[robot.x, robot.y]

                # On place le robot au nouvel endroit
                self.grille[x, y] = robot
                robot.x = x
                robot.y = y
                self.actualiser_invisibles()

                # On appelle la méthode 'arriver' de l'obstacle, si il existe
                if obstacle:
                    obstacle.arriver(self, robot)
                return True

        return False

def creer_labyrinthe_depuis_chaine(chaine):
    """Crée un labyrinthe depuis une chaîne.

    Les symboles sont définis par correspondance ici.

    """
    symboles = {
        "o": Mur,
        ".": Porte,
        "u": Sortie,
    }

    x = 0
    y = 0
    obstacles = []
    for lettre in chaine:
        if lettre == "\n":
            x = 0
            y += 1
            continue
        elif lettre.lower() in " x":
            pass
        elif lettre.lower() in symboles:
            classe = symboles[lettre.lower()]
            objet = classe(x, y)
            obstacles.append(objet)
        else:
            raise ValueError("symbole inconnu {}".format(lettre))

        x += 1

    labyrinthe = Labyrinthe(obstacles)
    return labyrinthe
