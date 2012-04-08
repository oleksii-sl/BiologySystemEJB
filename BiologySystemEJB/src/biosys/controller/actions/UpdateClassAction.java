package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BioClass;
import model.BioClassHome;

import biosys.controller.Application;

/**
 * Class which performs update of <tt>BioClass</tt> objects
 * @author Алексей
 */
public class UpdateClassAction implements Action {

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException
    {

        BioClassHome bioSystem = (BioClassHome)request.getAttribute(Application.EJB_CLASS_MODEL);
        Map<String, String[]> map = request.getParameterMap();
        String s;
        try {
            BioClass bioClass = bioSystem.findByPrimaryKey(Integer.parseInt(map.get("id")[0]));
            if ((s = map.get("name")[0]).isEmpty())
                throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);
            bioClass.setName(map.get("name")[0]);
            if (!(s = map.get("parentId")[0]).isEmpty())
                bioClass.setParentId(Integer.parseInt(s));
            else
                bioClass.setParentId(null);
        } catch (NumberFormatException e) {
            throw new WrongInputDataException(EXCEPTION_WRONG_NUMBER_FORMAT);
        } catch (FinderException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("main");
    }

}
