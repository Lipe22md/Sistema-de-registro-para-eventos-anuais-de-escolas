// PACOTE PRINCIPAL DA APLICAÇÃO
package br.com.unijorge.sistema_eventos;

// IMPORTA A CLASSE SPRINGAPPLICATION PARA INICIAR O SPRING BOOT
import org.springframework.boot.SpringApplication;

// IMPORTA A ANOTAÇÃO PRINCIPAL DO SPRING BOOT
import org.springframework.boot.autoconfigure.SpringBootApplication;

// DEFINE ESSA CLASSE COMO A CLASSE PRINCIPAL DA APLICAÇÃO SPRING BOOT
@SpringBootApplication
public class SistemaEventosApplication {

	// MÉTODO PRINCIPAL QUE INICIA A APLICAÇÃO
	public static void main(String[] args) {

		// EXECUTA A APLICAÇÃO SPRING BOOT
		SpringApplication.run(SistemaEventosApplication.class, args);
	}

}