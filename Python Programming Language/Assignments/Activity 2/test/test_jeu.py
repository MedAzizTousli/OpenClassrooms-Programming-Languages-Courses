# -*-coding:Utf-8 -*

"""Ce fichier contient le test unitaire du jeu proprement dit."""

from unittest import TestCase

from carte import Carte
from jeu import Jeu

# Constantes
GRILLE = """OOOOO
O . O
OOOOO"""

class TestJeu(TestCase):

    """Test vérifiant le bon fonctionnement du jeu.

    Ceci inclut l'application des règles.

    """

    def setUp(self):
        """Crée un jeu vide avec deux joueurs."""
        carte = Carte("test", GRILLE)
        self.jeu = Jeu(carte)
        self.grille = dict(self.jeu.labyrinthe.grille)
        self.joueur_1 = self.jeu.ajouter_joueur(None)
        self.joueur_2 = self.jeu.ajouter_joueur(None)

    def test_tour(self):
        """Vérifie le bon fonctionnement du changement de tour."""
        self.assertEqual(self.jeu.tour, self.joueur_1)
        self.jeu.changer_tour()
        self.assertEqual(self.jeu.tour, self.joueur_2)
        self.jeu.changer_tour()
        self.assertEqual(self.jeu.tour, self.joueur_1)

    def test_placement(self):
        """Vérifie que le robot est placé sur une case autrefois vide."""
        x, y = self.joueur_1.robot.x, self.joueur_1.robot.y

        # Vérifie que le robot s'est bien ajouté sur un emplacement vide
        self.assertNotIn((x, y), self.grille)

    def test_deplacement(self):
        """Essaye de déplacer le robot."""
        robot = self.jeu.labyrinthe.grille.get((1, 1))
        retour = self.jeu.labyrinthe.deplacer_robot(robot, "est")
        self.assertTrue(retour)
        self.assertEqual(robot.x, 2)
        self.assertEqual(robot.y, 1)

    def test_collision(self):
        """Vérifie que le robot ne bouge pas en cas de collision."""
        robot = self.jeu.labyrinthe.grille.get((1, 1))

        # Si le robot va au nord, il doit se prendre le mur
        retour = self.jeu.labyrinthe.deplacer_robot(robot, "nord")
        self.assertFalse(retour)
        self.assertEqual(robot.x, 1)
        self.assertEqual(robot.y, 1)

        # Si il va deux fois vers l'est, il doit se heurter à l'autre robot
        self.jeu.labyrinthe.deplacer_robot(robot, "est")
        retour = self.jeu.labyrinthe.deplacer_robot(robot, "est")
        self.assertFalse(retour)
