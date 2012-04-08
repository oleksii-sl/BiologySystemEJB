package model;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


public interface AliveHome extends EJBHome
{
    /**
     * Used to add new <tt>Alive</tt> to the model
     * @throws CreateException
     * @throws RemoteException
     */
    public Alive create(String name, String nameLatin, int lifespan,
            double awgWeight, String nativeRange, long population, int bioClass)
            throws CreateException, RemoteException;

    /**
     * Used to remove <tt>Alive</tt> from model by specified id
     * @param id
     * @throws RemoteException
     */
    public void delete(Integer id) throws RemoteException;

    /**
     * Method used to get all Alive objects with specified constraint
     * @param ordercol  column to order
     * @param constraints  list of constraints with specified format for substring and between expressions.
     * 		"substr: {column name} {substring to search}" or "between: {column name} {min number} {max number}"
     * @return list of <tt>Alive</tt> objects
     * @throws FinderException
     * @throws RemoteException
     */
    public List<Alive> findAllConstraint(String colname, List<String> constraints)
            throws FinderException, RemoteException;

    /**
     * Used to get <tt>Alive</tt> with specified id
     * @param id
     * @return Alive object
     * @throws FinderException
     * @throws RemoteException
     */
    public Alive findByPrimaryKey(Integer id)
        throws FinderException, RemoteException;

    /**
     * Method used to get all <tt>Alive</tt> objects
     * @return list of <tt>Alive</tt> objects
     * @throws FinderException
     * @throws RemoteException
     */
    public Collection<Alive> findAll()
        throws FinderException, RemoteException;
}
