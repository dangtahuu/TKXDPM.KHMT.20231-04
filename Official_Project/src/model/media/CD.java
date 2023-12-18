package model.media;

public class CD extends Media {
	private String artist;
	private String recordLabel;
	private String tracklist;
	private String releaseDate;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRecordLabel() {
        return artist;
    }

    public void setRecordLabel(String label) {
        this.recordLabel = label;
    }
    
    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }
    
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
 
}
