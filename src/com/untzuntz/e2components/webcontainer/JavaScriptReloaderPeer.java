package com.untzuntz.e2components.webcontainer;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.update.ServerComponentUpdate;
import nextapp.echo2.webcontainer.ComponentSynchronizePeer;
import nextapp.echo2.webcontainer.ContainerInstance;
import nextapp.echo2.webcontainer.PartialUpdateManager;
import nextapp.echo2.webcontainer.PartialUpdateParticipant;
import nextapp.echo2.webcontainer.RenderContext;
import nextapp.echo2.webrender.ServerMessage;
import nextapp.echo2.webrender.Service;
import nextapp.echo2.webrender.WebRenderServlet;
import nextapp.echo2.webrender.servermessage.DomUpdate;
import nextapp.echo2.webrender.service.JavaScriptService;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.untzuntz.e2components.app.JavaScriptReloader;

public class JavaScriptReloaderPeer implements ComponentSynchronizePeer
{
	
	static final Service			JavaScriptReloader_SERVICE		= JavaScriptService
																	.forResource("Echo.JavaScriptReloader",
																			"/com/untzuntz/e2components/resources/Sync.JavaScriptReloader.js");
	
	static
	{
		WebRenderServlet.getServiceRegistry().add(JavaScriptReloader_SERVICE);
	}
	
	protected PartialUpdateManager	partialUpdateManager	= new PartialUpdateManager();
	
	/**
	 * Constructs a <code>LightBoxPeer</code>
	 */
	public JavaScriptReloaderPeer()
	{
		super();
		partialUpdateManager.add(JavaScriptReloader.PROPERTY_HIDDEN, new PartialUpdateParticipant()
		{
			
			public void renderProperty(RenderContext rc, ServerComponentUpdate update)
			{
				renderHiddenDirective(rc, update.getParent());
			}
			
			public boolean canRenderProperty(RenderContext rc, ServerComponentUpdate update)
			{
				return true;
			}
		});
	}
	
	/**
	 * @see nextapp.echo2.webcontainer.ComponentSynchronizePeer#getContainerId(nextapp.echo2.app.Component)
	 */
	public String getContainerId(Component component)
	{
		throw new UnsupportedOperationException("Component does not support children.");
	}
	
	/**
	 * @see nextapp.echo2.webcontainer.ComponentSynchronizePeer#renderAdd(nextapp.echo2.webcontainer.RenderContext,
	 *      nextapp.echo2.app.update.ServerComponentUpdate, java.lang.String,
	 *      nextapp.echo2.app.Component)
	 */
	public void renderAdd(RenderContext rc, ServerComponentUpdate update, String targetId, Component component)
	{
		
		Element domAddElement = DomUpdate.renderElementAdd(rc.getServerMessage());
		DocumentFragment htmlFragment = rc.getServerMessage().getDocument().createDocumentFragment();
		renderHtml(rc, update, htmlFragment, component);
		DomUpdate.renderElementAddContent(rc.getServerMessage(), domAddElement, targetId, htmlFragment);		
	}
	
	/**
	 * @see nextapp.echo2.webcontainer.DomUpdateSupport#renderHtml(nextapp.echo2.webcontainer.RenderContext,
	 *      nextapp.echo2.app.update.ServerComponentUpdate, org.w3c.dom.Node,
	 *      nextapp.echo2.app.Component)
	 */
	public void renderHtml(RenderContext rc, ServerComponentUpdate addUpdate, Node parentNode, Component component)
	{
		
		ServerMessage serverMessage = rc.getServerMessage();
		serverMessage.addLibrary(JavaScriptReloader_SERVICE.getId());
		
		JavaScriptReloader loader = (JavaScriptReloader) component;
		String elementId = ContainerInstance.getElementId(loader);
		
		System.out.println("Rendering JavaScriptReloader...");
		
		Element itemizedUpdateElement = serverMessage.appendPartDirective(ServerMessage.GROUP_ID_UPDATE,
				"UntzUntzJavaScriptReloader.MessageProcessor", "init");
		
		Element itemElement = rc.getServerMessage().getDocument().createElement("div");
		itemizedUpdateElement.appendChild(itemElement);
		
		itemElement.setAttribute("hidden", !loader.isVisible() + "");
		itemElement.setAttribute(JavaScriptReloader.PROPERTY_SCRIPT_URI, loader.getScriptURI());
		itemElement.setAttribute("eid", elementId);
	}
	
	/**
	 * @see nextapp.echo2.webcontainer.ComponentSynchronizePeer#renderDispose(nextapp.echo2.webcontainer.RenderContext,
	 *      nextapp.echo2.app.update.ServerComponentUpdate,
	 *      nextapp.echo2.app.Component)
	 */
	public void renderDispose(RenderContext rc, ServerComponentUpdate update, Component component)
	{
		ServerMessage serverMessage = rc.getServerMessage();
		serverMessage.addLibrary(JavaScriptReloader_SERVICE.getId());
		renderDisposeDirective(rc, component);
	}
	
	/**
	 * @see nextapp.echo2.webcontainer.ComponentSynchronizePeer#renderUpdate(nextapp.echo2.webcontainer.RenderContext,
	 *      nextapp.echo2.app.update.ServerComponentUpdate, java.lang.String)
	 */
	public boolean renderUpdate(RenderContext rc, ServerComponentUpdate update, String targetId)
	{
		boolean fullReplace = false;
		if (update.hasUpdatedProperties())
		{
			if (!partialUpdateManager.canProcess(rc, update))
			{
				fullReplace = true;
			}
		}
		if (fullReplace)
		{
			// Perform full update.
			DomUpdate.renderElementRemove(rc.getServerMessage(), ContainerInstance.getElementId(update.getParent()));
			renderAdd(rc, update, targetId, update.getParent());
		}
		else
		{
			partialUpdateManager.process(rc, update);
		}
		return fullReplace;
	}
	
	/**
	 * Renders a dispose directive.
	 */
	private void renderDisposeDirective(RenderContext rc, Component component)
	{
		Element itemizedUpdateElement = rc.getServerMessage().getItemizedDirective(ServerMessage.GROUP_ID_PREREMOVE,
				"UntzUntzJavaScriptReloader.MessageProcessor", "dispose", new String[0], new String[0]);
		Element itemElement = rc.getServerMessage().getDocument().createElement("item");
		itemElement.setAttribute("eid", ContainerInstance.getElementId(component));
		itemizedUpdateElement.appendChild(itemElement);
	}
	
	private void renderHiddenDirective(RenderContext rc, Component component)
	{
		Element itemizedUpdateElement = rc.getServerMessage().getItemizedDirective(ServerMessage.GROUP_ID_POSTUPDATE,
				"UntzUntzJavaScriptReloader.MessageProcessor", "hidden", new String[0], new String[0]);
		Element itemElement = rc.getServerMessage().getDocument().createElement("item");
		itemizedUpdateElement.appendChild(itemElement);
		
		itemElement.setAttribute("eid", ContainerInstance.getElementId(component));
		boolean hidden = (Boolean) component.getRenderProperty(JavaScriptReloader.PROPERTY_HIDDEN);
		itemElement.setAttribute("hidden", String.valueOf(hidden));
	}
}
