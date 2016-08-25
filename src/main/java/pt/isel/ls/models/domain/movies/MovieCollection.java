package pt.isel.ls.models.domain.movies;


public class MovieCollection {

	private int id;
    private String name;
    private String description;
	private Movie[] movies;

    public MovieCollection(int id, String name, String description, Movie[] movies){
		this.id = id;
		this.name = name;
		this.description = description;
		this.movies = movies;
    }

	public int getId() { return id; }
	public String getName() { return name; }
	public Movie[] getMovies() { return movies; }
    public String getDescription() { return description; }
}
