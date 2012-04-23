package biosys.controller.actions;


/**
 * Throws when user trying to delete <tt>BioClass</tt> record
 * and this record is referenced from another table.
 * @author Алексей
 */
public class ParentClassDeleteException extends RuntimeException {

    private static final long serialVersionUID = 11L;

    public ParentClassDeleteException() { }

    public ParentClassDeleteException(String arg0) {
        super(arg0);
    }

    public ParentClassDeleteException(Throwable arg0) {
        super(arg0);
    }

    public ParentClassDeleteException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
