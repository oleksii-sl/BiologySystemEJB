package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Alive;
import model.AliveHome;

import biosys.controller.Application;

/**
 * Class which performs update of <tt>Alive</tt> objects
 * @author Алексей
 */
public class UpdateAliveAction implements Action
{

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException
    {

        AliveHome bioSystem = (AliveHome)request.getAttribute(Application.EJB_ALIVE_MODEL);
        Map<String, String[]> map = request.getParameterMap();

        String s;
        try {
            Alive alive =
                    bioSystem.findByPrimaryKey(Integer.parseInt(map.get("id")[0]));

            if ((s = map.get("name")[0]).isEmpty())
                throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);
            alive.setName(s);
            if ((s = map.get("nameLatin")[0]).isEmpty())
                throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);
            alive.setNameLatin(s);
            alive.setLifespan(Integer.parseInt(map.get("lifespan")[0]));
            alive.setAvgWeight(Double.parseDouble(map.get("avgWeight")[0]));
            if ((s = map.get("nativeRange")[0]).isEmpty())
                throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);
            alive.setNativeRange(s);
            alive.setPopulation(Long.parseLong(map.get("population")[0]));
            alive.setBioClass(Integer.parseInt(map.get("bioClass")[0]));
        } catch (NumberFormatException e) {
            throw new WrongInputDataException(EXCEPTION_WRONG_NUMBER_FORMAT);
        } catch (FinderException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("main");
    }

}
