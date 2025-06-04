package compiler;

import java.io.FileReader;
import java.io.IOException;
import java_cup.runtime.Symbol;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java Compiler <arquivo-fonte>");
            System.out.println("Exemplo: java Compiler input.txt");
        } else {
            String sourceFile = args[0];

            try {
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║           COMPILADOR BÁSICO            ║");
                System.out.println("║        Scanner + Parser JFlex/JCup     ║");
                System.out.println("╚════════════════════════════════════════╝");
                System.out.println();
                System.out.println("🔍 ETAPA 1: ANÁLISE LÉXICA");
                System.out.println("─────────────────────────────────────────");
                performLexicalAnalysis(sourceFile);
                System.out.println();
                System.out.println("🌳 ETAPA 2: ANÁLISE SINTÁTICA");
                System.out.println("─────────────────────────────────────────");
                performSyntacticAnalysis(sourceFile);
                System.out.println();
                System.out.println("✅ COMPILAÇÃO CONCLUÍDA COM SUCESSO!");
            } catch (Exception e) {
                System.err.println("❌ Erro durante compilação: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void performLexicalAnalysis(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            Scanner lexer = new Scanner(reader);
            System.out.println("Arquivo: " + filename);
            System.out.println("Tokens identificados:");
            System.out.println();

            int count = 0;
            Symbol tok; // renomeei de 'sym' para 'tok'
            while ((tok = lexer.next_token()).sym != sym.EOF) {
                count++;
                int symCode = tok.sym;
                String name = getTokenName(symCode);
                String literal = (tok.value != null ? tok.value.toString() : "");
                if (count <= 20) {
                    System.out.printf("  %2d. %-14s %-15s (Linha:%d, Col:%d)%n",
                            count,
                            name,
                            literal.isEmpty() ? "" : "'" + literal + "'",
                            tok.left + 1,
                            tok.right + 1);
                }
            }

            if (count > 20) {
                System.out.println("  ... e mais " + (count - 20) + " tokens");
            }

            System.out.println();
            System.out.printf("📊 Total: %d tokens processados%n", count);
            System.out.println("✓ Análise léxica bem-sucedida!");
        } catch (IOException ex) {
            throw new RuntimeException("Erro na análise léxica: " + ex.getMessage());
        }
    }

    private static void performSyntacticAnalysis(String filename) {
        try {
            FileReader reader = new FileReader(filename);
            Scanner lexer = new Scanner(reader);
            parser p = new parser(lexer);
            System.out.println("Construindo árvore sintática...");
            System.out.println();
            Symbol result = p.parse();
            System.out.println();
            if (result != null) {
                System.out.println("🌳 Árvore sintática construída com sucesso!");
                System.out.println("✓ Programa sintaticamente correto!");
            } else {
                System.out.println("❌ Falha na construção da árvore sintática");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro na análise sintática: " + ex.getMessage());
        }
    }

    private static String getTokenName(int symCode) {
        switch (symCode) {
            case sym.IF:
                return "IF";
            case sym.ELSE:
                return "ELSE";
            case sym.WHILE:
                return "WHILE";
            case sym.FOR:
                return "FOR";
            case sym.RETURN:
                return "RETURN";
            case sym.INT:
                return "INT";
            case sym.FLOAT:
                return "FLOAT";
            case sym.DOUBLE:
                return "DOUBLE";
            case sym.VOID:
                return "VOID";
            case sym.MAIN:
                return "MAIN";
            case sym.TRUE:
                return "TRUE";
            case sym.FALSE:
                return "FALSE";
            case sym.PLUS:
                return "+";
            case sym.MINUS:
                return "-";
            case sym.MULT:
                return "*";
            case sym.DIV:
                return "/";
            case sym.MOD:
                return "%";
            case sym.ASSIGN:
                return "=";
            case sym.EQ:
                return "==";
            case sym.NE:
                return "!=";
            case sym.LT:
                return "<";
            case sym.LE:
                return "<=";
            case sym.GT:
                return ">";
            case sym.GE:
                return ">=";
            case sym.AND:
                return "&&";
            case sym.OR:
                return "||";
            case sym.NOT:
                return "!";
            case sym.SEMICOLON:
                return ";";
            case sym.COMMA:
                return ",";
            case sym.LPAREN:
                return "(";
            case sym.RPAREN:
                return ")";
            case sym.LBRACE:
                return "{";
            case sym.RBRACE:
                return "}";
            case sym.LBRACKET:
                return "[";
            case sym.RBRACKET:
                return "]";
            case sym.INTEGER_LITERAL:
                return "INTEGER_LITERAL";
            case sym.FLOAT_LITERAL:
                return "FLOAT_LITERAL";
            case sym.DOUBLE_LITERAL:
                return "DOUBLE_LITERAL";
            case sym.IDENTIFIER:
                return "IDENTIFIER";
            default:
                return "UNKNOWN";
        }
    }
}
