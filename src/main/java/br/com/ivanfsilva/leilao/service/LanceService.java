package br.com.ivanfsilva.leilao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ivanfsilva.leilao.dto.NovoLanceDto;
import br.com.ivanfsilva.leilao.model.Lance;
import br.com.ivanfsilva.leilao.model.Leilao;
import br.com.ivanfsilva.leilao.model.Usuario;
import br.com.ivanfsilva.leilao.repositories.LanceRepository;
import br.com.ivanfsilva.leilao.repositories.LeilaoRepository;
import br.com.ivanfsilva.leilao.repositories.UsuarioRepository;

@Service
public class LanceService {

	@Autowired
	private LanceRepository lances;

	@Autowired
	private UsuarioRepository usuarios;

	@Autowired
	private LeilaoRepository leiloes;

	public boolean propoeLance(NovoLanceDto lanceDto, String nomeUsuario) {

		Usuario usuario = usuarios.getUserByUsername(nomeUsuario);
		Lance lance = lanceDto.toLance(usuario);

		Leilao leilao = this.getLeilao(lanceDto.getLeilaoId());

		if (leilao.propoe(lance)) {
			lances.save(lance);
			return true;
		}

		return false;
	}

	public Leilao getLeilao(Long leilaoId) {
		return leiloes.getOne(leilaoId);
	}

}