package biosys.controller;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import biosys.model.BiosystemDAO;
import biosys.model.DBBiosystem;
import biosys.model.EJBBiosystem;

/**
 * Used to get model according to specified string
 * @author Alexey
 *
 */
public class ModelFactory
{
    public static String EJB_MODEL = "ejb model";
    public static String SIMPLE_MODEL = "simple model";

    public BiosystemDAO create(String id) throws NamingException
    {
        if (EJB_MODEL.equals(id)) {
            return new EJBBiosystem();
        } else if (SIMPLE_MODEL.equals(id)) {
            Context cont = (Context) new InitialContext().lookup("java:/");
            DataSource ds = (DataSource) cont.lookup("BiologySystemDS");
            return new DBBiosystem(ds);
        }
        return null;
    }

}
