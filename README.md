Rabdomante
==========

Rabdomante (Rhabdomancer in Italian) is an application that helps you finding the optimal mix of waters and salts to match a target water profile: 
given a list of waters and salts rabdomante finds the combination closest to the target.

The distance from the target profile is derived from this components:

* Calcium (Ca)
* Magnesium (Mg)
* Sodium (Na)
* Sulfate (SO4)
* Chloride (Cl)
* Bicarbonates (HCO3)

## Demo

You can see it in action here: [molok.github.io](https://molok.github.io)

In this version the computing time is capped to 30 seconds, so it might not give the optimal
result if given a lot of variables.

## Code

It started as a CLI application, then I added a desktop application (JavaFX).
Then I realized it would be most useful on the web so, so I created a REST API 
with Spark Framework and wrote a React+Redux application in Typescript for it.

You'll find the React code under the `frontend/` directory and the rest under the `backend/`.
The single jar generated in the `backend/` directory contains the CLI, the JavaFX GUI and the REST API.

The React+Redux application can be deployed on a static server, I'm hosting it on a S3 bucket.
I provided a script to deploy the REST API on AWS Lambda.

These are the screenshot of the desktop application:

![screenshot](https://raw.githubusercontent.com/molok/rabdomante/master/backend/misc/screenshot_windows_1.2.png)

The input and output is a XLSX file. It can be run both with a graphical interface and with a command line interface.

![screenshot](https://raw.githubusercontent.com/molok/rabdomante/master/backend/misc/screenshot_cli_1.2.png)

Mostyl useful for homebrewing, maybe more. It supports the English and Italian locales

Rabdomante is released under the AGPL3, if you need a less restrictive license feel free to contact me at [alessio.bolognino+rabdo@gmail.com](mailto:alessio.bolognino+rabdo@gmail.com)

Under the hood rabdomante uses the excellent [Choco Solver](https://github.com/chocoteam/choco-solver)
