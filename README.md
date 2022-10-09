| Terminals  |                  Value                  |
|------------|:---------------------------------------:|
| var        |                  "var"                  |
| Array      |                 "Array"                 |
| x          | java identifier (variable name or type) |
| :          |                   ':'                   |
| ;          |                   ';'                   |
| <          |                   '<'                   |
| \\>        |                   '>'                   |
 | empty      |                   ""                    |  

| NonTerminals |  First   | Follow   | Description                            |
|--------------|:--------:|----------|----------------------------------------|
| S            |   var    | $        | declaration statement (start terminal) |
| E            | empty, ; | $        | end of the statement                   |  
| A            |  Array   | empty, ; | type of variable                       |


* **Grammar**:  

S -> varx:AE  
A -> x
A -> Array\<A>
E -> ;  
E -> empty

* **Tree Visualization format**:

GraphStream library was used.

Demo version locates in main/java/Main.java.

* **Tests**:  
I think the names of the tests explain purposes of writing them
