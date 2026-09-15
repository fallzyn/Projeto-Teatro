import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Teatro {
    private Vetor<Espetaculo> espetaculos;
    private Vetor<Reserva> reservas;

    public Teatro() {
        espetaculos = new Vetor<>(10);
        reservas = new Vetor<>(40);
    }

    public static void main(String[] args) throws Exception {
        Teatro teatro = new Teatro();
        Scanner ent = new Scanner(System.in);
        int opcao;

        do {
            System.out.println();
            System.out.println("===== TEATRO =====");
            System.out.println("1. Carregar espetáculos");
            System.out.println("2. Exibir espetáculos");
            System.out.println("3. Fazer reserva");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = ent.nextInt();
            ent.nextLine();

            if (opcao == 1) {
                teatro.carregarEspetaculos();
            } else if (opcao == 2) {
                if (teatro.espetaculos.isEmpty()) {
                    System.out.println("É necessário carregar os espetáculos primeiro.");
                } else {
                    teatro.exibirEspetaculos();
                }
            } else if (opcao == 3) {
                teatro.fazerReserva(ent);
            }
        } while (opcao != 7);

        System.out.println("Programa encerrado.");
        ent.close();
    }

    public void carregarEspetaculos() throws Exception {
        if (!espetaculos.isEmpty()) {
            System.out.println("Os espetáculos já foram carregados.");
            return;
        }

        try (BufferedReader data = new BufferedReader(new FileReader("espetaculos.txt"))) {
            String linha;
            while ((linha = data.readLine()) != null) {
                String[] dados = linha.split(";");
                Espetaculo espetaculo = new Espetaculo(
                    Integer.parseInt(dados[0]),
                    dados[1],
                    dados[2],
                    Double.parseDouble(dados[3])
                );
                espetaculos.add(espetaculos.size(), espetaculo);
            }
            System.out.println("Arquivo de dados carregado com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados não encontrado!");
        }
    }

    public void exibirEspetaculos() throws Exception {
        for (int i = 0; i < espetaculos.size(); i++) {
            Espetaculo espetaculo = espetaculos.get(i);
            System.out.println(espetaculo.getCodigo() + " - " + espetaculo.getNome()
                + " - " + espetaculo.getData() + " - R$ " + espetaculo.getPreco());
        }
    }

    public Espetaculo buscaEspetaculo(int codigo) throws Exception {
        for (int i = 0; i < espetaculos.size(); i++) {
            Espetaculo espetaculo = espetaculos.get(i);
            if (espetaculo.getCodigo() == codigo) {
                return espetaculo;
            }
        }
        return null;
    }

    public void fazerReserva(Scanner ent) throws Exception {
        if (espetaculos.isEmpty()) {
            System.out.println("É necessário carregar os espetáculos primeiro.");
            return;
        }

        System.out.print("Código do espetáculo: ");
        int codigo = ent.nextInt();
        Espetaculo espetaculo = buscaEspetaculo(codigo);
        if (espetaculo == null) {
            System.out.println("Espetáculo não encontrado!");
            return;
        }

        ent.nextLine();
        System.out.print("CPF: ");
        String cpf = ent.nextLine();
        System.out.print("Nome do comprador: ");
        String nome = ent.nextLine();
        System.out.print("Quantidade de ingressos (máximo 4): ");
        int quantidade = ent.nextInt();
        if (quantidade < 1 || quantidade > 4) {
            System.out.println("Quantidade inválida de ingressos!");
            return;
        }

        String[] assentos = new String[quantidade];
        char[][] mapa = espetaculo.getAssentos();
        for (int i = 0; i < quantidade; i++) {
            System.out.print("Digite o assento (exemplo: A8): ");
            String entrada = ent.next().toUpperCase();
            if (entrada.length() < 2) {
                System.out.println("Assento inválido!");
                i--;
                continue;
            }

            int linha = entrada.charAt(0) - 'A';
            int coluna;
            try {
                coluna = Integer.parseInt(entrada.substring(1)) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Assento inválido!");
                i--;
                continue;
            }

            if (linha < 0 || linha >= mapa.length || coluna < 0 || coluna >= mapa[0].length) {
                System.out.println("Assento inexistente!");
                i--;
            } else if (mapa[linha][coluna] != 'L') {
                System.out.println("Assento já ocupado!");
                i--;
            } else {
                mapa[linha][coluna] = 'X';
                assentos[i] = entrada;
            }
        }

        reservas.add(reservas.size(), new Reserva(cpf, nome, espetaculo, quantidade, assentos));
        System.out.println("Reserva realizada com sucesso!");
    }
}
