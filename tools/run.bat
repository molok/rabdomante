@echo off
cls
set JBIN=java.exe

setlocal ENABLEEXTENSIONS
set KEY_NAME="HKLM\SOFTWARE\JavaSoft\Java Runtime Environment"
set VALUE_NAME=CurrentVersion

FOR /F "usebackq skip=2 tokens=3" %%A IN (`REG QUERY %KEY_NAME% /v %VALUE_NAME% 2^>nul`) DO (
    set JavaCurrVersion=%%A
)
if defined JavaCurrVersion (
    @echo the current Java runtime is  %JavaCurrVersion%
    set JAVA_CURRENT="HKLM\SOFTWARE\JavaSoft\Java Runtime Environment\%JavaCurrVersion%"
    FOR /F "usebackq skip=2 tokens=3*" %%A IN (`REG QUERY %JAVA_CURRENT% /v JavaHome 2^>nul`) DO (
        set JAVA_PATH=%%A %%B
    )
    set JBIN = "%JAVA_PATH%\bin\java.exe"
) else (
    @echo %KEY_NAME%\%VALUE_NAME% not found.
)
@echo starting rabdo
"%JBIN%" -jar rabdomante-${project.version}.jar -v
@echo end rabdo
pause
