package biosys.controller.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represents a factory used to produce different actions
 * @author Алексей
 */
public class ActionFactory {

    /**
     * Maps action name to action Class object
     */
    Map<String, Class<? extends Action>> map;

    public ActionFactory() {
        defaultMap();
    }

    /**
     * Used to get concrete realization of <tt>Action</tt> interface
     * @param actionName string description of action
     * @return
     */
    @SuppressWarnings("unchecked")
    public Action create(String actionName) {
        Class<Action> klass = (Class<Action>) map.get(actionName);
        if (klass == null)
             throw new RuntimeException(getClass() + " was unable to find " +
                 "an action named '" + actionName + "'.");
        Action actionInstance = null;
        try {
            actionInstance = (Action) klass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create an instance of class",e);
        }

        return actionInstance;
    }

    /**
     * Used to fill action map
     */
    private void defaultMap() {
        map = new HashMap<String, Class<? extends Action>>();

        map.put("addAliveAction", AddAliveAction.class);
        map.put("addClassAction", AddClassAction.class);
        map.put("updateAliveAction", UpdateAliveAction.class);
        map.put("updateClassAction", UpdateClassAction.class);
        map.put("deleteAliveAction", DeleteAliveAction.class);
        map.put("deleteClassAction", DeleteClassAction.class);
        map.put("getAliveAction", GetAliveAction.class);
        map.put("getClassAction", GetClassAction.class);
        map.put("getAlivesAction", GetAlivesAction.class);
        map.put("getClassesAction", GetClassesAction.class);
        map.put("getClassesHierarchyAction", GetClassesHierarchyAction.class);
   }

}
