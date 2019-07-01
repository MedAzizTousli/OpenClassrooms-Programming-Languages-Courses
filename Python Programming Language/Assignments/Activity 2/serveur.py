# -*-coding:Utf-8 -*

"""Ce fichier contient le code du serveur.

Il faut exécuter ce fichier avant de lancer un ou plusieurs clients.
Les clients sont contenus dans le fichier 'client.py'.

"""

import os
import socket
import select

from carte import Carte
from commande import commandes
from jeu import Jeu

# On charge les cartes existantes
cartes = []
for nom_fichier in os.listdir("cartes"):
    if nom_fichier.endswith(".txt"):
        chemin = os.path.join("cartes", nom_fichier)
        nom_carte = nom_fichier[:-3].lower()
        with open(chemin, "r") as fichier:
            contenu = fichier.read()
            try:
                carte = Carte(nom_carte, contenu)
            except ValueError as err:
                print("Erreur lors de la lecture de {} : {}.".format(
                        chemin, str(err)))
            else:
                cartes.append(carte)

print("Labyrinthes existants :")
for i, carte in enumerate(cartes):
    print("  {} - {}".format(i + 1, carte.nom))

# Choix de la carte
labyrinthe = None
while labyrinthe is None:
    choix = input("Entrez un numéro de labyrinthe pour commencer à jouer : ")
    # Si le joueur n'a pas entré R, on s'attend à un nombre
    try:
        choix = int(choix)
    except ValueError:
        print("Choix invalide : {}".format(choix))
    else:
        if choix < 1 or choix > len(cartes):
            print("Numéro invalide : ".format(choix))
            continue

        carte = cartes[choix - 1]
        labyrinthe = carte.labyrinthe

# On crée le jeu
jeu = Jeu(carte)
for i, commande in enumerate(commandes):
    # Instanciation de la commande
    commandes[i] = commande(jeu)

# On connete le serveur
clients = []
connexion = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion.bind(('localhost', 29408))
connexion.listen(5)
print("On attend les clients.")

# On boucle pendant toute la durée du jeu
while not jeu.labyrinthe.gagnee:
    # On accepte les nouvelles connexions
    nouvelles, w, x = select.select([connexion], [], [], 0.1)
    for nouvelle in nouvelles:
        client, infos = nouvelle.accept()
        if jeu.partie_commencee:
            client.send("La partie a déjà commencée.".encode())
            client.close()
        else:
            clients.append(client)
            joueur = jeu.ajouter_joueur(client)
            print("Connexion du joueur {}".format(joueur.numero))
            joueur.envoyer("Bienvenue, joueur {}.".format(joueur.numero))
            jeu.envoyer_a_tous("Joueur {} se connecte.".format(
                    joueur.numero), joueur)
            jeu.afficher_grille()
            jeu.envoyer_a_tous("Entrez C pour commencer à jouer :")

    # On accepte les commandes
    if clients:
        entrees, w, x = select.select(clients, [], [], 0.1)
        for client in entrees:
            msg = client.recv(1024)
            msg = msg.decode()
            joueur = jeu.get_joueur_depuis_client(client)
            if jeu.tour is not joueur:
                joueur.envoyer("Ce n'est pas votre tour.")
                continue

            # On cherche la commande
            trouve = False
            for commande in commandes:
                reste = commande.doit_executer(joueur, msg)
                if reste is not None:
                    retour = commande.executer(joueur, reste)
                    if retour:
                        jeu.afficher_grille()
                        jeu.changer_tour()
                        trouve = True
                        break

            if not trouve:
                joueur.envoyer("Commande invalide.")

            if jeu.labyrinthe.gagnee:
                break

    # Enfin, on joue les coups différés
    joueur = jeu.tour
    if joueur in jeu.coups_differes:
        direction, nb = jeu.coups_differes[joueur]
        essai = jeu.labyrinthe.deplacer_robot(joueur.robot, direction)
        nb -= 1
        if nb == 0:
            del jeu.coups_differes[joueur]
            jeu.afficher_grille()
            jeu.changer_tour()
        elif not essai:
            joueur.envoyer("Vous ne pouvez aller par là.")
            del jeu.coups_differes[joueur]
        else:
            jeu.afficher_grille()
            jeu.changer_tour()

# On déconnecte tout le monde
for client in clients:
    client.send("La partie est finie !".encode())
    client.close()
