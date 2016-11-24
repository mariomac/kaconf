# KAConf

KickAss Configuration system

[2016 Mario Macías](http://github.com/mariomac)

## About Kaconf

KickAss Configuration system is an Annotation-based configuration system
inspired in the wonderful [Spring Boot](http://spring.io) configuration
system.

Quick demonstration of usage:

* The `@Property` annotation allows you to define any field, whatever
  its visibility is

        public class DbManager {
            @Property("db.username")
            private String user;
            
            @Property("db.password")
            private String password;
                  
            // ...
        }
    
* You can even define `final` and `static` fields, with default values.
  Properties that are both `final static` require to use the `Value.def`
  or `Value.a[Type]` helper methods
  
        import static info.macias.kaconf.Value.*
        
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

    

## Default Configurator behaviour

* Numbers
* Strings
* Booleans

## Use builder for custom configurator behaviour

## Inherited fields

## Adding custom PropertySources

### PropertySources failing to load

## Static fields

## Final fields

## Static final fields

    TO DO

## Kotlin basic types support
TO DO?

## Future

* Arrays of basic types and strings