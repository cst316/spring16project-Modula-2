package net.sf.memoranda;

import nu.xom.Element;

public class ContactImpl implements Contact {

	private Element _element = null;

	public ContactImpl(Element elem) {
		_element = elem;
	}
	
	@Override
	public String getName() {
		return _element.getFirstChildElement("name").getValue();
	}

	@Override
	public String getEmail() {
		return _element.getFirstChildElement("email").getValue();
	}

	@Override
	public String getPhone() {
		return _element.getFirstChildElement("phone").getValue();
	}

	@Override
	public Element getContent() {
		return _element;
	}
	
}
