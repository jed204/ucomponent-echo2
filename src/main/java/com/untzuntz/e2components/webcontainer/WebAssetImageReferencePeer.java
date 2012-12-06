package com.untzuntz.e2components.webcontainer;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.componentxml.InvalidPropertyException;
import nextapp.echo2.app.componentxml.PropertyXmlPeer;
import nextapp.echo2.app.componentxml.propertypeer.ExtentPeer;
import nextapp.echo2.app.util.DomUtil;

import org.w3c.dom.Element;

import com.untzuntz.e2components.app.WebAssetImageReference;

public class WebAssetImageReferencePeer implements PropertyXmlPeer {

	/**
	 * @see nextapp.echo2.app.componentxml.PropertyXmlPeer#getValue(java.lang.ClassLoader,
	 *      java.lang.Class, org.w3c.dom.Element)
	 */
	public Object getValue(ClassLoader classLoader, Class objectClass,
			Element propertyElement) throws InvalidPropertyException {
		if (propertyElement.hasAttribute("value")) {
			return new WebAssetImageReference(
					propertyElement.getAttribute("value"));
		} else {
			Element httpImageReferenceElement = DomUtil
					.getChildElementByTagName(propertyElement,
							"http-image-reference");
			if (!httpImageReferenceElement.hasAttribute("uri")) {
				throw new InvalidPropertyException(
						"Invalid WebAssetImageReference property (uri not specified).",
						null);
			}
			String url = httpImageReferenceElement.getAttribute("uri");
			Extent width = null;
			if (httpImageReferenceElement.hasAttribute("width")) {
				width = ExtentPeer.toExtent(httpImageReferenceElement
						.getAttribute("width"));
			}
			Extent height = null;
			if (httpImageReferenceElement.hasAttribute("height")) {
				height = ExtentPeer.toExtent(httpImageReferenceElement
						.getAttribute("height"));
			}
			return new WebAssetImageReference(url, width, height);
		}
	}

}
