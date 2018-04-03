package org.gaurav.webservices.messanger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.gaurav.webservices.messanger.beans.MessageFIlterBean;
import org.gaurav.webservices.messanger.exceptions.DataNotFoundException;
import org.gaurav.webservices.messanger.model.Message;
import org.gaurav.webservices.messanger.service.MessageService;
	
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();	
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFIlterBean fIlterBean) {
		
		if(fIlterBean.getYear()>0) {
			return messageService.getAllMessgesForYears(fIlterBean.getYear());
		}
		if (fIlterBean.getStart()>=0 && fIlterBean.getSize() >0) {
			return messageService.getAllMessgesPaginated(fIlterBean.getStart(), fIlterBean.getSize());
		}
		return messageService.getAllMessges();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessages(@PathParam("messageId") long id ,
			@Context UriInfo uriInfo) {
		
		String uriMessage = buildUriMessage(id, uriInfo);
		String uriComments = buildUriComments(id, uriInfo);

		Message message = messageService.getMessage(id);
		if(message == null) {
			throw new DataNotFoundException("No data found for the ID " +id);
		}
		message.addLink("self", uriMessage );
		message.addLink("comment", uriComments);
		return message;
	}
	
	private String buildUriComments(long id, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
				.path(MessageResource.class).path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", id)
				.build().toString();
	}

	private String buildUriMessage(long id, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(id))
				.build().toString();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message messageNew = messageService.addMessage(message);
		String id = String.valueOf(messageNew.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri).entity(messageNew).build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id,
			Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void updateMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
