grammar ConditionExp;

prog:   stat;                     

stat:   expr NEWLINE                  # printExpr
    ;
	 
expr:   expr op=('+'|'-'|'*') expr    # ArithOperation
    |   ID                            # id
    |   INT                           # int
    |   '(' expr ')'                  # parens
    ;
 
ADD :   '+' ;
SUB :   '-' ;
MUL :   '*' ;
ID  :   'p'[0-9]+ ;      // match identifiers
INT :   [0-9]+ ;            // match integers
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace