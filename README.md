# KotlinMppPublicationBuilder

This application was created to decrease routine in process of project creating. Currently this tool can:

* Create `publish.gradle` file with
    * Customizable project name and description
    * Autoloading and filling license info
    * Optional GPG signing
    * Opportunity to automatically include `MavenCentral` (Sonatype) repository as target repo
    * Include different developers with their nicknames and e-mails
* Create configuration file with extension `kpsb` to be able to reconfigure publication in future via this app and simply update after new versions of application will be created

## Launch

`java -jar artifact.jar`

Instead of `artifact.jar` place name of loaded file. Currently in [releases](https://github.com/InsanusMokrassar/KotlinPublicationScriptsBuilder/releases) section there are eachcommit versions at least for linux x64. You may pass path to configuration. In this case launching will looks like `java -jar artifact.jar "path/to/config.kpsb"`

## Building

`./gradlew clean build`

In case you wish to launch: `./gradlew run`. You may pass your configuration name with `./gradlew run --args="path/to/config.kpsb"`. Besides, you may create jar for your OS with `./gradlew packageUberJarForCurrentOS`, but with high probability you will require at least JDK 14 for this operation.

## Output

As an output you will get ready to use `publish.gradle` file with content like in [my other project (link to github file)](https://github.com/InsanusMokrassar/MicroUtils/blob/master/publish.gradle).

## Screenshots

![Clear state](https://github.com/InsanusMokrassar/KotlinPublicationScriptsBuilder/blob/master/.github/images/Screenshot%20clear.png)

![License autofilling](https://github.com/InsanusMokrassar/KotlinPublicationScriptsBuilder/blob/master/.github/images/Screenshot%20license%20example.png)

![Developers](https://github.com/InsanusMokrassar/KotlinPublicationScriptsBuilder/blob/master/.github/images/Screenshot%20developers%20example.png)
