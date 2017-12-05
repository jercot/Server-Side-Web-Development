package model;

import java.util.Date;

public class ForumPost<T> {
	private String post;
	private long time;
	private T poster;
	private int id;

	public ForumPost(String post, long time, T poster) {
		this.post = post;
		this.time = time;
		this.poster = poster;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Date getTime() {
		return new Date(time);
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	// The next 3 methods are bad but I only did them to fulfill the project brief of having a generic class. To do it properly using an interface instead of T would be a better idea.
	public String getPoster() {
		Account p = (Account) getPosterT();
		return p.getName();
	}
	
	public Date getPostRegister() {
		Account p = (Account) getPosterT();
		return new Date(p.getRegisterDate());
	}
	
	public int getMessageCount() {
		Account p = (Account) getPosterT();
		return p.getMessages();
	}
	
	public T getPosterT() {
		return poster;
	}
}