package model;

import java.util.Date;

@SuppressWarnings("rawtypes")
public class Account implements Comparable {
	private String name;
	private long registerDate;
	private int messages, type, id;

	public Account(String name, long date) {
		this.name = name;
		registerDate = date;
		messages = 0;
	}

	public Account(String name, long date, int messages) {
		this(name, date);
		this.messages = messages;
	}

	public Account(String name, long date, int messages, int type) {
		this(name, date, messages);
		this.type = type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws MyException {
		if(name==null)
			throw new MyException("Name cannot be null");
		this.name = name;
	}

	public long getRegisterDate() {
		return registerDate;
	}
	
	public Date getRegDate() {
		return new Date(registerDate);
	}

	public void setRegisterDate(long registerDate) {
		this.registerDate = registerDate;
	}

	public int getMessages() {
		return messages;
	}

	public void setMessages(int messages) {
		this.messages = messages;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	@Override
	public int compareTo(Object arg0) {
		if(name.compareToIgnoreCase(arg0.getClass().getName())>=0)
			return 0;
		return 1;
	}
}