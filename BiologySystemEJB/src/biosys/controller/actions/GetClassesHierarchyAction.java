package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.ejb.FinderException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BioClass;
import model.BioClassHome;

import biosys.controller.Application;

/**
 * Class which performs to get hierarchy of <tt>BioClass</tt> objects
 * @author Алексей
 */
public class GetClassesHierarchyAction implements Action {

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        BioClassHome bioSystem = (BioClassHome)request.getAttribute(Application.EJB_CLASS_MODEL);
        Collection<BioClass> hierarchyList;
        String id = request.getParameter("id");
        try {
            if (id == null || id.isEmpty()) {
                hierarchyList = bioSystem.findAllHierarchy();
            } else {
                hierarchyList = bioSystem.findHierarchy(Integer.parseInt(id));
            }
        } catch (FinderException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("classesHierarchy", hierarchyList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("hierarchy");
        dispatcher.forward(request, response);
    }

}
