import java.util.Scanner;

public class Usuario {
    // Atributos para armazenar o nome de usuário e a senha corretos
    private String usuarioCorreto = "admin";
    private String senhaCorreta = "1234";

    // Método para realizar o login com até 3 tentativas
    public boolean realizarLogin(Scanner scanner) {
        int tentativas = 3;

        while (tentativas > 0) {
            System.out.print("Digite o nome de usuário: ");
            String usuarioDigitado = scanner.nextLine();

            System.out.print("Digite a senha: ");
            String senhaDigitada = scanner.nextLine();

            // Verifica se o usuário e a senha estão corretos
            if (usuarioDigitado.equals(usuarioCorreto) && senhaDigitada.equals(senhaCorreta)) {
                System.out.println("Login realizado com sucesso!\n");
                return true; // Retorna verdadeiro se acertar
            } else {
                tentativas--;
                if (tentativas > 0) {
                    System.out.println("Dados incorretos! Você ainda tem " + tentativas + " tentativa(s).\n");
                }
            }
        }

        // Se esgotar as 3 tentativas
        System.out.println("Você errou as 3 vezes. O programa será encerrado.");
        return false; // Retorna falso se esgotar as tentativas
    }
}