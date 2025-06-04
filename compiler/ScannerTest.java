package compiler;

import java.io.*;
import java_cup.runtime.Symbol;

public class ScannerTest {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java ScannerTest <arquivo-fonte>");
            return;
        }
        
        try {
            FileReader file = new FileReader(args[0]);
            Scanner scanner = new Scanner(file);
            
            System.out.println("=== ANÁLISE LÉXICA ===");
            System.out.println("Arquivo: " + args[0]);
            System.out.println("Tokens encontrados:\n");
            
            Symbol token;
            int tokenCount = 0;
            
            while ((token = scanner.next_token()).sym != sym.EOF) {
                tokenCount++;
                String tokenType = getTokenName(token.sym);
                String tokenValue = token.value != null ? token.value.toString() : "";
                
                System.out.printf("%-3d | %-15s | %-20s | Linha: %d, Coluna: %d%n", 
                    tokenCount, tokenType, tokenValue, 
                    token.left + 1, token.right + 1);
            }
            
            System.out.println("\nAnálise léxica concluída!");
            System.out.println("Total de tokens: " + tokenCount);
            
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + args[0]);
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro durante análise léxica: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String getTokenName(int tokenId) {
        switch (tokenId) {
            case sym.IF: return "IF";
            case sym.ELSE: return "ELSE";
            case sym.WHILE: return "WHILE";
            case sym.FOR: return "FOR";
            case sym.RETURN: return "RETURN";
            case sym.INT: return "INT";
            case sym.FLOAT: return "FLOAT";
            case sym.DOUBLE: return "DOUBLE";
            case sym.VOID: return "VOID";
            case sym.MAIN: return "MAIN";
            case sym.TRUE: return "TRUE";
            case sym.FALSE: return "FALSE";
            case sym.PLUS: return "PLUS";
            case sym.MINUS: return "MINUS";
            case sym.MULT: return "MULT";
            case sym.DIV: return "DIV";
            case sym.MOD: return "MOD";
            case sym.ASSIGN: return "ASSIGN";
            case sym.EQ: return "EQ";
            case sym.NE: return "NE";
            case sym.LT: return "LT";
            case sym.LE: return "LE";
            case sym.GT: return "GT";
            case sym.GE: return "GE";
            case sym.AND: return "AND";
            case sym.OR: return "OR";
            case sym.NOT: return "NOT";
            case sym.SEMICOLON: return "SEMICOLON";
            case sym.COMMA: return "COMMA";
            case sym.LPAREN: return "LPAREN";
            case sym.RPAREN: return "RPAREN";
            case sym.LBRACE: return "LBRACE";
            case sym.RBRACE: return "RBRACE";
            case sym.LBRACKET: return "LBRACKET";
            case sym.RBRACKET: return "RBRACKET";
            case sym.INTEGER_LITERAL: return "INTEGER";
            case sym.FLOAT_LITERAL: return "FLOAT_NUM";
            case sym.DOUBLE_LITERAL: return "DOUBLE_NUM";
            case sym.IDENTIFIER: return "IDENTIFIER";
            default: return "UNKNOWN(" + tokenId + ")";
        }
    }
}