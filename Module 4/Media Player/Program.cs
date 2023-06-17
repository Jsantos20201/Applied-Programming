// See https://aka.ms/new-console-template for more information

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

        static string GetDirectoryPath()
        {
            // Provide a default directory path
            string directoryPath = "C:\\Users\\Josep\\Desktop\\Applied-Programming\\Module 4\\Media Player";

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

        static string[] GetFilesInDirectory(string directoryPath, string[] supportedFormats)
        {
            string[] files = Directory.GetFiles(directoryPath);
            return Array.FindAll(files, file => supportedFormats.Contains(Path.GetExtension(file)));
        }

        static int DisplayMediaFiles(string[] files)
        {
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

        static void PlayMediaFile(string filePath)
        {
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

        static void DisplayMediaPlayerMenu()
        {
            Console.WriteLine("Media Player Menu:");
            Console.WriteLine("1. Play/Pause");
            Console.WriteLine("2. Stop");
            Console.WriteLine("3. Skip to next");
            Console.WriteLine("4. Skip to previous");
            Console.WriteLine("5. Increase volume");
            Console.WriteLine("6. Decrease volume");
            Console.WriteLine("7. Exit");
        }

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

        static void StopPlayback()
        {
            outputDevice.Stop();
            isPlaying = false;
            Console.WriteLine("Stopping playback...");
        }

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

        static void SkipToNext()
        {
            if (currentFileIndex < files.Length - 1)
            {
                StopPlayback();
                currentFileIndex++;
                string nextFile = files[currentFileIndex];
                PlayMediaFile(nextFile);
            }
            else
            {
                Console.WriteLine("No next file available.");
            }
        }

        static void SkipToPrevious()
        {
            if (currentFileIndex > 0)
            {
                StopPlayback();
                currentFileIndex--;
                string previousFile = files[currentFileIndex];
                PlayMediaFile(previousFile);
            }
            else
            {
                Console.WriteLine("No previous file available.");
            }
        }

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