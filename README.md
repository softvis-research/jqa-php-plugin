# jQAssistant PHP Plugin

[![GitHub license](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](LICENSE)
[![Build Status](https://api.travis-ci.com/softvis-research/jqa-php-plugin.svg?branch=master)](https://travis-ci.com/softvis-research/jqa-php-plugin)

This is a PHP parser for [jQAssistant](https://jqassistant.org/). It enables jQAssistant to scan and to analyze PHP files.

## Getting Started

Download the jQAssistant command line tool for your system: [jQAssistant - Get Started](https://jqassistant.org/get-started/).

Next download the latest version from the release tab. 
If you want to build the plugin yourself use the following command:

```bash
mvn clean install
```

Put the `jqa-php-plugin-*.jar` into the plugins folder of the jQAssistant command line tool.

Now scan your software artifact and wait for the plugin to finish:

```bash
jqassistant.sh scan -f lib
```

You can then start a local **Neo4j** server to start querying the database at [http://localhost:7474](http://localhost:7474):

```bash
jqassistant.sh server
```

## Model

![graph](material/graph.png)


## Other Plugins

Other plugins developed by the **Visual Software Analytics** team can be found [here](https://softvis-research.github.io/jqassistant-plugins/).
