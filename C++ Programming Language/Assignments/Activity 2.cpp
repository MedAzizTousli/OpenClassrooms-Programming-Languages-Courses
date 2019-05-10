#include <iostream>
#include <string>
#include <bits/stdc++.h>

using namespace std;

// Foncteur qui effectue un chiffrement par decalage
class ChiffrementDecal{

public:

  // Constructeur prenant le decalage voulu en argument
  ChiffrementDecal(int decalage)
  {
     m_decalage = decalage;
  }


  char operator()(char c)
      {
          return (c + m_decalage - 65) % 26 + 65 ; //Decaler le charactere en faisant un modulo
      }

private:

  int m_decalage; // Le decalage a appliquer au texte

};

int main()
{

    // Le message a crypter
    string texte("BIENVENUE SUR LE MOOC C++ D'OCR !!");

    // Demande du decalage a l'utilisateur
    cout << "Quel decalage voulez-vous utiliser ? ";
    int decalage;
    cin >> decalage;

    // Creation du foncteur
    ChiffrementDecal foncteur(decalage);

    // Chaine de caracteres pour le message crypter
    string texte_crypte=texte;

    for(string::iterator it=texte_crypte.begin(); it!=texte_crypte.end(); ++it) //Boucle qui parcourt le texte crypte
        if (isupper(*it)) //Verifier si le charactere est une lettre majuscule
            *it = foncteur(*it); //Si oui, remplacer le grace au foncteur

    cout << "Texte original: " << texte << endl;
    cout << "Texte crypte: " << texte_crypte << endl;

    return 0;

}

