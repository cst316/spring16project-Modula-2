@echo

echo 1
set path=%~dp0
echo 2
cd ..
set path1=%cd%
echo %path1%

MOVE /y %path%\Memoranda1.jar %path1%
cd ..
rmdir %path%
