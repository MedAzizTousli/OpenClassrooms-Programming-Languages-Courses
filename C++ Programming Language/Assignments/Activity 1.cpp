#include <iostream>
#include <fstream>
#include <string>
#include <ctime>
#include <cstdlib>
#include <vector>

using namespace std;

string melangerLettres(string mot)
{
    string melange;
    string motBase = mot;
    int position(0);

    while (motBase.size() != 0)
    {
        position = rand() % motBase.size();
        melange += motBase[position];
        motBase.erase(position, 1);
    }

    return melange;
}

void chargerFichier(string nomFichier, vector<string> &mots)
{
    ifstream fichier(nomFichier.c_str());
    string ligne;

    while (fichier >> ligne)
    {
        mots.push_back(ligne);
    }
}

int main()
{
    string motMystere, motMelange, motUtilisateur;
    char recommencer;
    int nombreCoupsRestants;
    vector<string> mots;

    srand(time(0));

    chargerFichier("dico.txt", mots);

    do
    {
        motMystere = mots[rand() % mots.size()];

        motMelange = melangerLettres(motMystere);
        nombreCoupsRestants = 5;

        for (nombreCoupsRestants = 5 ; motUtilisateur != motMystere && nombreCoupsRestants > 0 ; nombreCoupsRestants--)
        {
            cout << endl << "Quel est ce mot ? (" << nombreCoupsRestants << " coups restants)" << endl << motMelange << endl;
            cin >> motUtilisateur;

            if (motUtilisateur != motMystere)
            {
                cout << "Ce n'est pas le mot !" << endl;
            }
        }

        if (nombreCoupsRestants > 0)
        {
            cout << "Bravo ! :o)" << endl;
        }
        else
        {
            cout << "Perdu ! :o(" << endl;
        }

        cout << "Voulez-vous recommencer une partie ? (o/N)" << endl;
        cin >> recommencer;
    }
    while (recommencer == 'o');



    return 0;
}
