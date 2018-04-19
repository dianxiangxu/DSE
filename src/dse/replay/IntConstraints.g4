grammar IntConstraints;
@header {
package replay;
}
constraints : (constraint)*;

constraint : posConstraint | negConstraint;
negConstraint:  '!''(' lhs=expr op=('>' | '<' | '>=' | '<=' | '==' | '!=') rhs=expr ')';
posConstraint : lhs=expr op=('>' | '<' | '>=' | '<=' | '==' | '!=') rhs=expr;


expr :
	   SUB expr #unaryArithm 
     | UNARYBITWISE expr  #unaryBitwise
     | '(' expr ')' #parens
     | lhs=expr op=('+' | '-' | '*' | '%' | '/') rhs=expr #binaryArithm 
     | lhs=expr op=('&' | '^' | '|' | '<<' | '>>' | '>>>') rhs=expr #binaryBitwise
     | SYMB #symbolic
     | INT #concrete
     ;
     
//Lexer Rules

UNARYBITWISE :'~';

GR : '>';
LS : '<';
GE : '>=';
LE : '<=';
EQ : '==';
NEQ : '!=';

ADD : '+';
SUB : '-';
MULT : '*';
MOD : '%';
DIV : '/';

AND : '&';
OREXL : '^'; 
ORINCL : '|';
SHL : '<<';
SHR : '>>';
SHRU : '>>>'; 

//+ signifies it can be read 1 or more times 
SYMB : 'p'[0-9]+;
INT : [0-9]+;
WS : [ \t\r\n]+ -> skip ;
//We are skipping tabs, new lines, and return statements 
