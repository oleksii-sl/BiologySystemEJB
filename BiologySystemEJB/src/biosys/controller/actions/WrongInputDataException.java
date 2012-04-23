package biosys.controller.actions;

/**
 * Throws when user input wrong values
 * @author Алексей
 */
public class WrongInputDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WrongInputDataException() {
        super();
    }

    public WrongInputDataException(String arg0) {
        super(arg0);
    }

    public WrongInputDataException(Throwable arg0) {
        super(arg0);
    }

    public WrongInputDataException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
