package net.sf.memoranda;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class ContactListImpl implements ContactList {

	private static Vector<ContactListener> _listeners;
	
	private Vector<Contact> _vector;
	private Project _project = null;
	private Document _doc = null;
	private Element _root = null;
	
	public ContactListImpl(Document doc, Project prj) {
		_doc = doc;
		_root = _doc.getRootElement();
		_vector = new Vector<Contact>();
		_listeners = new Vector<ContactListener>();
		
		// populate _vector with Contact objects
		Elements elements = _root.getChildElements();
		for (int i = 0; i < elements.size(); i++) {
			_vector.add(new ContactImpl(elements.get(i)));
		}
	}
	
	public ContactListImpl(Project prj) {
		_root = new Element("contactlist");
		_doc = new Document(_root);
		_vector = new Vector<Contact>();
		_listeners = new Vector<ContactListener>();
		_project = prj;
	}

	@Override
	public Contact addContact(String name, String email, String phone) {
		
		Element element = new Element("contact");
		String id = Util.generateId();
		element.addAttribute(new Attribute("id", id));
		
		Element n = new Element("name");
		n.appendChild(name);
		element.appendChild(n);
		
		Element e = new Element("email");
		e.appendChild(name);
		element.appendChild(n);
		
		Element p = new Element("phone");
		p.appendChild(name);
		element.appendChild(n);
		
		_root.appendChild(element);
		
		Contact contact = new ContactImpl(element);
		_vector.add(contact);
		
		notifyListeners();
		
		return contact;
	}

	@Override
	public void removeTimeEntry(Contact contact) {
		_root.removeChild(contact.getContent());
		_vector.remove(contact);
		notifyListeners();
	}

	@Override
	public List<Contact> getLog() {
		Collections.sort(_vector, new Comparator<Contact>() {
			@Override
			public int compare(Contact c1, Contact c2) {
				return c1.getName().compareTo(c2.getName());
			}
		});
		
		return _vector;
	}

	@Override
	public int size() {
		return _root.getChildCount();
	}

	@Override
	public Project getProject() {
		return _project;
	}

	@Override
	public Document getXMLContent() {
		return _doc;
	}
	
	private static void notifyListeners() {
        for (int i = 0; i < _listeners.size(); i++) {
            _listeners.get(i).contactChanged();            
        }
    }
}
