package biosys.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.AliveHome;
import model.BioClassHome;

public class EJBBiosystem implements BiosystemDAO
{

    private static final String ALIVE_CONTEXT_NAME = "Alive";
    private static final String CLASS_CONTEXT_NAME = "BioClass";


    private AliveHome aliveHome;
    private BioClassHome bioClassHome;

    public EJBBiosystem()
    {
        try {
            Context ctx = (Context)new InitialContext();
            aliveHome = (AliveHome)ctx.lookup(ALIVE_CONTEXT_NAME);
            bioClassHome = (BioClassHome)ctx.lookup(CLASS_CONTEXT_NAME);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Alive> getAllAlive() throws BiologySystemException
    {
        Collection<model.Alive> remoteList = null;
        List<Alive> list = null;
        try {
            remoteList = aliveHome.findAll();
            list = new ArrayList<Alive>(remoteList.size());
            Alive alive;
            for (model.Alive temp : remoteList) {
                alive = new Alive();
                alive.setId(temp.getId());
                alive.setName(temp.getName());
                alive.setNameLatin(temp.getNameLatin());
                alive.setAvgWeight(temp.getAvgWeight());
                alive.setLifespan(temp.getLifespan());
                alive.setBioClass(temp.getBioClass());
                alive.setNativeRange(temp.getNativeRange());
                alive.setPopulation(temp.getPopulation());
                list.add(alive);
            }
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
        return list;
    }

    @Override
    public Alive getAlive(int id) throws BiologySystemException
    {
        Alive alive = new Alive();
        try {
            model.Alive temp = aliveHome.findByPrimaryKey(id);
            alive.setId(temp.getId());
            alive.setName(temp.getName());
            alive.setNameLatin(temp.getNameLatin());
            alive.setAvgWeight(temp.getAvgWeight());
            alive.setLifespan(temp.getLifespan());
            alive.setBioClass(temp.getBioClass());
            alive.setNativeRange(temp.getNativeRange());
            alive.setPopulation(temp.getPopulation());
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
        return alive;
    }

    @Override
    public void addAlive(Alive alive) throws BiologySystemException
    {
        try {
            aliveHome.create(alive.getName(), alive.getNameLatin(), alive.getLifespan(),
                    alive.getAvgWeight(), alive.getNativeRange(), alive.getPopulation(),
                    alive.getBioClass());
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (CreateException e) {
            throw new BiologySystemException(e);
        }

    }

    @Override
    public void updateAlive(Alive alive) throws BiologySystemException
    {
        try {
            model.Alive oldAlive = aliveHome.findByPrimaryKey(alive.getId());
            oldAlive.setName(alive.getName());
            oldAlive.setNameLatin(alive.getNameLatin());
            oldAlive.setAvgWeight(alive.getAvgWeight());
            oldAlive.setLifespan(alive.getLifespan());
            oldAlive.setBioClass(alive.getBioClass());
            oldAlive.setNativeRange(alive.getNativeRange());
            oldAlive.setPopulation(alive.getPopulation());
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
    }

    @Override
    public void removeAlive(int id) throws BiologySystemException
    {
        try {
            aliveHome.remove(id);
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (RemoveException e) {
            throw new BiologySystemException(e);
        }

    }

    @Override
    public List<Alive> getAllAliveConstraint(String ordercol, List<String> constraints)
            throws BiologySystemException
    {

        List<Alive> list = null;
        try {
            List<model.Alive> remoteList = aliveHome.findAllConstraint(ordercol, constraints);
            list = new ArrayList<Alive>(remoteList.size());
            Alive alive;
            for (model.Alive temp : remoteList) {
                alive = new Alive();
                alive.setId(temp.getId());
                alive.setName(temp.getName());
                alive.setNameLatin(temp.getNameLatin());
                alive.setAvgWeight(temp.getAvgWeight());
                alive.setLifespan(temp.getLifespan());
                alive.setBioClass(temp.getBioClass());
                alive.setNativeRange(temp.getNativeRange());
                alive.setPopulation(temp.getPopulation());
                list.add(alive);
            }
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
        return list;
    }

    @Override
    public List<BioClass> getAllClasses() throws BiologySystemException
    {
        List<BioClass> list = null;
        try {
            Collection<model.BioClass> remoteList = bioClassHome.findAll();
            list = new ArrayList<BioClass>(remoteList.size());
            BioClass bioClass;
            for (model.BioClass b : remoteList) {
                bioClass = new BioClass();
                bioClass.setId(b.getId());
                bioClass.setName(b.getName());
                bioClass.setParentId(b.getParentId());
                list.add(bioClass);
            }
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
        return list;
    }

    @Override
    public BioClass getBioClass(int id) throws BiologySystemException
    {
        BioClass bioClass = new BioClass();
        try {
            model.BioClass remote = bioClassHome.findByPrimaryKey(id);
            bioClass.setId(remote.getId());
            bioClass.setName(remote.getName());
            bioClass.setParentId(remote.getParentId());
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
        return bioClass;
    }

    @Override
    public void addBioClass(BioClass bio) throws BiologySystemException
    {
        try {
            bioClassHome.create(bio.getName(), bio.getParentId());
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (CreateException e) {
            throw new BiologySystemException(e);
        }

    }

    @Override
    public void updateBioClass(BioClass bio) throws BiologySystemException
    {
        try {
            model.BioClass oldClass = bioClassHome.findByPrimaryKey(bio.getId());
            oldClass.setName(bio.getName());
            oldClass.setParentId(bio.getParentId());
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }

    }

    @Override
    public void removeBioClass(int id) throws BiologySystemException
    {
        try {
            bioClassHome.remove(id);
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (RemoveException e) {
            throw new BiologySystemException(e);
        }

    }

    @Override
    public List<BioClass> getAllClassesConstraint(String ordercol, List<String> constraints)
            throws BiologySystemException
    {
        List<BioClass> list = null;
        try {
            List<model.BioClass> remoteList =
                    bioClassHome.findAllConstraint(ordercol, constraints);
            list = new ArrayList<BioClass>(remoteList.size());
            BioClass bioClass;
            for (model.BioClass b : remoteList) {
                bioClass = new BioClass();
                bioClass.setId(b.getId());
                bioClass.setName(b.getName());
                bioClass.setParentId(b.getParentId());
                list.add(bioClass);
            }
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
        return list;
    }

    @Override
    public List<BioClass> getClassesHierarchy(int id) throws BiologySystemException
    {
        List<BioClass> list = null;
        try {
            Collection<model.BioClass> remoteList = bioClassHome.findHierarchy(id);
            list = new ArrayList<BioClass>(remoteList.size());
            BioClass bioClass;
            for (model.BioClass b : remoteList) {
                bioClass = new BioClass();
                bioClass.setId(b.getId());
                bioClass.setName(b.getName());
                bioClass.setParentId(b.getParentId());
                list.add(bioClass);
            }
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
        return list;
    }

    @Override
    public List<BioClass> getAllClassesHierarchy() throws BiologySystemException
    {
        List<BioClass> list = null;
        try {
            Collection<model.BioClass> remoteList = bioClassHome.findAllHierarchy();
            list = new ArrayList<BioClass>(remoteList.size());
            BioClass bioClass;
            for (model.BioClass b : remoteList) {
                bioClass = new BioClass();
                bioClass.setId(b.getId());
                bioClass.setName(b.getName());
                bioClass.setParentId(b.getParentId());
                list.add(bioClass);
            }
        } catch (RemoteException e) {
            throw new BiologySystemException(e);
        } catch (FinderException e) {
            throw new BiologySystemException(e);
        }
        return list;
    }

}
