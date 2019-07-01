# -*-coding:Utf-8 -*

"""Package contenant les commandes."""

from commande.commencer import Commencer
from commande.deplacer import Deplacer
from commande.murer_porte import MurerPorte
from commande.percer_mur import PercerMur

commandes = [
        Commencer,
        Deplacer,
        MurerPorte,
        PercerMur,
]
