# PickledEggplant

There are many things still to be done in this repository.
Classes will be moved back and forth. This is still not a suitable
version of the code.

## Running

### From command line
```shell
java com.olenickglobal.Main -DSTATIC_CONFIG_FILE=/some/path/config.json -DRUNTIME_CONFIG_FILE=/some/path/runtime.json
```
_Note: The default `config.json` and `runtime.json` files are currently looked up on the classpath._

### From TestNG runner in your IDE (tested on IntelliJ)
Just try running class `com.olenickglobal.TestNGCucumberTests`.

### From Cucumber runner in your IDE (tested on IntelliJ)
Either add the following VM options:
```shell
-Dcucumber.object-factory=com.olenickglobal.configuration.SUTAwarePicoFactory
-Dcucumber.plugin=com.olenickglobal.ListenerManager
```

Or add these extra program parameters before the name of the test to be run:
```shell
--object-factory com.olenickglobal.configuration.SUTAwarePicoFactory
--plugin com.olenickglobal.ListenerManager
```
