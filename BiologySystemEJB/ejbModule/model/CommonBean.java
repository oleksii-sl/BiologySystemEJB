package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.ejb.EJBException;


/**
 * Class contains common methods for all beans
 * @author Alexey
 *
 */
public abstract class CommonBean
{
    private static final String REG_EXP_SUBSTRING = "substr: (\\w|-|_)+ (\\w|-|_)+";

    private static final String REG_EXP_BETWEEN = "between: (\\w|-|_)+ \\d+(.\\d+)? \\d+(.\\d+)?";

    /**
     * Used to make constraint part of SQL query
     * @param ordercol column to order by
     * @param constraints list of constraints with specified format for substring and between expressions.
     * 		"substr: {column name} {substring to search}" or "between: {column name} {min number} {max number}"
     * @return
     */
    protected String makeConstraint(String ordercol, List<String> constraints) throws EJBException {
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
                    throw new EJBException("Input constraints was wrong!");
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
    protected String sqlSubstringExpression(String expression) {
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
    protected String sqlBetweenExpression(String expression) {
        StringBuilder builder = new StringBuilder(" ");
        String[] split = expression.split("\\s+");
        builder.append(split[1]).append(" BETWEEN ").append(split[2]);
        builder.append(" AND ").append(split[3]);

        return builder.toString();
    }

    /**
     * Used to close open sources
     * @param conn <tt>Connection</tt> to close
     * @param st <tt>Statement</tt> to close
     * @param rs <tt>ResultSet</tt> to close
     * @throws SQLException
     */
    protected void closeAll(Connection conn, Statement st, ResultSet rs) throws SQLException {
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
