# -*-coding:Utf-8 -*

"""Ce module contient la classe Carte."""

from labyrinthe import creer_labyrinthe_depuis_chaine

class Carte:

    """Objet de transition entre un fichier et un labyrinthe.

    La carte conserve le nom du fichier. Le labyrinthe est créé depuis le
    contenu du fichier.

    """

    def __init__(self, nom, chaine):
        self.nom = nom
        self.labyrinthe = creer_labyrinthe_depuis_chaine(chaine)

    def __repr__(self):
        return "<Carte {}>".format(self.nom)
