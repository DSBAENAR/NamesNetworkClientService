## Exercise 1

The following code block shows how to create a URL object and print the data returned by its 8 methods:

```java
try {
 URI uri = new URI("https://doodles.google/doodle/nba-playoffs-2025-am");
 URL url = uri.toURL();
 System.out.println("getProtocol: " + url.getProtocol());
 System.out.println("getAuthority: " + url.getAuthority());
 System.out.println("getHost: " + url.getHost());
 System.out.println("getPort: " + url.getPort());
 System.out.println("getPath: " + url.getPath());
 System.out.println("getQuery: " + url.getQuery());
 System.out.println("getFile: " + url.getFile());
 System.out.println("getRef: " + url.getRef());
} catch (Exception e) {
 System.out.println("URL mal formada: " + e.getMessage());
}
```
