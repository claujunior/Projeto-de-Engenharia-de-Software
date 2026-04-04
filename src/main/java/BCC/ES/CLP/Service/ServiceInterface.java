package BCC.ES.CLP.Service;

import java.util.List;

import BCC.ES.CLP.Model.Alvo;

public interface ServiceInterface {
      List<Alvo> AllAlvos();
      void SalvarAlvo(Alvo a);
      void AtualizarAlvo(Alvo b);
      Alvo DeletarAlvo(Long id);
}
