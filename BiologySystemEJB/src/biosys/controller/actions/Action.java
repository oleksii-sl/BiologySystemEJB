package biosys.controller.actions;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Common interface for all actions that user can perform
 * @author Алексей
 */
public interface Action {

    String EXCEPTION_WRONG_DATA_FORMAT = "You enter wrong data";
    String EXCEPTION_WRONG_NUMBER_FORMAT = "You enter incorrect number(s)";
    String EXCEPTION_EMPTY_STRING = "You didn't fill some of the form(s)";


    /**
     * Performs the current action
     * @param request
     * @param response
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     */
    public void perform(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException;
}
