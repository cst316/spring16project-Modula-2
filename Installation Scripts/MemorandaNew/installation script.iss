; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Memoranda"
#define MyAppVersion "2.0"
#define MyAppPublisher "Modula-2"
#define MyAppURL "https://github.com/cst316/spring16project-Modula-2/"
#define MyAppExeName "Memoranda.jar"
#define MyAppIcoName "memoranda_icon_32.ico"
#define MyAppIcoNameUninstall "MemorandaUninstall.ico"

[Setup]
AppId={{D62148CC-5565-410F-878C-EAF0D6A36626}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
AllowNoIcons=yes
LicenseFile=C:\Users\Eric\Desktop\MemorandaNew\Licence.txt
InfoBeforeFile=C:\Users\Eric\Desktop\MemorandaNew\BeforeInstalltion.txt
OutputBaseFilename=setup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; \
    GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "C:\Users\Eric\Desktop\MemorandaNew\Memoranda.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\Eric\Desktop\MemorandaNew\{#MyAppIcoName}"; DestDir: "{app}"
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{userdesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; \
    IconFilename: "{app}\{#MyAppIcoName}"; Tasks: desktopicon