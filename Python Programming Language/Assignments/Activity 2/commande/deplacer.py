# -*-coding:Utf-8 -*

"""Fichier contenant la commande 'deplacer'."""

from commande.commande import Commande

class Deplacer(Commande):

    def doit_executer(self, joueur, message):
        """Retourne le reste si la commande doit s'exécuter."""
        message = message.lower()
        lettre = message[:1]
        if lettre and lettre in "eson":
            # C'est l'exception, on garde la direction
            return message

        return None

    def executer(self, joueur, message):
        """Exécution de la commande.

        Le joueur demande à déplacer son robot. Le message peut
        être une direction simple (sous la forme d'une lettre) ou
        bien une direction de plusieurs tours d'un coup.

        """
        lettre = message[:1]
        reste = message[1:]
        directions = {
            "e": "est",
            "s": "sud",
            "o": "ouest",
            "n": "nord",
        }

        direction = directions[lettre]
        nb = 1
        if reste:
            try:
                nb = int(reste)
                assert nb > 0
            except (ValueError, AssertionError):
                joueur.envoyer("Nombre invalide.")
                return False

        essai = self.jeu.labyrinthe.deplacer_robot(joueur.robot, direction)
        if not essai:
            joueur.envoyer("Vous ne pouvez aller par là.")
            return False
        else:
            nb -= 1
            if nb > 0:
                self.jeu.coups_differes[joueur] = (direction, nb)

            return True
