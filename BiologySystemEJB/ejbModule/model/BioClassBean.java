package model;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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

public class BioClassBean extends CommonBean implements EntityBean
{

    private static final long serialVersionUID = -1735072859341327771L;

    private static final Logger log = Logger.getLogger(BioClassBean.class);

    private static final String SQL_GET_ALL_CLASSES = "SELECT * FROM classification";

    private static final String SQL_GET_CLASS = "SELECT * FROM classification WHERE id = ? ";

    private static final String SQL_DELETE_CLASS = "DELETE FROM classification WHERE id = ? ";

    private static final String SQL_UPDATE_CLASS = "UPDATE classification " +
                            "SET name = ?, parent = ? WHERE id = ? ";

    private static final String SQL_ADD_CLASS = "INSERT INTO classification " +
                            "VALUES(seq_classification.nextval, ?, ?)";

    private static final String SQL_CLASSES_HIERARCHY_BY_ID = "SELECT * FROM classification " +
                                                    "START WITH id = ? " +
                                                    "CONNECT BY PRIOR id = parent";

    private static final String SQL_CLASSES_HIERARCHY = "SELECT * FROM classification " +
                                                    "START WITH parent IS NULL " +
                                                    "CONNECT BY PRIOR id = parent";

    private static final String SQL_CHECK_CLASS_CHILDREN = "SELECT class FROM living_entity " +
                                        "WHERE class = ?";

    private static final String SQL_CHECK_CLASS_EXIST = "SELECT * FROM classification WHERE id = ?";

    private static final String SQL_CLASS_CURR_ID = "SELECT seq_classification.currval FROM dual";

    private static final String EXCEPTION_PARENT_CLASS_DELETE = "You are trying to delete a class " +
                                                    "that is referenced in the living entity table";

    private static final String EXCEPTION_NO_REFERENCED_CLASS = "There is no class record with such id";

    private EntityContext context;
    private DataSource ds;

    private String name;
    private Integer parentId;
    private int id;

    public BioClassBean()
    {
        try {
            ds = (DataSource)new InitialContext().lookup("java:/BiologySystemDS");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer ejbCreate(String name, Integer parent) throws CreateException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement pstCheck = null;
        ResultSet rsCheck = null;
        Statement st = null;
        ResultSet rs = null;
        Integer primaryKey = null;

        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_ADD_CLASS);
            pst.setString(1, name);
            if (parent == null) {
                pst.setNull(2, Types.NULL);
            } else {
                pstCheck = conn.prepareStatement(SQL_CHECK_CLASS_EXIST);
                pstCheck.setInt(1, parent);
                rsCheck = pstCheck.executeQuery();
                if (!rsCheck.next())
                    throw new CreateException(EXCEPTION_NO_REFERENCED_CLASS);
                pst.setInt(2, parent);
            }
            //pst.executeUpdate();
            log.info("Add Alive: " + pst.executeUpdate());
            st = conn.createStatement();
            rs = st.executeQuery(SQL_CLASS_CURR_ID);
            rs.next();
            primaryKey = rs.getInt(1);
            setStateChanged(false);
        } catch (SQLException e) {
            throw new CreateException(e.getMessage());
        } finally {
            try {
                closeAll(conn, pst, null);
                closeAll(null, pstCheck, rsCheck);
                closeAll(null, st, rs);
            } catch (SQLException e) {
                throw new EJBException(e.getMessage());
            }
        }

        return primaryKey;
    }

    public void ejbPostCreate(String name, Integer parent) throws CreateException { }

    public Integer ejbFindByPrimaryKey(Integer id) throws FinderException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Integer primaryKey = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_GET_CLASS);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            primaryKey = handleClassesResultSet(rs).get(0);
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
        PreparedStatement pst = null;
        List<Integer> list = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_GET_ALL_CLASSES);
            rs = pst.executeQuery();
            list = handleClassesResultSet(rs);
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
            pst = conn.prepareStatement(temp = SQL_GET_ALL_CLASSES +
                    makeConstraint(ordercol, constraints));
            log.info("getAllClasses statement: " + temp);
            rs = pst.executeQuery();
            list = handleClassesResultSet(rs);
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

    public Collection<Integer> ejbFindAllHierarchy() throws FinderException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        List<Integer> list = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_CLASSES_HIERARCHY);
            rs = pst.executeQuery();
            list = handleClassesResultSet(rs);
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

    public Collection<Integer> ejbFindHierarchy(Integer parent) throws FinderException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        List<Integer> list = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_CLASSES_HIERARCHY_BY_ID);
            pst.setInt(1, parent);
            rs = pst.executeQuery();
            list = handleClassesResultSet(rs);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setStateChanged(true);
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
        setStateChanged(true);
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException
    {
        this.id = (Integer)context.getPrimaryKey();

    }

    @Override
    public void ejbLoad() throws EJBException, RemoteException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_GET_CLASS);
            pst.setInt(1, this.id);
            rs = pst.executeQuery();
            rs.next();
            this.id = rs.getInt("id");
            this.name = rs.getString("name");
            this.parentId = rs.getInt("parent");
            if (parentId == 0)
                parentId = null;
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
    public void ejbPassivate() throws EJBException, RemoteException { }

    @Override
    public void ejbRemove() throws RemoveException, EJBException, RemoteException
    {
        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement checkSt = null;
        ResultSet checkRs = null;

        try {
            conn = ds.getConnection();
            checkSt = conn.prepareStatement(SQL_CHECK_CLASS_CHILDREN);
            checkSt.setInt(1, this.id);
            checkRs = checkSt.executeQuery();
            if (checkRs.next())
                throw new RemoveException(EXCEPTION_PARENT_CLASS_DELETE);
            pst = conn.prepareStatement(SQL_DELETE_CLASS);
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

    @Override
    public void ejbStore() throws EJBException, RemoteException
    {
        if (!isStateChanged())
            return;
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = ds.getConnection();
            pst = conn.prepareStatement(SQL_UPDATE_CLASS);
            pst.setString(1, this.name);
            if (parentId == null)
                pst.setNull(2, Types.NULL);
            else
                pst.setInt(2, this.parentId);
            pst.setInt(3, this.id);
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

    /**
     * Used to handle <tt>BioClass</tt> result set
     * @param rs <tt>ResultSet</tt> to handle
     * @return list of <tt>BioClass</tt> objects
     * @throws SQLException
     */
    private List<Integer> handleClassesResultSet(ResultSet rs) throws SQLException {
        List<Integer> list = new ArrayList<Integer>();
        while(rs.next()) {
            list.add(rs.getInt("id"));
        }
        return list;
    }
}
