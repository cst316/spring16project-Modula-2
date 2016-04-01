package net.sf.memoranda;

import nu.xom.Element;

public interface Contact {
	
	public String getName();
	
	public String getEmail();
	
	public String getPhone();
	
	public Element getContent();
	
}
