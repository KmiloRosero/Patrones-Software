import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TemplateRepository {
    private final Map<String, Contract> templates = new HashMap<>();

    public void registerTemplate(String code, Contract baseContract) {
        templates.put(code, baseContract);
    }

    public Contract cloneTemplate(String code) {
        Contract template = templates.get(code);
        if (template == null) {
            throw new IllegalArgumentException("No template found for code: " + code);
        }
        return template.cloneContract();
    }

    public Set<String> availableCodes() {
        return new HashSet<>(templates.keySet());
    }
}
