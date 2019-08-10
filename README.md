## Junit JSON parameters extension example

[Extension class](./src/test/java/andkudr/extensions/JsonParameterExtension.java)

## Requirements
**Junit 5+**

**Jackson 2.0** for JSON parsing

## Usage

[Example](./src/test/java/andkudr/JsonParameterExtensionTest.java)

```java

@ExtendWith(JsonParameterExtension.class)
public class JsonParameterExtensionTest {

    @Test
    public void testWithParameter(@JsonParameter(json = "{\"name\": \"TestWithParameter\"}") SimplePojo pojo) {
        assertEquals(pojo.getName(), "TestWithParameter");
    }

    @Test
    public void testWithResource(@JsonResourceParameter(path = "SimplePojo.json") SimplePojo pojo) {
        assertEquals(pojo.getName(), "TestWithResource");
    }

    @Test
    public void multiParamsTest(@JsonParameter(json = "{\"name\": \"TestWithParameter\"}") SimplePojo firstPojo,
                                @JsonResourceParameter(path = "SimplePojo.json") SimplePojo secondPojo) {
        assertEquals(firstPojo.getName(), "TestWithParameter");
        assertEquals(secondPojo.getName(), "TestWithResource");

    }

    @Test
    public void multiParamsTestReverse(@JsonResourceParameter(path = "SimplePojo.json") SimplePojo firstPojo,
                                       @JsonParameter(json = "{\"name\": \"TestWithParameter\"}") SimplePojo secondPojo) {
        assertEquals(firstPojo.getName(), "TestWithResource");
        assertEquals(secondPojo.getName(), "TestWithParameter");
    }

}
```