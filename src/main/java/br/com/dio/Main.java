package br.com.dio;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;

import java.util.Arrays;
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

    // cria conta após entrada de pix e valor
    private static void criarConta() {
        System.out.println("1 === Criar uma conta ===");

        System.out.print("Informe as chaves Pix separadas por ';': ");
        var pix = Arrays.stream(scanner.next().split(";")).toList();

        System.out.print("Informe o valor inicial do depósito: ");
        var amount = scanner.nextLong();

        var wallet = accountRepository.create(pix, amount);
        System.out.printf("Conta criada: %s%n", wallet);
    }

    // cria investimento após entrada de taxa e valor inicial
    private static void criarInvestimento() {
        System.out.println(" 2 === Criar um investimento ===");

        System.out.print("Informe a taxa de investimento: ");
        var tax = scanner.nextInt();

        System.out.print("Informe o valor inicial do depósito: ");
        var initialFunds = scanner.nextLong();

        var investment = investmentRepository.create(tax, initialFunds);
        System.out.printf("Investimento criado: %s%n", investment);
    }

    private static void fazerInvestimento() {
        System.out.println("3 === Fazer um investimento conta ===");
    }

    private static void depositarNaConta() {
        System.out.println(" 4 === Depositar na conta ===");
        System.out.print("Informe a chave pix da conta para depósito: ");
        var pix = scanner.next();
        System.out.print("Informe o valor a ser depositado: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException | NoFundsEnoughException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sacarDaConta() {
        System.out.println(" 5 === Sacar da comta ===");
        System.out.print("Informe a chave pix da conta para saque: ");
        var pix = scanner.next();
        System.out.print("Informe o valor a ser sacado: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void transferenciaEntreContas() {
        System.out.println(" 6 === Transferência entre contas ===");
        System.out.print("Informe a chave pix da conta para saque: ");
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

    // agradecimento e saída
    private static void sair() {
        System.out.println("14 === Sair ===");
        System.out.println("Obrigado por utilizar nosso software!");
        System.exit(EXIT_SUCCESS);
    }

}

