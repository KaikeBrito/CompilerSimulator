/*============================================================================== 
  Scanner.flex
  Versão ajustada para package "compiler" e compatível com parser CUP.
==============================================================================*/

package compiler;

import java_cup.runtime.*;

%%

%class Scanner
%unicode
%cup
%line
%column

%{
  // Métodos auxiliares para criar Symbol
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

/* ==================== Definições de padrões ==================== */

/* Espaço em branco e quebras de linha */
LineTerminator       = \r|\n|\r\n
InputCharacter       = [^\r\n]
WhiteSpace           = {LineTerminator} | [ \t\f]

/* Comentários */
Comment              = {TraditionalComment} | {EndOfLineComment}
TraditionalComment   = "/*" (~[\*] | "\*"+[^/])* "\*/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}? 

/* Identificadores */
Identifier           = [:jletter:] [:jletterdigit:]*

// ---------------------------------------------------------------------------
//  LITERÁIS NUMÉRICOS
//  - INTEGER_LITERAL: número decimal sem pontos e sem expoente
//  - FLOAT_LITERAL: combinação com ponto obrigatório (ex: 3.14, .5, 2. e opcional [fF])
//  - DOUBLE_LITERAL: possui ponto ou expoente e opcional [dD] (ex: 3.14, 2e-3, 5.0d)
// ---------------------------------------------------------------------------

DecIntegerLiteral    = 0 | [1-9][0-9]*

FloatLiteral         = (([0-9]+\.[0-9]*)|(\.[0-9]+))([eE][+-]?[0-9]+)?[fF]?
DoubleLiteral        = (([0-9]+\.[0-9]*)|(\.[0-9]+))([eE][+-]?[0-9]+)?[dD]? 
                     | ([0-9]+([eE][+-]?[0-9]+))

%%

/* ==================== Regras Léxicas ==================== */

/* Ignorar espaços em branco e quebras de linha */
{WhiteSpace}         { /* ignora */ }

/* Ignorar comentários */
{Comment}            { /* ignora */ }

// Palavras-chave de controle
"if"                 { return symbol(sym.IF); }
"else"               { return symbol(sym.ELSE); }
"while"              { return symbol(sym.WHILE); }
"for"                { return symbol(sym.FOR); }
"return"             { return symbol(sym.RETURN); }

// Tipos primitivos e main
"int"                { return symbol(sym.INT); }
"float"              { return symbol(sym.FLOAT); }
"double"             { return symbol(sym.DOUBLE); }
"void"               { return symbol(sym.VOID); }
"main"               { return symbol(sym.MAIN); }

// Literais booleanos
"true"               { return symbol(sym.TRUE); }
"false"              { return symbol(sym.FALSE); }

// Operadores aritméticos e lógicos
"+"                  { return symbol(sym.PLUS); }
"-"                  { return symbol(sym.MINUS); }
"*"                  { return symbol(sym.MULT); }
"/"                  { return symbol(sym.DIV); }
"%"                  { return symbol(sym.MOD); }
"=="                 { return symbol(sym.EQ); }
"!="                 { return symbol(sym.NE); }
"<="                 { return symbol(sym.LE); }
"<"                  { return symbol(sym.LT); }
">="                 { return symbol(sym.GE); }
">"                  { return symbol(sym.GT); }
"&&"                 { return symbol(sym.AND); }
"||"                 { return symbol(sym.OR); }
"="                  { return symbol(sym.ASSIGN); }
"!"                  { return symbol(sym.NOT); }

// Delimitadores
";"                  { return symbol(sym.SEMICOLON); }
","                  { return symbol(sym.COMMA); }
"("                  { return symbol(sym.LPAREN); }
")"                  { return symbol(sym.RPAREN); }
"{"                  { return symbol(sym.LBRACE); }
"}"                  { return symbol(sym.RBRACE); }
"["                  { return symbol(sym.LBRACKET); }
"]"                  { return symbol(sym.RBRACKET); }

// Literais numéricos com valor exato
{DecIntegerLiteral}  { 
                       Integer val = Integer.valueOf(yytext());
                       return symbol(sym.INTEGER_LITERAL, val);
                     }
{FloatLiteral}       {
                       // Float.parseFloat aceita “3.”, “.5”, “3.14f”, etc.
                       Float val = Float.valueOf(yytext());
                       return symbol(sym.FLOAT_LITERAL, val);
                     }
{DoubleLiteral}      {
                       Double val = Double.valueOf(yytext());
                       return symbol(sym.DOUBLE_LITERAL, val);
                     }

// Identificador (variáveis, nomes de funções, etc.)
{Identifier}         { return symbol(sym.IDENTIFIER, yytext()); }

// Qualquer outro caractere não reconhecido: erro
.                    { throw new Error("Caractere ilegal: " + yytext()); }

<<EOF>>              { return symbol(sym.EOF); }
