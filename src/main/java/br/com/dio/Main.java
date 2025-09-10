package br.com.dio;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int EXIT_SUCCESS = 0;
    private static final AccountRepository accountRepository = new AccountRepository();
    private static final InvestmentRepository investmentRepository = new InvestmentRepository();

    public static void main(String[] args) {
        int option;

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
                case  6 -> transferirEntreContas();
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
        System.out.print("Informe as chaves Pix (separadas por ';'): ");
        var pix = Arrays.stream(scanner.next().split(";")).toList();
        System.out.print("Informe o valor inicial do depósito: ");
        var amount = scanner.nextLong();
        System.out.println("Conta criada: " + accountRepository.create(pix, amount));
    }

    // cria investimento após entrada de taxa e valor inicial
    private static void criarInvestimento() {
        System.out.println(" 2 === Criar um investimento ===");
        System.out.print("Informe a taxa de investimento: ");
        var tax = scanner.nextInt();
        System.out.print("Informe o valor inicial do depósito: ");
        var initialFunds = scanner.nextLong();
        System.out.println("Investimento criado: " + investmentRepository.create(tax, initialFunds));
    }

    // cria investimento dasos conta e ID
    private static void fazerInvestimento() {
        System.out.println("3 === Fazer um investimento conta ===");
        System.out.print("Informe a chave pix da conta: ");
        var pix = scanner.next();
        System.out.print("Informe o ID do investimento:");
        var investmentID = scanner.nextInt();
        var wallet = investmentRepository.initInvestment(accountRepository.findByPix(pix), investmentID);
        System.out.println("Conta de investimento criada: " + wallet);
    }

    // depositar valor em uma conta
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

    // sacar valor de uma conta
    private static void sacarDaConta() {
        System.out.println(" 5 === Sacar da comta ===");
        System.out.print("Informe a chave pix da conta para saque: ");
        var pix = scanner.next();
        System.out.print("Informe o valor a ser sacado: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (AccountNotFoundException | NoFundsEnoughException e) {
            System.out.println(e.getMessage());
        }
    }

    // transferir valores entre contas
    private static void transferirEntreContas() {
        System.out.println(" 6 === Transferência entre contas ===");
        System.out.print("Informe a chave pix da conta de origem: ");
        var source = scanner.next();
        System.out.print("Informe a chave pix da conta de destino: ");
        var target = scanner.next();
        System.out.print("Valor a ser transferido: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.transferMoney(source, target, amount);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // investir valores em conta
    private static void investir() {
        System.out.println(" 7 === Investir ===");
        System.out.print("Informe a chave pix da conta para investimento: ");
        var pix = scanner.next();
        System.out.print("Informe o valor a ser investido: ");
        var amount = scanner.nextLong();
        try {
            investmentRepository.deposit(pix, amount);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // sacar valores investidos em uma conta
    private static void sacarInvestimento() {
        System.out.println(" 8 === Sacar investimento ===");
        System.out.print("Informe a chave pix da conta para resgate de investimento: ");
        var pix = scanner.next();
        System.out.print("Informe o valor a ser resgatado: ");
        var amount = scanner.nextLong();
        try {
            investmentRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // listar contas do banco
    private static void listarContas() {
        System.out.println("9 === Listar contas ===");
        accountRepository.list().forEach(System.out::println);
    }

    // listar investimentos de uma conta
    private static void listarInvestimentos() {
        System.out.println("10 === Listar investimentos ===");
        investmentRepository.list().forEach(System.out::println);
    }

    // listar carteiras de investimento
    private static void listarCarteirasDeInvestimentos() {
        System.out.println("11 === Listar carteiras de investimento ===");
        investmentRepository.listWallets().forEach(System.out::println);
    }

    // atualização (não-automática) do invesatimento
    private static void atualizarInvestimentos() {
        System.out.println("12 === Atualizar investimentos ===");
        investmentRepository.updateAmount();
        System.out.println("Investimentos reajustados!");
    }

    // extrato
    private static void historicoDeConta() {
        System.out.println("13 === Histórico de conta ===");
        System.out.print("Informe a chave pix da conta para verificar extrato: ");
        var pix = scanner.next();
        AccountWallet wallet;

        try {
            var sortedHistory = accountRepository.
            sortedHistory.forEach((k, v) -> {
                System.out.println(k.format(ISO_DATE_TIME));
                System.out.println(v.getFirst().transactionID());
                System.out.println(v.getFirst().description());
                System.out.println("R$" + (v.size()/100) + (v.size()%100));
            });
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // agradecimento e saída
    private static void sair() {
        System.out.println("14 === Sair ===");
        System.out.println("Obrigado por utilizar nosso software!");
        System.exit(EXIT_SUCCESS);
    }
}

