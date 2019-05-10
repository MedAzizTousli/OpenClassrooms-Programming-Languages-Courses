#include <iostream>
#include <bits/stdc++.h>

using namespace std;

// Foncteur qui effectue un chiffrement par decalage
class ChiffrementSubst{

public:

  // Constructeur prenant le nom fichier contenant les substitutions a utiliser
  ChiffrementSubst(const string& nomFichier)
  {
      ifstream fi("C:\\Users\\Lenovo\\Desktop\\Openclassroom2\\"+nomFichier); //Ouvrir le fichier (changer le code selon la position)
      istream_iterator<char> end;
      istream_iterator<char> it(fi);
      for(char i=65; it!=end ; ++it, ++i)
      {
          ++it; //Sauter les alphabets normaux 'A', 'B', 'C'
          T_[i]=*it; //Associer chaque alphabet à son équivalent du fichier
      }
  }

  char operator()(char c)
      {
         return T_[c] ; //Retourner le character qui va remplacer
      }
private:
  map<char, char> T_;
};


int main()
{

    // Le message a crypter
    string texte("BIENVENUE SUR LE MOOC C++ D'OCR !!");

    // Demande de la cle a l'utilisateur
    cout << "Quel fichier contenant la cle voulez-vous utiliser ? ";
    string nomFichier;
    cin >> nomFichier;

    // Creation du foncteur
    ChiffrementSubst foncteur(nomFichier);

    // Chaine de caracteres pour le message crypter
    string texte_crypte=texte;

    for(string::iterator it=texte_crypte.begin(); it!=texte_crypte.end(); ++it) //Boucle qui parcourt le texte crypte
        if (isupper(*it)) //Verifier si le charactere est une lettre majuscule
            *it = foncteur(*it); //Si oui, remplacer le grace au foncteur

    cout << "Texte original: " << texte << endl;
    cout << "Texte crypte: " << texte_crypte << endl;

    cout << endl << "Si le texte crypte est vide, il fallait verifier l'emplacement de cle.txt" << endl;

    return 0;
}

