# myproject

## Running

    gradle run
    

### Configuration

Point to a config overrides file:

	gradle clean install

	MYPROJECT_OPTS=-Dconfig_path=/path/to/config \
		build/install/myproject/bin/myproject

Override one or more individual properties:

    gradle clean install
    
    MYPROJECT_OPTS="-D'myproject.greeting=how do you do'" \
        build/install/myproject/bin/myproject


## Testing

    gradle test


## Packaging for Deployment

    gradle assemble
