# -*-coding:Utf-8 -*

"""Fichier contenant la commande abstraite 'ModifierMur."""

from commande.commande import Commande

class ModifierMur(Commande):

    """Cette commande est une commande abstraite.

    Elle ne sera pas directement utilisée par les joueurs mais elle
    servira à plusieurs commandes dont le but est de modifier le
    mur (typiquement ici, PercerMur et MurerPorte).

    """

    def trouver_obstacle(self, joueur, direction):
        """Trouve l'obstacle dans la direction indiquée.

        La direction doit être précisée sous la forme d'une unique
        lettre, 'e' pour est, 's' pour sud', 'o' pour ouest et 'n'
        pour nord. Les coordonnées du robot sont retrouvées grâce
        au joueur.

        """
        x, y = joueur.robot.x, joueur.robot.y
        grille = self.jeu.labyrinthe.grille
        if direction == 'e':
            x += 1
        elif direction == "s":
            y += 1
        elif direction == "o":
            x -= 1
        elif direction == "n":
            y -= 1
        else:
            raise ValueError("direction {} incorrecte".format(direction))

        return grille.get((x, y))
