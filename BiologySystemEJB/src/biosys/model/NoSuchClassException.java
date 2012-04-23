/**
 *
 */
package biosys.model;

/**
 * Throws when user references to non-existing <tt>BioClass</tt> object
 * @author Алексей
 */
public class NoSuchClassException extends RuntimeException {

    private static final long serialVersionUID = -3439776523345482814L;

    public NoSuchClassException() { }

    public NoSuchClassException(String arg0) {
        super(arg0);
    }

    public NoSuchClassException(Throwable arg0) {
        super(arg0);
    }

    public NoSuchClassException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
