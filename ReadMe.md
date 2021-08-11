### DCrawler
DCrawler is a flexable crawler framework implement in java,make you crawler any url in one line.
```java
new CrawlerBooter().seed("https://www.pexels.com")
    .visitable(new JsoupVisitable())
    .threadCount(String.valueOf(3))
    .run();
```
**structure:**

four model:

1. URLPOOL:accept and manager urls
2. FETCHING:query http data from giving url
3. RESOLVING:resolve fetched document to specified arch
4. VISITER:define how to proc document

each model defined a interface,support you to define your own implementation.inner default implement is use jsoup.
