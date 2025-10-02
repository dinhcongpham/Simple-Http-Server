package routing;

import java.util.*;
import java.util.regex.*;

public class PathTemplate {
  private final Pattern pattern;
  private final List<String> names;

  // e.g. "/hello/{name}" or "/assets/{*path}"
  public PathTemplate(String template) {
    names = new ArrayList<>();
    StringBuilder re = new StringBuilder("^");
    for (String part : template.split("/")) {
      if (part.isEmpty())
        continue;
      re.append("/");
      if (part.startsWith("{*") && part.endsWith("}")) { // wildcard tail
        String name = part.substring(2, part.length() - 1);
        names.add(name);
        re.append("(?<").append(name).append(">.+)$");
        pattern = Pattern.compile(re.toString());
        return;
      } else if (part.startsWith("{") && part.endsWith("}")) {
        String name = part.substring(1, part.length() - 1);
        names.add(name);
        re.append("(?<").append(name).append(">[^/]+)");
      } else {
        re.append(Pattern.quote(part));
      }
    }
    re.append("/?$");
    pattern = Pattern.compile(re.toString());
  }

  public Map<String, String> match(String path) {
    Matcher m = pattern.matcher(path);
    if (!m.matches())
      return null;
    Map<String, String> params = new HashMap<>();
    for (String name : names)
      params.put(name, m.group(name));
    return params;
  }
}
