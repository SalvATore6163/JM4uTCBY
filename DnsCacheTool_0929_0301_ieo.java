// 代码生成时间: 2025-09-29 03:01:24
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DnsCacheTool {

    private static final Map<String, String> cache = new ConcurrentHashMap<>();

    /**
     * Resolves a domain to an IP address and caches the result.
     *
     * @param domain The domain to resolve.
     * @return The IP address of the domain or null if an error occurs.
     */

    public static String resolveDomain(String domain) {

        // Check if the domain is already in the cache

        if (cache.containsKey(domain)) {

            return cache.get(domain);

        }

        try {

            // Perform DNS resolution

            String ip = InetAddress.getByName(domain).getHostAddress();

            // Cache the result

            cache.put(domain, ip);

            return ip;

        } catch (UnknownHostException e) {

            // Handle the error

            System.err.println("Error resolving domain: " + domain);
            return null;
        }
    }

    public static void main(String[] args) {

        // Example usage of the DNS resolution and caching tool

        String domain = "example.com";
        String ip = resolveDomain(domain);

        if (ip != null) {
            System.out.println("Resolved IP for domain: " + domain + " is " + ip);
        } else {
            System.out.println("Failed to resolve domain: " + domain);
        }
    }
}
