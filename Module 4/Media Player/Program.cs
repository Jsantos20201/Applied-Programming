// See https://aka.ms/new-console-template for more information

/* 
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
*/

using NAudio.Wave;
using System;
using System.IO;
using System.Linq;
using System.Threading;

namespace MediaPlayer
{
    class Program
    {
        // Flag to control whether the media player is playing or paused
        static bool isPlaying = false;

        // Current volume level (0.0 - 1.0)
        static float volumeLevel = 1.0f;

        // Audio output device
        static WaveOutEvent outputDevice = new WaveOutEvent();

        // Array of media files
        static string[] files;

        // Index of the currently playing file
        static int currentFileIndex;

        static void Main(string[] args)
        {
            Console.WriteLine();
            Console.WriteLine();
            Console.WriteLine("Welcome to the Media Player!");

            string[] supportedFormats = { ".mp3", ".mp4", ".wav" };

            string directoryPath = GetDirectoryPath();
            files = GetFilesInDirectory(directoryPath, supportedFormats);

            if (files.Length == 0)
            {
                Console.WriteLine("No supported media files found in the directory.");
                return;
            }

            int selectedIndex = DisplayMediaFiles(files);

            if (selectedIndex >= 0 && selectedIndex < files.Length)
            {
                string selectedFile = files[selectedIndex];
                PlayMediaFile(selectedFile);
            }
            else
            {
                Console.WriteLine("Invalid selection. Exiting...");
            }
        }

        /* Gets the directory path where the media files are located.
         * If the default directory is not found, it prompts the user to enter a valid directory path.
         * Returns the directory path as a string.
         */
        static string GetDirectoryPath()
        {
            // Provide a default directory path
            string directoryPath = "C:\\Users\\Josep\\Desktop\\Applied-Programming\\Module 4\\Media Player"; // You can change file path here.

            // Check if the default directory exists
            if (Directory.Exists(directoryPath))
            {
                return directoryPath;
            }

            Console.WriteLine("Default directory not found. Please enter the path of the directory containing media files:");
            directoryPath = Console.ReadLine();

            while (!Directory.Exists(directoryPath))
            {
                Console.WriteLine("Invalid directory path. Please enter a valid path:");
                directoryPath = Console.ReadLine();
            }

            return directoryPath;
        }

        /* Retrieves the media files in the specified directory that have supported formats.
         * Returns an array of file paths.
         */
        static string[] GetFilesInDirectory(string directoryPath, string[] supportedFormats)
        {
            string[] files = Directory.GetFiles(directoryPath);
            return Array.FindAll(files, file => supportedFormats.Contains(Path.GetExtension(file)));
        }

        /* Displays the list of media files and prompts the user to select a file to play.
         * Returns the index of the selected file.
         */
        static int DisplayMediaFiles(string[] files)
        {
            Console.WriteLine();
            Console.WriteLine("Select a media file to play:");

            for (int i = 0; i < files.Length; i++)
            {
                string fileName = Path.GetFileName(files[i]);
                Console.WriteLine($"{i + 1}. {fileName}");
            }

            Console.Write("Enter the file number: ");
            string input = Console.ReadLine();

            if (int.TryParse(input, out int selectedIndex) && selectedIndex > 0 && selectedIndex <= files.Length)
            {
                return selectedIndex - 1;
            }

            return -1;
        }

        /* Plays the selected media file.
         * Initializes a new thread to play the media file and runs the media player menu on the main thread.
         */
        static void PlayMediaFile(string filePath)
        {
            Console.WriteLine();
            Console.WriteLine($"Playing {Path.GetFileName(filePath)}...");

            // Set the current file index
            currentFileIndex = Array.IndexOf(files, filePath);

            // Create a new thread to play the media file
            Thread playbackThread = new Thread(() => PlayMedia(filePath));
            playbackThread.Start();

            // Run the media player menu on the main thread
            RunMediaPlayer();

            // Wait for the playback thread to complete
            playbackThread.Join();

            Console.WriteLine("Media playback finished.");
        }

        /* Plays the specified media file.
         * Uses NAudio to read and play the audio file.
         */
        static void PlayMedia(string filePath)
        {
            isPlaying = true;

            using (var audioFile = new AudioFileReader(filePath))
            {
                outputDevice.Init(audioFile);
                outputDevice.Play();

                while (outputDevice.PlaybackState == PlaybackState.Playing)
                {
                    // Check if the media player is currently playing or paused
                    if (isPlaying)
                    {
                        Thread.Sleep(500);
                    }
                    else
                    {
                        Thread.Sleep(100);
                    }
                }
            }

            isPlaying = false;
        }

        /* Displays the media player menu options. */
        static void DisplayMediaPlayerMenu()
        {
            Console.WriteLine();
            Console.WriteLine("Media Player Menu:");
            Console.WriteLine("1. Play/Pause");
            Console.WriteLine("2. Stop");
            Console.WriteLine("3. Skip to next");
            Console.WriteLine("4. Skip to previous");
            Console.WriteLine("5. Increase volume");
            Console.WriteLine("6. Decrease volume");
            Console.WriteLine("7. Exit");
        }

        /* Handles the user's choice from the media player menu. */
        static void HandleMediaPlayerMenu()
        {
            Console.Write("Enter your choice: ");
            string input = Console.ReadLine();

            switch (input)
            {
                case "1":
                    TogglePlayback();
                    break;
                case "2":
                    StopPlayback();
                    break;
                case "3":
                    SkipToNext();
                    break;
                case "4":
                    SkipToPrevious();
                    break;
                case "5":
                    IncreaseVolume();
                    break;
                case "6":
                    DecreaseVolume();
                    break;
                case "7":
                    Console.WriteLine("Exiting...");
                    Environment.Exit(0);
                    break;
                default:
                    Console.WriteLine("Invalid choice.");
                    break;
            }
        }

        /* Toggles the playback state between play and pause. */
        static void TogglePlayback()
        {
            if (isPlaying)
            {
                outputDevice.Pause();
                Console.WriteLine("Pausing playback...");
            }
            else
            {
                outputDevice.Play();
                Console.WriteLine("Resuming playback...");
            }

            isPlaying = !isPlaying;
        }

        /* Stops the playback and resets the media player state. */
        static void StopPlayback()
        {
            outputDevice.Stop();
            isPlaying = false;
            Console.WriteLine("Stopping playback...");
        }

        /* Increases the volume of the media player. */
        static void IncreaseVolume()
        {
            if (volumeLevel < 1.0f)
            {
                volumeLevel += 0.1f;
                outputDevice.Volume = volumeLevel;
                Console.WriteLine("Increasing volume...");
            }
            else
            {
                Console.WriteLine("Maximum volume reached.");
            }
        }

        /* Decreases the volume of the media player. */
        static void DecreaseVolume()
        {
            if (volumeLevel > 0.0f)
            {
                volumeLevel -= 0.1f;
                outputDevice.Volume = volumeLevel;
                Console.WriteLine("Decreasing volume...");
            }
            else
            {
                Console.WriteLine("Minimum volume reached.");
            }
        }

        /* Skips to the next media file in the playlist. */
        static void SkipToNext()
        {
            if (currentFileIndex < files.Length - 1)
            {
                StopPlayback();
                currentFileIndex++;
                string nextFile = files[currentFileIndex];
                Console.WriteLine($"Skipping to next song: {Path.GetFileName(nextFile)}...");
                PlayMediaFile(nextFile);
            }
            else
            {
                Console.WriteLine("No next file available.");
            }
        }

        /* Skips to the previous media file in the playlist. */
        static void SkipToPrevious()
        {
            if (currentFileIndex > 0)
            {
                StopPlayback();
                currentFileIndex--;
                string previousFile = files[currentFileIndex];
                Console.WriteLine($"Skipping to previous song: {Path.GetFileName(previousFile)}...");
                PlayMediaFile(previousFile);
            }
            else
            {
                Console.WriteLine("No previous file available.");
            }
        }

        /* Runs the media player by continuously displaying the menu and handling user input. */
        static void RunMediaPlayer()
        {
            while (true)
            {
                DisplayMediaPlayerMenu();
                HandleMediaPlayerMenu();
            }
        }
    }
}