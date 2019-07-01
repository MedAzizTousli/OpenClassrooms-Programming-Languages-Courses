# -*-coding:Utf-8 -*

"""Ce fichier contient le code du client.

Il doit être exécuté une fois par client souhaitant jouer, après avoir
exécuté le serveur.

Afin d'attendre pour une enrée (input) et de recevoir les
messages du serveur, il est nécessaire de créer un thread indépendant.

"""

import socket
import sys
import threading

# Définition du thread
class Entree(threading.Thread):
    def __init__(self, connexion):
        threading.Thread.__init__(self)
        self.connexion = connexion
        self.lance = True

    def run(self):
        """Exécution du thread."""
        while self.lance:
            msg = input()
            if self.lance:
                self.connexion.send(msg.encode())

# Client proprement dit
print("On tente de se connecter au serveur...")

connexion = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    connexion.connect(('localhost', 29408))
except ConnectionRefusedError:
    print("Impossible de se connecter. Le serveur est-il lancé ?")
    sys.exit(1)

print("Connexion établie avec le serveur.")
entree = Entree(connexion)
entree.start()

while True:
    msg = connexion.recv(1024)
    msg = msg.decode()
    if msg == "":
        print("Le serveur a interrompu la connexion. Appuyez sur entrée.")
        entree.lance = False
        sys.exit(0)
    else:
        print(msg)
