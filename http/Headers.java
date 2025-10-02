package http;

import java.util.TreeMap;

public class Headers extends TreeMap<String, String> {
  public Headers() {
    super(String.CASE_INSENSITIVE_ORDER);
  }

  public void add(String k, String v) {
    put(k.trim(), v.trim());
  }

  public int getInt(String name, int def) {
    String v = get(name);
    if (v == null)
      return def;
    try {
      return Integer.parseInt(v);
    } catch (NumberFormatException e) {
      return def;
    }
  }
}
