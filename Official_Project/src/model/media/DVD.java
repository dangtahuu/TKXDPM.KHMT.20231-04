package model.media;

public class DVD extends Media {
	private String discType;
	private String director;
	private String studio;
	private String subtitle;
	private String releaseDate;
	private int runtime;

    public String getDiscType() {
        return discType;
    }

    public void setDiscType(String discType) {
        this.discType = discType;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
    
    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }
    
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public int getRuntime() {
        return runtime;
    }

    public void setRuntime (int runtime) {
        this.runtime = runtime;
    }
 
}
