# -*-coding:Utf-8 -*

"""Fichier contenant la commande 'commencer'."""

from commande.commande import Commande

class Commencer(Commande):

    prefixe = "c"

    def doit_executer(self, joueur, message):
        """Retourne le reste si la commande doit s'exécuter."""
        reste = Commande.doit_executer(self, joueur, message)
        if reste is not None:
            if self.jeu.partie_commencee:
                return None

        return reste

    def executer(self, joueur, message):
        """Exécution de la commande.

        La commande 'commencer' permet de lancer le jeu si ce
        n'est pas déjà fait. Quand le jeu est lancé, on n'accepte
        plus de nouveaux joueurs. Les joueurs présents
        jouent tour par tour.

        """
        if self.jeu.partie_commencee:
            joueur.envoyer("Le jeu a déjà commencé.")
            return False
        else:
            self.jeu.partie_commencee = True
            self.jeu.envoyer_a_tous("La partie commence !")
            self.jeu.afficher_grille()
            return True
