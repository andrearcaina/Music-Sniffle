import java.util.Scanner;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			try {
			String action = scanner.nextLine();
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT")) {
					scanner.close(); return;
				}
				else if (action.equalsIgnoreCase("STORE"))	// List all songs
				{
					store.listAll(); 
				}
				else if (action.equalsIgnoreCase("SONGS"))	// List all songs
				{
					mylibrary.listAllSongs(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
				{
					mylibrary.listAllAudioBooks(); 
				}
				else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
				{
					mylibrary.listAllArtists(); 
				}
				else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
				{
					mylibrary.listAllPlaylists(); 
				}
				// Download audiocontent (song/audiobook/podcast) from the store 
				// Specify the index of the content
				else if (action.equalsIgnoreCase("DOWNLOAD")) 
				{
					int start = 0;
					int end = 0;
					
					System.out.print("Store Content #: ");
					if (scanner.hasNextInt())
					{
						start = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
						System.out.print("To Store Content #: ");
						end = scanner.nextInt();
						scanner.nextLine();
					}
					// looping through given range to download from
					for(int i = start; i <= end; i++) { 
						try { // try to download content as it iterates through the range
							AudioContent content = store.getContent(i);
							mylibrary.download(content);
						} catch (AudioContentAlreadyExistsException e) { // catch if content already exists
							System.out.println(e.getMessage());
						} catch (NullPointerException e) { // catch if content doesn't exist / content is null
							System.out.println("No Content Found in Store");
						}
					}
				}
				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song 
				else if (action.equalsIgnoreCase("PLAYSONG")) 
				{
					// Print error message if the song doesn't exist in the library
					int index = 0;

					System.out.print("Song Number: ");
					if (scanner.hasNextInt()){
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.playSong(index);
				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
				// Print error message if the book doesn't exist in the library
					int index = 0;

					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt()){
						index = scanner.nextInt();
						scanner.nextLine();
					}
					//print audio book table of contents
					mylibrary.printAudioBookTOC(index);
				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter 
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{
					int index = 0;
					int chapter = 0;
					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt()){
						index = scanner.nextInt();
						scanner.nextLine();
					}
					System.out.print("Chapter: ");
					if (scanner.hasNextInt()){
						chapter = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.playAudioBook(index, chapter);
				}
				// Specify a playlist title (string) 
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()){
						title = scanner.next();
						scanner.nextLine();
					}
					mylibrary.playPlaylist(title);
				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				// Play all the audio content 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					String title = "";
					int index = 0;
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()){
						title = scanner.next();
						scanner.nextLine();
					}
					System.out.print("Playlist Content #: ");
					if (scanner.hasNextInt()){
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.playPlaylist(title, index);
				}
				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					int index = 0;
					System.out.print("Library Song #: ");
					if (scanner.hasNextInt()){
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.deleteSong(index);
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()){
						title = scanner.next();
						scanner.nextLine();
					}
					mylibrary.makePlaylist(title);
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
				{
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()){
						title = scanner.next();
						scanner.nextLine();
					}
					mylibrary.printPlaylist(title);
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from the keyboard
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					String playlist_title = "";
					String type = "";
					int index = 0;

					// ask for playlist title
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()){
						playlist_title = scanner.next();
					}
					// ask for type
					System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
					if (scanner.hasNext()){
						type = scanner.next().toUpperCase();
					}
					// ask for index in playlist
					System.out.print("Library Content #: ");
					if (scanner.hasNextInt()){
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.addContentToPlaylist(type, index, playlist_title);
				}
				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
			// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{
					String title = "";
					int index = 0;
					System.out.print("Playlist Title: ");
					if(scanner.hasNext()) {
						title = scanner.next();
					}
					System.out.print("Playlist Content: ");
					if(scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
					mylibrary.delContentFromPlaylist(index, title);
				}

				else if (action.equalsIgnoreCase("DOWNLOADA")) {
					// asks user for artist name
					String artist = "";
					System.out.print("Artist Name: ");
					if(scanner.hasNextLine()) {
						artist = scanner.nextLine();
					}

					for(int index : store.getArtists().get(artist)) {
						try {
							AudioContent content = store.getContent(index+1);
							mylibrary.download(content);
						} catch (AudioContentAlreadyExistsException e) {
							System.out.println(e.getMessage());
						}
					}
				}
				else if (action.equalsIgnoreCase("DOWNLOADG")) {
					// asks user for genre
					String genre = "";
					System.out.print("Genre: ");
					if(scanner.hasNextLine()) {
						genre = scanner.nextLine();
					}

					for(int index : store.getGenres().get(genre)) {
						try {
							AudioContent content = store.getContent(index+1);
							mylibrary.download(content);
						} catch (AudioContentAlreadyExistsException e) {
							System.out.println(e.getMessage());
						}
					}
				}

				else if (action.equalsIgnoreCase("SEARCH")) {
					// asks user for title
					String title = "";
					System.out.print("Title: ");
					if(scanner.hasNextLine()) {
						title = scanner.nextLine();
					}
					// calls search method in AudioContentStore
					// searches for title in titles hashmap
					// and prints out the song or audiobook associated with it
					store.search(title);
				}
				else if (action.equalsIgnoreCase("SEARCHA")) {
					// asks user for artist
					String artist = "";
					System.out.print("Artist: ");
					if(scanner.hasNextLine()) {
						artist = scanner.nextLine();
					}
					// calls search method in AudioContentStore
					// searches for artist in artists hashmap
					// and prints out the songs or audiobooks associated with it
					store.search(artist, "artists");
				}
				else if (action.equalsIgnoreCase("SEARCHG")) {
					// asks user for genre
					String genre = "";
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
					if(scanner.hasNextLine()) {
						genre = scanner.nextLine();
					}
					// calls search method in AudioContentStore
					// searches for genre in artists hashmap
					// and prints out the songs associated with it
					store.search(genre, "genres");
				}
				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}
				System.out.print("\n>");
			} catch (AudioContentAlreadyExistsException e) {
				System.out.println(e.getMessage());
				System.out.print("\n>");
			} catch (AudioContentNotFoundException e) {
				System.out.println(e.getMessage());
				System.out.print("\n>");
			} catch (PlaylistAlreadyExistsException e) {
				System.out.println(e.getMessage());
				System.out.print("\n>");
			}
		}
	}
}
