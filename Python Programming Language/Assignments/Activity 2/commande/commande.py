# -*-coding:Utf-8 -*

"""Fichier contenant la classe de base des commandes."""

class Commande:

    """Classe représentant une commande de base.

    Les commandes devraient hériter de cette classe.

    """

    prefixe = ""

    def __init__(self, jeu):
        self.jeu = jeu

    def doit_executer(self, joueur, message):
        """Retourne le reste si la commande doit s'exécuter.

        On s'appuie généralement sur le préfixe de la commande
        (attribut de classe 'prefixe') qui détermine la ou les premières
        lettres de la commande, mais on peut redéfinir cette méthode
        si besoin pour introduire d'autres critères.

        Le retour est :
            None pour dire que la commande ne doit pas être exécutée dans ce cas
            Le retour non interprété sous la forme d'une chaîne

        """
        if message.startswith(self.prefixe):
            return message[len(self.prefixe):]
        else:
            return None

    def executer(self, joueur, message):
        """Exécution de la commande.

        Paramètres à préciser :

            joueur -- le joueur exécutant la commande
            message -- la partie de la commande non interprétée

        La commande doit retourner vrai (True) si elle a pu
        s'exécuter, faux (False) sinon. Ceci est important car si
        la commande a pu s'exécuter correctement, le serveur passe
        au joueur suivant.

        """
        pass
