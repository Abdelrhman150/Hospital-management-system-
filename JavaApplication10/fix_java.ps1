$srcDir = "C:\Users\AE-Hossam\Desktop\Hospital Management System\JavaApplication10\JavaApplication10\src"
$packageDir = "$srcDir\Package by Mermaid"
$package1Dir = "$packageDir\Package1"

Get-ChildItem -Path $srcDir -Recurse -Filter "*.java" | ForEach-Object {
    $content = [System.IO.File]::ReadAllText($_.FullName)

    $content = [regex]::Replace($content, "\bstring\b", "String")
    $content = [regex]::Replace($content, "\bdecimal\b", "double")
    $content = [regex]::Replace($content, "\bDateTime\b", "Date")
    $content = [regex]::Replace($content, "\bbool\b", "boolean")

    $content = [regex]::Replace($content, "\bvoid\s+([a-zA-Z0-9_]+)(?=\s*[,)])", {
        param($m)
        $p = $m.Groups[1].Value
        $pl = $p.ToLower()
        if ($pl -match "phone|email|text|diagnosis") { return "String " + $p }
        if ($pl -match "id|days") { return "int " + $p }
        if ($pl -match "date") { return "Date " + $p }
        if ($pl -match "time") { return "Time " + $p }
        if ($pl -match "available|is") { return "boolean " + $p }
        if ($pl -match "shift") { return "ShiftType " + $p }
        if ($pl -match "status") { return "RoomStatus " + $p }
        return "Object " + $p
    })

    if ($content.Contains("Date ") -and -not $content.Contains("import java.util.Date;") -and -not $content.Contains("import java.util.*;")) {
        $content = [regex]::Replace($content, "(package\s+[^;]+;)", "`$1`r`n`r`nimport java.util.Date;", 1)
    }
    if ($content.Contains("Time ") -and -not $content.Contains("import java.sql.Time;")) {
        $content = [regex]::Replace($content, "(package\s+[^;]+;)", "`$1`r`n`r`nimport java.sql.Time;", 1)
    }

    [System.IO.File]::WriteAllText($_.FullName, $content, [System.Text.Encoding]::UTF8)
}

$enums = @('Gender.java', 'AppointmentStatus.java', 'AppointmentType.java', 'RoomType.java', 'ShiftType.java', 'BloodType.java', 'NurseStatus.java', 'RoomStatus.java')
foreach ($enum in $enums) {
    $oldPath = "$packageDir\$enum"
    $newPath = "$package1Dir\$enum"
    if (Test-Path $oldPath) {
        $content = [System.IO.File]::ReadAllText($oldPath)
        if (-not $content.Contains("package Package1;")) {
            $content = "package Package1;`r`n`r`n" + $content
        }
        [System.IO.File]::WriteAllText($oldPath, $content, [System.Text.Encoding]::UTF8)
        Move-Item -Path $oldPath -Destination $newPath -Force
    }
}
Write-Output "Script executed successfully."
