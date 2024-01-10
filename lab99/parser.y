%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define YYDEBUG 1

int yylex(void);
int yyerror(char *s);

#define YYDEBUG 1
%}


%token PROG;
%token INT;
%token STR;
%token CHAR;
%token READ;
%token IF;
%token ELSE;
%token PRINT;
%token WHILE;
%token ARR;

%token PLUS;
%token MINUS;
%token TIMES;
%token DIV;
%token LESS;
%token LESSEQ;
%token EQ;
%token NEQ;
%token BIGGEREQ;
%token EQQ;
%token BIGGER;
%token SQRT;

%token SQBRACKETOPEN;
%token SQBRACKETCLOSE;
%token SEMICOLON;
%token OPEN;
%token CLOSE;
%token BRACKETOPEN;
%token BRACKETCLOSE;
%token COMMA;

%token IDENTIFIER;
%token INTCONSTANT;
%token STRINGCONSTANT;

%start Program

%%
Program : PROG BRACKETOPEN CompoundStmt BRACKETCLOSE     { printf("Program -> prog { CompoundStmt }\n"); }
        ;
CompoundStmt : Stmt SEMICOLON CompoundStmt     { printf("CompoundStmt -> Stmt ; CompoundStmt\n"); }
                  | Stmt SEMICOLON                       { printf("CompoundStmt -> Stmt ;\n"); }
                  ;
Stmt : DeclarationStmt     { printf("Stmt -> DeclarationStmt\n"); }
          | AssignmentStmt     { printf("Stmt -> AssignmentStmt\n"); }
          | IfStmt     { printf("Stmt -> IfStmt\n"); }
          | WhileStmt     { printf("Stmt -> WhileStmt\n"); }
          | PrintStmt     { printf("Stmt -> PrintStmt\n"); }
          | ReadStmt     { printf("Stmt -> ReadStmt\n"); }
          ;
DeclarationStmt : IDENTIFIER OPEN Type CLOSE COMMA DeclarationStmt     { printf("DeclarationStmt -> IDENTIFIER ( Type ) , DeclarationStmt\n"); }
                     | IDENTIFIER OPEN Type CLOSE     { printf("DeclarationStmt -> IDENTIFIER ( Type )\n"); }
                     ;
Type : INT     { printf("Type -> int\n"); }
     | STR     { printf("Type -> str\n"); }
     | CHAR     { printf("Type -> char\n"); }
     | ARR     { printf("Type -> arr\n"); }
     ;

ReadStmt : READ OPEN IDENTIFIER CLOSE     { printf("ReadStmt -> read ( IDENTIFIER )\n"); }
              ;

PrintStmt : PRINT OPEN Expression CLOSE     { printf("PrintStmt -> print ( Expression )\n"); }
               | PRINT OPEN STRINGCONSTANT CLOSE     { printf("PrintStmt -> print ( STRINGCONSTANT )\n"); }
               ;

AssignmentStmt : IDENTIFIER EQ Expression     { printf("AssignmentStmt -> IDENTIFIER = Expression\n"); }
                    | IDENTIFIER EQ ArrayStmt     { printf("AssignmentStmt -> IDENTIFIER = ArrayStmt\n"); }
                    ;
Expression : Expression PLUS Term     { printf("Expression -> Expression + Term\n"); }
           | Expression MINUS Term     { printf("Expression -> Expression - Term\n"); }
           | Term     { printf("Expression -> Term\n"); }
           ;
Term : Term TIMES Factor     { printf("Term -> Term * Factor\n"); }
     | Term DIV Factor     { printf("Term -> Term / Factor\n"); }
     | Factor     { printf("Term -> Factor\n"); }
     ;
Factor : OPEN Expression CLOSE     { printf("Factor -> ( Expression )\n"); }
       | IDENTIFIER     { printf("Factor -> IDENTIFIER\n"); }
       | INTCONSTANT     { printf("Factor -> INTCONSTANT\n"); }
       | MINUS IDENTIFIER     { printf("Factor -> - IDENTIFIER\n"); }
       | SQRT OPEN Expression CLOSE     { printf("Factor -> sqrt ( Expression )\n"); }
       ;
ArrayStmt : SQBRACKETOPEN SQBRACKETCLOSE    { printf("ArrayStmt -> []\n"); }
               | SQBRACKETOPEN ExpressionList SQBRACKETCLOSE    { printf("ArrayStmt -> [ ExpressionList ]\n"); }
               ;
ExpressionList : Expression COMMA ExpressionList    { printf("ExpressionList -> Expression , ExpressionList\n"); }
               | Expression    { printf("ExpressionList -> Expression\n"); }
               ;
IfStmt : IF Condition BRACKETOPEN CompoundStmt BRACKETCLOSE  { printf("IfStmt -> if Expression { CompoundStmt }\n"); }
            | IF Condition BRACKETOPEN CompoundStmt BRACKETCLOSE ELSE BRACKETOPEN CompoundStmt BRACKETCLOSE  { printf("IfStmt -> if Expression { CompoundStmt } else { CompoundStmt }\n"); }
            ;
WhileStmt : WHILE Condition BRACKETOPEN CompoundStmt BRACKETCLOSE  { printf("WhileStmt -> while Expression { CompoundStmt }\n"); }
               ;
Condition : Expression Relation Expression     { printf("Condition -> Expression Relation Expression\n"); }
          ;
Relation : LESS     { printf("Relation -> <\n"); }
         | LESSEQ     { printf("Relation -> <=\n"); }
         | EQQ     { printf("Relation -> ==\n"); }
         | NEQ     { printf("Relation -> <>\n"); }
         | BIGGEREQ     { printf("Relation -> >=\n"); }
         | BIGGER     { printf("Relation -> >\n"); }
         ;
%%
int yyerror(char *s) {
    printf("Error: %s", s);
    return 1;
}

extern FILE *yyin;

int main(int argc, char** argv) {
    if (argc > 1) 
        yyin = fopen(argv[1], "r");
    if (!yyparse()) 
        fprintf(stderr, "\tCompiled successfully. No errors found.\n");
}