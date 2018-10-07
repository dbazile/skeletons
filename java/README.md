# myproject

## Running

    gradle run
    
-OR- 
    
    gradle bundle && java -jar build/libs/myproject-bundle-*.jar

### Configuration

Point to a config overrides file:

    java \
        -Dconfig_path=/path/to/config \
        -jar build/libs/myproject-bundle-*.jar

Override one or more individual properties:

    java \
        -D'myproject.greeting=how do you do' \
        -jar build/libs/myproject-bundle-*.jar


## Testing

    gradle test


## Packaging for Deployment

    gradle assemble  # for a normal Gradle application distribution
    gradle bundle    # for a single JAR with all dependencies
