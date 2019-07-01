# -*-coding:Utf-8 -*

"""Fichier contenant la commande 'murer porte'."""

from commande.modifier_mur import ModifierMur
from obstacle.mur import Mur
from obstacle.porte import Porte

class MurerPorte(ModifierMur):

    """Commande permettant de murer une porte."""

    prefixe = "m"

    def executer(self, joueur, message):
        """Exécution de la commande.

        La commande 'murer porte' permet de changer une porte en
        mur. Elle attend en reste la direction sous la forme d'une
        unique lettre.

        """
        message = message.lower()
        grille = self.jeu.labyrinthe.grille
        if message not in "eons":
            joueur.envoyer("Spécifiez la direction sous la forme d'une " \
                    "seule lettre (e, s, o ou n).")
            return False

        obstacle = self.trouver_obstacle(joueur, message)
        if isinstance(obstacle, Porte):
            joueur.envoyer("Votre robot mure la porte.")
            mur = Mur(obstacle.x, obstacle.y)
            grille[obstacle.x, obstacle.y] = mur
            return True
        else:
            joueur.envoyer("Il n'y a pas de porte ici.")
            return False
