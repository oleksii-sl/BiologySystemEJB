package biosys.controller;

import java.util.Locale;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import biosys.model.BiosystemDAO;

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
    public static ModelFactory factory = new ModelFactory();

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet.ServletRequestEvent)
     */
    @Override
    public void requestInitialized(ServletRequestEvent event)
    {
        try {
            Locale.setDefault(Locale.ENGLISH);
            BiosystemDAO model = factory.create(ModelFactory.EJB_MODEL);
            event.getServletRequest().setAttribute(MODEL, model);
        } catch (Exception e) {
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
