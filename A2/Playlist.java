/*
	Name: Andre Arcaina
	Student ID: 501157540 
 */

import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook, podcast)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents()
	{
		//loops through the contents inside the playlist
		//prints out i+1 (representing 1 and so forth) 
		//and the audio name and title using printInfo() method
		for (int i = 0; i < contents.size(); i++) {
			System.out.print(i+1+". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	// Play all the AudioContent in the contents list
	public void playAll()
	{
		//loops through contents inside of the playlist
		//and then plays each audio file
		for(int i = 0; i < contents.size(); i++){
			contents.get(i).play();
			System.out.println("");
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index)
	{
		//if given index is in not in the correct range
		//return nothing
		if (!contains(index)) return;
		//else, play the specific audio content
		contents.get(index-1).play();
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{
		//cast other to a Playlist object
		Playlist playlist = (Playlist) other;
		//returns true if the two playlists are equal through checking the titles
		return getTitle().equals(playlist.getTitle());
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	// The given index is 1-indexed so convert to 0-indexing before removing
	public void deleteContent(int index)
	{
		//deletes the given index from the playlist
		//first checks if the index is valid
		if (!contains(index)) return;
		contents.remove(index-1);
	}
}
