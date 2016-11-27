# KAConf

KickAss Configuration system

v0.8.0

[2016 Mario Macías](http://github.com/mariomac)

[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

## About KAConf

_KickAss_ is an Annotation-based configuration system
inspired in the wonderful [Spring Boot](http://spring.io).

Quick demonstration of usage:

* The `@Property` annotation allows you to define any field that recevies
  its value from a configuration source, whatever its visibility is.

        public class DbManager {
            @Property("db.username")
            private String user;
            
            @Property("db.password")
            private String password;
                  
            // ...
        }
    
* You can even define `final` and `static` fields, with default values.
  Properties that are both `final static` require to use the `KA.def`
  or `KA.a[Type]` helper methods.
  
        import static info.macias.kaconf.KA.*
        
        public class Constants {
            @Property("timeout")
            public static final long TIMEOUT_MS = def(1000); // default=1000
            
            @Property("security.enabled")
            public static final boolean SECURITY_ENABLED = aBoolean();
        }

* The `Configurator.configure` method will automatically set the values
  from its configuration sources. You can build a `Configurator` object
  with multiple sources and different priorities.

        public class SomeController {
            private DbManager dbm;
            
            public void start() {
                Configurator conf = new ConfiguratorBuilder()
                    .addSource(System.getenv()) // most priority
                    .addSource(System.getProperties())
                    .addSource(new JavaUtilPropertySource("app.properties"))
                    .addSource(new JavaUtilPropertySource( // least priority
                        getClass().getResourceAsStream("defaults.properties")
                    )).build():
                    
                conf.configure(Constants.class);
                conf.configure(dbm);
            }
        }
    
* It's easy to hardcode configuration for testing purposes.
   
        public class TestSuite {

            DbManager dbm = new DbManager();

            public void setUp() {
                 
                Map<String,String> customProperties = new HashMap<>();
                customProperties.put("db.username","admin");
                customProperties.put("db.password","1234");
                customProperties.put("security.enabled", "false");
                
                Configurator conf = new ConfiguratorBuilder()
                    .addSource(customProperties)
                    .addSource(new JavaUtilPropertySource(
                        getClass().getResourceAsStream("defaults.properties")
                    )).build():
                    
                conf.configure(Constants.class);                    
                conf.configure(dbm);
            }
        }     

## Building and using a `Configurator` object

The `ConfiguratorBuilder` class allows building a `Configurator` object.
The `ConfiguratorBuilder.addSource` method sets the different sources of
properties (`PropertySource` interface). The `PropertySource` with most
priority is the first instance passed as argument to the `addSource`
method, and the `PropertySource` with least preference is the object passed
to the last `addSource` invocation.

Example of usage:

    Configurator conf = new ConfiguratorBuilder()
        .addSource(System.getenv()) // most priority
        .addSource(System.getProperties())
        .addSource(new JavaUtilPropertySource("app.properties"))
        .addSource(new JavaUtilPropertySource( // least priority
            getClass().getResourceAsStream("defaults.properties")
        )).build():

The `addSource` method accepts the next types as argument:

* `java.util.Map<String,?>`
* `java.util.Properties`
* Any implementing class of the `PropertySource` interface. KAConf bundles
  two helper implementations:
    - `JavaUtilPropertySource`
    - `MapPropertySource` 
    
Once a `Configurator` object is built, you can pass the configurable object
(if object/class properties must be set) or class (if only static fields are
willing to be set).

    conf.configure(object);
    conf.configure(Constants.class);
    
## Default Configurator behaviour

Given the next example properties:

        some.value=1234
        some.other.value=yes

* *Numbers*: any property that parses into a number is valid. If not,
  the `Configurator.configure` will throw a `ConfiguratorException`:
   
        @Property("some.value")
        private int someValue;       // correct
        
        @Property("some.other.value")
        private int someOtherValue;  // throws ConfiguratorException
  
  If the property to look is not on the properties sources, the value
  will remain as 0, or as the default one.
  
        @Property("value.not.found")
        private int value1;           // will be 0
    
        @Property("value.not.found")
        private int value2 = def(1000); // will be 1000 (default)
        
        //default valid for non-final & static primitive fields
        @Property("value.not.found")
        private int value3 = 1000;    // will be 1000 (default)

* *Strings*: any property is valid. If the property is not found, the
  value will be `null` or the default one.
  
        @Property("some.value")
        private String someValue;        // value -> "1234"
        
        @Property("some.other.value")
        private String someOtherValue;   // value -> "yes"
  
        @Property("value.not.found")
        private String value1;           // value -> null
    
        @Property("value.not.found")
        private String value2 = def(""); // value -> empty, non-null String
        
        //default valid for non-final & static primitive fields
        @Property("value.not.found")
        private String value3 = "";      // value -> empty, non-null String
    
* *Booleans*: any property whose string value exists and is `true`, `1`
  or `yes` will be set as `true`. Otherwise will be `false`.

        @Property("some.value")
        private boolean someValue;       // value -> false
        
        @Property("some.other.value")
        private boolean someOtherValue;  // value -> true
        
        @Property("value.not.found")
        private boolean value1;          // value -> null
        
* *Chars*: the value of the property will be the first character of a string.
  Any non-found property will set the value to '\0' or the default one.
        
* *Boxed primitive types*: boxed primitive types will behave as their
  unboxed equivalents, but properties that are not found will get the
  `null` default value.
  
        @Property("some.value")
        private Integer intValue;     // value --> 1234

        @Property("not.found.value")
        private Integer nullableInt;  // value --> null

## Inherited fields

KAConf allows setting properties that are annotated in the superclass of the
configurable object or class. For example:

    public class Animal {
        @Property("animal.name")
        private final String name;
    }
    public class Dog extends Animal {
        @Property("animal.species")
        private final String species;
    }
    
    public class PetShop {
        Configurator conf = ...
        public Animal buy() {
            Dog puppy = new Dog();
            conf.configure(puppy);
            return puppy;
        }
    }
    

## Adding custom Property Sources

Adding new Property Sources is simple. Usually is enough to extending the
`AbstractPropertySource` class and implementing only two abstract methods:

    protected String get(String name);
    
Which returns the string value of the property named according to the `name`
argument.

    boolean isAvailable();

Which returns `true` if the properties have been successfully read from the
source (e.g. a file or DB).

### PropertySources failing to load

Any implementation of `PropertySource` is expected to fail silently (e.g. if
it tries to read the values from a file that is not accessible), and then
return `false` in the `isAvailable` method.

## `Static final` fields

Because of the way the Java compiler inlines the `static final` fields of
primitive types, it is
necessary to assign the result of any method call to the declaration of the
field. The `KA` class provides some simple functions to allow that. For example:

    @Property("some.property")
    public static final int SOME_PROPERTY = KA.def(1234) // default value
    
    @Property("other.property")
    protected static final byte OTHER_PROPERTY = KA.aByte(); //defaults to 0

## Kotlin basic types support

As my favourite programming language, [Kotlin](https://kotlinlang.org/) is a first-class citizen in KAConf, and
it is fully supported out of the box.

    class KonfigurableClass {
        @Property("publicint")
        var publicint = KA.def(321)
    
        @Property("finalchar")
        val finalchar = KA.def('a')
    
        companion object {
            @Property("finalstaticint")
            val finalstaticint: Int = 0
        }
    }
    
    
    object KonfigurableObject {
        @Property("aboolean")
        val aboolean = KA.aBoolean()
    
        @Property("anint")
        var anint: Int? = null
    }


Other JVM languages (Scala, Groovy, Ceylon...) have not been tested. ¿Maybe you
can test them for me and tell us the results? :wink:

## Next steps

There are still some potential improvements of interest in KAConf.

### To implement in v0.8.1

* Some refactoring of the `Configurator.configure` code to be less _spaghetti_ and
  more efficient
  
### To implement in v0.9

* Arrays of basic types and strings
* Analyse `Property` usages in compile time to warn the user about potential
  issues (e.g. annotating a `final static` primitive value without using any
  helper method from the `KA` class);
* Specify mandatory properties (`Configurator` will throw and exception if
  the property is not found)
