package validadorcpfrest.validadorcpfrest.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import validadorcpfrest.validadorcpfrest.repository.RepositorioUsuario;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class ControllerUsuario {
	JSONObject json = new JSONObject();
	RepositorioUsuario R = new RepositorioUsuario(); // classe repositorio

	@GetMapping(value = "/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getByCpf(@PathVariable String cpf) {

		if (R.CpfValido(cpf)) {
			String a = R.checaArquivo(cpf);
			if (a.equals("200")) {
				json.put("Retorno", "CPF Válido e encontrado no arquivo TXT");
				return ResponseEntity.status(HttpStatus.OK).body(json.toString());
			} else {
				json.put("Retorno", "CPF Válido e não encontrado no arquivo TXT");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json.toString());
			}

		}
		json.put("Retorno", "O CPF não é válido");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json.toString());

	}

	@GetMapping (value = "/telefone/{telefone}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getByTelefone(@PathVariable String telefone) {
		if (telefone == "celular") {
			json.put("Retorno", "Telefone celular");
			return ResponseEntity.status(HttpStatus.OK).body(json.toString());
		} if (telefone == "fixo") {
			json.put("Retorno", "Telefone fixo");
			return ResponseEntity.status(HttpStatus.OK).body(json.toString());
		}
		return null;
	}
}
