package com.untzuntz.e2components.app;


import nextapp.echo2.app.Component;

public class JavaScriptReloader extends Component
{
	
	private static final long	serialVersionUID	= 8201078688307790515L;
	public static final String	PROPERTY_HIDDEN		= "hidden";
	public static final String	PROPERTY_SCRIPT_URI		= "scriptURI";
	
	public JavaScriptReloader()
	{
		this(null); 
	}
	
	public JavaScriptReloader(final String scriptURI)
	{
		super();
		
		setProperty(PROPERTY_SCRIPT_URI, scriptURI);
		setRenderId("JavaScriptReloader");
		setFocusTraversalParticipant(false);
	}
	
	public void setHidden(boolean newValue)
	{
		setProperty(PROPERTY_HIDDEN, newValue);
	}
	
	public boolean isHidden()
	{
		return (Boolean) getProperty(PROPERTY_HIDDEN);
	}
	
	public String getScriptURI()
	{
		return (String)getProperty(PROPERTY_SCRIPT_URI);
	}
	
}
