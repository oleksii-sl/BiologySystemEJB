package model;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Class mapped to a table "classification" in the database.
 * It's simple Java Bean
 * @author Alexey
 */
public interface BioClass extends EJBObject {

    public int getId() throws RemoteException;

    public String getName() throws RemoteException;

    public void setName(String name) throws RemoteException;

    public Integer getParentId() throws RemoteException;

    public void setParentId(Integer parentId) throws RemoteException;

}
