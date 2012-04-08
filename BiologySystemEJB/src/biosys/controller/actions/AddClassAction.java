package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BioClassHome;

import biosys.controller.Application;

/**
 * Class which performs adding <tt>BioClass</tt> object
 * @author Алексей
 */
public class AddClassAction implements Action
{

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException
    {

        BioClassHome bioSystem = (BioClassHome)request.getAttribute(Application.EJB_CLASS_MODEL);
        Map<String, String[]> map = request.getParameterMap();
        String name;
        Integer parentId;

        if ((name = map.get("name")[0]).isEmpty())
            throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);

        String s;
        try {
            if (!(s = map.get("parentId")[0]).isEmpty())
               parentId = Integer.parseInt(s);
            else
                parentId = null;
            bioSystem.create(name, parentId);
        } catch (NumberFormatException e) {
            throw new WrongInputDataException(EXCEPTION_WRONG_NUMBER_FORMAT);
        } catch (CreateException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("main");
    }

}
