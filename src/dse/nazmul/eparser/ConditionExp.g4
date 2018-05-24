grammar ConditionExp;

prog:   stat;                     

stat  :   expr NEWLINE                                                # printExpr
      ;
	 
expr  :   expr op=('+' | '-' | '*' | '/' | '%') expr                  # ArithOperation
      |   expr op=('&' | '^' | '|' | '<<' | '>>' | '>>>') expr        # BitWiseOperation
      |   SUB expr                                                    # UnaryArithOperation
      |   COMPL expr                                                  # unaryBitwise
      |   ID                                                          # id
      |   INT                                                         # int
      |   '(' expr ')'                                                # parens
      ;
 
ADD   :   '+';
SUB   :   '-';
MUL   :   '*';
DIV   :   '/';
MOD   :   '%';

AND   :   '&';
OR    :   '|';
XOR   :   '^';
LSH   :   '<<';
RSH   :   '>>';
RSH0F :   '>>>';
COMPL :   '~';

ID  :   'p'[0-9]+ ;      // match identifiers
INT :   [0-9]+ ;            // match integers
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace