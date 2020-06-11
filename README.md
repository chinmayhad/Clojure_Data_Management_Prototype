# Data Management App Prototype in **Clojure**

This app reads data from 3 different text files.
On selection of appropriate menus, displays data that is read from files.
For menus that require special processing on data read from files,
it performs multiple calculations combining and manipulating data from multiple files.
The results obtained from one function are given as input to another function.

As **Clojure** is a **functional programming language**, I intended to use more functional style programming constructs 
like **recursion** or leveraging full potential of the *apply-to-all* style functions (e.g, map, reduce, filters).

To run the program in **command prompt** :
1.  Move to the directory where program is saved.
2.  Open command prompt and type-
powershell -command clj sales.clj

To run the program in **PowerShell** :
1.  Move to the directory where program is saved.
2.  Open PowerShell and type-
clj sales.clj
