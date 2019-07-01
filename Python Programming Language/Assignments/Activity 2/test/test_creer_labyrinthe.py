# -*-coding:Utf-8 -*

"""Ce fichier contient le test unitaire de la création de labyrinthe."""

from unittest import TestCase

from labyrinthe import creer_labyrinthe_depuis_chaine
from obstacle.mur import Mur
from obstacle.porte import Porte
from obstacle.sortie import Sortie

class TestCreerLabyrinthe(TestCase):

    """Essaye de créer des labyrinthes selon plusieurs moyens."""

    def test_chaine(self):
        """Crée un labyrinthe depuis une chaîne."""
        chaine = "OOO.OUO"
        labyrinthe = creer_labyrinthe_depuis_chaine(chaine)
        coords = (
                (0, 0, Mur),
                (3, 0, Porte),
                (5, 0, Sortie),
        )

        # Vérifie que chaque obstacle est du bon type
        for x, y, type_obstacle in coords:
                obstacle = labyrinthe.grille.get((x, y))
                self.assertTrue(isinstance(obstacle, type_obstacle))
