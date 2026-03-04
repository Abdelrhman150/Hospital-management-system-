$srcDir = "C:\Users\AE-Hossam\Desktop\Hospital Management System\JavaApplication10\JavaApplication10\src"
$mermaidDir = "$srcDir\Package by Mermaid"

if (Test-Path $mermaidDir) {
    # Move Package1, Package2, Package3, Package4 out to src
    $packages = @("Package1", "Package2", "Package3", "Package4")
    foreach ($pkg in $packages) {
        $pkgPath = "$mermaidDir\$pkg"
        if (Test-Path $pkgPath) {
            Move-Item -Path $pkgPath -Destination "$srcDir\$pkg" -Force
        }
    }

    # Move anything else (interfaces) directly to src (default package)
    # Or put them in a package? Let's give them a package "hospital" if we need to.
    # Actually, they are default package. Let's just move them locally.
    Get-ChildItem -Path $mermaidDir -File | ForEach-Object {
        Move-Item -Path $_.FullName -Destination "$srcDir\" -Force
    }
    
    # Remove the old mermaid folder if empty
    if ((Get-ChildItem -Path $mermaidDir).Count -eq 0) {
        Remove-Item -Path $mermaidDir -Recurse -Force
    }
}
Write-Output "Structure fixed"
