::Possible example of a memoranda patch for patches and upates.
::Larger updates would have a larger script for converting the XML .Memoranda folder.
::Too new XML system if neeeded.
@echo
::Path to Memoranda Patch folder.
set path=%~dp0 
::Drops into Memoranda Folder.
cd .. 
::Gets path of Memoranda Folder.
set path1=%cd% 
::Replaces Memoranda Jar.
copy /y %path%\Memoranda.jar %path1% 