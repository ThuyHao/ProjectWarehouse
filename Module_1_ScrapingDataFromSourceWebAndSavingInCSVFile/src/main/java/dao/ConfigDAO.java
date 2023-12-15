package dao;

import context.DBContext;
import model.Configs;
import org.jdbi.v3.core.Handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigDAO {

    /**
     * Select the data ID field and format field, then use a Map to set ID as the key and format as the value.
     * Retrieve the value using soft code.
     *
     * @return a Map<Integer, String> containing the retrieved value
     */

    public Map<Integer, String> loadConfigs() {
        try (Handle handle = DBContext.me().open()) {
            String query = "SELECT id, format FROM configs";

            List<Configs> configList = handle.createQuery(query)
                    .mapToBean(Configs.class)
                    .list();

            Map<Integer, String> configMap = new HashMap<>();
            for (Configs config : configList) {
                configMap.put(config.getId(), config.getFormat());
            }

            return configMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getValueById(Map<Integer, String> configMap, int id) {
        return configMap.get(id);
    }
}
