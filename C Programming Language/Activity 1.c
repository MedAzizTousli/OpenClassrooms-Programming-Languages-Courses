#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main ( int argc, char** argv )
{
    int continuerPartie=1; //Continuer la partie si c'est différent de 0
    while(continuerPartie)
    {
        // Niveau de difficulté
        int niveau,nombreMaximum;
        printf("Veuillez choisir le niveau de difficulte: 1, 2 ou 3\n");
        scanf("%d",&niveau);
        switch (niveau)
        {
        case 1:
            nombreMaximum=100;
            break;
        case 2:
            nombreMaximum=1000;
            break;
        case 3:
            nombreMaximum=10000;
            break;
        default:
            nombreMaximum=100000;
            printf("Vous n'avez pas choisi un bon niveau. Difficulte = 3\n");
            break;
        }

        int nombreMystere = 0, nombreEntre = 0;
        const int MIN = 1;

        // Génération du nombre aléatoire

        srand(time(NULL));
        nombreMystere = (rand() % (nombreMaximum - MIN + 1)) + MIN;

        int compteur=0;

        /* La boucle du programme. Elle se répète tant que l'utilisateur n'a pas trouvé le nombre mystère */

        do
        {
            compteur++; //Le compteur avance

            // On demande le nombre
            printf("Quel est le nombre ? ");
            scanf("%d", &nombreEntre);

            // On compare le nombre entré avec le nombre mystère

            if (nombreMystere > nombreEntre)
                printf("C'est plus !\n\n");
            else if (nombreMystere < nombreEntre)
                printf("C'est moins !\n\n");
            else
                printf ("Bravo, vous avez trouve le nombre mystere en %d coups!!!\n\n",compteur);
        } while (nombreEntre != nombreMystere);

        printf("Voulez-vous faire une autre partie? !0 pour continuer, 0 pour arreter\n");
        scanf("%d", &continuerPartie); //Lire la valeur

    }
    return 0;
}
