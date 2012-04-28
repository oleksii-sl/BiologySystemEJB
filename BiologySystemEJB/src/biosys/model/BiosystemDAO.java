package biosys.model;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Alexey
 * Model interface
 */
public interface BiosystemDAO {

    /**
     * Method used to get all <tt>Alive</tt> objects
     * @return list of <tt>Alive</tt> objects
     * @throws SQLException
     */
    List<Alive> getAllAlive() throws BiologySystemException;

    /**
     * Used to get <tt>Alive</tt> with specified id
     * @param id
     * @return Alive object
     * @throws SQLException
     */
    Alive getAlive(int id) throws BiologySystemException;

    /**
     * Used to add new <tt>Alive</tt> to the model
     * @param bio
     * @throws SQLException
     */
    void addAlive(Alive Alive) throws BiologySystemException;

    /**
     * Used to update existing <tt>Alive</tt>
     * @param bio
     * @throws SQLException
     */
    void updateAlive(Alive Alive) throws BiologySystemException;

    /**
     * Used to remove <tt>Alive</tt> from model by specified id
     * @param id
     * @throws SQLException
     */
    void removeAlive(int id) throws BiologySystemException;

    /**
     * Method used to get all Alive objects with specified constraint
     * @param ordercol - column to order
     * @param constraints - list of constraints with specified format for substring and between expressions.
     * 		"substr: {column name} {substring to search}" or "between: {column name} {min number} {max number}"
     * @return list of <tt>Alive</tt> objects
     * @throws SQLException
     */
    List<Alive> getAllAliveConstraint(String ordercol, List<String> constraints)
            throws BiologySystemException;

    /**
     * Method used to get all <tt>BioClass</tt> objects
     * @return list of <tt>BioClass</tt> objects
     * @throws SQLException
     */
    List<BioClass> getAllClasses() throws BiologySystemException;

    /**
     * Used to get <tt>BioClass</tt> with specified id
     * @param id
     * @return BioClass object
     * @throws SQLException
     */
    BioClass getBioClass(int id) throws BiologySystemException;

    /**
     * Used to add new <tt>BioClass</tt> to the model
     * @param bio
     * @throws SQLException
     */
    void addBioClass(BioClass bio) throws BiologySystemException;

    /**
     * Used to update existing <tt>BioClass</tt>
     * @param bio
     * @throws SQLException
     */
    void updateBioClass(BioClass bio) throws BiologySystemException;

    /**
     * Used to remove <tt>BioClass</tt> from model by specified id
     * @param id
     * @throws SQLException
     */
    void removeBioClass(int id) throws BiologySystemException;

    /**
     *
     * Method used to get all <tt>BioClass</tt> objects with specified constraint
     * @param ordercol - column to order
     * @param constraints - list of constraints with specified format for substring and between expressions.
     * 		"substr: {column name} {substring to search}" or "between: {column name} {min number} {max number}"
     * @return list of <tt>BioClass</tt> objects
     * @throws SQLException
     */
    List<BioClass> getAllClassesConstraint(String ordercol, List<String> constraints)
            throws BiologySystemException;

    /**
     * Used to get hierarchy of classes starting from specified id
     * @param id id to start
     * @return resulting list of <tt>BioClass</tt>
     * @throws SQLException
     */
    List<BioClass> getClassesHierarchy(int id) throws BiologySystemException;

    /**
     * Used to get full hierarchy of classes
     * @return resulting hierarchy list
     * @throws SQLException
     */
    List<BioClass> getAllClassesHierarchy() throws BiologySystemException;

}
