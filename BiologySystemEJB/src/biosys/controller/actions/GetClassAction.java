package biosys.controller.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosys.controller.Application;
import biosys.model.BiologySystemException;
import biosys.model.BiosystemDAO;

public class GetClassAction implements Action {

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws BiologySystemException, IOException, ServletException
    {
        BiosystemDAO bioSystem = (BiosystemDAO)request.getAttribute(Application.MODEL);
        String target = request.getParameter("target");
        request.setAttribute("bioClass", bioSystem.getBioClass(
                Integer.parseInt(request.getParameter("id"))));
        if (target == null) {
            throw new RuntimeException("Target is null");
        } else {
            request.getRequestDispatcher(target).forward(request, response);
        }

    }

}
