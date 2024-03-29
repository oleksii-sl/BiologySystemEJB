package biosys.controller.actions;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biosys.controller.Application;
import biosys.model.BioClass;
import biosys.model.BiologySystemException;
import biosys.model.BiosystemDAO;

/**
 * Class which performs update of <tt>BioClass</tt> objects
 * @author �������
 */
public class UpdateClassAction implements Action {

    /* (non-Javadoc)
     * @see biosys.controller.actions.Action#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws BiologySystemException, IOException {

        BiosystemDAO bioSystem = (BiosystemDAO)request.getAttribute(Application.MODEL);
        Map<String, String[]> map = request.getParameterMap();
        BioClass bioClass = new BioClass();
        String s;
        try {
            bioClass.setId(Integer.parseInt(map.get("id")[0]));
            if ((s = map.get("name")[0]).isEmpty())
                throw new WrongInputDataException(EXCEPTION_EMPTY_STRING);
            bioClass.setName(map.get("name")[0]);
            if (!(s = map.get("parentId")[0]).isEmpty())
                bioClass.setParentId(Integer.parseInt(s));
            else
                bioClass.setParentId(null);
        } catch (NumberFormatException e) {
            throw new WrongInputDataException(EXCEPTION_WRONG_NUMBER_FORMAT);
        }

        bioSystem.updateBioClass(bioClass);
        response.sendRedirect("main");
    }

}
