#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {
  
  private static Logger logger = Logger.getLogger(HomeServlet.class.getName());
  
  @Inject
  public HomeServlet() {
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    logger.info("HomeServlet.doGet() info message");
    
    UserService userService = UserServiceFactory.getUserService();

    String thisURL = request.getRequestURI();

    response.setContentType("text/html");
    if (request.getUserPrincipal() != null) {
      response.getWriter().println(
          "<p>Hello, " + request.getUserPrincipal().getName() + "!  You can <a href=${symbol_escape}""
              + userService.createLogoutURL(thisURL) + "${symbol_escape}">sign out</a>.</p>");
    } else {
      response.getWriter()
          .println("<p>Please <a href=${symbol_escape}"" + userService.createLoginURL(thisURL) + "${symbol_escape}">sign in</a>.</p>");
    }
  }
  
}
