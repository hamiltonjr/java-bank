package br.com.dio;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Money;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;
import java.util.Arrays;
import java.util.Scanner;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

/**
 * Classe principal do DIO Bank.
 * Permite interações com contas, investimentos e operações financeiras via console.
 */
public class Main {

    /** Scanner para entrada de dados pelo console */
    private static final Scanner scanner = new Scanner(System.in);

    /** Código de saída do sistema */
    private static final int EXIT_SUCCESS = 0;

    /** Repositório de contas */
    private static final AccountRepository accountRepository = new AccountRepository();

    /** Repositório de investimentos */
    private static final InvestmentRepository investmentRepository = new InvestmentRepository();

    /**
     * Método principal que inicializa o sistema bancário e exibe o menu.
     *
     * @param args Argumentos de linha de comando
     */
    public static void main(String[] args) {
        int option;

        System.out.println("Olá... seja bem-vindo ao DIO Bank!");
        while (true) {
            // Exibe menu de opções
            System.out.print(
                    " 1. Criar uma conta\n" +
                    " 2. Criar um investimento\n" +
                    " 3. Fazer um investimento\n" +
                    " 4. Depositar na conta\n" +
                    " 5. Sacar da comta\n" +
                    " 6. Transferência entre contas\n" +
                    " 7. Investir\n" +
                    " 8. Sacar investimento\n" +
                    " 9. Listar contas\n" +
                    "10. Listar investimentos\n" +
                    "11. Listar carteiras de investimento\n" +
                    "12. Atualizar investimentos\n" +
                    "13. Histórico de conta\n" +
                    "14. Sair\n" +
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

    /**
     * Cria uma conta bancária.
     * Solicita as chaves Pix e o valor inicial do depósito.
     */
    private static void criarConta() {
        System.out.println("1 === Criar uma conta ===");
        System.out.print("Informe as chaves Pix (separadas por ';'): ");
        var pix = Arrays.stream(scanner.next().split(";")).toList();
        System.out.print("Informe o valor inicial do depósito: ");
        var amount = scanner.nextLong();
        System.out.println("Conta criada: " + accountRepository.create(pix, amount));
    }

    /**
     * Cria um investimento.
     * Solicita a taxa de rendimento e o valor inicial do investimento.
     */
    private static void criarInvestimento() {
        System.out.println(" 2 === Criar um investimento ===");
        System.out.print("Informe a taxa de investimento: ");
        var tax = scanner.nextInt();
        System.out.print("Informe o valor inicial do depósito: ");
        var initialFunds = scanner.nextLong();
        System.out.println("Investimento criado: " + investmentRepository.create(tax, initialFunds));
    }

    /**
     * Inicializa um investimento a partir de uma conta e ID de investimento.
     */
    private static void fazerInvestimento() {
        System.out.println("3 === Fazer um investimento conta ===");
        System.out.print("Informe a chave pix da conta: ");
        var pix = scanner.next();
        System.out.print("Informe o ID do investimento:");
        var investmentID = scanner.nextInt();
        System.out.println("Conta de investimento criada: " +
                investmentRepository.initInvestment(
                        accountRepository.findByPix(pix),
                        investmentID
                )
        );
    }

    /**
     * Realiza depósito em uma conta.
     */
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

    /**
     * Realiza saque em uma conta.
     */
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

    /**
     * Realiza transferência entre contas.
     */
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

    /**
     * Deposita valores em investimento de uma conta.
     */
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

    /**
     * Realiza saque de investimento.
     */
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

    /**
     * Lista todas as contas do banco.
     */
    private static void listarContas() {
        System.out.println("9 === Listar contas ===");
        accountRepository.list().forEach(System.out::println);
    }

    /**
     * Lista todos os investimentos cadastrados.
     */
    private static void listarInvestimentos() {
        System.out.println("10 === Listar investimentos ===");
        investmentRepository.list().forEach(System.out::println);
    }

    /**
     * Lista todas as carteiras de investimento.
     */
    private static void listarCarteirasDeInvestimentos() {
        System.out.println("11 === Listar carteiras de investimento ===");
        investmentRepository.listWallets().forEach(System.out::println);
    }

    /**
     * Atualiza os investimentos (rendimento não automático).
     */
    private static void atualizarInvestimentos() {
        System.out.println("12 === Atualizar investimentos ===");
        investmentRepository.updateAmount();
        System.out.println("Investimentos reajustados!");
    }

    /**
     * Exibe o histórico financeiro de uma conta.
     */
    private static void historicoDeConta() {
        System.out.print("Informe a chave pix da conta para verificar extrato:");
        var pix = scanner.next();
        AccountWallet wallet;
        System.out.println("-----------------------------------------------");
        try {
            var sortedHistory = accountRepository.getHistory(pix);
            sortedHistory.forEach((k, v) -> {
                System.out.println(k.format(ISO_DATE_TIME));
                System.out.println(v.getFirst().transactionId());
                System.out.println(v.getFirst().description());
                System.out.println(Money.formatMoney(v.size()));
                System.out.println("-----------------------------------------------");
            });
        } catch (AccountNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Sai do sistema exibindo mensagem de agradecimento.
     */
    private static void sair() {
        System.out.println("14 === Sair ===");
        System.out.println("Obrigado por utilizar nosso software!");
        System.exit(EXIT_SUCCESS);
    }
}

## Estrutura do Projeto

