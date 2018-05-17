grammar CONDEXP;

expr 
    : '(' expr OP expr ')'
    | ID
    | INT
    ;
 
OP  : OP_ADD | OP_SUB | OP_MUL ;

OP_ADD: '+';
OP_SUB: '-';
OP_MUL: '*';

ID     : [a-z]+ ;
INT : [0-9]+ ;
WS     : [ \n\t]+ -> skip;

