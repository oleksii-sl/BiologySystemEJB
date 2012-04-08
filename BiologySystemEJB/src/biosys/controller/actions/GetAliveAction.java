package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Alive;
import model.AliveHome;

import biosys.controller.Application;

/**
 * Class which performs to get all <tt>alive</tt> objects
 * @author Алексей
 */
public class GetAliveAction implements Action {

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException
    {

        AliveHome bioSystem = (AliveHome)request.getAttribute(Application.EJB_ALIVE_MODEL);
        Map<String, String[]> map = request.getParameterMap();
        List<String> constraints = new LinkedList<String>();
        String ordercol;
        Collection<Alive> aliveList = null;
        try {
            if (map.containsKey("ordercol") && !map.get("ordercol")[0].isEmpty()) {
                ordercol = (String)request.getParameter("ordercol");
            } else {
                ordercol = null;
            }
            if (map.containsKey("nameSubstr") && !map.get("nameSubstr")[0].isEmpty()) {

                constraints.add("substr: " + "name " + map.get("nameSubstr")[0]);
            }
            if (map.containsKey("nameLatinSubstr") && !map.get("nameLatinSubstr")[0].isEmpty()) {

                constraints.add("substr: " + "name_latin " + map.get("nameLatinSubstr")[0]);
            }
            if (map.containsKey("lifespanMin") && map.containsKey("lifespanMax") &&
                    !map.get("lifespanMin")[0].isEmpty() && !map.get("lifespanMax")[0].isEmpty()) {

                constraints.add("between: " + "lifespan " + Integer.parseInt(map.get("lifespanMin")[0]) +
                        " " + Integer.parseInt(map.get("lifespanMax")[0]));
            }
            if (map.containsKey("avgWeightMin") && map.containsKey("avgWeightMax") &&
                       !map.get("avgWeightMin")[0].isEmpty() && !map.get("avgWeightMax")[0].isEmpty()) {

                constraints.add("between: " + "avg_weight " + Double.parseDouble(map.get("avgWeightMin")[0]) +
                           " " + Double.parseDouble(map.get("avgWeightMax")[0]));
            }
            if (map.containsKey("rangeSubstr") && !map.get("rangeSubstr")[0].isEmpty()) {

                constraints.add("substr: " + "native_range " + map.get("rangeSubstr")[0]);
            }
            if (map.containsKey("populatationMin") && map.containsKey("populatationMax") &&
                       !map.get("populatationMin")[0].isEmpty() && !map.get("populatationMax")[0].isEmpty()) {

                constraints.add("between: " + "population " + Long.parseLong(map.get("populatationMin")[0]) +
                           " " + Long.parseLong(map.get("populatationMax")[0]));
            }
            try {
                if (constraints.isEmpty() && (ordercol == null || ordercol.isEmpty())) {
                    aliveList = bioSystem.findAll();

                } else {
                    aliveList = bioSystem.findAllConstraint(ordercol, constraints);
                }
            } catch (FinderException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            throw new WrongInputDataException(EXCEPTION_WRONG_NUMBER_FORMAT);
        }
        request.setAttribute("aliveList", aliveList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("alive");
        dispatcher.forward(request, response);
    }

}
