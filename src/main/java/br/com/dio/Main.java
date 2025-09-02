package br.com.dio;

import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int EXIT_SUCCESS = 0;
    private static final AccountRepository accountRepository = new AccountRepository();
    private static final InvestmentRepository investmentRepository = new InvestmentRepository();

    public static void main(String[] args) {
        int option = -1;

        System.out.println("Olá... seja bem-vindo ao DIO Bank!");
        while (true) {
            System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s",
                    " 1. Criar uma conta",
                    " 2. Criar um investimento",
                    " 3. Fazer um investimento",
                    " 4. Depositar na conta",
                    " 5. Sacar da comta",
                    " 6. Transferência entre contas",
                    " 7. Investir",
                    " 8. Sacar investimento",
                    " 9. Listar contas",
                    "10. Listar investimentos",
                    "11. Listar carteiras de investimento",
                    "12. Atualizar investimentos",
                    "13. Histórico de conta",
                    "14. Sair",
                    "Selecione a operação desejada: "
            );
            option = scanner.nextInt();

            switch (option) {
                case  1 -> criarConta();
                case  2 -> criarInvestimento();
                case  3 -> fazerInvestimento();
                case  4 -> depositarNaConta();
                case  5 -> sacarDaConta();
                case  6 -> transferenciaEntreContas();
                case  7 -> investir();
                case  8 -> sacarInvestimento();
                case  9 -> listarContas();
                case 10 -> listarInvestimentos();
                case 11 -> listarCarteirasDeInvestimentos();
                case 12 -> atualizarInvestimentos();
                case 13 -> historicoDeConta();
                case 14 -> sair();
                default -> System.out.println("Opção inválida!");
            }
        }
    }


    private static void criarConta() {
        System.out.println("1 === Criar uma conta ===");

    }

    private static void criarInvestimento() {
        System.out.println(" 2 === Criar um investimento ===");
    }

    private static void fazerInvestimento() {
        System.out.println("3 === Fazer um investimento conta ===");
    }

    private static void depositarNaConta() {
        System.out.println(" 4 === Depositar na conta ===");
    }

    private static void sacarDaConta() {
        System.out.println(" 5 === Sacar da comta ===");
    }

    private static void transferenciaEntreContas() {
        System.out.println(" 6 === Transferência entre contas ===");
    }

    private static void investir() {
        System.out.println(" 7 === Investir ===");
    }

    private static void sacarInvestimento() {
        System.out.println(" 8 === Sacar investimento ===");
    }

    private static void listarContas() {
        System.out.println("9 === Listar contas ===");
        accountRepository.list().forEach(System.out::println);
    }

    private static void listarInvestimentos() {
        System.out.println("10 === Listar investimentos ===");
        investmentRepository.list().forEach(System.out::println);
    }

    private static void listarCarteirasDeInvestimentos() {
        System.out.println("11 === Listar contas de investimento ===");
        investmentRepository.listWallets().forEach(System.out::println);
    }

    private static void atualizarInvestimentos() {
        System.out.println("12 === Atualizar investimentos ===");
        investmentRepository.updateAmount();
        System.out.println("Investimentos reajustados!");
    }

    private static void historicoDeConta() {
        System.out.println("13 === Histórico de conta ===");
    }

    private static void sair() {
        System.out.println("14 === Sair ===");
        System.out.println("Obrigado por utilizar nosso software!");
        System.exit(EXIT_SUCCESS);
    }

}

