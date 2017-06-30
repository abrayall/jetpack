<img src="http://assets.halfbrick.com/jj/v2/images/jetpacks/jetpack-4.png" width="150px">

# Jetpack - Native Executable Builder
Jetpack is a build tool that can convert Java applications into native executables for Windows, Linux and (soon) MacOS.

Jetpack [v1.0](https://github.com/abrayall/jetpack/releases/download/v1.0.0/jetpack.tar) currently can generate sophisticated shell scripts for your Java app for Linux and Windows.  Future versions will able to support native binaries / apps for Windows, Linux and MacOS.

### Install
 - Download the [current release](https://github.com/abrayall/jetpack/releases/download/v1.0.0/jetpack.tar) tar file and extract to your system
 
### Usage
```
 $> bin/jetpack [name] [main] [platforms] [directory]
 
 where: 
  - name:       name of executable to generate
  - main:       name of class with main() that should be launched
  - platforms:  plaforms to generate executables (linux|window - default both)
  - directory:  directory that executables should be built
  
```
  
  
