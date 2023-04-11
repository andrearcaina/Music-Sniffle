/* 
	Name: Andre Arcaina
	Student ID: 501157540
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public Library()
	{
		songs 		= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		//checking to see if audiocontent is a song
		if(content.getType() == Song.TYPENAME) {
			//cast the audio content to a song object if so
			Song Song_content = (Song) content;
			if(!songs.contains(Song_content)) { //checks to see if the song is already downloaded using contains method
				songs.add(Song_content); //adds it if it isn't
				System.out.println("SONG "+Song_content.getTitle()+" Added to Library");
			} else { 
				errorMsg = "Song " +Song_content.getTitle()+" already downloaded"; //sends an error msg if it is already downloaded
				throw new AudioContentAlreadyExistsException(errorMsg);
			}
		}
		// if audiocontent is a audiobook
		else if (content.getType() == AudioBook.TYPENAME){
			AudioBook AudioBook_content = (AudioBook) content; //casts the audiocontent to a audiobook object
			if(!audiobooks.contains(AudioBook_content)) { //same as songs, checks to see if you already have audiobook downloaded
				audiobooks.add(AudioBook_content); //adds it if it isn't
				System.out.println("AUDIOBOOK "+AudioBook_content.getTitle()+" Added to Library");
			} else { //sends an error msg if it is already downloaded
				errorMsg = "AudioBook "+AudioBook_content.getTitle()+" already downloaded";
				throw new AudioContentAlreadyExistsException(errorMsg);
			}
		}
		return;
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		if(playlists.size() <= 0) { 
			errorMsg = "There are no playlists"; 
			throw new PlaylistAlreadyExistsException(errorMsg); 
		}
		for (int i = 0; i < playlists.size(); i++) {
			int index = i + 1;
			System.out.println(index+". "+playlists.get(i).getTitle());
			System.out.println("");
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		if(songs.size() <= 0) { 
			errorMsg = "There are no songs downloaded"; 
			throw new AudioContentNotFoundException(errorMsg);
		}
		ArrayList<String> artists = new ArrayList<String>();
		for (Song song : songs) {
			if(!artists.contains(song.getArtist())){
				artists.add(song.getArtist());
			}
		}
		for(int i = 0; i < artists.size(); i++) { 
			System.out.println(i+1+". "+artists.get(i)); 
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		//if index is within the indices of the songs array list
		if(index >= 1 && index <= songs.size()) { 
			for (Playlist playlist : playlists) { //checks each playlist in the playlist arraylist
				if(playlist.getContent().contains(songs.get(index-1))) { //if the playlist's arraylist contains the song at that index
					int songIndex = playlist.getContent().indexOf(songs.get(index-1)); //retrieve the index
					playlist.deleteContent(songIndex+1); //and delete it using deleteContent method from Playlist.java
				}
			}
			songs.remove(index-1); //then, remove the song at that index in the songs arraylist
		}
		// call error handler if song cannot be found
		else { 
			errorMsg = "Song not found"; 
			throw new AudioContentNotFoundException(errorMsg); 
		}
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
		
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song song1, Song song2){
			return Integer.compare(song1.getYear(), song2.getYear());
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song song1, Song song2){
			return Integer.compare(song1.getLength(),song2.getLength());
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public boolean playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			errorMsg = "Song Not Found";
			return false;
		}
		songs.get(index-1).play();
		return true;
	}

	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		//checks to see if the index is within the indices of the audiobooks array list
		if(index >= 1 && index <= audiobooks.size()) {
			audiobooks.get(index-1).selectChapter(chapter); //selects chapter if audiobook is found
			audiobooks.get(index-1).play(); } //and then plays it using the play method
		else { 
			errorMsg = "AudioBook Not Found"; //else, returns an error message
			throw new AudioContentNotFoundException(errorMsg);
		}
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		//prints TOC if audiobook is found
		if(index >= 1 && index <= audiobooks.size()) {
			audiobooks.get(index-1).printTOC(); return; }
		else { 
			errorMsg = "AudioBook Not Found";
			throw new AudioContentNotFoundException(errorMsg); 
		}
	}
	
	/*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		//make a new playlist variable with the same title
		Playlist new_playlist = new Playlist(title);
		if(playlists.contains(new_playlist)) {  // the playlists arraylist contains new_playlist
			errorMsg = "Playlist "+title+" Already Exists"; // send an error msg
			throw new PlaylistAlreadyExistsException(errorMsg); // throw an exception
		} else { // else, add the playlist with the given title to the playlists arraylist
			playlists.add(new_playlist);
		}
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		//loops through each playlist in the playlists arraylist
		for (Playlist playlist : playlists) {
			if(playlist.getTitle().equals(title)) { //if title of playlist equals the input title
				playlist.printContents(); //prints contents using printContents() method
				return;
			}
		}
		// call error handler if it can't be found
		errorMsg = "Playlist Not Found";
		throw new PlaylistAlreadyExistsException(errorMsg);
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		//loops through each playlist in the playlists arraylist
		for (Playlist playlist : playlists) {
			if(playlist.getTitle().equals(playlistTitle)){ //if title matches playlistTitle (input parameter)
				playlist.playAll(); //calls playAll() method
				return;
			}
		}
		// call error handler if it can't be found
		errorMsg = "Playlist Not Found";
		throw new PlaylistAlreadyExistsException(errorMsg);
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		for (Playlist playlist : playlists) { //loops through all playlists
			if(playlist.getTitle().equals(playlistTitle)) { //if playlist title equals String parameter
				System.out.println(playlist.getTitle()); //prints playlist Title
				playlist.play(indexInPL); //play specific song given by index parameter
				return;
			}
		}
		// call error handler if it can't be found
		errorMsg = "Playlist Not Found";
		throw new PlaylistAlreadyExistsException(errorMsg);
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		//first checks the type parameter whether it is a song or an audiobook
		if(type.equals(Song.TYPENAME)){ // if so
			for (Playlist playlist : playlists) { //loop through the playlists title
				if(playlist.getTitle().equals(playlistTitle)) { //get title of playlist
					playlist.addContent(songs.get(index-1)); //use addContent method to add the song into the playlist
					return;
				}
			}
		}
		//same logic with song applied to audiobook
		else if(type.equals(AudioBook.TYPENAME)) { 
			for (Playlist playlist : playlists) {
				if(playlist.getTitle().equals(playlistTitle)) { 
					playlist.addContent(audiobooks.get(index-1));
					return;
				}
			}
		}
		// call error handler if it can't be found
		errorMsg = "Playlist "+playlistTitle+" Not Found";
		throw new AudioContentAlreadyExistsException(errorMsg);
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		//loop through each playlist in the playlists arraylist
		for (Playlist playlist : playlists) {
			if(playlist.getTitle().equals(title)) {  //if playlist title equals parameter title 
				playlist.deleteContent(index); // call deleteContent method with input index passed inside
				return;
			}
		}
		//if playlist title does not match parameter title, return error message
		errorMsg = "Playlist "+title+" Not Found";
		throw new PlaylistAlreadyExistsException(errorMsg);
	}
}

// exception class for audio content that has already been downloaded
class AudioContentAlreadyExistsException extends RuntimeException {
	public AudioContentAlreadyExistsException(String message) {
		super(message);
	}
}

// exception class for audio content that hasn't been downloaded or doesn't exist
class AudioContentNotFoundException extends RuntimeException {
	public AudioContentNotFoundException(String message) {
		super(message);
	}
}

// exception class for playlists that exists or doesn't already exist
class PlaylistAlreadyExistsException extends RuntimeException {
	public PlaylistAlreadyExistsException(String message) {
		super(message);
	}
}