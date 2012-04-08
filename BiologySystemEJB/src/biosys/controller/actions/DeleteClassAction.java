package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BioClassHome;

import biosys.controller.Application;

/**
 * Class which performs delete <tt>BioClass</tt> object
 * @author �������
 */
public class DeleteClassAction implements Action {

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException
    {

        Map<String, String[]> map = request.getParameterMap();
        BioClassHome bioSystem = (BioClassHome)request.getAttribute(Application.EJB_CLASS_MODEL);
        bioSystem.delete(Integer.parseInt(map.get("id")[0]));
        response.sendRedirect("main");
    }

}
