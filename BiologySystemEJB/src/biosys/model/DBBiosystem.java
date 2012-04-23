package biosys.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * @author Алексей
 * Model instance in MVC paradigm
 */
public class DBBiosystem implements BiosystemDAO {

    private static final Logger log = Logger.getLogger(DBBiosystem.class);

    private static final String SQL_GET_ALL_ALIVE = "SELECT * FROM living_entity";
    private static final String SQL_GET_ALL_CLASSES = "SELECT * FROM classification";
    private static final String SQL_GET_ALIVE = "SELECT * FROM living_entity WHERE id = ? ";
    private static final String SQL_GET_CLASS = "SELECT * FROM classification WHERE id = ? ";
    private static final String SQL_DELETE_ALIVE = "DELETE FROM living_entity WHERE id = ? ";
    private static final String SQL_DELETE_CLASS = "DELETE FROM classification WHERE id = ? ";

    private static final String SQL_UPDATE_ALIVE = "UPDATE living_entity " +
                            "SET name = ?, name_latin = ?, lifespan = ?, avg_weight = ?, " +
                            "native_range = ?,  population = ?, class = ? WHERE id = ? ";

    private static final String SQL_UPDATE_CLASS = "UPDATE classification " +
                            "SET name = ?, parent = ? WHERE id = ? ";

    private static final String SQL_ADD_ALIVE = "INSERT INTO living_entity " +
                            "VALUES(seq_living_entity.nextval, ?, ?, ?, ?, ?, ?, ?)";

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

    private static final String REG_EXP_SUBSTRING = "substr: (\\w|-|_)+ (\\w|-|_)+";

    private static final String REG_EXP_BETWEEN = "between: (\\w|-|_)+ \\d+(.\\d+)? \\d+(.\\d+)?";

    private static final String EXCEPTION_PARENT_CLASS_DELETE = "You are trying to delete a class " +
                                                    "that is referenced in the living entity table";
    private static final String EXCEPTION_NO_REFERENCED_CLASS = "There is no class record with such id";


    private DataSource ds;

    public DBBiosystem(DataSource ds) {
        this.ds = ds;
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#getAllAlive()
     */
    @Override
    public List<Alive> getAllAlive() throws SQLException {
        Connection conn = ds.getConnection();
        Statement st = null;
        List<Alive> list = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(SQL_GET_ALL_ALIVE);
            list = handleAliveResultSet(rs);
        } finally {
            closeAll(conn, st, rs);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#getAlive(int)
     */
    @Override
    public Alive getAlive(int id) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        Alive alive = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(SQL_GET_ALIVE);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            alive = handleAliveResultSet(rs).get(0);
        } finally {
            closeAll(conn, pst, rs);
        }
        return alive;
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#addAlive(biosys.model.Alive)
     */
    @Override
    public void addAlive(Alive alive) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(SQL_ADD_ALIVE);
            pst.setString(1, alive.getName());
            pst.setString(2, alive.getNameLatin());
            pst.setInt(3, alive.getLifespan());
            pst.setDouble(4, alive.getAvgWeight());
            pst.setString(5, alive.getNativeRange());
            pst.setLong(6, alive.getPopulation());
            pst.setInt(7, alive.getBioClass());

            log.info("Add Alive: " + pst.executeUpdate());
        } finally {
            closeAll(conn, pst, null);
        }
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#updateAlive(biosys.model.Alive)
     */
    @Override
    public void updateAlive(Alive alive) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(SQL_UPDATE_ALIVE);
            pst.setString(1, alive.getName());
            pst.setString(2, alive.getNameLatin());
            pst.setInt(3, alive.getLifespan());
            pst.setDouble(4, alive.getAvgWeight());
            pst.setString(5, alive.getNativeRange());
            pst.setLong(6, alive.getPopulation());
            pst.setInt(7, alive.getBioClass());
            pst.setInt(8, alive.getId());

            log.info("Update Alive: " + pst.executeUpdate());
        } finally {
            closeAll(conn, pst, null);
        }
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#removeAlive(int)
     */
    @Override
    public void removeAlive(int id) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(SQL_DELETE_ALIVE);
            pst.setInt(1, id);
            pst.executeUpdate();
        } finally {
            closeAll(conn, pst, null);
        }
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#getAllAliveConstraint(java.lang.String, java.util.List)
     */
    public List<Alive> getAllAliveConstraint(String ordercol, List<String> constraints) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        List<Alive> list = null;
        ResultSet rs = null;
        String temp;
        try {
            pst = conn.prepareStatement(temp = SQL_GET_ALL_ALIVE + makeConstraint(ordercol, constraints));
            log.info("getAllAlive statement: " + temp);
            rs = pst.executeQuery();
            list = handleAliveResultSet(rs);
        } finally {
            closeAll(conn, pst, rs);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#getAllClasses()
     */
    @Override
    public List<BioClass> getAllClasses() throws SQLException {
        Connection conn = ds.getConnection();
        Statement st = null;
        List<BioClass> list = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(SQL_GET_ALL_CLASSES);
            list = handleClassesResultSet(rs);
        } finally {
            closeAll(conn, st, rs);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#getBioClass(int)
     */
    @Override
    public BioClass getBioClass(int id) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        BioClass bioClass = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(SQL_GET_CLASS);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            bioClass = handleClassesResultSet(rs).get(0);
        } finally {
            closeAll(conn, pst, rs);
        }
        return bioClass;
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#addBioClass(biosys.model.BioClass)
     */
    @Override
    public void addBioClass(BioClass bio) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        PreparedStatement pstCheck = null;
        ResultSet rsCheck = null;
        try {
            pst = conn.prepareStatement(SQL_ADD_CLASS);
            pst.setString(1, bio.getName());
            if (bio.getParentId() == null) {
                pst.setNull(2, Types.NULL);
            } else {
                pstCheck = conn.prepareStatement(SQL_CHECK_CLASS_EXIST);
                pstCheck.setInt(1, bio.getParentId());
                rsCheck = pstCheck.executeQuery();
                if (!rsCheck.next())
                    throw new NoSuchClassException(EXCEPTION_NO_REFERENCED_CLASS);
                pst.setInt(2, bio.getParentId());
            }
            pst.executeUpdate();
        } finally {
            closeAll(conn, pst, null);
        }
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#updateBioClass(biosys.model.BioClass)
     */
    @Override
    public void updateBioClass(BioClass bio) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(SQL_UPDATE_CLASS);
            pst.setString(1, bio.getName());
            if (bio.getParentId() == null)
                pst.setNull(2, Types.NULL);
            else
                pst.setNull(2, bio.getParentId());
            pst.setInt(3, bio.getId());
            pst.executeUpdate();
        } finally {
            closeAll(conn, pst, null);
        }
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#removeBioClass(int)
     */
    @Override
    public void removeBioClass(int id) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        PreparedStatement checkSt = null;
        ResultSet checkRs = null;
        try {
            checkSt = conn.prepareStatement(SQL_CHECK_CLASS_CHILDREN);
            checkSt.setInt(1, id);
            checkRs = checkSt.executeQuery();
            if (checkRs.next())
                throw new ParentClassDeleteException(EXCEPTION_PARENT_CLASS_DELETE);
            pst = conn.prepareStatement(SQL_DELETE_CLASS);
            pst.setInt(1, id);
            pst.executeUpdate();
        } finally {
            try {
                closeAll(conn, pst, checkRs);
            } finally {
                if (checkSt != null)
                    checkSt.close();
            }
        }
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#getAllClassesConstraint(java.lang.String, java.util.List)
     */
    @Override
    public List<BioClass> getAllClassesConstraint(String ordercol, List<String> constraints)
            throws SQLException {

        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        List<BioClass> list = null;
        ResultSet rs = null;
        String temp;
        try {
            pst = conn.prepareStatement(temp = SQL_GET_ALL_CLASSES +
                    makeConstraint(ordercol, constraints));
            log.info("getAllClasses statement: " + temp);
            rs = pst.executeQuery();
            list = handleClassesResultSet(rs);
        } finally {
            closeAll(conn, pst, rs);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#getClassesHierarchy(int)
     */
    @Override
    public List<BioClass> getClassesHierarchy(int id) throws SQLException {
        Connection conn = ds.getConnection();
        PreparedStatement pst = null;
        List<BioClass> list = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(SQL_CLASSES_HIERARCHY_BY_ID);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            list = handleClassesResultSet(rs);
        } finally {
            closeAll(conn, pst, rs);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see biosys.model.BiosystemDAO#getAllClassesHierarchy()
     */
    @Override
    public List<BioClass> getAllClassesHierarchy() throws SQLException {
        Connection conn = ds.getConnection();
        Statement st = null;
        List<BioClass> list = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(SQL_CLASSES_HIERARCHY);
            list = handleClassesResultSet(rs);
        } finally {
            closeAll(conn, st, rs);
        }
        return list;
    }

    /**
     * Used to make constraint part of SQL query
     * @param ordercol column to order by
     * @param constraints list of constraints with specified format for substring and between expressions.
     * 		"substr: {column name} {substring to search}" or "between: {column name} {min number} {max number}"
     * @return
     */
    private String makeConstraint(String ordercol, List<String> constraints) {
        StringBuilder constr = new StringBuilder();
        boolean first = true;
        if (constraints != null && !constraints.isEmpty()) {
            constr.append(" WHERE ");
            for (String s : constraints) {
                if (s.matches(REG_EXP_SUBSTRING)) {
                    if (!first)
                        constr.append(" AND ");
                    constr.append(sqlSubstringExpression(s));
                } else if (s.matches(REG_EXP_BETWEEN)) {
                    if (!first)
                        constr.append(" AND ");
                    constr.append(sqlBetweenExpression(s));
                } else {
                    throw new RuntimeException("Input constraints was wrong!");
                }
                first = false;
            }
        }
        if (ordercol != null)
            constr.append(" ORDER BY ").append(ordercol);

        return constr.toString();
    }

    /**
     * Used to convert string which matches to regular expression to SQL expression.
     * @param expression string to convert
     * @return converted string
     */
    private String sqlSubstringExpression(String expression) {
        StringBuilder builder = new StringBuilder(" instr(");
        String[] split = expression.split("\\s+");
        builder.append("lower(").append(split[1]).append("), ");
        builder.append("lower('").append(split[2]).append("')) > 0 ");

        return builder.toString();
    }

    /**
     * Used to convert string which matches to regular expression to SQL expression.
     * @param expression string to convert
     * @return converted string
     */
    private String sqlBetweenExpression(String expression) {
        StringBuilder builder = new StringBuilder(" ");
        String[] split = expression.split("\\s+");
        builder.append(split[1]).append(" BETWEEN ").append(split[2]);
        builder.append(" AND ").append(split[3]);

        return builder.toString();
    }

    /**
     * Used to handle <tt>Alive</tt> result set
     * @param rs <tt>ResultSet</tt> to handle
     * @return list of <tt>Alive</tt> objects
     * @throws SQLException
     */
    private List<Alive> handleAliveResultSet(ResultSet rs) throws SQLException {
        List<Alive> list = new ArrayList<Alive>();
        while(rs.next()) {
            Alive alive = new Alive();
            alive.setId(rs.getInt("id"));
            alive.setName(rs.getString("name"));
            alive.setNameLatin(rs.getString("name_latin"));
            alive.setLifespan(rs.getInt("lifespan"));
            alive.setAvgWeight(rs.getDouble("avg_weight"));
            alive.setNativeRange(rs.getString("native_range"));
            alive.setPopulation(rs.getLong("population"));
            alive.setBioClass(rs.getInt("class"));
            list.add(alive);
        }
        return list;
    }

    /**
     * Used to handle <tt>BioClass</tt> result set
     * @param rs <tt>ResultSet</tt> to handle
     * @return list of <tt>BioClass</tt> objects
     * @throws SQLException
     */
    private List<BioClass> handleClassesResultSet(ResultSet rs) throws SQLException {
        List<BioClass> list = new ArrayList<BioClass>();
        while(rs.next()) {
            BioClass temp = new BioClass();
            temp.setId(rs.getInt("id"));
            temp.setName(rs.getString("name"));
            if (rs.getInt("parent") == 0)
                temp.setParentId(null);
            else
                temp.setParentId(rs.getInt("parent"));
            list.add(temp);
        }
        return list;
    }

    /**
     * Used to close open sources
     * @param conn <tt>Connection</tt> to close
     * @param st <tt>Statement</tt> to close
     * @param rs <tt>ResultSet</tt> to close
     * @throws SQLException
     */
    private void closeAll(Connection conn, Statement st, ResultSet rs) throws SQLException {
        try {
            if (conn != null)
                conn.close();
        } finally {
            try {
                if (st != null)
                    st.close();
            } finally {
                if (rs != null)
                    rs.close();
            }
        }

    }
}
