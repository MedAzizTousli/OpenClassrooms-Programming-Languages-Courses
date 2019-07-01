# -*-coding:Utf-8 -*

"""Ce module contient la classe Jeu.

Un jeu ici définit une partie en cours avec plusieurs joueurs (voir
le module 'joueur').

"""

from joueur import Joueur

class Jeu:

    """Classe représentant un jeu, une partie entre plusieurs joueurs.

    La carte du labyrinthe, les joueurs participants et d'autres
    informations sont centralisées ici. Les joueurs sont des instances
    de la classe 'Joueur' (voir le module 'joueur'). Dans cet exemple,
    les mécanismes réseaux sont gérés par la classe Joueur, mais
    on pourrait très bien utiliser des joueurs qui ne se connectent pas
    en réseau en gardant la même classe 'Jeu'.

    """

    def __init__(self, carte):
        self.partie_commencee = False
        self.carte = carte
        self.joueurs = []
        self.clients = {}
        self.no_tour = 0
        self.coups_differes = {}

    def _get_labyrinthe(self):
        """Retourne le labyrinthe."""
        return self.carte.labyrinthe
    labyrinthe = property(_get_labyrinthe)

    def _get_tour(self):
        """Retourne le joueur correspondant au tour si il existe."""
        if self.joueurs:
            return self.joueurs[self.no_tour]
        else:
            return None
    tour = property(_get_tour)

    def ajouter_joueur(self, client):
        """Ajoute un joueur.

        On doit simplement préciser le client (le socket) en
        paramètre. Un nouveau joueur est créé et retourné.

        """
        if self.partie_commencee:
            raise RuntimeError("La partie a déjà commencé")

        joueur = Joueur(self, client)
        joueur.numero = len(self.joueurs) + 1
        joueur.placer_robot()
        self.clients[client] = joueur
        self.joueurs.append(joueur)
        return joueur

    def get_joueur_depuis_client(self, client):
        """Retourne le joueur lié au client (socket) donné en paramètre."""
        return self.clients[client]

    def changer_tour(self):
        """Change le tour.

        On incrémente de 1 la valeur de 'no_tour' sauf si on est
        à la fin de la liste des joueurs, dans ce cas on revient
        au début.

        """
        if (self.no_tour + 1) < len(self.joueurs):
            self.no_tour += 1
        else:
            self.no_tour = 0

        suivant = self.tour
        if suivant:
            suivant.envoyer("C'est votre tour de jouer :")

    def envoyer_a_tous(self, message, exceptions=None):
        """Envoie le message à tous les joueurs.

        Paramètres à préciser :

            message -- le message à envoyer (une chaîne 'str')
            exceptions -- les exceptions à ignorer

        Les exceptions peuvent être :
            Non précisées
            Un joueur simple
            Une liste de joueurs

        """
        if exceptions is None:
            exceptions = []
        elif isinstance(exceptions, Joueur):
            exceptions = [exceptions]

        for joueur in self.joueurs:
            if joueur not in exceptions:
                joueur.envoyer(message)

    def afficher_grille(self):
        """Affiche la grille pour tous les joueurs."""
        for joueur in self.joueurs:
            robot = joueur.robot
            joueur.envoyer(self.labyrinthe.afficher(robot))
