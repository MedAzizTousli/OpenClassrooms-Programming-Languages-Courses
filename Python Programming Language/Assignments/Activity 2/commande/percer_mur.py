# -*-coding:Utf-8 -*

"""Fichier contenant la commande 'percer_mur'."""

from commande.modifier_mur import ModifierMur
from obstacle.mur import Mur
from obstacle.porte import Porte

class PercerMur(ModifierMur):

    """Commande permettant de percer une porte dans un mur."""

    prefixe = "p"

    def executer(self, joueur, message):
        """Exécution de la commande.

        La commande 'percer mur' permet de changer un mur en
        porte. Elle attend en reste la direction sous la forme d'une
        unique lettre.

        """
        message = message.lower()
        grille = self.jeu.labyrinthe.grille
        if message not in "eons":
            joueur.envoyer("Spécifiez la direction sous la forme d'une " \
                    "seule lettre (e, s, o ou n).")
            return False

        obstacle = self.trouver_obstacle(joueur, message)
        if isinstance(obstacle, Mur):
            joueur.envoyer("Votre robot perce une porte dans le mur.")
            porte = Porte(obstacle.x, obstacle.y)
            grille[obstacle.x, obstacle.y] = porte
            return True
        else:
            joueur.envoyer("Il n'y a pas de mur ici.")
            return False
