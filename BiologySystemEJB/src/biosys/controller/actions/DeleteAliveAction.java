package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AliveHome;

import biosys.controller.Application;

/**
 * Class which performs delete <tt>Alive</tt> objects
 * @author Алексей
 */
public class DeleteAliveAction implements Action {

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        AliveHome bioSystem = (AliveHome)request.getAttribute(Application.EJB_ALIVE_MODEL);
        Map<String, String[]> map = request.getParameterMap();
        bioSystem.delete(Integer.parseInt(map.get("id")[0]));
        response.sendRedirect("main");
    }

}
