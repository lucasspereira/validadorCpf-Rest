package validadorcpfrest.validadorcpfrest.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

import org.springframework.web.bind.annotation.PathVariable;

public class RepositorioUsuario {

	public boolean CpfValido(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public String checaArquivo(String CPF) {

		String arquivo = "listaCPF.txt";
		String listaCPF = "C:\\Users\\365NOTE06\\OneDrive - 365TI\\Desktop\\Lucas\\" + arquivo;
		File arq = new File(listaCPF);
		String linha;

		FileReader leitor = null;
		BufferedReader bufferDeArquivo = null;

		String retorno = "";
		try {

			leitor = new FileReader(arq);
			bufferDeArquivo = new BufferedReader(leitor);
			boolean existeCPF = false;
			while ((linha = bufferDeArquivo.readLine()) != null) {

				String[] array = linha.split(";");
				if (array[1].equals(CPF)) {
					existeCPF = true;
				}
			}
			if (existeCPF) {

				retorno = String.format("%s é um CPF válido e consta no TXT", CPF);
				System.out.println(retorno);
				return "200";
			} else {
				retorno = String.format("%s é um CPF válido porém NÃO consta no txt", CPF);
				return "204";
			}

		} catch (FileNotFoundException arqNotFound) {
			retorno = String.format("O CPF é válido porém o arquivo não existe");
			return "500";
		} catch (IOException e) {

			e.printStackTrace();
			return "504";
		} finally {
			try {
				if (leitor != null)
					leitor.close();
			} catch (IOException e) {

			}
			try {
				if (bufferDeArquivo != null)
					bufferDeArquivo.close();
			} catch (IOException e) {

			}

		}

	}

}