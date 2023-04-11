import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
	private ArrayList<AudioContent> contents = new ArrayList<AudioContent>(); 
	
	private HashMap<String, Integer> titles = new HashMap<String, Integer>();

	private HashMap<String, ArrayList<Integer>> artists = new HashMap<String, ArrayList<Integer>>();

	private HashMap<String, ArrayList<Integer>> genres = new HashMap<String, ArrayList<Integer>>();

	public AudioContentStore()
	{
		try {
			GetStoreContent();
			for (int index = 0; index < contents.size(); index++) {
				AudioContent content = contents.get(index);
				if (titles.containsKey(content.getTitle())) {
					titles.put(content.getTitle(), titles.get(content.getTitle()) + 1);
				}
				else {
					titles.put(content.getTitle(), index);
				}
				if (content.getType() == Song.TYPENAME) {
					Song song = (Song) content;
					if (artists.containsKey(song.getArtist())) {
						artists.get(song.getArtist()).add(index);
					}
					else {
						ArrayList<Integer> indices = new ArrayList<Integer>();
						indices.add(index);
						artists.put(song.getArtist(), indices);
					}
					if (genres.containsKey(song.getGenre().toString())) {
						genres.get(song.getGenre().toString()).add(index);
					}
					else {
						ArrayList<Integer> indices = new ArrayList<Integer>();
						indices.add(index);
						genres.put(song.getGenre().toString(), indices);
					}
				}
				else if (content.getType() == AudioBook.TYPENAME) {
					AudioBook audioBook = (AudioBook) content;
					if (artists.containsKey(audioBook.getAuthor())) {
						artists.get(audioBook.getAuthor()).add(index);
					}
					else {
						ArrayList<Integer> indices = new ArrayList<Integer>();
						indices.add(index);
						artists.put(audioBook.getAuthor(), indices);
					}
				}
			}
			/* testing if maps are right
			System.out.println(getTitles());
			System.out.println(getArtists());
			System.out.println(getGenres());
			*/
		}
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		}
	}
	
	// file io method for reading
	private void GetStoreContent() {
		try {
			Scanner scanFile = new Scanner(new File("store.txt"));
			while(scanFile.hasNextLine()) {
				String keyWord = scanFile.nextLine();
				if(keyWord.equals("SONG")) {
					String id = scanFile.nextLine(); 
					String title = scanFile.nextLine();
					int year = Integer.parseInt(scanFile.nextLine()); 
					int length = Integer.parseInt(scanFile.nextLine());
					String artist = scanFile.nextLine(); 
					String composer = scanFile.nextLine();
					String genre = scanFile.nextLine();
					int lineLyrics = Integer.parseInt(scanFile.nextLine());
					String lyrics = "";
					for (int i = 0; i < lineLyrics; i++) {
						lyrics += scanFile.nextLine()+"\n";
					}
					contents.add(new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer, Song.Genre.valueOf(genre), lyrics));
					System.out.println("Loading SONG");
				}
				else if (keyWord.equals("AUDIOBOOK")) {
					String id = scanFile.nextLine();
					String title = scanFile.nextLine();
					int year = Integer.parseInt(scanFile.nextLine());
					int length = Integer.parseInt(scanFile.nextLine());
					String author = scanFile.nextLine();
					String narrator = scanFile.nextLine();
					int numChapters = Integer.parseInt(scanFile.nextLine());
					ArrayList<String> chapterTitles = new ArrayList<String>();
					for (int i = 0; i < numChapters; i++) {
						chapterTitles.add(scanFile.nextLine());
					}
					ArrayList<String> chapters = new ArrayList<String>();
					for (int i = 0; i < numChapters; i++) {
						int numLines = Integer.parseInt(scanFile.nextLine());
						String lines = "";
						for (int j = 0; j < numLines; j++) {
							lines += scanFile.nextLine();
						}
						chapters.add(lines);
					}
					contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapters));
					System.out.println("Loading AUDIOBOOK");
				}
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// functions for each commands [SEARCH, SEARCHA, SEARCHG]
	// this method is used for searching a specific title in the titles map
	public void search(String title) {
		if(getTitles().containsKey(title)) {
			System.out.print(getTitles().get(title)+1+". ");
			contents.get(getTitles().get(title)).printInfo();
			System.out.println("");
		}
		else {
			System.out.println("No matches for "+title);
		}
	}

	// this method is used for both search artists and search genres
	public void search(String input, String option) {
		// empty hashmap with string as key and arraylist of integers as value
		// like the artists map and genres map
		HashMap<String, ArrayList<Integer>> searchMap = new HashMap<String, ArrayList<Integer>>();
		
		// depending on the parameter, searchMap will change to their respective map option
		if(option == "artists") { searchMap = getArtists(); }
		if(option == "genres") { searchMap = getGenres(); }

		if(searchMap.containsKey(input)) {
			for(int index : searchMap.get(input)) {
				System.out.print(index+1+". ");
				contents.get(index).printInfo();
				System.out.println("");
			}
		}
		else {
			System.out.println("No matches for "+input);
		}
	}

	// getter methods for each map
	public HashMap<String, Integer> getTitles() {
		return titles;
	}

	public HashMap<String, ArrayList<Integer>> getArtists() {
		return artists;
	} 

	public HashMap<String, ArrayList<Integer>> getGenres() {
		return genres;
	}

	public AudioContent getContent(int index)
	{
		if (index < 1 || index > contents.size())
		{
			return null;
		}
		return contents.get(index-1);
	}
	
	public void listAll()
	{
		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}
}
