package BCC.ES.CLP.excepitons;

public class ErroAoEncontrarIp extends RuntimeException {

        public ErroAoEncontrarIp() {
            super("Esse Ip não existe no banco de dados");
        }

        public ErroAoEncontrarIp(String message) {
            super(message);
        }
}

