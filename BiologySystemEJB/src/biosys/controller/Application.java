package biosys.controller;

import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import model.AliveHome;
import model.BioClassHome;

/**
 * Class used to listen user requests and creates instance of Model for
 * current request.
 * @author Алексей
 */
public class Application implements ServletRequestListener {

    /**
     * String identificator for Model object in MVC paradigm
     */
    public static final String MODEL = "model";
    public static final String EJB_ALIVE_MODEL = "ejbAliveModel";
    public static final String EJB_CLASS_MODEL = "ejbClassModel";

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet.ServletRequestEvent)
     */
    @Override
    public void requestInitialized(ServletRequestEvent event) {
        try {
            Locale.setDefault(Locale.ENGLISH);
            Context cont = (Context) new InitialContext();
            AliveHome aliveHome = (AliveHome)cont.lookup("Alive");
            BioClassHome bioClassHome = (BioClassHome)cont.lookup("BioClass");
            event.getServletRequest().setAttribute(EJB_ALIVE_MODEL, aliveHome);
            event.getServletRequest().setAttribute(EJB_CLASS_MODEL, bioClassHome);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method doesn't do anything
     */
    @Override
    public void requestDestroyed(ServletRequestEvent event) {

    }

}
