package model;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class AliveBean extends CommonBean implements EntityBean
{
    private static final Logger log = Logger.getLogger(CommonBean.class);

    private static final long serialVersionUID = 8642066343608624751L;

    private static final String SQL_GET_ALL_ALIVE = "SELECT * FROM living_entity";

    private static final String SQL_GET_ALIVE = "SELECT * FROM living_entity WHERE id = ? ";

    private static final String SQL_DELETE_ALIVE = "DELETE FROM living_entity WHERE id = ? ";

    private static final String SQL_UPDATE_ALIVE = "UPDATE living_entity " +
            "SET name = ?, name_latin = ?, lifespan = ?, avg_weight = ?, " +
            "native_range = ?,  population = ?, class = ? WHERE id = ? ";

    private static final String SQL_ADD_ALIVE = "INSERT INTO living_entity " +
            "VALUES(seq_living_entity.nextval, ?, ?, ?, ?, ?, ?, ?)";


    private EntityContext context;
    private DataSource ds;

    private String name;
    private String nameLatin;
    private int lifespan;
    private double avgWeight;
    private String nativeRange;
    private long population;
    private int bioClass;
    private int id;




    public AliveBean()
    {
        try {
            ds = (DataSource)new InitialContext().lookup("java:/BiologySystemDS");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer ejbCreate(String name, String nameLatin, int lifespan,
            double awgWeight, String nativeRange, long population, int bioClass) throws CreateException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_ADD_ALIVE);
            pst.setString(1, name);
            pst.setString(2, nameLatin);
            pst.setInt(3, lifespan);
            pst.setDouble(4, awgWeight);
            pst.setString(5, nativeRange);
            pst.setLong(6, population);
            pst.setInt(7, bioClass);
            //pst.executeUpdate();
            log.info("Add Alive: " + pst.executeUpdate());
            setStateChanged(false);
        } catch (SQLException e) {
            throw new CreateException(e.getMessage());
        } finally {
            try {
                closeAll(conn, pst, null);
            } catch (SQLException e) {
                throw new EJBException(e.getMessage());
            }
        }

        return null;
    }

    public void ejbPostCreate(String name, String nameLatin, int lifespan,
            double awgWeight, String nativeRange, long population, int bioClass) throws CreateException{ }

    public Integer ejbFindByPrimaryKey(Integer id) throws FinderException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Integer primaryKey = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_GET_ALIVE);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            primaryKey = handleAliveResultSet(rs).get(0);
        } catch (SQLException e) {
            throw new FinderException(e.getMessage());
        } finally {
            try {
                closeAll(conn, pst, rs);
            } catch (SQLException e) {
                throw new EJBException(e.getMessage());
            }
        }
        return primaryKey;
    }

    public Collection<Integer> ejbFindAll() throws FinderException
    {
        Connection conn = null;
        Statement st = null;
        List<Integer> list = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(SQL_GET_ALL_ALIVE);
            list = handleAliveResultSet(rs);
        } catch (SQLException e) {
            throw new FinderException(e.getMessage());
        } finally {
            try {
                closeAll(conn, st, rs);
            } catch (SQLException e) {
                throw new EJBException(e.getMessage());
            }
        }
        return list;
    }

    public List<Integer> ejbFindAllConstraint(String ordercol, List<String> constraints)
            throws FinderException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        List<Integer> list = null;
        ResultSet rs = null;
        String temp;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(temp = SQL_GET_ALL_ALIVE + makeConstraint(ordercol, constraints));
            log.info("getAllAlive statement: " + temp);
            rs = pst.executeQuery();
            list = handleAliveResultSet(rs);
        } catch (SQLException e) {
            throw new FinderException(e.getMessage());
        } finally {
            try {
                closeAll(conn, pst, rs);
            } catch (SQLException e) {
                throw new EJBException(e.getMessage());
            }
        }
        return list;
    }

    public int getId() {
        return id;
    }

    public int getBioClass() {
        return bioClass;
    }

    public void setBioClass(int bioClass) {
        this.bioClass = bioClass;
        setStateChanged(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setStateChanged(true);
    }

    public String getNameLatin() {
        return nameLatin;
    }

    public void setNameLatin(String nameLatin) {
        this.nameLatin = nameLatin;
        setStateChanged(true);
    }

    public String getNativeRange() {
        return nativeRange;
    }

    public void setNativeRange(String nativeRange) {
        this.nativeRange = nativeRange;
        setStateChanged(true);
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
        setStateChanged(true);
    }

    public double getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(double avgWeight) {
        this.avgWeight = avgWeight;
        setStateChanged(true);
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
        setStateChanged(true);
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException
    {
        this.id = (Integer) context.getPrimaryKey();
    }

    @Override
    public void ejbLoad() throws EJBException, RemoteException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_GET_ALIVE);
            pst.setInt(1, this.id);
            rs = pst.executeQuery();
            rs.next();
            this.id = rs.getInt("id");
            this.name = rs.getString("name");
            this.nativeRange = rs.getString("native_range");
            this.nameLatin = rs.getString("name_latin");
            this.avgWeight = rs.getDouble("avg_weight");
            this.bioClass = rs.getInt("class");
            this.lifespan = rs.getInt("lifespan");
            this.population = rs.getLong("population");
        } catch (SQLException e) {
            throw new EJBException(e.getMessage());
        } finally {
            try {
                closeAll(conn, pst, rs);
            } catch (SQLException e) {
                throw new EJBException(e.getMessage());
            }
        }
    }

    @Override
    public void ejbStore() throws EJBException, RemoteException
    {
        if (!isStateChanged())
            return;
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_UPDATE_ALIVE);
            pst.setString(1, this.name);
            pst.setString(2, this.nameLatin);
            pst.setInt(3, this.lifespan);
            pst.setDouble(4, this.avgWeight);
            pst.setString(5, this.nativeRange);
            pst.setLong(6, this.population);
            pst.setInt(7, this.bioClass);
            pst.setInt(8, this.id);
            //pst.executeUpdate();
            log.info("Update Alive: " + pst.executeUpdate());
            setStateChanged(false);
        } catch (SQLException e) {
            throw new EJBException(e.getMessage());
        } finally {
            try {
                closeAll(conn, pst, null);
            } catch (SQLException e) {
                throw new EJBException(e.getMessage());
            }
        }
    }

    @Override
    public void setEntityContext(EntityContext context) throws EJBException,
            RemoteException
    {
        this.context = context;

    }

    @Override
    public void unsetEntityContext() throws EJBException, RemoteException
    {
        this.context = null;

    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException { }

    @Override
    public void ejbRemove() throws RemoveException, EJBException, RemoteException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_DELETE_ALIVE);
            pst.setInt(1, this.id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RemoveException(e.getMessage());
        } finally {
            try {
                closeAll(conn, pst, null);
            } catch (SQLException e) {
                throw new EJBException(e.getMessage());
            }
        }
    }

    /**
     * Used to handle <tt>Alive</tt> result set
     * @param rs <tt>ResultSet</tt> to handle
     * @return list of <tt>Alive</tt> objects
     * @throws SQLException
     */
    private List<Integer> handleAliveResultSet(ResultSet rs) throws SQLException {
        List<Integer> list = new ArrayList<Integer>();
        while(rs.next()) {
            list.add(rs.getInt("id"));
        }
        return list;
    }
}
