package biosys.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import biosys.controller.actions.ActionFactory;

/**
 * Servlet implementation class UpdateHandler
 * Used to handle user requests. It's a Controller in MVC paradigm.
 */
public class UpdateHandler extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Logger instance
     */
    private static final Logger log = Logger.getLogger(UpdateHandler.class);

    /**
     * Factory of actions
     */
    private ActionFactory factory = new ActionFactory();

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        try {
            if (log.isDebugEnabled())
                log.info("Action to perform: " + map.get("action")[0]);

            factory.create(map.get("action")[0]).perform(request, response);
        } catch (SQLException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

}
