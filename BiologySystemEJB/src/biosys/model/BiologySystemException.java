package biosys.model;

/**
 * Thrown when there is some problems appeared
 * in model or in interaction with it.
 * @author Alexey
 *
 */
public class BiologySystemException extends Exception
{

    private static final long serialVersionUID = 4734161359548329487L;

    public BiologySystemException() { }

    public BiologySystemException(String arg0) {
        super(arg0);
    }

    public BiologySystemException(Throwable arg0) {
        super(arg0);
    }

    public BiologySystemException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
