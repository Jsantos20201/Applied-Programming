# Overview

## Description of Software
### Media Player

The Media Player program is a console-based application that allows users to play and control media files such as MP3, MP4, and WAV files. 
It uses the NAudio library for audio playback.

Here's a summary of the program's functionality:

1. The program starts by prompting the user to enter the path of the directory containing the media files. If a default directory exists, it is used.
2. The program scans the directory for supported media files (MP3, MP4, and WAV) and displays a list of files to the user.
3. The user can select a file to play from the list. Once a file is selected, the program initiates a new thread to play the media file using NAudio.
4. While the media file is playing, the program displays a media player menu with various options:
    -Play/Pause: Toggles between playing and pausing the current media file.
    -Stop: Stops the playback and resets the media player state.
    -Skip to next: Skips to the next media file in the playlist (if available).
    -Skip to previous: Skips to the previous media file in the playlist (if available).
    -Increase volume: Increases the volume of the media player.
    -Decrease volume: Decreases the volume of the media player.
    -Exit: Exits the program.
5. The program continuously displays the media player menu and handles user input until the user chooses to exit.

Overall, the Media Player program provides a basic media playback functionality with the ability to control playback, adjust volume, 
and navigate through a playlist of media files.

## Purpose of the Software
The purpose of your program, the Media Player, is to provide a console-based application that allows users to play and control media files such as MP3, MP4, and WAV files. It utilizes the NAudio library for audio playback.

## Video of Software
[Module 4: Demo video](https://www.youtube.com/watch?v=5Nsz3l7Z5xs)

# Development Enviroment
This program is created by C# on Visual Studio Code. 

# Useful Websites
* [C# Basics: W3schoos](https://www.w3schools.com/cs/index.php)
* [C# Basics: tutorialspoint](https://www.tutorialspoint.com/csharp/index.htm)
* [C# Basics: geeksforgeeks](https://www.geeksforgeeks.org/csharp-programming-language/)
* [How to Install C# on Visual Studio Code](https://code.visualstudio.com/docs/languages/csharp)
* [Video on Installing NET for Visual Studio Code](https://www.youtube.com/watch?v=Y7GMBmd1EAk)