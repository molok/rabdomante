# rabdomante

Rabdomante (Rhabdomancer in Italian) is a Java application that helps you finding the optimal mix of waters and salts to match a target water profile: 
given a list of waters and salts rabdomante finds the combination closest to the target.

The distance from the target profile is derived from this components:

* Calcium (Ca)
* Magnesium (Mg)
* Sodium (Na)
* Sulfate (SO4)
* Chloride (Cl)
* Bicarbonates (HCO3)

![screenshot](https://raw.githubusercontent.com/molok/rabdomante/master/misc/screenshot_windows_1.2.png)

The input and output is a XLSX file. It can be run both with a graphical interface and with a command line interface.

![screenshot](https://raw.githubusercontent.com/molok/rabdomante/master/misc/screenshot_cli_1.2.png)

Mostyl useful for homebrewing, maybe more. It supports the English and Italian locales

Rabdomante is released under the AGPL3, if you need a less restrictive license feel free to contact me at [alessio.bolognino@gmail.com](mailto:alessio.bolognino@gmail.com)

Under the hood rabdomante uses [Choco Solver](https://github.com/chocoteam/choco-solver)
