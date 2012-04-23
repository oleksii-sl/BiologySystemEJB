package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosys.controller.Application;
import biosys.model.BioClass;
import biosys.model.BiosystemDAO;

/**
 * Class which performs to get all <tt>BioClass</tt> objects
 * @author Алексей
 */
public class GetClassesAction implements Action {

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {


        BiosystemDAO bioSystem = (BiosystemDAO)request.getAttribute(Application.MODEL);
        Map<String, String[]> map = request.getParameterMap();
        List<String> constraints = new LinkedList<String>();
        List<BioClass> classList;
        String ordercol;

        if (map.containsKey("ordercol") && !map.get("ordercol")[0].isEmpty())
            ordercol = (String)request.getParameter("ordercol");
        else
            ordercol = null;

        try {
            if (map.containsKey("nameSubstr") && !map.get("nameSubstr")[0].isEmpty()) {

                constraints.add("substr: " + "name " + map.get("nameSubstr")[0]);
            }
            if (map.containsKey("parentMin") && map.containsKey("parentMax") &&
                    !map.get("parentMin")[0].isEmpty() && !map.get("parentMax")[0].isEmpty()) {

                constraints.add("between: " + "parent " + Integer.parseInt(map.get("parentMin")[0]) +
                        " " + Integer.parseInt(map.get("parentMax")[0]));
            }
        } catch (NumberFormatException e) {
            throw new WrongInputDataException(EXCEPTION_WRONG_NUMBER_FORMAT);
        }
        if (constraints.isEmpty() && (ordercol == null || ordercol.isEmpty())) {
            classList = bioSystem.getAllClasses();
        } else {
            classList = bioSystem.getAllClassesConstraint(ordercol, constraints);
        }
        request.setAttribute("classList", classList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("classes");
        dispatcher.forward(request, response);
    }

}
