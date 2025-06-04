package compiler;

import java.io.*;
import java_cup.runtime.Symbol;

public class ParserTest {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java ParserTest <arquivo-fonte>");
            return;
        }
        
        try {
            FileReader file = new FileReader(args[0]);
            Scanner scanner = new Scanner(file);
            parser parser = new parser(scanner);
            
            System.out.println("=== ANÁLISE SINTÁTICA ===");
            System.out.println("Arquivo: " + args[0]);
            System.out.println("Iniciando análise...\n");
            
            Symbol parseResult = parser.parse();
            
            System.out.println("\n=== RESULTADO ===");
            if (parseResult != null) {
                System.out.println("✓ Análise sintática concluída com sucesso!");
                System.out.println("✓ Programa sintaticamente correto!");
            } else {
                System.out.println("✗ Erro durante análise sintática");
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + args[0]);
        } catch (Exception e) {
            System.err.println("Erro durante análise sintática: " + e.getMessage());
            System.err.println("O programa contém erros de sintaxe.");
        }
    }
}