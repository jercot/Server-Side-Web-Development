package model;

public class Forum {
	private String name;
	private int id, type;
	public Forum(String name, int type) {
		super();
		this.name = name;
		this.type = type;
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
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}