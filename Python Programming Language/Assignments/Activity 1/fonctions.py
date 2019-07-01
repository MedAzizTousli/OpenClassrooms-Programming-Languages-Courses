# Fonction pour changer une chaine
def change_char(s, p, r):
    return s[:p]+r+s[p+1:]

# Fonction pour préparer les positions et l'état de la porte
def preparationJeu(labyrintheList):
    
    # X position
    for i in range(len(labyrintheList)):
        if 'X' in labyrintheList[i]:
            Xposition = i
            break

    # Y position
    Yposition = labyrintheList[Xposition].find('X')

    # Porte
    porte = False

    return Xposition, Yposition, porte

# Fonction pour aller au nord
def goNord(labyrintheList, mouvement, Xposition, Yposition, porte):

    # Boucle sur le chiffre après la lettre
    for i in range(int(mouvement[1:])):

        # Si c'est la position correcte, bravo!
        if labyrintheList[Xposition-1][Yposition] == 'U':

            # Vérifier si on est sur une porte ou non
            labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, ' ')    
            labyrintheList[Xposition-1] = change_char(labyrintheList[Xposition-1], Yposition, 'X')
            Xposition = -1
            Yposition = -1
            print("\nBravo! Vous avez gagné!!!\n")
            print('\n'.join(labyrintheList)+"\n")
            break

        # Si c'est un mur, mauvais mouvement
        if labyrintheList[Xposition-1][Yposition] == 'O':
            print("\nMauvais mouvement! :(\n")
            print('\n'.join(labyrintheList)+"\n")
            break

        # Sinon, bon mouvement
        else:
            if porte == True:
                labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, '.')
                porte = False
            else:
                labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, ' ')
                
            if labyrintheList[Xposition-1][Yposition] == '.':
                porte = True
            labyrintheList[Xposition-1] = change_char(labyrintheList[Xposition-1], Yposition, 'X')
            Xposition = Xposition - 1
            print("\nBon mouvement! :)\n")
            print('\n'.join(labyrintheList)+"\n")
            
    return Xposition, Yposition, porte

# Fonction pour aller au sud
def goSud(labyrintheList, mouvement, Xposition, Yposition, porte):
    for i in range(int(mouvement[1:])):
        if labyrintheList[Xposition+1][Yposition] == 'U':
            labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, ' ')
            labyrintheList[Xposition+1] = change_char(labyrintheList[Xposition+1], Yposition, 'X')
            Xposition = -1
            Yposition = -1
            print("\nBravo! Vous avez gagné!!!\n")
            print('\n'.join(labyrintheList)+"\n")
            break
        
        if labyrintheList[Xposition+1][Yposition] == 'O':
            print("\nMauvais mouvement! :(\n")
            print('\n'.join(labyrintheList)+"\n")
            break
        
        else:
            if porte == True:
                labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, '.')
                porte = False
            else:
                labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, ' ')
                
            if labyrintheList[Xposition+1][Yposition] == '.':
                porte = True
                
            labyrintheList[Xposition+1] = change_char(labyrintheList[Xposition+1], Yposition, 'X')
            Xposition = Xposition + 1
            print("\nBon mouvement! :)\n")
            print('\n'.join(labyrintheList)+"\n")
        
    return Xposition, Yposition, porte

# Fonction pour aller à l'est
def goEst(labyrintheList, mouvement, Xposition, Yposition, porte):
    for i in range(int(mouvement[1:])):
        if labyrintheList[Xposition][Yposition+1] == 'U':
            labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, ' ')
            labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition+1, 'X')
            Xposition = -1
            Yposition = -1
            print("\nBravo! Vous avez gagné!!!\n")
            print('\n'.join(labyrintheList)+"\n")
            break
        
        if labyrintheList[Xposition][Yposition+1] == 'O':
            print("\nMauvais mouvement! :(\n")
            print('\n'.join(labyrintheList)+"\n")
            break
        
        else:
            if porte == True:
                labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, '.')
                porte = False
            else:
                labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, ' ')
                
            if labyrintheList[Xposition][Yposition+1] == '.':
                porte = True
                
            labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition+1, 'X')
            Yposition = Yposition + 1
            print("\nBon mouvement! :)\n")
            print('\n'.join(labyrintheList)+"\n")
        
    return Xposition, Yposition, porte

# Fonction pour aller à l'ouest
def goOuest(labyrintheList, mouvement, Xposition, Yposition, porte):
    for i in range(int(mouvement[1:])):
        if labyrintheList[Xposition][Yposition-1] == 'U':
            labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, ' ')
            labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition-1, 'X')
            Xposition = -1
            Yposition = -1
            print("\nBravo! Vous avez gagné!!!\n")
            print('\n'.join(labyrintheList)+"\n")
            break
        
        if labyrintheList[Xposition][Yposition-1] == 'O':
            print("\nMauvais mouvement :(\n")
            print('\n'.join(labyrintheList)+"\n")
            break
        
        else:
            if porte == True:
                labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, '.')
                porte = False
            else:
                labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition, ' ')
                
            if labyrintheList[Xposition][Yposition-1] == '.':
                porte = True
                
            labyrintheList[Xposition] = change_char(labyrintheList[Xposition], Yposition-1, 'X')
            Yposition = Yposition - 1
            print("\nBon mouvement :)\n")
            print('\n'.join(labyrintheList)+"\n")
        
    return Xposition, Yposition, porte
