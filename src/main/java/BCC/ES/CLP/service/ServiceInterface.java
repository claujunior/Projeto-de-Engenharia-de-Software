package BCC.ES.CLP.service;

import java.util.List;

import BCC.ES.CLP.model.Alvo;

public interface ServiceInterface {
      List<Alvo> AllAlvos();
      void SalvarAlvo(Alvo a);
      void AtualizarAlvo(Alvo b);
      Alvo DeletarAlvo(Long id);
}
