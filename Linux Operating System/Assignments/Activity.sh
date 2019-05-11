#!/bin/bash

#./langstat.sh param1 [param2]
#param1 = dico.txt
#param2 = nom du fichier où vous voulez sauvegarder le résultat

if [ $# -eq 0 ] || [ $# -gt 2 ]
then
	#Verification de la présence d'un paramètre au minimum
	echo "Veuillez entrer un ou deux paramètre(s)"


elif [ ! -e $1 ]
then

	#Verification de la présence de dico.txt
	echo "Veuillez mettre dico.txt dans le même dossier que le script shell"

else

	#Parcours de l'alphabet
	for alphabet in {A..Z}
	do
		#Recherche des lignes qui contiennent les lettres
		echo `grep $alphabet dico.txt | wc -l` $alphabet >> alphabetCount
	done

	#Si c'est un seul paramètre, on affiche dans la console
	if [ $# -eq 1 ]
	then
		sort -rnk1 alphabetCount
	
	#Si c'est deux paramètres, on met le contenu dans un fichier dont le nom est le deuxième paramètre
	else
		sort -rnk1 alphabetCount > $2
	fi

	#Ne pas laisser le fichier temporaire sur le disque
	rm alphabetCount

fi
