UntzUntzPageMask = function(elementId) {
	this.elementId = elementId;
};

UntzUntzPageMask.getComponent = function(element) {
    return EchoDomPropertyStore.getPropertyValue(element, "component");
};

/**
 * UntzUntzPageMask has a ServerMessage processor.
 */
UntzUntzPageMask.MessageProcessor = function() { };

UntzUntzPageMask.MessageProcessor.process = function(messagePartElement) {
    for (var i = 0; i < messagePartElement.childNodes.length; ++i) {
        if (messagePartElement.childNodes[i].nodeType == 1) {
            switch (messagePartElement.childNodes[i].tagName) {
	            case "init":
	                UntzUntzPageMask.MessageProcessor.processInit(messagePartElement.childNodes[i]);
	                break;
	            case "dispose":
	                UntzUntzPageMask.MessageProcessor.processDispose(messagePartElement.childNodes[i]);
	                break;
	            case "hidden":
	                UntzUntzPageMask.MessageProcessor.processHidden(messagePartElement.childNodes[i]);
	                break;
            }
        }
    }
};

UntzUntzPageMask.MessageProcessor.processDispose = function(messageElement) {
    for (var itemXML = messageElement.firstChild; itemXML; itemXML = itemXML.nextSibling) {
        var elementId = itemXML.getAttribute("eid");

		var divEX = document.getElementById(elementId);
		if (divEX && divEX.parentElement) {
			divEX.parentElement.removeChild(divEX);
			
	        var comp = UntzUntzPageMask.getComponent(elementId);
	        if (comp) {
	            comp.dispose();
	        }
		}        
		else if (divEX && divEX.parentNode) {
			divEX.parentNode.removeChild(divEX);
			
	        var comp = UntzUntzPageMask.getComponent(elementId);
	        if (comp) {
	            comp.dispose();
	        }
		}        
    }
};

UntzUntzPageMask.prototype.destroy = function() {
	var divE = document.getElementById(this.elementId);
	if (divE && divE.parentElement) {
		divE.parentElement.removeChild(divE);
	}
	if (divE && divE.parentNode) {
		divE.parentNode.removeChild(divE);
	}
};

/*
 * -----------------------------------  
 */
UntzUntzPageMask.MessageProcessor.processHidden = function(messageElement) {
    for (var itemXML = messageElement.firstChild; itemXML; itemXML = itemXML.nextSibling) {
        var elementId = itemXML.getAttribute("eid");


    }
};


UntzUntzPageMask.MessageProcessor.processInit = function(messageElement) {
    for (var itemXML = messageElement.firstChild; itemXML; itemXML = itemXML.nextSibling) {
        var elementId = itemXML.getAttribute("eid");
		var clientObj = new UntzUntzPageMask(elementId);
		clientObj.init(itemXML);
    }
};


/*
 * ---------------------------
 */
UntzUntzPageMask.prototype.init = function(itemXML) {
	// then create it if it not there
	var divEX = document.createElement('div');
	divEX.id = this.elementId;
	divEX.setAttribute('eid', this.elementId);
	divEX.setAttribute('style', 'position:absolute;z-index:0;width:100%;height:100%;background-color:#000000;opacity:0.75;filter:alpha(opacity=75)');
	
	var parent = document.getElementById("c_1");
    if (parent == null) { parent = document.getElementsByTagName('div')[0]; }
    if (parent != null) { parent.appendChild(divEX); this.divE = divEX; }
};
