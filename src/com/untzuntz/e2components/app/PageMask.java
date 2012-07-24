package com.untzuntz.e2components.app;

import nextapp.echo2.app.Component;

public class PageMask extends Component {

	private static final long serialVersionUID = 8201078688307790515L;
	public static final String PROPERTY_HIDDEN = "hidden";

    public PageMask() {
    	setRenderId("PageMask");
		setFocusTraversalParticipant(false);
    }

	public void setHidden(boolean newValue) {
		setProperty(PROPERTY_HIDDEN, newValue);
	}

	public boolean isHidden() {
		return (Boolean)getProperty(PROPERTY_HIDDEN);
	}

}
