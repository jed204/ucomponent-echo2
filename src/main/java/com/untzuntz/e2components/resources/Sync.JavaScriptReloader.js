UntzUntzJavaScriptReloader = function(elementId) {
	this.elementId = elementId;
};

UntzUntzJavaScriptReloader.getComponent = function(element) {
    return EchoDomPropertyStore.getPropertyValue(element, "component");
};

/**
 * UntzUntzJavaScriptReloader has a ServerMessage processor.
 */
UntzUntzJavaScriptReloader.MessageProcessor = function() { };

UntzUntzJavaScriptReloader.MessageProcessor.process = function(messagePartElement) {
    for (var i = 0; i < messagePartElement.childNodes.length; ++i) {
        if (messagePartElement.childNodes[i].nodeType == 1) {
            switch (messagePartElement.childNodes[i].tagName) {
	            case "init":
	                UntzUntzJavaScriptReloader.MessageProcessor.processInit(messagePartElement.childNodes[i]);
	                break;
	            case "dispose":
	                UntzUntzJavaScriptReloader.MessageProcessor.processDispose(messagePartElement.childNodes[i]);
	                break;
	            case "hidden":
	                UntzUntzJavaScriptReloader.MessageProcessor.processHidden(messagePartElement.childNodes[i]);
	                break;
            }
        }
    }
};

UntzUntzJavaScriptReloader.MessageProcessor.processDispose = function(messageElement) {
    for (var itemXML = messageElement.firstChild; itemXML; itemXML = itemXML.nextSibling) {
        var elementId = itemXML.getAttribute("eid");

		this.divE = document.getElementById(elementId);
		if (divE && divE.parentElement) {
			divE.parentElement.removeChild(divE);
			
	        var comp = UntzUntzJavaScriptReloader.getComponent(elementId);
	        if (comp) {
	            comp.dispose();
	        }
		}        
		else if (divE && divE.parentNode) {
			divE.parentNode.removeChild(divE);
			
	        var comp = UntzUntzJavaScriptReloader.getComponent(elementId);
	        if (comp) {
	            comp.dispose();
	        }
		}        
    }
};

UntzUntzJavaScriptReloader.prototype.destroy = function() 
{
	var divE = document.getElementById(this.elementId);
	if (divE && divE.parentElement) 
	{
		divE.parentElement.removeChild(divE);
	}
	if (divE && divE.parentNode) 
	{
		divE.parentNode.removeChild(divE);
	}
};

/*
 * -----------------------------------  
 */
UntzUntzJavaScriptReloader.MessageProcessor.processHidden = function(messageElement) 
{
    for (var itemXML = messageElement.firstChild; itemXML; itemXML = itemXML.nextSibling) 
    {
        var elementId = itemXML.getAttribute("eid");

    }
};


UntzUntzJavaScriptReloader.MessageProcessor.processInit = function(messageElement) 
{
    for (var itemXML = messageElement.firstChild; itemXML; itemXML = itemXML.nextSibling) 
    {
        var elementId = itemXML.getAttribute("eid");
		var clientObj = new UntzUntzJavaScriptReloader(elementId);
		clientObj.init(itemXML);
    }
    
    
};


/*
 * ---------------------------
 */
UntzUntzJavaScriptReloader.prototype.init = function(itemXML) 
{
	divE = document.createElement('div');
	divE.id = this.elementId;
	divE.setAttribute('eid', this.elementId);
	
	this.scriptURI = itemXML.getAttribute("scriptURI");
	var parent = document.getElementsByTagName('div')[0];
	
	if (parent != null)
	{
		parent.appendChild(divE);
		if (this.scriptURI != null)
		{
			var script = document.createElement('script');
		     script.setAttribute("src", this.scriptURI);
		     parent.appendChild(script);
		}
		
		this.divE = divE;
	}
	
};
