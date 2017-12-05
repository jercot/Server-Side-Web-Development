package model;

public class ForumThread {
	private String name;
	private int id;
	public ForumThread(String name) {
		super();
		this.name = name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLinkName() {
		return name.replaceAll(" ", "-");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}