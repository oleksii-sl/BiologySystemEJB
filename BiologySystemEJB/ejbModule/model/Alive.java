package model;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;


/**
 * Class mapped to a table "living_entity" in the database.
 * It's simple Java Bean
 * @author Alexey
 */
public interface Alive extends EJBObject
{

    public int getId() throws RemoteException;

    public int getBioClass() throws RemoteException;

    public void setBioClass(int bioClass) throws RemoteException;

    public String getName() throws RemoteException;

    public void setName(String name) throws RemoteException;

    public String getNameLatin() throws RemoteException;

    public void setNameLatin(String nameLatin) throws RemoteException;

    public String getNativeRange() throws RemoteException;

    public void setNativeRange(String nativeRange) throws RemoteException;

    public int getLifespan() throws RemoteException;

    public void setLifespan(int lifespan) throws RemoteException;

    public double getAvgWeight() throws RemoteException;

    public void setAvgWeight(double avgWeight) throws RemoteException;

    public long getPopulation() throws RemoteException;

    public void setPopulation(long population) throws RemoteException;
}
