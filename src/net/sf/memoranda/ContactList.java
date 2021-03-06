package net.sf.memoranda;

import java.util.List;

import nu.xom.Document;

public interface ContactList {
	
	Contact addContact(String name,
			           String email,
			           String phone);
	
	void removeContact(Contact contact);
	
	List<Contact> getLog();
	
	int size();
	
	Project getProject();
	
	Document getXMLContent();
	
}
