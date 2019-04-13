#include <fmod.h> //Sound library
#include <SDL/SDL.h> //SDL library
#include <dirent.h> //Dirent library to open musicDirectory
#include <string.h> //String library

#define frequencyArraySize 512 //frequencyArraySize = power of 2, because of GetSpectrum function
#define widthScreen 512 //widthScreen = frequencyArraySize
#define heightScreen 400 //heightScreen = may be variable
#define heightRatio (heightScreen / 255.0) //To be able to convert colors (255.0 must be float)
#define refreshDelay 25 //Time to update graph, minimum value of GetSpectrum function

void setPixel(SDL_Surface *surface, int x, int y, Uint32 pixel); //Function to set pixels

int main(int argc, char* argv[])
{
    //FMOD initialization
    FMOD_SYSTEM *system=NULL; //Create system object
    FMOD_System_Create(&system); //Give space to system object
    FMOD_System_Init(system, 1, FMOD_INIT_NORMAL, NULL); //Initialize system object

    //Open music files from music directory
    DIR *musicDirectory=opendir("music"); //Open directory
    readdir(musicDirectory);readdir(musicDirectory); //Pass the directories "." and ".."
    char songList[512][1024]; //Create an array of strings
    struct dirent *musicFile;
    int i=0;
    while(1)
    {
        musicFile=readdir(musicDirectory); //Read file in musicDirectory
        if (musicFile==NULL) break; //if we are by the end, we break
        char musicFileDirectory[]="music/"; //Edit fileName
        strcat(strcat(songList[i],musicFileDirectory),musicFile->d_name); //Add fileName to songList
        i++;
    }
    closedir(musicDirectory); //Close directory

    //Sound initialization
    FMOD_SOUND *music = NULL; //Create pointer on sound object
    float volumeValue=1.0; //Initialize volumeValue

    //Channel initialization
    FMOD_CHANNEL *channel=NULL; //Create pointer on channel object
    FMOD_System_GetChannel(system, 0, &channel); //Get pointer on channel
    float frequencyArray[widthScreen]={}; //Create an array for different frequencies

    //SDL initialization
    SDL_Init(SDL_INIT_VIDEO); //Load SDL
    SDL_WM_SetIcon(SDL_LoadBMP("musicplayer.bmp"), NULL); //Set icon
    SDL_Surface* screen=SDL_SetVideoMode(widthScreen, heightScreen, 32,SDL_HWSURFACE | SDL_DOUBLEBUF); //Create a surface
    SDL_WM_SetCaption("Aziz Music Player",NULL); //Set screen name
    SDL_Event event; //Create event object

    int exit=0, currentTime=0, currentTime2=0, previousTime2=0, timeBool=1, previousTime=0, songIndex=-1, state=0; //Variable to exit if = 1, currentTime and previousTime for delay purposes
    while (exit==0)
    {
        currentTime2 = SDL_GetTicks(); //For delay purposes
        if (currentTime2 - previousTime2 > 500)
            timeBool = 1;

        SDL_PollEvent(&event); //Wait event without blocking the program

        switch(event.type)
        {
        case SDL_QUIT: //Exit if X symbol is clicked
            exit=1;
            break;

        case SDL_KEYDOWN: //When a keyboard button is clicked
            {
            switch(event.key.keysym.sym)
            {
            case SDLK_RIGHT: //When right arrow is clicked

                if (timeBool == 1) //For delay purposes
                {
                    timeBool = 0;
                    previousTime2 = currentTime2;
                    songIndex++;
                }

                FMOD_System_CreateSound(system, songList[songIndex],FMOD_SOFTWARE | FMOD_2D | FMOD_CREATESTREAM, 0, &music); //Create sound object
                FMOD_System_PlaySound(system, FMOD_CHANNEL_FREE, music, 0, NULL); //Play music
                break;

            case SDLK_LEFT: //When left arrow is clicked

                if (timeBool == 1)
                {
                    timeBool = 0;
                    previousTime2 = currentTime2;
                    songIndex--;
                }

                FMOD_System_CreateSound(system, songList[songIndex],FMOD_SOFTWARE | FMOD_2D | FMOD_CREATESTREAM, 0, &music); //Create sound object
                FMOD_System_PlaySound(system, FMOD_CHANNEL_FREE, music, 0, NULL); //Play music
                break;

            case SDLK_DOWN: //When down button is clicked

                if (timeBool == 1)
                {
                    timeBool = 0;
                    previousTime2 = currentTime2;
                    if (volumeValue>0.0) //If volume is not equal to 0.0
                    {
                        volumeValue-=0.1;
                        FMOD_Channel_SetVolume(channel, volumeValue); //Make volume lower
                    }
                }
                break;

            case SDLK_UP: //When up button is clicked

                if (timeBool == 1)
                {
                    timeBool = 0;
                    previousTime2 = currentTime2;
                    if (volumeValue<1.0) //If volume is not equal to 1.0
                    {
                        volumeValue+=0.1;
                        FMOD_Channel_SetVolume(channel, volumeValue); //Make volume higher
                    }
                }
                break;

            case SDLK_SPACE:

                FMOD_Channel_GetPaused(channel, &state); //Check state of paused

                if (timeBool == 1)
                {
                    timeBool = 0;
                    previousTime2 = currentTime2;
                    if (state==0) //If it is paused, unpause it
                        FMOD_Channel_SetPaused(channel, 1);
                    else          //If it is unpaused, pause it
                        FMOD_Channel_SetPaused(channel, 0);
                }
                break;

            default:
                break;
            }
            }
        }

        SDL_FillRect(screen,NULL,SDL_MapRGB(screen->format, 0, 0, 0)); //Update screen with black

        //Stop program for a minimum of refreshDelay period
        currentTime = SDL_GetTicks();
        if (currentTime - previousTime < refreshDelay)
            SDL_Delay(refreshDelay - (currentTime - previousTime));
        previousTime = SDL_GetTicks();

        FMOD_Channel_GetSpectrum(channel, frequencyArray, frequencyArraySize, 0, FMOD_DSP_FFT_WINDOW_RECT); //Get spectrum of music

        SDL_LockSurface(screen); //Lock screen before manual modifications

        int i=0, j=0; //Indexes that will go through the screen
        for (i=0;i<widthScreen;i++)
        {
            int maxColor=heightScreen*frequencyArray[i]*100; //Get maximum value of color (*20 to zoom in)

            if (maxColor > heightScreen) //To assure that maxColor doesn't exceed heightScreen
                maxColor=heightScreen;

            for (j=heightScreen-maxColor;j<heightScreen;j++) //Color from heightScreen-maxColor to heightScreen
            {
                setPixel(screen, i, j, SDL_MapRGB(screen->format, 255-(j/heightRatio), j/heightRatio, 0));
            }
        }
        SDL_UnlockSurface(screen); //Unlock screen after manual modifications
        SDL_Flip(screen); //Update screen
    }

    //Close SDL
    SDL_FreeSurface(screen); //Free screen
    SDL_Quit(); //Close SDL

    //Close FMOD
    FMOD_Channel_Stop(channel); //Stop a channel
    FMOD_Sound_Release(music); //Release sound from system
    FMOD_System_Close(system); //Close system object
    FMOD_System_Release(system); //Release system object

    //Close directory
    closedir(musicDirectory);

    return EXIT_SUCCESS; //main has to return an int
}

void setPixel(SDL_Surface *surface, int x, int y, Uint32 pixel)
{
    int bpp = surface->format->BytesPerPixel;

    Uint8 *p = (Uint8 *)surface->pixels + y * surface->pitch + x * bpp;

    switch(bpp) {
    case 1:
        *p = pixel;
        break;

    case 2:
        *(Uint16 *)p = pixel;
        break;

    case 3:
        if(SDL_BYTEORDER == SDL_BIG_ENDIAN) {
            p[0] = (pixel >> 16) & 0xff;
            p[1] = (pixel >> 8) & 0xff;
            p[2] = pixel & 0xff;
        } else {
            p[0] = pixel & 0xff;
            p[1] = (pixel >> 8) & 0xff;
            p[2] = (pixel >> 16) & 0xff;
        }
        break;

    case 4:
        *(Uint32 *)p = pixel;
        break;
    }
}
