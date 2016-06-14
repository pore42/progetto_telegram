package it.unimi.di.sweng.eventfinderbottests.ebapi;

import java.io.File;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.util.Series;

public class MockServer {

	private Server server;

	public MockServer(final int port) throws Exception {
		server = new Server(Protocol.HTTP, port);
		server.start();
	}

	public void setReply(final Method method, final String path, final String reply) {
		server.setNext(new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				setContext(new Context());
				Series<Parameter> parameters = getContext().getParameters();
				String certificate = getClass().getClassLoader().getResource("mockServer.cer").getPath();
				parameters.add("keyStorePath", certificate);
				final Method requestMethod = request.getMethod();
				if (method != requestMethod) {
					response.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
					return;
				}
				final String requestPath = request.getResourceRef().getPath();
				if (path.equals(requestPath))
					response.setEntity(reply, MediaType.APPLICATION_ALL_JSON);
				else
					response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			}
		});
	}

	public void stop() throws Exception {
		server.stop();
	}

}
