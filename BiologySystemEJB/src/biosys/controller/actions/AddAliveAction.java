package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AliveHome;

import biosys.controller.Application;

/**
 * Class which performs adding <tt>Alive</tt> object
 * @author Алексей
 */
public class AddAliveAction implements Action {

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException
    {

        AliveHome bioSystem = (AliveHome)request.getAttribute(Application.EJB_ALIVE_MODEL);
        Map<String, String[]> map = request.getParameterMap();

        String name;
        String nameLatin;
        int lifespan;
        double avgWeight;
        String nativeRange;
        long population;
        int bioClass;

        try {
            if ((name = map.get("name")[0]).isEmpty())
                throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);
            if ((nameLatin = map.get("nameLatin")[0]).isEmpty())
                throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);
            lifespan = Integer.parseInt(map.get("lifespan")[0]);
            avgWeight = Double.parseDouble(map.get("avgWeight")[0]);
            if ((nativeRange = map.get("nativeRange")[0]).isEmpty())
                throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);
            population = Long.parseLong(map.get("population")[0]);
            bioClass = Integer.parseInt(map.get("bioClass")[0]);
            bioSystem.create(name, nameLatin, lifespan, avgWeight, nativeRange, population, bioClass);
        } catch (NumberFormatException e) {
            throw new WrongInputDataException(EXCEPTION_WRONG_NUMBER_FORMAT);
        } catch (CreateException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("main");
    }

}
