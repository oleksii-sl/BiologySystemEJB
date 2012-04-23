package model;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;


public interface BioClassHome extends EJBHome
{
    /**
     * Used to add new <tt>BioClass</tt> to the model
     * @param bio
     * @throws CreateException
     * @throws RemoteException
     */
    public BioClass create(String name, Integer parent) throws CreateException, RemoteException;

    /**
    * Method used to get all <tt>BioClass</tt> objects with specified constraint
     * @param ordercol - column to order
     * @param constraints - list of constraints with specified format for substring and between expressions.
     * 		"substr: {column name} {substring to search}" or "between: {column name} {min number} {max number}"
     * @return list of <tt>BioClass</tt> objects
     * @throws FinderException
     * @throws RemoteException
     */
    public List<BioClass> findAllConstraint(String colname, List<String> constraints)
            throws FinderException, RemoteException;

    /**
     * Used to get <tt>BioClass</tt> with specified id
     * @param id
     * @return BioClass object
     * @throws FinderException
     * @throws RemoteException
     */
    public BioClass findByPrimaryKey(Integer id) throws FinderException, RemoteException;

    /**
     * Method used to get all <tt>BioClass</tt> objects
     * @return list of <tt>BioClass</tt> objects
     * @throws FinderException
     * @throws RemoteException
     */
    public Collection<BioClass> findAll() throws FinderException, RemoteException;

    /**
     * Used to get full hierarchy of classes
     * @return resulting hierarchy list
     * @throws FinderException
     * @throws RemoteException
     */
    public Collection<BioClass> findAllHierarchy() throws FinderException, RemoteException;

    /**
     * Used to get hierarchy of classes starting from specified id
     * @param id id to start
     * @return resulting list of <tt>BioClass</tt>
     * @throws FinderException
     * @throws RemoteException
     */
    public Collection<BioClass> findHierarchy(Integer parent) throws FinderException, RemoteException;

}
