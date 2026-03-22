import os, glob, subprocess

files = glob.glob('src/**/*.java', recursive=True)
with open('sources.txt', 'w', encoding='utf-8') as f:
    for file in files:
        f.write(f'"{os.path.abspath(file).replace(chr(92), "/")}"\n')

res = subprocess.run(['javac', '-d', 'build/classes', '-cp', 'lib/mssql-jdbc-13.2.1.jre8.jar', '@sources.txt'], capture_output=True, text=True)
if res.returncode != 0:
    print(res.stderr)
    exit(res.returncode)
print("Compile Success")
