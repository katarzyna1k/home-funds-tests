package cotext;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private Map<String, Object> contextMap;

    public ScenarioContext() {
        this.contextMap = new HashMap<>();
    }

    public void set(String key, Object value) {
        contextMap.put(key, value);
    }

    public Object get(String key) {
        return contextMap.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(contextMap.get(key));
    }

    public boolean contains(String key) {
        return contextMap.containsKey(key);
    }
}
