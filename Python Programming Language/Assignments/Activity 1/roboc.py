# -*-coding:Utf-8 -*

"""Ce fichier contient le code principal du jeu.

Exécutez-le avec Python pour lancer le jeu.

"""

import os
import re
from fonctions import *

from carte import Carte

# On charge les cartes existantes
cartes = []
for nom_fichier in os.listdir("cartes"):
    if nom_fichier.endswith(".txt"):
        chemin = os.path.join("cartes", nom_fichier)
        nom_carte = nom_fichier[:-3].lower()
        with open(chemin, "r") as fichier:
            contenu = fichier.read()
            carte = Carte(nom_carte, contenu)
            cartes.append(carte)

# On affiche les cartes existantes
print("Labyrinthes existants :")
for i, carte in enumerate(cartes):
    if '_' not in carte.nom:
        print("  {} - {}".format(i + 1, carte.nom))

# Choix du labyrinthe
numeroDePartie = 0
while numeroDePartie not in {1,2}:
    numeroDePartie = int(input("Entrez un numéro de labyrinthe pour commencer à jouer : "))

# Chercher les parties sauvegardées
partieSauvegardee = False
for j, carte in enumerate(cartes):
    if carte.nom == "saved_" + cartes[numeroDePartie-1].nom:
        partieSauvegardee = True
        break
    
# Choix de jouer une partie sauvegardée (ou pas)
if partieSauvegardee:

    print("\nIl y a une partie sauvegardée. Voulez vous jouer?")
    rejouer = input("\no/O pour oui | n/N pour non\n\n")
    EXPRESSION = "^[OoNn]$"

    # Verifier la validité de l'input de l'utilisateur
    while re.match(EXPRESSION, rejouer) is None:
        print("\nEntrez une lettre valide!")
        print("o/O pour oui | n/N pour non")
        rejouer = input('\n')

    if rejouer.upper() == "N":
        labyrintheString = cartes[numeroDePartie-1].labyrinthe
    else:
        labyrintheString = cartes[j].labyrinthe
        
else:
    print("\nIl n'y a pas une partie sauvegardée.")
    labyrintheString = cartes[numeroDePartie-1].labyrinthe

print("\n" + labyrintheString + "\n")

labyrintheList = labyrintheString.split('\n')

# Extraction de la position actuelle (X,Y) de 'X' et préparation de l'état de la porte
Xposition, Yposition, porte = preparationJeu(labyrintheList)

chemin = os.path.dirname(os.path.realpath(__file__)) + "\\cartes\\saved_" + cartes[numeroDePartie-1].nom + "txt"

while True:

    # Choix du mouvement
    print("Entrez un mouvement :)")
    print("n->nord, s->sud, o->ouest, e->east, suivi par le nombre de fois entre 2 et 9")
    print("q->quitter")  
    mouvement = input('\n')
    EXPRESSION_VALIDE = "^[qnsoeQNSOE][1-90-9]*$"

    # Verifier la validité de l'input de l'utilisateur
    while re.match(EXPRESSION_VALIDE, mouvement) is None:
        print("\nEntrez un mouvement valide!")
        print("n->nord, s->sud, o->ouest, e->east, suivi par le nombre de fois entre 2 et 9")
        mouvement = input('\n')

    # Q = Quitter
    if mouvement[0].upper()=="Q":
        break

    # Regler la taille du mouvement pour que tout sera compatible pour les fonctions
    if len(mouvement) == 1:
        mouvement = change_char(mouvement, 1, '1')

    # Traitement du mouvement
    if mouvement[0].upper()=="N":
        Xposition, Yposition, porte = goNord(labyrintheList, mouvement, Xposition, Yposition, porte)

    elif mouvement[0].upper()=="S":
        Xposition, Yposition, porte = goSud(labyrintheList, mouvement, Xposition, Yposition, porte)

    elif mouvement[0].upper()=="E":
        Xposition, Yposition, porte = goEst(labyrintheList, mouvement, Xposition, Yposition, porte)

    elif mouvement[0].upper()=="O":
        Xposition, Yposition, porte = goOuest(labyrintheList, mouvement, Xposition, Yposition, porte)

    # Gagner = Quitter
    if Xposition == -1 and Yposition == -1:
        break

    # Sauvegarder le jeu chaque tour
    with open(chemin, "w") as fichier:
        fichier.write('\n'.join(labyrintheList))
